package ui;

import java.awt.Graphics;
import java.awt.Image;
import java.util.List;

import config.GameConfig;
import dto.Player;

public abstract class LayerData extends Layer {

	/**
	 * ���������
	 */
	private static final int MAX_ROW = GameConfig.getDataConfig().getMaxRow();
		
	/**
	 * ��ʼy����
	 */
	private static int START_Y = 0;
	
	/**
	 * ���
	 */
	private static int SPA = 0;
	
	/**
	 * ֵ���⾶
	 */
	private static int IMG_RECT_H = RECT_H + 4;

	public LayerData(int x,int y,int w,int h){
		super(x,y,w,h);
		//�����¼�м��
		SPA = (this.h - IMG_RECT_H * 5 - (PADDING << 1) - Img.DB.getHeight(null)) / MAX_ROW;
		//������ʼy����
		START_Y = PADDING + Img.DB.getHeight(null) + SPA;
	}
	@Override
	public abstract void paint(Graphics g);
	/**
	 * ���Ƹô�������ֵ��
	 * 
	 * @param imgTitle ����ͼƬ
	 * @param player ����Դ
	 * @param g ����
	 */
	public void showData(Image imgTitle,List<Player> players,Graphics g){
		//���Ʊ���
		g.drawImage(imgTitle, this.x + PADDING, this.y + PADDING, null);
		//������ڷ���
		int nowScores = this.dto.getNowScores();
		//ѭ�����Ƽ�¼
		for (int i = 0; i < MAX_ROW; i++) {
			//���һ����Ҽ�¼
			Player pla = players.get(i);
			//��ø���ҷ���
			int recodeScore = pla.getScore();
			//���������ڷ������¼������ֵ
			double percent = (double)nowScores/pla.getScore();
			//������Ƽ�¼����ֵ��Ϊ100%
			percent = percent > 1?1.0:percent;
			//���Ƶ�����¼
			String strScore = recodeScore == 0? null:Integer.toString(recodeScore);
			this.drawRect(START_Y+i*(IMG_RECT_H+SPA), pla.getName(), strScore, percent, g);
		}
	}
}
