import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import ecs100.UI;

public class ServerConnection {
	private Socket socket;
	private Server server;
	private final int connectionId;
	private static int lastId =0;
	private Packet outPack = new Packet("client",0);
	private Player player;

	private Sender sender;
	private Listener listener;

	public ServerConnection(Socket s,Server ss){
		player=new Player();
		socket=s;
		server=ss;
		sender=new Sender();
		listener=new Listener();
		listener.start();
		sender.start();
		connectionId = lastId++;

	}
	
	public Player getPlayer(){
		return player;
	}

	/**
	 * Thread that sends packates to the client
	 * once again this is not nessecary as there can be a individual sendPackate method
     */
	public class Sender extends Thread{
		public Sender(){}

		public void run(){
			try {
				ObjectOutputStream objO = new ObjectOutputStream(socket.getOutputStream());
				while(true){
					outPack.setBoard(server.serverBoard);
					outPack.setInv(player.getInv());
					objO.writeObject(outPack);
					outPack.clear();
					UI.sleep(100); //Pauses to optimize program a little.
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Thread that listenes to the client
     */
	public class Listener extends Thread{
		public Listener(){}

		public void run(){
			try {
				UI.println("Server: attemptiung to start a server connection");
				ObjectInputStream objI = new ObjectInputStream(socket.getInputStream());
				UI.println("Server: Started input stream.");
				while(true){
					Packet p = (Packet)objI.readObject();
					handlePacket(p);
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private double lastX =0;
	private double lastY =0;

	private void handlePacket(Packet p){
		if(p !=null){

			if(p.click!=null){ //TODO FIX THIS IT IS ALWAYS NULL.
				double x = p.click.get("x");
				double y = p.click.get("y");
				UI.println("Click at: " + p.click.get("x") + " " + p.click.get("y"));
				UI.setColor(Color.white);
				UI.fillOval(lastX, lastY, 20, 20);
				UI.setColor(Color.red);
				UI.fillOval(x, y, 20, 20);
				UI.repaintGraphics();
				lastX=x;
				lastY=y;
				
				String clickHit = server.serverBoard.on(x,y);
				if(clickHit!=null && !clickHit.equals("sea")){
					UI.println("Click on: "+clickHit);
					player.addToInv(clickHit, 1);
				}
			}
		}
	}

	/**
	 * Starts the game on the server
      */
	private void startGame(){
		if(server.readyToStart){
			server.serverBoard = new Board(30+UI.askInt("Board width: "),UI.askInt("Board height: "));
			server.serverBoard.draw();
			UI.repaintGraphics();
		}
	}

}
