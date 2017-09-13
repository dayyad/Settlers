package src;
import ecs100.UI;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by root on 21/08/16.
 */
public class Client {
    private static Client instance = null;

    public static Client getInstance(){
        return instance;
    }

    public static Client getInstance(Socket socket){
        instance = new Client(socket);
        return getInstance();
    }

    //Object IO for catching objects
    private ObjectOutputStream outputStream;

    private Board clientBoard = null;
    private Player clientPlayer = null;

    private boolean playing = false;

    private Listener listener;

    private Client(Socket socket){
        clientPlayer = new Player();
        try {
            listener = new Listener(new ObjectInputStream(socket.getInputStream()));
            outputStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        listener.start();
    }

    /**
     * Draws the Clients board
     */
    private void draw(){
        if(clientBoard!=null){
            clientBoard.draw();
        }

        UI.repaintGraphics();
    }

    /**
     * Thread that listenes to the server
     */
    public class Listener extends Thread {  //Waits for server and adds to the in queue

        private ObjectInputStream fromServer;
        private boolean listening = true;

        public Listener(ObjectInputStream fromServer) {
            this.fromServer = fromServer;
        }

        public void stopListening(){this.listening = false;}

        /**
         * goes in to an infinate loop looking for packates then drawing them
         */
        public void run(){
            //On connection happens here!
            UI.println("Listener Started: ");
            while (listening) {
                try {
                    Packet p = (Packet) fromServer.readObject();
                    handlePacket(p);
                } catch(IOException e){
                    System.out.println("Connection Temporarily failed...");
                    e.printStackTrace();
                } catch(ClassNotFoundException e){
                    e.printStackTrace();
                }
            }
        }

        /**
         * draws the board from the passed package
         * @param p
         */
        private void handlePacket(Packet p){
            if(p!=null){
                if(p.board!=null){
                    if(!p.board.equals(clientBoard))
                        clientBoard=p.board;
                    draw(); //Only redraws if change detected
                }
                if(!clientPlayer.getInv().equals(p.getInv())){
                    clientPlayer.setInv(p.getInv());
                    draw(); //Only redraws if change detected
                }
            }
        }
    }

    public boolean sendPacket(Packet p){
        try {
            p.setFromId(clientPlayer.id);
            outputStream.writeObject(p);
            outputStream.flush();
        } catch (IOException e) {
            return false; //communication falied
        }
        return true;
    }
}
