import java.awt.Color;
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
	private final int prob;
	public String type;

	public Tile(String type,double x,double y,double w, double h){
		this.x=x;
		this.y=y;
		this.width=w;
		this.height=h;
		this.type = type;
		this.prob = (int)(Math.random()*10+2);
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getProb() {
		return prob;
	}

	public void draw(){
		UI.drawImage("./assets/hex_"+type+".png",x,y,width,height);
		UI.setColor(Color.red);
		if(!type.equals("sea")){
			UI.setFontSize(30);
			UI.drawString(Integer.toString(prob), x + width/2, y + height/2);
		}
	}
}
