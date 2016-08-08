import java.util.HashMap;
import java.util.Map;

import ecs100.*;

public class Player {
	
	private static int lastId =1;
	public int id;
	
	private Map<String,Integer> inv = new HashMap<String, Integer>();
	
	public Player(){
		id = lastId++;
	}
}
