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
	private Scanner scanner;
	private PrintStream PS;
	private Listener listener;
	
	private ArrayList<Object> outQ = new ArrayList<Object>();
	
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
	            
	            listener = new Listener();
	            listener.start();
	           
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	    
	}
	
	 public class Listener extends Thread {  //Waits for server and adds to the in queue
	        public void Listener(){
	        	
	        }
	        
	        public void run(){
	        	//On connection happens here!
	        	UI.println("Listener Started: ");
	        	connected = true;
	            while(socket!=null){
	            	
	            	
	            	try {//Creates a generic object that can be matched against all possible cases later on.
	            		objectI = new ObjectInputStream(socket.getInputStream());
		            	
	            		
						Object obj = (Object)objectI.readObject();
						
						if(obj instanceof Player){
							//UI.println("Player recieved");
							clientPlayer=(Player)obj;
						} else if(obj instanceof Board){
							//UI.println("Board recieved");
							clientBoard = (Board) obj;
						}
						
						objectI.close();
						
					} catch (ClassNotFoundException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            	
	            	
					try {
						objectO = new ObjectOutputStream(socket.getOutputStream());
						while(outQ.size()!=0){
							objectO.writeObject(outQ.remove(outQ.size()-1));
							objectO.flush();
						}
		            	objectO.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
						
				}
	            	
	            	
	            	draw();
	            }
	        }
	    
	
	private void doMouse(String action,double x, double y){
		UI.println("Mouse pressed");
		if(socket!=null){
			if(action.equals("pressed")){
				//Sends click packet to server
				Click click = new Click(x,y,this.clientPlayer);
				UI.println("Sending click");
				try {
	            	objectO = new ObjectOutputStream(socket.getOutputStream());
					outQ.add(click);
				} catch (IOException e) {
					// TODO Auto-generated catch block
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
		if(clientBoard!=null){
			clientBoard.draw();
		}
		
		if(clientBoard!=null){
			clientPlayer.draw();
		}
		UI.repaintGraphics();
	}
	 
	private void sendMeme(){
		UI.println("Attempted to send a fuck");
	}
	
	private void createServer(){
		Server server = new Server(UI.askInt("Port: "));
	}
	

	public static void main(String[] args) {
		Game game = new Game();
	}

}
