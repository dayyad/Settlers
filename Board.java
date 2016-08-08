import ecs100.*;

public class Board {
	int width;
	int height;
	
	double tileWidth = 50;
	double tileHeight = 50 ;
	
	String[] tileTypes = {"grain","ore","brick","wood","sheep","sea"}; 
	Tile[][] board;
	
	
	public Board(int w,int h){
		this.width =w;
		this.height = h;
		
		generateBoard();
	}
	
	public void draw(){
		for(int x = 0; x<board.length;x++){
			for(int y = 0; y < board[0].length;y++){
				
			}
		}
	}
	
	private void generateBoard(){
		for(int x =0; x < board.length;x++){
			for(int y = 0;y< board[0].length;y++){
				//sea sheep brick ore wood grain
				board[x][y]=new Tile(tileTypes[(int)(Math.random()*6)],tileWidth*x,tileHeight*y);
				
			}
		}
	}
}
