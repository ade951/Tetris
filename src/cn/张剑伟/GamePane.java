package cn.�Ž�ΰ;
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
	 * eclipse���ɵ�,�Ҳ�֪�����serialVersionUID�Ǹ�ʲô�õģ��Ժ��ٲ�
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
					//��������ķ��鳬���߽磬���ٲ�����
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
		//���ﻹ�������Ż������شӵ�һ�п�ʼ���
		for(int i=0; i<backPanel.length; i++){
			boolean isWhole = true;
			//�ж�����
			byte[] line = backPanel[i];
			for(int j=0; j<line.length; j++){
				if(line[j]==0){
					isWhole = false;
					break;
				}
			}
			//��������
			if(isWhole){
				for(int j=i; j>0; j--){
					backPanel[j] = backPanel[j-1];
				}
			}
		}
	}
}
