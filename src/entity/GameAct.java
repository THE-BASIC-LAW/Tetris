package entity;

import java.awt.Point;
import java.util.List;

import config.GameConfig;

public class GameAct {

	/**
	 * ��������
	 */
	private Point[] actPoints;

	/**
	 * ������
	 */
	private int typeCode;
	
	private static int MIN_X = GameConfig.getSystemConfig().getMinX();
	private static int MAX_X = GameConfig.getSystemConfig().getMaxX();
	private static int MIN_Y = GameConfig.getSystemConfig().getMinY();
	private static int MAX_Y = GameConfig.getSystemConfig().getMaxY();

	private final static List<Point[]> TYPE_CONFIG = GameConfig.getSystemConfig().getTypeConfig();
	private final static List<Boolean> TYPE_ROUND = GameConfig.getSystemConfig().getTypeRound();


	public GameAct(int typeCode) {
		this.init(typeCode);
	}

	public void init(int typeCode) {
		// ����actCode��ֵˢ�·���
		this.typeCode = typeCode;
		Point[] points = TYPE_CONFIG.get(typeCode);
		actPoints = new Point[points.length];
		for (int i = 0; i < points.length; i++) {
			actPoints[i] = new Point(points[i].x,points[i].y);
		}
	}

	public Point[] getActPoints() {
		return actPoints;
	}

	/**
	 * �����ƶ�
	 * 
	 * 
	 * @param moveX
	 *            X��ƫ����
	 * @param moveY
	 *            Y��ƫ����
	 */
	public boolean move(int moveX, int moveY, boolean[][] gameMap) {
		// �ƶ�����
		for (int i = 0; i < actPoints.length; i++) {
			int newX = actPoints[i].x + moveX;
			int newY = actPoints[i].y + moveY;
			if (this.isoverZone(newX, newY, gameMap)) {
				return false;
			}
		}
		for (int i = 0; i < actPoints.length; i++) {
			actPoints[i].x += moveX;
			actPoints[i].y += moveY;
		}
		return true;
	}

	/**
	 * ������ת
	 * 
	 * ˳ʱ�� A.x =0.y+0.x-B.y 
	 * A.y =0.y-0.x+B.x
	 */
	public void round(boolean[][] gameMap) {
		if(!TYPE_ROUND.get(this.typeCode)){
			return;
		}
		for (int i = 1; i < actPoints.length; i++) {
			int newX = actPoints[0].y + actPoints[0].x - actPoints[i].y;
			int newY = actPoints[0].y - actPoints[0].x + actPoints[i].x;
			if (this.isoverZone(newX, newY, gameMap)) {
				return;
			}
		}
		for (int i = 1; i < actPoints.length; i++) {
			int newX = actPoints[0].y + actPoints[0].x - actPoints[i].y;
			int newY = actPoints[0].y - actPoints[0].x + actPoints[i].x;
			actPoints[i].x = newX;
			actPoints[i].y = newY;
		}
	}

	/**
	 * �ж��Ƿ񳬳��߽�
	 */
	private boolean isoverZone(int x, int y, boolean[][] gameMap) {
		return x < MIN_X || x > MAX_X || y < MIN_Y || y > MAX_Y || gameMap[x][y];
	}

	/**
	 * ��ȡ������
	 */
	public int getTypectCode() {
		return typeCode;
	}
}
