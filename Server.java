import ecs100.*;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Server {
	private int port;
	
	
	public Server (){
		port = UI.askInt("Enter Port:");
		run();
	}
	
	private void run()  {
		try{
			ServerSocket serverSocket = new ServerSocket(port);
			Socket socket = serverSocket.accept();
			Scanner scanner = new Scanner(socket.getInputStream());
			
			
			
			
		} catch (IOException e){
			UI.println("Exception: " + e);
		}
	}
}
