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
	 * 数据库记录
	 */
	private List<Player> dbRecode;

	/**
	 * 本地记录
	 */
	private List<Player> diskRecode;

	/**
	 * 游戏地图
	 */
	private boolean[][] gameMap;

	/**
	 * 下落方块
	 */
	private GameAct gameAct;
	
	/**
	 * 下一个方块
	 */
	private int next;
	
	/**
	 * 目前等级
	 */
	private int level;
	
	/**
	 * 目前分数
	 */
	private int nowScores;
	
	/**
	 * 目前已消除行数
	 */
	private int nowRemoveLine;
	
	/**
	 * 判断游戏是否是开始状态
	 */
	private boolean start;
	
	/**
	 * 是否显示阴影
	 */
	private boolean showShadow;
	
	/**
	 * 暂停
	 */
	private boolean pause;
	
	/**
	 * 线程睡眠时间
	 */
	private long sleepTime;
	
	/**
	 * 构造函数
	 */
	public GameDto(){
		dtoInit();
	}

	/**
	 * dto初始化
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
		//如果进来的是空，那么就创建
		if(players == null){
			players = new ArrayList<Player>();
		}
		//如果记录书小于5，则加到5为止
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
