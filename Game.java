import ecs100.*;
import java.io.*;
import java.net.*;

public class Game {
	
	public Game (){
		UI.initialise();
		UI.clearGraphics();
		UI.setImmediateRepaint(false);
		
		UI.addButton("Connect to Server", this::connectServer);
		UI.addButton("Create Server", this::createServer);
	}
	
	private void connectServer(){
		
	}
	
	private void createServer(){
		
	}
	

	public static void main(String[] args) {
		
	}

}
