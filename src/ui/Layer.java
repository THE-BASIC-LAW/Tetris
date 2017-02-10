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
		// 获得游戏配置
		FrameConfig fcfg = GameConfig.getFrameConfig();
		PADDING = fcfg.getPadding();
		BORDER = fcfg.getBorder();
	}

	/**
	 * 数字切片宽度
	 */
	protected static final int NUMBER_W = Img.NUMBER.getWidth(null) / 10;

	/**
	 * 数字切片高度
	 */
	private static final int NUMBER_H = Img.NUMBER.getHeight(null);

	/**
	 * 矩形值槽高度
	 */
	protected static final int RECT_H = Img.RECT.getHeight(null);
	/**
	 * 矩形值槽宽度
	 */
	private static final int RECT_W = Img.RECT.getWidth(null);

	private final int rectW;

	/**
	 * 字体设置
	 */
	private static final Font DEF_FONT = new Font("黑体", Font.BOLD, 20);

	// 窗口左上角x坐标
	protected int x;
	// 窗口左上角y坐标
	protected int y;
	// 窗口宽度
	protected int w;
	// 窗口高度
	protected int h;

	/**
	 * 游戏数据
	 */
	protected GameDto dto;

	// 构造方法
	protected Layer(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.rectW = this.w - (PADDING << 1);
	}

	/**
	 * 绘制窗口
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
	 * 显示数字
	 * 
	 * @param x
	 *            左上角x坐标
	 * @param y
	 *            左上角y坐标
	 * @param num
	 *            要显示的数字
	 * @param g
	 *            画笔对象
	 */
	protected void drawNumberLeftPad(int x, int y, int num, int maxBit, Graphics g) {
		// 把数字number中的每一位取出
		String strNum = Integer.toString(num);
		// 循环绘制数字右对齐
		for (int i = 0; i < maxBit; i++) {
			// 判断是否满足绘制条件
			if (maxBit - i <= strNum.length()) {
				// 获得数字在字符串中的下标
				int idx = i - maxBit + strNum.length();
				// 把数字number中的每一位输出
				int bit = strNum.charAt(idx) - '0';
				g.drawImage(Img.NUMBER, this.x + x + NUMBER_W * i, this.y + y, this.x + x + NUMBER_W * (i + 1),
						this.y + y + NUMBER_H, bit * NUMBER_W, 0, (bit + 1) * NUMBER_W, NUMBER_H, null);
			}
		}
	}

	/**
	 * 
	 * 绘制值槽
	 * 
	 */
	protected void drawRect(int y, String title, String number, double percent, Graphics g) {
		// 各种值初始化
		int rect_x = this.x + PADDING;
		int rect_y = this.y + y;
		// 绘制背景
		g.setColor(Color.BLACK);
		g.fillRect(rect_x, rect_y, this.rectW, RECT_H + 4);
		g.setColor(Color.WHITE);
		g.fillRect(rect_x + 1, rect_y + 1, this.rectW - 2, RECT_H + 2);
		g.setColor(Color.BLACK);
		g.fillRect(rect_x + 2, rect_y + 2, this.rectW - 4, RECT_H);
		g.setColor(Color.GREEN);
		// 求出宽度
		int w = (int) (percent * (this.rectW - 4));
		// 求出颜色
		int subIdx = (int) (percent * RECT_W) - 1;
		// 绘制值槽
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
	 * 正中绘图
	 */
	protected void drawImageAtCenter(Graphics g, Image img) {
		int imgW = img.getWidth(null);
		int imgH = img.getHeight(null);
		g.drawImage(img, this.x + (this.w - imgW >> 1), this.y + (this.h - imgH >> 1), null);
	}
}
