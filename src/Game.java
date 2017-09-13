package src;
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

	private Client client;
	private Server server;

	public Game (){
		UI.initialise();
		UI.clearGraphics();
		//UI.setImmediateRepaint(false);

		UI.addButton("Connect to Server", this::connectServer);
		UI.addButton("Create Server", this::createServer);
		//UI.addButton("Start game", this::startGame);
		UI.setMouseMotionListener(this::doMouse);
	}

	/**
	 * creates a new Server on the current computer with an inputted port number
     */
	private void createServer(){
		Server server = new Server(UI.askInt("Port: "));
	}

	private void doMouse(String action,double x, double y){
		if(Client.getInstance() != null){
			if(action.equals("pressed")){
				//Sends click packet to server
				Packet p = new Packet("server",0);
				p.setClick(x, y);
				client.sendPacket(p);
				UI.println("Mouse pressed: ");
			}
		}
	}

	private void connectServer(){
		client = Client.getInstance();
	        /*# YOUR CODE HERE */
	        try {
	            Socket socket = new Socket(UI.askString("IP address"),UI.askInt("Port: ")); //Creates connection to socket
				client = Client.getInstance(socket);
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	}


	 public static void main(String[] args) {
			Game game = new Game();
		}
}
