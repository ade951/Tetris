package cn.张剑伟;
import java.awt.Graphics;

import javax.swing.JPanel;

public class HelpPane extends JPanel {

	/**
	 * 我不知道这个serialVersionUID是干什么用的，以后再查
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		g.drawString("游戏帮助：", 10, 20);
		g.drawString("使用上、下、左、右方向键控制", 10, 40);
		g.drawString("空格键有特殊用处", 10, 60);
	}
}
