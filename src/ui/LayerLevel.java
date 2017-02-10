package ui;

import java.awt.Graphics;

public class LayerLevel extends Layer {
;

	/**
	 * �ȼ�ͼƬ���
	 */
	private static final int LV_W = Img.LV.getWidth(null);
	
	public LayerLevel(int x, int y, int w, int h) {
		super(x, y, w, h);
	}

	public void paint(Graphics g) {
		this.createWindow(g);
		// ���ڱ���
		int centerX = this.w - LV_W>>1;
		g.drawImage(Img.LV, this.x+centerX, this.y + PADDING, null);
		// ��ʾ�ȼ�
		this.drawNumberLeftPad(centerX, 64, this.dto.getLevel(), 2, g);
	}
}
