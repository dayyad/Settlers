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
			
			Scanner scanner;
			PrintStream PS;
        	ObjectInputStream objectI;
			ObjectOutputStream objectO;
			try {
				
//				objectI = new ObjectInputStream(s.getInputStream());
//				objectO = new ObjectOutputStream(s.getOutputStream());
				while(stayConnected){ //Main connection loop
					UI.println("connected");
					
					PS = new PrintStream(s.getOutputStream());
					scanner = new Scanner(s.getInputStream());
	            	
					boolean followedByObject = false;
					String followingObject;
					
	            	if(scanner.hasNextLine()){
	            		String line = scanner.nextLine();
	            		UI.println("Server: Recieved: " + line);
	            		if(line.equals("click")){
	            			objectI = new ObjectInputStream(s.getInputStream());
	            			processClick((Click)objectI.readObject());
	            		} else if (line.equals("")){
	            			
	            		}
	            	}
	            	
	            	if(serverBoard!= null){
	            		readyToStart=false;
	            		PS.println("board");
	            	}
	            	
	            	PS.println("player");
	            	
	                UI.sleep(1);
	                scanner.close();
	                PS.close();
				}	
				
				
			} catch (IOException | ClassNotFoundException e1) {
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
		}
	}

}
