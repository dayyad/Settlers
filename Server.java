import ecs100.*;
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
			while(true){
				ServerSocket serverSocket = new ServerSocket(port);	
				Socket socket = serverSocket.accept();
				connections.add(new Connection(socket));
				connections.get(connections.size()-1).run();
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
	
	public class Connection extends Thread {
		private Socket s;
		public Connection(Socket s){
			this.s=s;
			UI.println("New connection established.");
		}
		
		public void run(){
			Player player = new Player(s);
			
            while(true){
            	Scanner scanner;
            	ObjectInputStream objectInput;
				ObjectOutputStream objectOutput;
            	
				try {
					//Creates object and scanners IO for each client
					objectInput=new ObjectInputStream(s.getInputStream());
					objectOutput=new ObjectOutputStream(s.getOutputStream());
					scanner = new Scanner(s.getInputStream());
					
	            	if(scanner.hasNextLine()){
	            		UI.println(scanner.nextLine());
	            	}
	                UI.sleep(1);
	                
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
		}
	}
	
	private void startGame(){
		if(readyToStart){
			Board board = new Board(UI.askInt("Board width: "),UI.askInt("Board height: "));
			 
		}
	}

}
