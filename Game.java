import ecs100.*;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Game {
	
	private boolean connected = false;
	private Socket socket;
	private Scanner scanner;
	private PrintStream PS;
	private Listener listener;
	
	private int port;
	private String ip;
	
	
	
	
	public Game (){
		UI.initialise();
		UI.clearGraphics();
		UI.setImmediateRepaint(false);
		
		UI.addButton("Connect to Server", this::connectServer);
		UI.addButton("Create Server", this::createServer);
	}
	
	private void connectServer(){
	        /*# YOUR CODE HERE */
	        try {
	            socket = null;

	            socket = new Socket(UI.askString("IP address"),UI.askInt("Port: ")); //Creates connection to socket
	            scanner=new Scanner(socket.getInputStream());
	            PS = new PrintStream(socket.getOutputStream());
	            
	            UI.addButton("Send meme", this::sendMeme);
	            listener = new Listener();
	            listener.run();
	           
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	    
	}
	
	 public class Listener extends Thread {  //Waits for server and adds to the in queue
	        @Override
	        public void run(){
	        	UI.println("Listener Started: ");
	            while(true){
	                if(scanner.hasNextLine()){
	                    String line = scanner.nextLine();
                 
	                }
	            }
	        }
	    }
	 
	private void sendMeme(){
		PS.println("FUCK");
		UI.println("Attempted to send a fuck");
	}
	
	private void createServer(){
		Server server = new Server(UI.askInt("Port: "));
	}
	

	public static void main(String[] args) {
		Game game = new Game();
	}

}
