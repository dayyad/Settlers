import ecs100.*;

import java.awt.List;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {
	//TODO check on the player connecting properly
	//TODO transfer board to player when its created and draw it
	//TODO make sure server operator ccan start the server properly
	//TODO work on server updating the clients player every time they click and manage the click server side.


	private boolean connected = false;
	private Socket socket = null;

	private Listener listener;
	private Sender sender;

	public Packet outPack = new Packet("server",0);

	//Object IO for catching objects
	private ObjectInputStream objectI;
	private ObjectOutputStream objectO;

	private Board clientBoard = null;
	private Player clientPlayer = null;

	private boolean playing = false;

	private int port;
	private String ip;

	public Game (){
		UI.initialise();
		UI.clearGraphics();
		//UI.setImmediateRepaint(false);

		UI.addButton("Connect to Server", this::connectServer);
		UI.addButton("Create Server", this::createServer);
		UI.setMouseListener(this::doMouse);
	}

	private void createServer(){
		Server server = new Server(UI.askInt("Port: "));
	}

	private void doMouse(String action,double x, double y){

		if(socket!=null){
			if(action.equals("pressed")){
				//Sends click packet to server
				Packet p = new Packet("server",0);
				p.setClick(x, y);
				sender.addToQue(p);
				UI.println("Mouse pressed: ");
			}
		}
	}

	private void draw(){
		if(clientBoard!=null){
			clientBoard.draw();
		}

		if(clientBoard!=null){
			clientPlayer.draw();
		}
		UI.repaintGraphics();
	}

	private void connectServer(){
	        /*# YOUR CODE HERE */
	        try {
	            socket = null;
	            socket = new Socket(UI.askString("IP address"),UI.askInt("Port: ")); //Creates connection to socket

	            clientPlayer= new Player();
	            listener = new Listener(this);
	            sender = new Sender(this);
	            listener.start();
	            sender.start();

	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	}

	public class Listener extends Thread {  //Waits for server and adds to the in queue
		Game g;

		public Listener(Game game) {
			this.g=game;
		}

		public void run(){
			//On connection happens here!
			UI.println("Listener Started: ");
			connected = true;
			try {

				ObjectInputStream objI = new ObjectInputStream(socket.getInputStream());

				while(socket!=null){
					Packet p = (Packet)objI.readObject();
					handlePacket(p);
					draw();
					System.out.println("s");
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		private void handlePacket(Packet p){
			if(p!=null){

			}
		}
	}

	public class Sender extends Thread{
		private ArrayList<Packet> packQue = new ArrayList<Packet>();
		Game g;
		public Sender(Game g){
			this.g=g;
			outPack.setFromId(g.clientPlayer.id);
		}

		public void addToQue(Packet p){
			packQue.add(p);
		}

		public void run(){

			try {
				ObjectOutputStream objO = new ObjectOutputStream(socket.getOutputStream());
				UI.println("Client output created.");
				while(true){
					if(packQue.size()>0){
						UI.println("Trying to send from que.");
						Packet p = packQue.get(0);
						p.setFromId(clientPlayer.id);
						objO.writeObject(p);
						packQue.remove(0);
						UI.println("Pack sent.");
					}
					UI.sleep(1);
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	 public static void main(String[] args) {
			Game game = new Game();
		}
}
