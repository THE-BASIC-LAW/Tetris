package ui;

import java.awt.Graphics;

public class LayerLevel extends Layer {
;

	/**
	 * 等级图片宽度
	 */
	private static final int LV_W = Img.LV.getWidth(null);
	
	public LayerLevel(int x, int y, int w, int h) {
		super(x, y, w, h);
	}

	public void paint(Graphics g) {
		this.createWindow(g);
		// 窗口标题
		int centerX = this.w - LV_W>>1;
		g.drawImage(Img.LV, this.x+centerX, this.y + PADDING, null);
		// 显示等级
		this.drawNumberLeftPad(centerX, 64, this.dto.getLevel(), 2, g);
	}
}
