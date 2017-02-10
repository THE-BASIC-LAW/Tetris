package ui;

import java.awt.Graphics;

import config.GameConfig;


public class LayerScores extends Layer {
	/**
	 * �������λ��
	 */
	private static final int SCORE_BIT = 5;
	
	
	//TODO
	private static final int LEVEL_UP = GameConfig.getSystemConfig().getLevelUp();
	

	/**
	 * ����y����
	 */
	private final int removeY;
	
	/**
	 * ����y����
	 */
	private final int scoreY;
	
	/**
	 * ����x����
	 */
	private final int comX;
	
	/**
	 * ����ֵ��y����
	 */
	private final int expY;
	
	public LayerScores(int x,int y,int w,int h){
		super(x,y,w,h);
		//��ʼ�����õ�x����
		this.comX = this.w-NUMBER_W*SCORE_BIT-PADDING;
		//��ʼ��������ʾ��y����
		this.scoreY =  PADDING;
		//��ʼ��������ʾ��y����
		this.removeY =  Img.SCORE.getHeight(null)+(PADDING<<1);
		//��ʼ������ֵ��y����
		this.expY= this.removeY + Img.SCORE.getHeight(null)+PADDING;
		
	}
	public void paint(Graphics g){
		this.createWindow(g);
		//���ƴ��ڱ���(����)
		g.drawImage(Img.SCORE, this.x+PADDING, this.y+scoreY, null);
		//��ʾ����
		this.drawNumberLeftPad(comX, scoreY, this.dto.getNowScores(), SCORE_BIT, g);
		//���ƴ��ڱ���(����)
		g.drawImage(Img.REMOVE, this.x+PADDING, this.y+removeY, null);
		//��ʾ����
		this.drawNumberLeftPad(comX, removeY, this.dto.getNowRemoveLine(), SCORE_BIT, g);
		//����ֵ�ۣ�����ֵ��
		double rmline = this.dto.getNowRemoveLine();
		this.drawRect(this.expY,"��һ��",null,rmline%LEVEL_UP/(double)LEVEL_UP,g);
		//TODO ��ʱ	
	}
}
