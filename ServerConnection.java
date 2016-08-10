import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import ecs100.UI;

public class ServerConnection extends Thread {
	private Socket socket;
	private Server server;
	private int connectionId = 0;
	private Packet outPack = new Packet("client",0);

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
				objO.writeObject(outPack);
				outPack.clear();
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
			if(connectionId==0){
				connectionId=p.fromId;
				UI.println("connection id set to : "+ connectionId);
			}

			if(p.click!=null){ //TODO FIX THIS IT IS ALWAYS NULL.
				UI.print("Click at: " + p.click.get("x") + " " + p.click.get("y"));
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
