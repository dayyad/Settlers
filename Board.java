import java.io.Serializable;

import ecs100.*;

public class Board implements Serializable{

	private static final long serialVersionUID = 1448444599664122862L;
	public int width;
	public int height;

	public double tileWidth = 150;
	public double tileHeight = 150 ;

	String[] tileTypes = {"grain","ore","brick","wood","sheep","sea"};
	Tile[][] board;


	public Board(int w,int h){
		this.width =w;
		this.height = h;

		generateBoard();
	}
	
	public String on(double mouseX, double mouseY){
		String on = null;
			for(int tileX=0;tileX<board.length;tileX++){
				for(int tileY = 0;tileY<board[0].length;tileY++){
					Tile tile = board[tileX][tileY];
					//If click within tile boundary, return tile type.
					if(mouseX<tile.getX()+tileWidth && mouseX>tile.getX()&&mouseY>tile.getY()&&mouseY<tile.getY()+tileHeight){
						return tile.getType();
					}
				}
			}
		return on;
	}

	public void draw(){
		for(int x = 0; x<board.length;x++){
			for(int y = 0; y < board[0].length;y++){
				board[x][y].draw();
			}
		}
	}

	private void generateBoard(){
		board = new Tile[width][height];
		for(int x =0; x <board.length;x++){
			for(int y = 0;y< board[0].length;y++){
				//sea sheep brick ore wood grain
				board[x][y]=new Tile(tileTypes[(int)(Math.random()*6)],tileWidth*x,tileHeight*y,tileWidth,tileHeight);

			}
		}
	}

	public void setBoard(int width,int height,Tile[][] tiles){
		this.width=width;
		this.height=height;
		generateBoard();
		this.board=tiles;
		draw();
	}

}
