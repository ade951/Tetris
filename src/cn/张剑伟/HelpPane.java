package cn.�Ž�ΰ;
import java.awt.Graphics;

import javax.swing.JPanel;

public class HelpPane extends JPanel {

	/**
	 * �Ҳ�֪�����serialVersionUID�Ǹ�ʲô�õģ��Ժ��ٲ�
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		g.drawString("��Ϸ������", 10, 20);
		g.drawString("ʹ���ϡ��¡����ҷ��������", 10, 40);
		g.drawString("�ո���������ô�", 10, 60);
	}
}
