package ui;

import java.awt.Graphics;
import java.awt.Point;

import config.GameConfig;
import entity.GameAct;

public class LayerGame extends Layer {

	/**
	 * ��λ��ƫ����
	 */
	private static final int SIZE_ROL = GameConfig.getFrameConfig().getSizeRol();

	private static final int LEFT_SIZE = 0;

	private static final int RIGHT_SIZE = GameConfig.getSystemConfig().getMaxX();

	private static final int OVER_IDX = GameConfig.getFrameConfig().getLoseIdx();

	public LayerGame(int x, int y, int w, int h) {
		super(x, y, w, h);
	}

	public void paint(Graphics g) {
		this.createWindow(g);
		GameAct act = this.dto.getGameAct();
		if (act != null) {
			// ��÷������鼯��
			Point[] points = act.getActPoints();
			// ������Ӱ
			this.drawShadow(points, g);
			// ���ƻ����
			this.drawMainAct(points, g);
		}
		// ������Ϸ
		this.drawMap(g);
		// ��ͣ
		if (this.dto.isPause()) {
			drawImageAtCenter(g, Img.PAUSE);
		}
	}

	/**
	 * ���ƻ����
	 */
	private void drawMainAct(Point[] points, Graphics g) {
		// ��÷������ͱ��(0~6)
		int typeCode = this.dto.getGameAct().getTypectCode() + 1;
		typeCode = this.dto.isStart() ? typeCode : OVER_IDX;
		// ��ӡ����
		for (int i = 0; i < points.length; i++) {
			drawActByPoint(points[i].x, points[i].y, typeCode, g);
		}

	}

	/**
	 * ���Ƶ�ͼ
	 */
	private void drawMap(Graphics g) {
		// ���Ƶ�ͼ
		boolean[][] map = this.dto.getGameMap();
		// ���㵱ǰ�ѻ���ɫ
		int lv = this.dto.getLevel();
		int imgIdx = lv == 0 ? 0 : (lv - 1) % 7 + 1;
		// TODO �����Ϸ����imgIdx = 8
		for (int x = 0; x < map.length; x++) {
			for (int y = 0; y < map[x].length; y++) {
				if (map[x][y]) {
					drawActByPoint(x, y, this.dto.isStart() ? imgIdx : OVER_IDX, g);
				}
			}

		}

	}

	private void drawShadow(Point[] points, Graphics g) {
		// TODO p)��Ӱ�ر�
		if (!this.dto.isShowShadow()) {
			return;
		}
		int leftX = RIGHT_SIZE;
		int rightX = LEFT_SIZE;
		for (Point p : points) {
			leftX = p.x < leftX ? p.x : leftX;
			rightX = p.x > rightX ? p.x : rightX;
		}
		g.drawImage(Img.SHADOW, this.x + BORDER + (leftX << SIZE_ROL), this.y + BORDER,
				(rightX - leftX + 1) << SIZE_ROL, this.h - (BORDER << 1), null);
	}

	/**
	 * ���������ο�
	 */
	private void drawActByPoint(int x, int y, int imgIdx, Graphics g) {
		g.drawImage(Img.ACT, this.x + (x << SIZE_ROL) + BORDER, this.y + (y << SIZE_ROL) + BORDER,
				this.x + (x + 1 << SIZE_ROL) + BORDER, this.y + (y + 1 << SIZE_ROL) + BORDER, imgIdx << SIZE_ROL, 0,
				imgIdx + 1 << SIZE_ROL, 1 << SIZE_ROL, null);
	}
}
