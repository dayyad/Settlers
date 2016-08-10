import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import ecs100.*;

public class Tile implements Serializable {
	private static final long serialVersionUID = 7638123221279651945L;
	private double x;
	private double y;
	private double width;
	private double height;
	public String type;

	public Tile(String type,double x,double y,double w, double h){
		this.x=x;
		this.y=y;
		this.width=w;
		this.height=h;
		this.type = type;
	}

	public void draw(){
		UI.drawImage("./assets/hex_"+type+".png",x,y,width,height);
	}
}
