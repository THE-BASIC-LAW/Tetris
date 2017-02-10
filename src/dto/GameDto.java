package dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import config.GameConfig;
import entity.GameAct;
import util.TimerFunction;

public class GameDto {

	private static final int GAMEZONE_W = GameConfig.getSystemConfig().getMaxX()+1;

	private static final int GAMEZONE_H = GameConfig.getSystemConfig().getMaxY()+1;
	
	/**
	 * ���ݿ��¼
	 */
	private List<Player> dbRecode;

	/**
	 * ���ؼ�¼
	 */
	private List<Player> diskRecode;

	/**
	 * ��Ϸ��ͼ
	 */
	private boolean[][] gameMap;

	/**
	 * ���䷽��
	 */
	private GameAct gameAct;
	
	/**
	 * ��һ������
	 */
	private int next;
	
	/**
	 * Ŀǰ�ȼ�
	 */
	private int level;
	
	/**
	 * Ŀǰ����
	 */
	private int nowScores;
	
	/**
	 * Ŀǰ����������
	 */
	private int nowRemoveLine;
	
	/**
	 * �ж���Ϸ�Ƿ��ǿ�ʼ״̬
	 */
	private boolean start;
	
	/**
	 * �Ƿ���ʾ��Ӱ
	 */
	private boolean showShadow;
	
	/**
	 * ��ͣ
	 */
	private boolean pause;
	
	/**
	 * �߳�˯��ʱ��
	 */
	private long sleepTime;
	
	/**
	 * ���캯��
	 */
	public GameDto(){
		dtoInit();
	}

	/**
	 * dto��ʼ��
	 */
	public void dtoInit(){
		this.gameMap = new boolean[GAMEZONE_W][GAMEZONE_H];
		this.level = 0;
		this.nowScores = 0;
		this.nowRemoveLine = 0;
		this.pause = false;
		this.sleepTime = TimerFunction.getSleepTimeByLevel(this.level);
	}
	
	public List<Player> getDbRecode() {
		return dbRecode;
	}

	public void setDbRecode(List<Player> dbRecode) {
		this.dbRecode = setFullRecode(dbRecode);
	}

	public List<Player> getDiskRecode() {
		return diskRecode;
	}

	public void setDiskRecode(List<Player> diskRecode) {
		this.diskRecode = setFullRecode(diskRecode);
	}

	private List<Player> setFullRecode(List<Player> players){
		//����������ǿգ���ô�ʹ���
		if(players == null){
			players = new ArrayList<Player>();
		}
		//�����¼��С��5����ӵ�5Ϊֹ
		while(players.size()<5){
			players.add(new Player("No Data", 0));
		}
		Collections.sort(players);
		return players;
	}
	
	public boolean[][] getGameMap() {
		return gameMap;
	}

	public void setGameMap(boolean[][] gameMap) {
		this.gameMap = gameMap;
	}

	public GameAct getGameAct() {
		return gameAct;
	}

	public void setGameAct(GameAct gameAct) {
		this.gameAct = gameAct;
	}

	public int getNext() {
		return next;
	}

	public void setNext(int next) {
		this.next = next;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
		this.sleepTime = TimerFunction.getSleepTimeByLevel(this.level);
	}

	public int getNowScores() {
		return nowScores;
	}

	public void setNowScores(int nowScores) {
		this.nowScores = nowScores;
	}

	public int getNowRemoveLine() {
		return nowRemoveLine;
	}

	public void setNowRemoveLine(int nowRemoveLine) {
		this.nowRemoveLine = nowRemoveLine;
	}

	public boolean isStart() {
		return start;
	}

	public void setStart(boolean start) {
		this.start = start;
	}

	public boolean isShowShadow() {
		return showShadow;
	}

	public void changeShowShadow() {
		this.showShadow = !this.showShadow;
	}

	public boolean isPause() {
		return pause;
	}

	public void changePause() {
		this.pause = !this.pause;
	}

	public long getSleepTime() {
		return sleepTime;
	}

}
