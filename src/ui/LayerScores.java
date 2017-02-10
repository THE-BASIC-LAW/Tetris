package ui;

import java.awt.Graphics;

import config.GameConfig;


public class LayerScores extends Layer {
	/**
	 * 分数最大位数
	 */
	private static final int SCORE_BIT = 5;
	
	
	//TODO
	private static final int LEVEL_UP = GameConfig.getSystemConfig().getLevelUp();
	

	/**
	 * 消行y坐标
	 */
	private final int removeY;
	
	/**
	 * 分数y坐标
	 */
	private final int scoreY;
	
	/**
	 * 分数x坐标
	 */
	private final int comX;
	
	/**
	 * 经验值的y坐标
	 */
	private final int expY;
	
	public LayerScores(int x,int y,int w,int h){
		super(x,y,w,h);
		//初始化共用的x坐标
		this.comX = this.w-NUMBER_W*SCORE_BIT-PADDING;
		//初始化分数显示的y坐标
		this.scoreY =  PADDING;
		//初始化消行显示的y坐标
		this.removeY =  Img.SCORE.getHeight(null)+(PADDING<<1);
		//初始化经验值槽y坐标
		this.expY= this.removeY + Img.SCORE.getHeight(null)+PADDING;
		
	}
	public void paint(Graphics g){
		this.createWindow(g);
		//绘制窗口标题(分数)
		g.drawImage(Img.SCORE, this.x+PADDING, this.y+scoreY, null);
		//显示分数
		this.drawNumberLeftPad(comX, scoreY, this.dto.getNowScores(), SCORE_BIT, g);
		//绘制窗口标题(消行)
		g.drawImage(Img.REMOVE, this.x+PADDING, this.y+removeY, null);
		//显示像行
		this.drawNumberLeftPad(comX, removeY, this.dto.getNowRemoveLine(), SCORE_BIT, g);
		//绘制值槽（经验值）
		double rmline = this.dto.getNowRemoveLine();
		this.drawRect(this.expY,"下一级",null,rmline%LEVEL_UP/(double)LEVEL_UP,g);
		//TODO 临时	
	}
}
