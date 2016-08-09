import java.io.Serializable;

public class Packet implements Serializable{
	private static final long serialVersionUID = 9108779363102530646L;

	public String to = "";

	public Player player = null;
	public Board board = null;
	public Click click = null;

	public Packet(String to,Player p,Board b,Click c){
		this.to=to;
		player = p;
		board=b;
		click=c;
	}

}
