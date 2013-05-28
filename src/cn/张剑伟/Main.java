package cn.张剑伟;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

class Main {
	public static void main(String[] args) {
		//为了线程安全要使用这个GUI专用的线程
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				initGame();
			}
		});
	}

	private static void initGame() {
		JFrame f = new JFrame();
		f.setTitle("俄罗斯方块--小剑伯");
		
		f.setLayout(new GridLayout(1,2));
		f.add(new HelpPane());

		GamePane p = new GamePane();
		f.add(p);
		f.pack();

		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		f.addKeyListener(p.keyListener());
		
	}
}