package cn.�Ž�ΰ;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

class Main {
	public static void main(String[] args) {
		//Ϊ���̰߳�ȫҪʹ�����GUIר�õ��߳�
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				initGame();
			}
		});
	}

	private static void initGame() {
		JFrame f = new JFrame();
		f.setTitle("����˹����--С����");
		
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