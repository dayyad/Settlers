import ecs100.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;


public class Server  {
	private int port;
	private ArrayList<Connection> connections = new ArrayList<Connection>();
	
	public Server (int port){
		this.port = port;
		
		try{
			while(true){
				ServerSocket serverSocket = new ServerSocket(port);	
				Socket socket = serverSocket.accept();
				connections.add(new Connection());
				connections.get(connections.size()-1).run();
			}
			
			
		} catch (IOException e){
			UI.println("Exception: " + e);
		}
	}
	
	public class Connection extends Thread {
		private Socket s;
		public Connection(Socket s){
			this.s=s;
		}
		
		public void run(){
            while(true){
            	Scanner scanner = new Scanner(s.getInputStream());
            	if(scanner.hasNextLine()){
            		UI.println(scanner.nextLine());
            	}
                UI.sleep(1);
            }
		}
	}

}
