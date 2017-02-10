package control;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PlayerControl extends KeyAdapter {

	private GameControl gameControl;

	public PlayerControl(GameControl gameControl) {
		this.gameControl = gameControl;
	}

	/*
	 * ���̰���ʱ��
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO p) ö��д������
		this.gameControl.actionByKeyCode(e.getKeyCode());
	}

}
