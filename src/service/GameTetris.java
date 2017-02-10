package service;

import java.awt.Point;
import java.util.Map;
import java.util.Random;

import config.GameConfig;
import dto.GameDto;
import entity.GameAct;

public class GameTetris implements GameService {

	/**
	 * ��Ϸ
	 */
	private GameDto dto;

	/**
	 * �����������
	 */
	private Random random = new Random();

	/**
	 * �����������
	 */
	private static final int MAX_TYPE = GameConfig.getSystemConfig().getTypeConfig().size() - 1;

	/**
	 * �������з�����
	 */
	private static final Map<Integer, Integer> PLUS_SCORE = GameConfig.getSystemConfig().getPlusScore();

	public GameTetris(GameDto dto) {
		this.dto = dto;
	}

	/**
	 * ��������������ϣ�
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
	 * ��������������£�
	 */
	public boolean keyDown() {
		synchronized (this.dto) {
			if (this.dto.isPause()) {
				return false;
			}
			if (this.dto.getGameAct().move(0, 1, this.dto.getGameMap())) {
				return false;
			}
			// �����Ϸ��ͼ����
			boolean[][] map = this.dto.getGameMap();
			// ��÷������
			Point[] act = this.dto.getGameAct().getActPoints();
			// ��������뵽��ͼ����
			for (int i = 0; i < act.length; i++) {
				map[act[i].x][act[i].y] = true;
			}
			// ����
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
			// ����ӷ�
			this.addscores(count);
			// ������һ������
			this.dto.getGameAct().init(this.dto.getNext());
			// �����������һ������
			this.dto.setNext(random.nextInt(MAX_TYPE));
			// �ж���Ϸ�Ƿ����
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
	 * ���������������
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
	 * ��������������ң�
	 */
	public void keyRight() {
		synchronized (this.dto) {
			if (this.dto.isPause()) {
				return;
			}
			this.dto.getGameAct().move(1, 0, this.dto.getGameMap());
		}
	}

	// ��i�����ϵķ����½�һ��
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

	// �ж��Ƿ��������
	private boolean isCanRemove(boolean[][] map, int i) {
		for (int j = 0; j < 10; j++) {
			if (!map[j][i]) {
				return false;
			}
		}
		return true;
	}

	// ���в���
	private void removeline(boolean[][] map, int i) {
		for (int j = 0; j < 10; j++) {
			map[j][i] = false;
		}
	}

	// ��ֲ���
	private void addscores(int count) {
		int score = this.dto.getNowScores();
		score += PLUS_SCORE.get(count);
		this.dto.setNowScores(score);
	}

	// �ж��Ƿ�����
	private boolean isLevelUp() {
		int rmline = this.dto.getNowRemoveLine();
		this.dto.setNowRemoveLine(++rmline);
		return rmline % 20 == 0;
	}

	// ����
	private void LevelUp() {
		int lv = this.dto.getLevel();
		this.dto.setLevel(++lv);
	}

	/**
	 * ˲������
	 */
	public void downButtom() {
		if (this.dto.isPause()) {
			return;
		}
		while (!this.keyDown())
			;
	}

	/**
	 * ��Ӱ����
	 */
	public void shadowOnOff() {
		this.dto.changeShowShadow();
	}
	// TODO=======================����ר�÷���

	/**
	 * ����
	 */
	public void cheat() {
		addscores(1);
		if (isLevelUp()) {
			LevelUp();
		}
	}

	/**
	 * ��ͣ
	 */
	public void pause() {
		if (this.dto.isStart()) {
			this.dto.changePause();
		}
	}

	@Override
	public void startGame() {
		// ���������һ������
		this.dto.setNext(random.nextInt(MAX_TYPE));
		// ����������ڷ���
		this.dto.setGameAct(new GameAct(random.nextInt(MAX_TYPE)));
		// ������Ϸ���߳�
		this.dto.setStart(true);
		//dto��ʼ��
		this.dto.dtoInit();
	}

	@Override
	public void mainAction() {
		this.keyDown();
	}
}
