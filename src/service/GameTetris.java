package service;

import java.awt.Point;
import java.util.Map;
import java.util.Random;

import config.GameConfig;
import dto.GameDto;
import entity.GameAct;

public class GameTetris implements GameService {

	/**
	 * 游戏
	 */
	private GameDto dto;

	/**
	 * 随机数生成器
	 */
	private Random random = new Random();

	/**
	 * 方块种类个数
	 */
	private static final int MAX_TYPE = GameConfig.getSystemConfig().getTypeConfig().size() - 1;

	/**
	 * 连续消行分数表
	 */
	private static final Map<Integer, Integer> PLUS_SCORE = GameConfig.getSystemConfig().getPlusScore();

	public GameTetris(GameDto dto) {
		this.dto = dto;
	}

	/**
	 * 控制器方向键（上）
	 */
	public void keyUp() {
		synchronized (this.dto) {
			if (this.dto.isPause()) {
				return;
			}
			this.dto.getGameAct().round(this.dto.getGameMap());
		}
	}

	/**
	 * 控制器方向键（下）
	 */
	public boolean keyDown() {
		synchronized (this.dto) {
			if (this.dto.isPause()) {
				return false;
			}
			if (this.dto.getGameAct().move(0, 1, this.dto.getGameMap())) {
				return false;
			}
			// 获得游戏地图对象
			boolean[][] map = this.dto.getGameMap();
			// 获得方块对象
			Point[] act = this.dto.getGameAct().getActPoints();
			// 将方块加入到地图数组
			for (int i = 0; i < act.length; i++) {
				map[act[i].x][act[i].y] = true;
			}
			// 消行
			int count = 0;
			for (int i = 0; i < 18; i++) {
				if (isCanRemove(map, i)) {
					removeline(map, i);
					count++;
					decline(map, i);
					if (isLevelUp()) {
						LevelUp();
					}
				}
			}
			// 计算加分
			this.addscores(count);
			// 创建下一个方块
			this.dto.getGameAct().init(this.dto.getNext());
			// 随机生成再下一个方块
			this.dto.setNext(random.nextInt(MAX_TYPE));
			// 判断游戏是否结束
			if (this.isgameOver(map)) {
				this.dto.setStart(false);
			}
			;
			return true;
		}
	}

	private boolean isgameOver(boolean[][] map) {
		Point[] act = this.dto.getGameAct().getActPoints();
		for (int i = 0; i < act.length; i++) {
			if (map[act[i].x][act[i].y])
				return true;
		}
		return false;
	}

	/**
	 * 控制器方向键（左）
	 */
	public void keyLeft() {
		synchronized (this.dto) {
			if (this.dto.isPause()) {
				return;
			}
			this.dto.getGameAct().move(-1, 0, this.dto.getGameMap());
		}
	}

	/**
	 * 控制器方向键（右）
	 */
	public void keyRight() {
		synchronized (this.dto) {
			if (this.dto.isPause()) {
				return;
			}
			this.dto.getGameAct().move(1, 0, this.dto.getGameMap());
		}
	}

	// 第i行以上的方块下降一层
	private void decline(boolean[][] map, int i) {
		for (; i > 0; i--) {
			for (int j = 0; j < 10; j++) {
				if (map[j][i - 1]) {
					map[j][i] = true;
					map[j][i - 1] = false;
				}
			}
		}
	}

	// 判断是否可以消行
	private boolean isCanRemove(boolean[][] map, int i) {
		for (int j = 0; j < 10; j++) {
			if (!map[j][i]) {
				return false;
			}
		}
		return true;
	}

	// 消行操作
	private void removeline(boolean[][] map, int i) {
		for (int j = 0; j < 10; j++) {
			map[j][i] = false;
		}
	}

	// 算分操作
	private void addscores(int count) {
		int score = this.dto.getNowScores();
		score += PLUS_SCORE.get(count);
		this.dto.setNowScores(score);
	}

	// 判断是否升级
	private boolean isLevelUp() {
		int rmline = this.dto.getNowRemoveLine();
		this.dto.setNowRemoveLine(++rmline);
		return rmline % 20 == 0;
	}

	// 升级
	private void LevelUp() {
		int lv = this.dto.getLevel();
		this.dto.setLevel(++lv);
	}

	/**
	 * 瞬间下落
	 */
	public void downButtom() {
		if (this.dto.isPause()) {
			return;
		}
		while (!this.keyDown())
			;
	}

	/**
	 * 阴影开关
	 */
	public void shadowOnOff() {
		this.dto.changeShowShadow();
	}
	// TODO=======================测试专用方法

	/**
	 * 作弊
	 */
	public void cheat() {
		addscores(1);
		if (isLevelUp()) {
			LevelUp();
		}
	}

	/**
	 * 暂停
	 */
	public void pause() {
		if (this.dto.isStart()) {
			this.dto.changePause();
		}
	}

	@Override
	public void startGame() {
		// 随机生成下一个方块
		this.dto.setNext(random.nextInt(MAX_TYPE));
		// 随机生成现在方块
		this.dto.setGameAct(new GameAct(random.nextInt(MAX_TYPE)));
		// 开启游戏主线程
		this.dto.setStart(true);
		//dto初始化
		this.dto.dtoInit();
	}

	@Override
	public void mainAction() {
		this.keyDown();
	}
}
