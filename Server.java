import ecs100.*;

import java.awt.Color;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;


public class Server  {
	private int port;
	
	private ArrayList<Connection> connections = new ArrayList<Connection>();
	private ArrayList<Player> players = new ArrayList<Player>();
	
	private boolean readyToStart=false;
	private Board serverBoard = null;
	
	public Server (int port){
		//Emptying the old arrays;
		connections = new ArrayList<Connection>();
		players = new ArrayList<Player>();
		
		this.port = port;
		UI.println("Server started:");
		
		try{
			ServerSocket serverSocket = new ServerSocket(port);	
			while(true){
				
				Socket socket = serverSocket.accept();
				Connection c = new Connection(socket,connections.size()+1);
				connections.add(c);
				c.start();
				
				UI.clearGraphics();
				UI.setColor(Color.red);
				UI.setFontSize(20);
				UI.drawString(Integer.toString(connections.size()), 0, 0);
				UI.repaintAllGraphics();
				if(connections.size()>=2){
					readyToStart=true;
				} else {
					readyToStart=false;
				}
				if(readyToStart){
					UI.addButton("Start Game", this::startGame);
				}
				UI.println("got to here");
			}
			
		} catch (IOException e){
			UI.println("Exception: " + e);
		}
	}
	
	public class Connection extends Thread { // Thread for each client
		private Socket s;
		private boolean stayConnected = true;
		private int id;
		
		public Connection(Socket s,int id){
			this.s=s;
			this.id=id;
		}
		
		public void run(){
			Player player = new Player(s);
			UI.println("New connection established.");
			
			try {
				
//				objectI = new ObjectInputStream(s.getInputStream());
//				objectO = new ObjectOutputStream(s.getOutputStream());
				while(stayConnected){ //Main connection loop
					UI.println("connected");
					
					ObjectInputStream objectI = new ObjectInputStream(s.getInputStream());
					ObjectOutputStream objectO = new ObjectOutputStream(s.getOutputStream());
					
					try { // DO all incoming reading in here.
						Object obj = objectI.readObject();
						if(obj instanceof Click){
							UI.println("Click recieved");
						}
						
						
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					 //All the outbound packets here
	            	if(serverBoard!= null){
	            		readyToStart=false;
	            		objectO.writeObject(serverBoard);
	            	}
	            	
	            	objectO.writeObject(players.get(id));
	            	
	            	
	            	objectO.close();
	            	objectI.close();
	                UI.sleep(1);
				}	
				
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	private void processClick(Click click){
		UI.println("Click from: "+click.player.id);
	}	
	
	private void startGame(){
		if(readyToStart){
			serverBoard = new Board(30+UI.askInt("Board width: "),UI.askInt("Board height: "));
			serverBoard.draw();
			UI.repaintGraphics();
		}
	}

}
