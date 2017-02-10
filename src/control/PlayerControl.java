package control;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PlayerControl extends KeyAdapter {

	private GameControl gameControl;

	public PlayerControl(GameControl gameControl) {
		this.gameControl = gameControl;
	}

	/*
	 * 键盘按下时间
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO p) 枚举写法不好
		this.gameControl.actionByKeyCode(e.getKeyCode());
	}

}
