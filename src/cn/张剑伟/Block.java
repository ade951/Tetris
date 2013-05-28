package cn.’≈Ω£Œ∞;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class Block {
	int x=Constant.GAME_PANE_WIDTH/2-2,y=0;
	int direction;
	int type;
	boolean isconflict;
	byte[][][][] shape = {
			{		//7
					{   { 0, 0, 0, 0 }, 
						{ 0, 0, 0, 0 }, 
						{ 0, 0, 0, 1 },
						{ 0, 1, 1, 1 } },
						
					{   { 0, 0, 0, 0 }, 
						{ 0, 0, 1, 1 }, 
						{ 0, 0, 0, 1 },
						{ 0, 0, 0, 1 } },
						
					{   { 0, 0, 0, 0 }, 
						{ 0, 0, 0, 0 }, 
						{ 0, 1, 1, 1 },
						{ 0, 1, 0, 0 } },
						
					{   { 0, 0, 0, 0 }, 
						{ 0, 1, 0, 0 }, 
						{ 0, 1, 0, 0 },
						{ 0, 1, 1, 0 } } },
					//µπ7
				{	{   { 0, 0, 0, 0 }, 
						{ 0, 0, 0, 0 }, 
						{ 0, 1, 0, 0 },
						{ 0, 1, 1, 1 } },
						
					{   { 0, 0, 0, 0 }, 
						{ 0, 1, 1, 0 }, 
						{ 0, 1, 0, 0 },
						{ 0, 1, 0, 0 } },
						
					{   { 0, 0, 0, 0 }, 
						{ 0, 0, 0, 0 }, 
						{ 0, 1, 1, 1 },
						{ 0, 0, 0, 1 } },
						
					{   { 0, 0, 0, 0 }, 
						{ 0, 0, 1, 0 }, 
						{ 0, 0, 1, 0 },
						{ 0, 1, 1, 0 } } },
					//z
				{
					{   { 0, 0, 0, 0 }, 
						{ 0, 0, 0, 0 }, 
						{ 1, 1, 0, 0 },
						{ 0, 1, 1, 0 } },
						
					{   { 0, 0, 0, 0 }, 
						{ 0, 0, 1, 0 }, 
						{ 0, 1, 1, 0 },
						{ 0, 1, 0, 0 } } },
					//µπz
				{
					{   { 0, 0, 0, 0 }, 
						{ 0, 0, 0, 0 }, 
						{ 0, 1, 1, 0 },
						{ 1, 1, 0, 0 } },
						
					{   { 0, 0, 0, 0 }, 
						{ 0, 1, 0, 0 }, 
						{ 0, 1, 1, 0 },
						{ 0, 0, 1, 0 } } },
				//≥§Ãı
				{
					{   { 0, 0, 0, 1 }, 
						{ 0, 0, 0, 1 }, 
						{ 0, 0, 0, 1 },
						{ 0, 0, 0, 1 } },
						
					{   { 0, 0, 0, 0 }, 
						{ 0, 0, 0, 0 }, 
						{ 0, 0, 0, 0 },
						{ 1, 1, 1, 1 } } },
					//ÃÔ
				{
					{   { 0, 0, 0, 0 }, 
						{ 0, 0, 0, 0 }, 
						{ 0, 1, 1, 0 },
						{ 0, 1, 1, 0 } } }
						};

	
	public Block(){
		setRandomTypeAndDirection();
	}
	public void setRandomTypeAndDirection() {
		type = (int)(Math.random()*shape.length);
		direction = (int)(Math.random()*shape[type].length);
	}
	public void paint(final Graphics g){
		each(new CallBack() {
			@Override
			public void call(Cell c) {
				c.paint(g);
			}
		});
	}
	public void rotate(){
		direction = (direction +1 ) % shape[type].length;
	}
	public void rotateBack(){
		direction -= 1;
		if(direction<0){
			direction = shape[type].length-1;
		}
	}
	public boolean move(int direction, byte[][] backPanel) {
		//if can move
		switch(direction){
		case KeyEvent.VK_UP:
			rotate();
			if(isConflict(backPanel)){
				rotateBack();
				return false;
			}
			break;
		case KeyEvent.VK_LEFT:
			x--;
			if(isConflict(backPanel)){
				x++;
				return false;
			}
			break;
		case KeyEvent.VK_RIGHT:
			x++;
			if(isConflict(backPanel)){
				x--;
				return false;
			}
			break;
		case KeyEvent.VK_DOWN:
			y++;
			if(isConflict(backPanel)){
				y--;
				return false;
			}
			break;
		}
		return true;
	}
	public boolean isConflict(final byte[][] backPanel){
		isconflict = false;
		each(new CallBack() {
			@Override
			public void call(Cell c) {
				if(c.x<0 || c.y<0 || c.x>=Constant.GAME_PANE_WIDTH || c.y>=Constant.GAME_PANE_HEIGHT 
						|| backPanel[c.y][c.x] == 1){
					isconflict = true;
				}
			}
		});
		return isconflict;
	}
	public void adhere(final byte[][] backPanel) {
		each(new CallBack() {
			@Override
			public void call(Cell c) {
				backPanel[c.y][c.x] = 1;
			}
		});
	}
	public void each(CallBack c){
		byte[][] s = shape[type][direction];
		for (int i = 0; i < s.length; i++) {
			for (int j = 0; j < s[i].length; j++) {
				if( s[i][j]==1 ){
					c.call(new Cell(x+j,y+i));
				}
			}
		}
	}
}
class Cell{
	public int x;
	public int y;
	public Cell(int x, int y){
		this.x = x;
		this.y = y;
	}
	public void paint(Graphics g){
		g.fill3DRect(x*Constant.CELL_WIDTH, y*Constant.CELL_WIDTH, Constant.CELL_WIDTH, Constant.CELL_WIDTH, true);
	}
}
interface CallBack {
	public void call(Cell c);
}
