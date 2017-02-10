package ui;

import java.awt.Graphics;
import java.awt.Point;

import config.GameConfig;
import entity.GameAct;

public class LayerGame extends Layer {

	/**
	 * 左位移偏移量
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
			// 获得方块数组集合
			Point[] points = act.getActPoints();
			// 绘制阴影
			this.drawShadow(points, g);
			// 绘制活动方块
			this.drawMainAct(points, g);
		}
		// 绘制游戏
		this.drawMap(g);
		// 暂停
		if (this.dto.isPause()) {
			drawImageAtCenter(g, Img.PAUSE);
		}
	}

	/**
	 * 绘制活动方块
	 */
	private void drawMainAct(Point[] points, Graphics g) {
		// 获得方块类型编号(0~6)
		int typeCode = this.dto.getGameAct().getTypectCode() + 1;
		typeCode = this.dto.isStart() ? typeCode : OVER_IDX;
		// 打印方块
		for (int i = 0; i < points.length; i++) {
			drawActByPoint(points[i].x, points[i].y, typeCode, g);
		}

	}

	/**
	 * 绘制地图
	 */
	private void drawMap(Graphics g) {
		// 绘制地图
		boolean[][] map = this.dto.getGameMap();
		// 计算当前堆积颜色
		int lv = this.dto.getLevel();
		int imgIdx = lv == 0 ? 0 : (lv - 1) % 7 + 1;
		// TODO 如果游戏结束imgIdx = 8
		for (int x = 0; x < map.length; x++) {
			for (int y = 0; y < map[x].length; y++) {
				if (map[x][y]) {
					drawActByPoint(x, y, this.dto.isStart() ? imgIdx : OVER_IDX, g);
				}
			}

		}

	}

	private void drawShadow(Point[] points, Graphics g) {
		// TODO p)阴影关闭
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
	 * 绘制正方形块
	 */
	private void drawActByPoint(int x, int y, int imgIdx, Graphics g) {
		g.drawImage(Img.ACT, this.x + (x << SIZE_ROL) + BORDER, this.y + (y << SIZE_ROL) + BORDER,
				this.x + (x + 1 << SIZE_ROL) + BORDER, this.y + (y + 1 << SIZE_ROL) + BORDER, imgIdx << SIZE_ROL, 0,
				imgIdx + 1 << SIZE_ROL, 1 << SIZE_ROL, null);
	}
}
