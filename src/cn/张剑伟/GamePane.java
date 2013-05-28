package cn.张剑伟;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.Transient;

import javax.swing.BorderFactory;
import javax.swing.JPanel;


public class GamePane extends JPanel {
	/**
	 * eclipse生成的,我不知道这个serialVersionUID是干什么用的，以后再查
	 */
	private static final long serialVersionUID = 1L;
	byte[][] backPanel = new byte[Constant.GAME_PANE_HEIGHT][Constant.GAME_PANE_WIDTH];
	private Block currentBlock;
	private boolean isGameOver = false;
	public GamePane(){
		setBorder(BorderFactory.createLineBorder(Color.black));
		currentBlock = new Block();
		update();
	}
	private void paintBackPanel(Graphics g){
		for(int i=0; i<backPanel.length; i++){
			for(int j=0; j<backPanel[i].length; j++){
				if( backPanel[i][j] == 1 ){
					g.fill3DRect( j*Constant.CELL_WIDTH, i*Constant.CELL_WIDTH, Constant.CELL_WIDTH, Constant.CELL_WIDTH,false);
				}
			}
		}
	}
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		paintBackPanel(g);
		currentBlock.paint(g);
	}
	@Override
	@Transient
	public Dimension getPreferredSize() {
		// TODO Auto-generated method stub
		return new Dimension(Constant.CELL_WIDTH*(Constant.GAME_PANE_WIDTH), Constant.CELL_WIDTH*(Constant.GAME_PANE_HEIGHT));
	}
	public void adhere(){
		currentBlock.adhere(backPanel);
	}
	public KeyListener keyListener(){
		return new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				super.keyPressed(e);
				currentBlock.move(e.getKeyCode(),backPanel);
				if(e.getKeyCode()==KeyEvent.VK_SPACE){
					currentBlock.setRandomTypeAndDirection();
					//如果产生的方块超过边界，就再产生。
					while(currentBlock.isConflict(backPanel)){
						currentBlock.setRandomTypeAndDirection();
					}
				}
				repaint();
			}
		};
	}
	public void update(){

		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true){
					if(isGameOver){
						break;
					}
					if(!currentBlock.move(KeyEvent.VK_DOWN, backPanel)){
						adhere();
						destroyWholeLine();
						currentBlock = new Block();
						if(currentBlock.isConflict(backPanel)){
							isGameOver = true;
						}
						
					}
					repaint();
					try {
						Thread.sleep(Constant.UPDATE_INTERVAL);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
		
	}
	public void destroyWholeLine(){
		//这里还可以再优化，不必从第一行开始检测
		for(int i=0; i<backPanel.length; i++){
			boolean isWhole = true;
			//判断满行
			byte[] line = backPanel[i];
			for(int j=0; j<line.length; j++){
				if(line[j]==0){
					isWhole = false;
					break;
				}
			}
			//消除满行
			if(isWhole){
				for(int j=i; j>0; j--){
					backPanel[j] = backPanel[j-1];
				}
			}
		}
	}
}
