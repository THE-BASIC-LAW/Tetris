package ui;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import config.GameConfig;

/**
 * @author Administrator
 *
 */
public class Img {

	private Img() {
	}

	/**
	 * 个人签名
	 */
	public static Image SIGN = new ImageIcon("graphics/string/sign.png").getImage();

	/**
	 * 窗口图片
	 */
	public static Image WINDOW = new ImageIcon("graphics/window/Window.png").getImage();

	/**
	 * 数字图片 260 36
	 */
	public static Image NUMBER = new ImageIcon("graphics/string/num.png").getImage();

	/**
	 * 值槽图片
	 */
	public static Image RECT = new ImageIcon("graphics/window/rect.png").getImage();

	/**
	 * 获取分数图片
	 */
	public static Image SCORE = new ImageIcon("graphics/string/point.png").getImage();

	/**
	 * 获取消行图片
	 */
	public static Image REMOVE = new ImageIcon("graphics/string/rmline.png").getImage();

	/**
	 * 获取暂停图片
	 */
	public static Image PAUSE = new ImageIcon("graphics/string/pause.png").getImage();
	
	/**
	 * 标题图片
	 *
	 */
	public static Image LV = new ImageIcon("graphics/string/level.png").getImage();

	/**
	 * 本地数据库图片
	 */
	public static Image DISK = new ImageIcon("graphics/string/disk.png").getImage();

	/**
	 * 数据库图片
	 */
	public static Image DB = new ImageIcon("graphics/string/db.png").getImage();

	/**
	 * 背景图片
	 */
	public static Image BG = new ImageIcon("graphics/background/aaa.jpg").getImage();

	/**
	 * 方块图片
	 */
	public static final Image ACT = new ImageIcon("graphics/game/rect.png").getImage();

	/**
	 * 阴影
	 */
	public static final Image SHADOW = new ImageIcon("graphics/game/shadow.png").getImage();
	
	/**
	 * 开始按钮
	 */
	public static final ImageIcon BTN_START = new ImageIcon("graphics/string/start.png");

	/**
	 * 设置按钮
	 */
	public static final ImageIcon BTN_CONFIG = new ImageIcon("graphics/string/config.png");
	
	/**
	 * 下一个图片数组
	 */
	public static Image[] NEXT_ACT;

	public static List<Image> BG_LIST;

	static {
		// 下一个方块图片
		NEXT_ACT = new Image[GameConfig.getSystemConfig().getTypeConfig().size()];
		for (int i = 0; i < NEXT_ACT.length; i++) {
			NEXT_ACT[i] = new ImageIcon("graphics/game/" + i + ".png").getImage();
		}
		// 背景图片数组
		File dir = new File("graphics/background");
		File[] files = dir.listFiles();
		BG_LIST = new ArrayList<>();
		for (File file : files) {
			BG_LIST.add((new ImageIcon(file.getPath())).getImage());
		}
	}
}
