import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import ecs100.*;

public class Player {
	
	public Socket socket;
	private static int lastId =1;
	public int id;
	
	private Map<String,Integer> inv = new HashMap<String, Integer>();
	
	public Player(Socket s){
		
		socket = s;
		id = lastId++;
		//brick,ore,grain,sheep,wood
		inv.put("brick", 0);inv.put("ore", 0);inv.put("grain", 0);inv.put("sheep", 0);inv.put("wood", 0);
	
	}
	
	public void draw(){
		
	}
}
