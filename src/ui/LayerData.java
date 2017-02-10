package ui;

import java.awt.Graphics;
import java.awt.Image;
import java.util.List;

import config.GameConfig;
import dto.Player;

public abstract class LayerData extends Layer {

	/**
	 * 最大数据行
	 */
	private static final int MAX_ROW = GameConfig.getDataConfig().getMaxRow();
		
	/**
	 * 起始y坐标
	 */
	private static int START_Y = 0;
	
	/**
	 * 间距
	 */
	private static int SPA = 0;
	
	/**
	 * 值槽外径
	 */
	private static int IMG_RECT_H = RECT_H + 4;

	public LayerData(int x,int y,int w,int h){
		super(x,y,w,h);
		//就算记录行间距
		SPA = (this.h - IMG_RECT_H * 5 - (PADDING << 1) - Img.DB.getHeight(null)) / MAX_ROW;
		//计算起始y坐标
		START_Y = PADDING + Img.DB.getHeight(null) + SPA;
	}
	@Override
	public abstract void paint(Graphics g);
	/**
	 * 绘制该窗口所有值槽
	 * 
	 * @param imgTitle 标题图片
	 * @param player 数据源
	 * @param g 画笔
	 */
	public void showData(Image imgTitle,List<Player> players,Graphics g){
		//绘制标题
		g.drawImage(imgTitle, this.x + PADDING, this.y + PADDING, null);
		//获得现在分数
		int nowScores = this.dto.getNowScores();
		//循环绘制记录
		for (int i = 0; i < MAX_ROW; i++) {
			//获得一条玩家记录
			Player pla = players.get(i);
			//获得该玩家分数
			int recodeScore = pla.getScore();
			//就是那现在分数与记录分数比值
			double percent = (double)nowScores/pla.getScore();
			//如果已破记录。比值设为100%
			percent = percent > 1?1.0:percent;
			//绘制单条记录
			String strScore = recodeScore == 0? null:Integer.toString(recodeScore);
			this.drawRect(START_Y+i*(IMG_RECT_H+SPA), pla.getName(), strScore, percent, g);
		}
	}
}
