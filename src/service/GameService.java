package service;

public interface GameService {

	/**
	 * �������
	 */
	public void keyUp();
	
	/**
	 * �������
	 */
	public boolean keyDown();
	
	/**
	 * �������
	 */
	public void keyLeft();
	
	/**
	 * �������
	 */
	public void keyRight();
	
	/**
	 * ���׼�
	 */
	public void cheat();
	
	/**
	 * ��Ӱ����
	 */
	public void shadowOnOff();
	
	/**
	 * ��ͣ
	 */
	public void pause();

	/**
	 * �������̣߳���ʼ��Ϸ
	 */
	public void startGame();
	
	/**
	 * ��Ϸ����Ϊ
	 */
	public void mainAction();
}
