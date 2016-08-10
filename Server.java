import ecs100.*;

import java.awt.Color;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;


public class Server  {
	private int port;

	public ArrayList<ServerConnection> connections = new ArrayList<ServerConnection>();
	public ArrayList<Player> players = new ArrayList<Player>();

	public boolean readyToStart=false;
	public Board serverBoard = null;

	public Server (int port){
		//Emptying the old arrays;
		connections = new ArrayList<ServerConnection>();
		players = new ArrayList<Player>();

		this.port = port;
		UI.println("Server started:");

		try{ // Starting and listening for connections
			ServerSocket serverSocket = new ServerSocket(port);
			while(true){
					ServerConnection sc = new ServerConnection(serverSocket.accept(),this);
					connections.add(sc);
					UI.println("server connection accepted.");
			}

		} catch (IOException e){
			UI.println("Exception: " + e);
		}
	}

}
