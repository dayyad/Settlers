import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Packet implements Serializable{
	private static final long serialVersionUID = 9108779363102530646L;

	public String to = "";
	public int fromId;
	public Map<String,Integer> inv = new HashMap<String,Integer>();
	public Map<String,Double> click = new HashMap<String,Double>();
	public Board board;

	public Packet(String to,int fromId){

		this.inv = null;
		this.click=null;
		this.fromId = fromId;
		this.to=to;
		this.board=null;

	}

	public void setBoard(Board board){
		this.board=board;
	}

	public void setInv(Map<String,Integer> inv){
		if(inv!=null){
			this.inv.clear();
			this.inv.put("ore",inv.get("ore"));
			this.inv.put("grain",inv.get("grain"));
			this.inv.put("brick",inv.get("brick"));
			this.inv.put("sheep",inv.get("sheep"));
			this.inv.put("wood",inv.get("wood"));
		} else {
			inv = new HashMap<String,Integer>();
			this.inv.put("ore",inv.get("ore"));
			this.inv.put("grain",inv.get("grain"));
			this.inv.put("brick",inv.get("brick"));
			this.inv.put("sheep",inv.get("sheep"));
			this.inv.put("wood",inv.get("wood"));
		}
	}

	public void setFromId(int id){
		this.fromId=id;
	}

	public void setClick(double x, double y){
		if(click!=null){
			this.click.clear();
			this.click.put("x", x);
			this.click.put("y",y);
		} else {
			click = new HashMap<String,Double>();
			click.put("x", x);
			click.put("y", y);
		}
		System.out.println("x: " +x + " y:"+y);
	}

	public void clear(){
		Map<String,Integer> inv = new HashMap<String,Integer>();
		Map<String,Double> click = new HashMap<String,Double>();
	}

}
