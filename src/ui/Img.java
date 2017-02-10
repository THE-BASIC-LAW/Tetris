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
	 * ����ǩ��
	 */
	public static Image SIGN = new ImageIcon("graphics/string/sign.png").getImage();

	/**
	 * ����ͼƬ
	 */
	public static Image WINDOW = new ImageIcon("graphics/window/Window.png").getImage();

	/**
	 * ����ͼƬ 260 36
	 */
	public static Image NUMBER = new ImageIcon("graphics/string/num.png").getImage();

	/**
	 * ֵ��ͼƬ
	 */
	public static Image RECT = new ImageIcon("graphics/window/rect.png").getImage();

	/**
	 * ��ȡ����ͼƬ
	 */
	public static Image SCORE = new ImageIcon("graphics/string/point.png").getImage();

	/**
	 * ��ȡ����ͼƬ
	 */
	public static Image REMOVE = new ImageIcon("graphics/string/rmline.png").getImage();

	/**
	 * ��ȡ��ͣͼƬ
	 */
	public static Image PAUSE = new ImageIcon("graphics/string/pause.png").getImage();
	
	/**
	 * ����ͼƬ
	 *
	 */
	public static Image LV = new ImageIcon("graphics/string/level.png").getImage();

	/**
	 * �������ݿ�ͼƬ
	 */
	public static Image DISK = new ImageIcon("graphics/string/disk.png").getImage();

	/**
	 * ���ݿ�ͼƬ
	 */
	public static Image DB = new ImageIcon("graphics/string/db.png").getImage();

	/**
	 * ����ͼƬ
	 */
	public static Image BG = new ImageIcon("graphics/background/aaa.jpg").getImage();

	/**
	 * ����ͼƬ
	 */
	public static final Image ACT = new ImageIcon("graphics/game/rect.png").getImage();

	/**
	 * ��Ӱ
	 */
	public static final Image SHADOW = new ImageIcon("graphics/game/shadow.png").getImage();
	
	/**
	 * ��ʼ��ť
	 */
	public static final ImageIcon BTN_START = new ImageIcon("graphics/string/start.png");

	/**
	 * ���ð�ť
	 */
	public static final ImageIcon BTN_CONFIG = new ImageIcon("graphics/string/config.png");
	
	/**
	 * ��һ��ͼƬ����
	 */
	public static Image[] NEXT_ACT;

	public static List<Image> BG_LIST;

	static {
		// ��һ������ͼƬ
		NEXT_ACT = new Image[GameConfig.getSystemConfig().getTypeConfig().size()];
		for (int i = 0; i < NEXT_ACT.length; i++) {
			NEXT_ACT[i] = new ImageIcon("graphics/game/" + i + ".png").getImage();
		}
		// ����ͼƬ����
		File dir = new File("graphics/background");
		File[] files = dir.listFiles();
		BG_LIST = new ArrayList<>();
		for (File file : files) {
			BG_LIST.add((new ImageIcon(file.getPath())).getImage());
		}
	}
}
