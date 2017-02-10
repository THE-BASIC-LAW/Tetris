package ui.window;


import javax.swing.JFrame;

import config.FrameConfig;
import config.GameConfig;
import util.FrameUtil;

public class JFrameGame extends JFrame{
	
	public JFrameGame(JPanelGame panelGame){
		//获得游戏配置
		FrameConfig fcfg = GameConfig.getFrameConfig();
		//设置标题
		this.setTitle(fcfg.getTitle());
		//设置默认关闭
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		//设置窗口大小
		this.setSize(fcfg.getWidth(), fcfg.getHeight());
		//不允许改变窗口大小
		this.setResizable(false);
		//居中
		FrameUtil.setFrameCenter(this);
		//设置默认Panel
		this.setContentPane(panelGame);
		//默认该窗口显示
		this.setVisible(true);
	}
}
