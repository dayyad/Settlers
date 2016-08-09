import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import ecs100.*;

public class Tile implements Serializable {
	private static final long serialVersionUID = 7638123221279651945L;
	private double x;
	private double y;
	public String type;

	public Tile(String type,double x,double y){
		this.x=x;
		this.y=y;
		this.type = type;
	}

	public void draw(){
		UI.drawImage("hex_"+type+".png",x,y);
	}
}
