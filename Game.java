import ecs100.*;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Game {
	//TODO check on the player connecting properly
	//TODO transfer board to player when its created and draw it 
	//TODO make sure server operator ccan start the server properly 
	//TODO work on server updating the clients player every time they click and manage the click server side.
	
	
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
	            objectInput = new ObjectInputStream(socket.getInputStream());
	            objectOutput = new ObjectOutputStream(socket.getOutputStream());
	            
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
		                    	updatePlayer((Player)(objectInput.readObject()));
		                    }
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
	                    
	                    draw();
	                }
	            }
	        }
	    }
	
	private void doMouse(String action,double x, double y){
		UI.println("Mouse pressed");
		if(playing && connected){
			if(action.equals("pressed")){
				//Sends click packet to server
				Click click = new Click(x,y);
				UI.println("Sending click");
				PS.println("click");
				try {
					objectOutput.writeObject(click);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		draw();
	} 
	
	private void updatePlayer(Player p){
		clientPlayer=p;
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
