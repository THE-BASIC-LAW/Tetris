package util;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class FrameUtil {

	/**
	 * ´°¿Ú¾ÓÖÐ
	 */
	public static void setFrameCenter(JFrame jf){
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension ds = tk.getScreenSize();
		int x = ds.width-jf.getWidth()>>1;
		int y = (ds.height-jf.getHeight()>>1)-32;
		jf.setLocation(x,y);
	}
	
}
