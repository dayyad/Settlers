import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import ecs100.UI;

public class ServerConnection extends Thread {
	private Socket socket;
	private Server server;

	public ServerConnection(Socket s,Server ss){
		socket=s;
		server=ss;
	}

	public void run(){
		try {
			UI.println("attemptiung to start a server connection");

			ObjectOutputStream objO = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream objI = new ObjectInputStream(socket.getInputStream());
			while(true){
				Packet pOut = new Packet("player",null,server.serverBoard,null);
				objO.writeObject(pOut);
				Packet p = (Packet)objI.readObject();
				handlePacket(p);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void handlePacket(Packet p){
		if(p !=null){
			if (p.click!=null){
				UI.println("Click recieved at: "+p.click.x + " " + p.click.y);
			}
			if(p.to.equals("server")){
				UI.println("packet was meant for server.");
			}
		}
	}

	private void startGame(){
		if(server.readyToStart){
			server.serverBoard = new Board(30+UI.askInt("Board width: "),UI.askInt("Board height: "));
			server.serverBoard.draw();
			UI.repaintGraphics();
		}
	}

}
