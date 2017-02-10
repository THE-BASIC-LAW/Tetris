package ui.window;


import javax.swing.JFrame;

import config.FrameConfig;
import config.GameConfig;
import util.FrameUtil;

public class JFrameGame extends JFrame{
	
	public JFrameGame(JPanelGame panelGame){
		//�����Ϸ����
		FrameConfig fcfg = GameConfig.getFrameConfig();
		//���ñ���
		this.setTitle(fcfg.getTitle());
		//����Ĭ�Ϲر�
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		//���ô��ڴ�С
		this.setSize(fcfg.getWidth(), fcfg.getHeight());
		//������ı䴰�ڴ�С
		this.setResizable(false);
		//����
		FrameUtil.setFrameCenter(this);
		//����Ĭ��Panel
		this.setContentPane(panelGame);
		//Ĭ�ϸô�����ʾ
		this.setVisible(true);
	}
}
