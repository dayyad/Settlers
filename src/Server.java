package src;
import ecs100.*;

import java.awt.Color;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;


public class Server  {
	private int port;

	public ArrayList<ServerConnection> connections = new ArrayList<ServerConnection>();
	public ArrayList<Player> players = new ArrayList<Player>();

	public boolean readyToStart=false;
	public Board serverBoard = null;

	public Server (int port){
		//Emptying the old arrays;
		serverBoard=new Board(UI.askInt("Board width: "),UI.askInt("Board height: "));
		connections = new ArrayList<ServerConnection>();
		players = new ArrayList<Player>();

		this.port = port;
		UI.println("Server started:");

		try{ // Starting and listening for connections
			ServerSocket serverSocket = new ServerSocket(port);
			while(true){
					ServerConnection sc = new ServerConnection(serverSocket.accept(),this);
					connections.add(sc);
					players.add(sc.getPlayer());
					UI.println("server connection accepted.");
			}

		} catch (IOException e){
			UI.println("Exception: " + e);
		}
	}

	public void addPlayer(Player p){

	}
}
