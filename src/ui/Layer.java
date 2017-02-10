package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import config.FrameConfig;
import config.GameConfig;
import dto.GameDto;

public abstract class Layer {
	protected static final int PADDING;
	protected static final int BORDER;

	static {
		// �����Ϸ����
		FrameConfig fcfg = GameConfig.getFrameConfig();
		PADDING = fcfg.getPadding();
		BORDER = fcfg.getBorder();
	}

	/**
	 * ������Ƭ���
	 */
	protected static final int NUMBER_W = Img.NUMBER.getWidth(null) / 10;

	/**
	 * ������Ƭ�߶�
	 */
	private static final int NUMBER_H = Img.NUMBER.getHeight(null);

	/**
	 * ����ֵ�۸߶�
	 */
	protected static final int RECT_H = Img.RECT.getHeight(null);
	/**
	 * ����ֵ�ۿ��
	 */
	private static final int RECT_W = Img.RECT.getWidth(null);

	private final int rectW;

	/**
	 * ��������
	 */
	private static final Font DEF_FONT = new Font("����", Font.BOLD, 20);

	// �������Ͻ�x����
	protected int x;
	// �������Ͻ�y����
	protected int y;
	// ���ڿ��
	protected int w;
	// ���ڸ߶�
	protected int h;

	/**
	 * ��Ϸ����
	 */
	protected GameDto dto;

	// ���췽��
	protected Layer(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.rectW = this.w - (PADDING << 1);
	}

	/**
	 * ���ƴ���
	 */
	protected void createWindow(Graphics g) {
		int WINDOW_W = Img.WINDOW.getWidth(null);
		int WINDOW_H = Img.WINDOW.getHeight(null);
		g.drawImage(Img.WINDOW, x, y, x + BORDER, y + BORDER, 0, 0, BORDER, BORDER, null);
		g.drawImage(Img.WINDOW, x + BORDER, y, x + w - BORDER, y + BORDER, BORDER, 0, WINDOW_W - BORDER, BORDER, null);
		g.drawImage(Img.WINDOW, x + w - BORDER, y, x + w, y + BORDER, WINDOW_W - BORDER, 0, WINDOW_W, BORDER, null);
		g.drawImage(Img.WINDOW, x, y + BORDER, x + BORDER, y + h - BORDER, 0, BORDER, BORDER, WINDOW_H - BORDER, null);
		g.drawImage(Img.WINDOW, x + BORDER, y + BORDER, x + w - BORDER, y + h - BORDER, BORDER, BORDER, WINDOW_W - BORDER,
				WINDOW_H - BORDER, null);
		g.drawImage(Img.WINDOW, x + w - BORDER, y + BORDER, x + w, y + h - BORDER, WINDOW_W - BORDER, BORDER, WINDOW_W,
				WINDOW_H - BORDER, null);
		g.drawImage(Img.WINDOW, x, y + h - BORDER, x + BORDER, y + h, 0, WINDOW_H - BORDER, BORDER, WINDOW_H, null);
		g.drawImage(Img.WINDOW, x + BORDER, y + h - BORDER, x + w - BORDER, y + h, BORDER, WINDOW_H - BORDER, WINDOW_W - BORDER,
				WINDOW_H, null);
		g.drawImage(Img.WINDOW, x + w - BORDER, y + h - BORDER, x + w, y + h, WINDOW_W - BORDER, WINDOW_H - BORDER, WINDOW_W,
				WINDOW_H, null);
	}

	public void setDto(GameDto dto) {
		this.dto = dto;
	}

	public abstract void paint(Graphics g);

	/**
	 * ��ʾ����
	 * 
	 * @param x
	 *            ���Ͻ�x����
	 * @param y
	 *            ���Ͻ�y����
	 * @param num
	 *            Ҫ��ʾ������
	 * @param g
	 *            ���ʶ���
	 */
	protected void drawNumberLeftPad(int x, int y, int num, int maxBit, Graphics g) {
		// ������number�е�ÿһλȡ��
		String strNum = Integer.toString(num);
		// ѭ�����������Ҷ���
		for (int i = 0; i < maxBit; i++) {
			// �ж��Ƿ������������
			if (maxBit - i <= strNum.length()) {
				// ����������ַ����е��±�
				int idx = i - maxBit + strNum.length();
				// ������number�е�ÿһλ���
				int bit = strNum.charAt(idx) - '0';
				g.drawImage(Img.NUMBER, this.x + x + NUMBER_W * i, this.y + y, this.x + x + NUMBER_W * (i + 1),
						this.y + y + NUMBER_H, bit * NUMBER_W, 0, (bit + 1) * NUMBER_W, NUMBER_H, null);
			}
		}
	}

	/**
	 * 
	 * ����ֵ��
	 * 
	 */
	protected void drawRect(int y, String title, String number, double percent, Graphics g) {
		// ����ֵ��ʼ��
		int rect_x = this.x + PADDING;
		int rect_y = this.y + y;
		// ���Ʊ���
		g.setColor(Color.BLACK);
		g.fillRect(rect_x, rect_y, this.rectW, RECT_H + 4);
		g.setColor(Color.WHITE);
		g.fillRect(rect_x + 1, rect_y + 1, this.rectW - 2, RECT_H + 2);
		g.setColor(Color.BLACK);
		g.fillRect(rect_x + 2, rect_y + 2, this.rectW - 4, RECT_H);
		g.setColor(Color.GREEN);
		// ������
		int w = (int) (percent * (this.rectW - 4));
		// �����ɫ
		int subIdx = (int) (percent * RECT_W) - 1;
		// ����ֵ��
		g.drawImage(Img.RECT, rect_x + 2, rect_y + 2, rect_x + w + 2, rect_y + RECT_H + 2, subIdx, 0, subIdx + 1,
				RECT_H, null);
		g.setColor(Color.WHITE);
		g.setFont(DEF_FONT);
		g.drawString(title, rect_x + 4, rect_y + 22);
		if (number != null) {
			for (int i = 0; i < 5; i++) {
				if (number.length() - i > 0 ) {
					g.drawString(number.charAt(i)+"", rect_x + 298-(number.length()-i)*10, rect_y + 22);
				}
			}
		}
	}

	/**
	 * ���л�ͼ
	 */
	protected void drawImageAtCenter(Graphics g, Image img) {
		int imgW = img.getWidth(null);
		int imgH = img.getHeight(null);
		g.drawImage(img, this.x + (this.w - imgW >> 1), this.y + (this.h - imgH >> 1), null);
	}
}
