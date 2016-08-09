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
				Connection c = new Connection(socket);
				connections.add(c);
				c.start();
				
				UI.clearGraphics();
				UI.setColor(Color.red);
				UI.drawString(Integer.toString(connections.size()), 0, 0);
				if(connections.size()>=2){
					readyToStart=true;
				} else {
					readyToStart=false;
				}
				if(readyToStart){
					UI.addButton("Start Game", this::startGame);
				}
			}
			
		} catch (IOException e){
			UI.println("Exception: " + e);
		}
	}
	
	public class Connection extends Thread { // Thread for each client
		private Socket s;
		private boolean stayConnected = true;
		
		public Connection(Socket s){
			this.s=s;
		}
		
		public void run(){
			Player player = new Player(s);
			UI.println("New connection established.");
			
			Scanner scanner;
        	ObjectInputStream objectInput;
			ObjectOutputStream objectOutput;
			try {
				scanner = new Scanner(s.getInputStream());
				
				while(stayConnected){ //Main connection loop
					UI.println("connected");
	            	
	            	if(scanner.hasNextLine()){
	            		String line = scanner.nextLine();
	            		UI.println(line);
	            		if(line.equals("click")){
	            			UI.println("Click recieved");
	            		}
	            	}
	                UI.sleep(1);
				}	
				scanner.close();
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	private void startGame(){
		if(readyToStart){
			Board board = new Board(30+UI.askInt("Board width: "),UI.askInt("Board height: "));
			 
		}
	}

}
