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
	
	//Object IO for catching objects
	private ObjectInputStream objectInput;
	private ObjectOutputStream objectOutput;
	
	private Board clientBoard;
	private Player clientPlayer;
	
	private boolean playing = false;
	
	private int port;
	private String ip;
	
	
	
	
	public Game (){
		UI.initialise();
		UI.clearGraphics();
		UI.setImmediateRepaint(false);
		
		UI.addButton("Connect to Server", this::connectServer);
		UI.addButton("Create Server", this::createServer);
		UI.setMouseListener(this::doMouse);
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
	        	//On connection happens here!
	        	UI.println("Listener Started: ");
	        	connected = true;
	            while(true){
	                if(scanner.hasNextLine()){
	                    String line = scanner.nextLine();
	                    try {
	                    	if(line.equals("board")){ // In case of board being passed
	                    		clientBoard=(Board)(objectInput.readObject());
		                    } else if (line.equals("setPlayer")){
		                    	clientPlayer=(Player)(objectInput.readObject());
		                    }
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
	                    
	                }
	            }
	        }
	    }
	
	private void doMouse(String action,double x, double y){
		if(playing && connected){
			if(action.equals("pressed")){
				
			}
		}
		
		draw();
	} 
	
	private void draw(){
		clientBoard.draw();
		clientPlayer.draw();
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
