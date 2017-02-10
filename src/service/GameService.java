package service;

public interface GameService {

	/**
	 * 方向键上
	 */
	public void keyUp();
	
	/**
	 * 方向键下
	 */
	public boolean keyDown();
	
	/**
	 * 方向键左
	 */
	public void keyLeft();
	
	/**
	 * 方向键右
	 */
	public void keyRight();
	
	/**
	 * 作弊键
	 */
	public void cheat();
	
	/**
	 * 阴影开关
	 */
	public void shadowOnOff();
	
	/**
	 * 暂停
	 */
	public void pause();

	/**
	 * 启动主线程，开始游戏
	 */
	public void startGame();
	
	/**
	 * 游戏主行为
	 */
	public void mainAction();
}
