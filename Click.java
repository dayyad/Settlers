import java.io.Serializable;

public class Click implements Serializable {
	public double x;
	public double y;
	public Player player;
	
	public Click(double x,double y,Player p){
		this.x=x;
		this.y=y;
		this.player=p;
		
	}
}
