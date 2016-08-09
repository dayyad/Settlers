import java.awt.Color;
import java.io.Serializable;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import ecs100.*;

public class Player implements Serializable{
	String[] tileTypes = {"grain","ore","brick","wood","sheep","sea"}; 
	
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
		for(int i =0; i < 5;i++){
			UI.setColor(Color.black);
			UI.drawString(tileTypes[i] + ": "+inv.get(tileTypes[i]),i*20, 0);
		}
	}
}
