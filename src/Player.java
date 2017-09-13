package src;
import java.awt.Color;
import java.io.Serializable;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import ecs100.*;

public class Player implements Serializable{
	private static final long serialVersionUID = -7915690137851055604L;

	String[] tileTypes = {"grain","ore","brick","wood","sheep","sea"};

	public Socket socket; //is this needed??
	private static int lastId =0;
	public int id;

	private Map<String,Integer> inv = new HashMap<String, Integer>();

	public Player(){
		id = lastId++;
		//brick,ore,grain,sheep,wood
		inv.put("brick", 0);inv.put("ore", 0);inv.put("grain", 0);inv.put("sheep", 0);inv.put("wood", 0);

	}
	
	public void addToInv(String type,int count){
		inv.put(type,inv.get(type)+count);
		UI.println("added "+count+ " to:" + type);
	}
	
	public void setInv(Map<String,Integer> inv){
		this.inv = inv;
	}

	public void draw(){
		for(int i =0; i < 5;i++){
			UI.setFontSize(20);
			UI.setColor(Color.black);
			UI.drawString(tileTypes[i] + ": "+inv.get(tileTypes[i]),i*150, 30);
		}
	}

	public Map<String,Integer> getInv(){
		return this.inv;
	}
}
