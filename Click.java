import java.io.Serializable;

public class Click implements Serializable {
	private static final long serialVersionUID = -9202034157612768663L;
	public double x;
	public double y;
	public Player player;

	public Click(double x,double y,Player p){
		this.x=x;
		this.y=y;
		this.player=p;

	}
}
