import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import ecs100.UI;

public class ServerConnection {
	private Socket socket;
	private Server server;
	private int connectionId = 0;
	private Packet outPack = new Packet("client",0);

	private Sender sender;
	private Listener listener;

	public ServerConnection(Socket s,Server ss){
		socket=s;
		server=ss;
		sender=new Sender();
		listener=new Listener();
		listener.start();
		sender.start();
		UI.initialise();

	}

	public class Sender extends Thread{
		public Sender(){}

		public void run(){
			try {
				ObjectOutputStream objO = new ObjectOutputStream(socket.getOutputStream());
				while(true){
					objO.writeObject(outPack);
					outPack.clear();
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public class Listener extends Thread{
		public Listener(){}

		public void run(){
			try {
				UI.println("Server: attemptiung to start a server connection");
				ObjectInputStream objI = new ObjectInputStream(socket.getInputStream());
				UI.println("Server: Started input stream.");
				while(true){
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
	}

	private void handlePacket(Packet p){
		if(p !=null){
			if(connectionId==0){
				connectionId=p.fromId;
				UI.println("connection id set to : "+ connectionId);
			}

			if(p.click!=null){ //TODO FIX THIS IT IS ALWAYS NULL.
				double x = p.click.get("x");
				double y = p.click.get("y");
				UI.setColor(Color.red);
				UI.print("Click at: " + p.click.get("x") + " " + p.click.get("y"));
				UI.drawOval(x, y, 10, 10);
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
