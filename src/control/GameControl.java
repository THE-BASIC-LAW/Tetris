package control;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.PasswordAuthentication;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import config.DataInterfaceConfig;
import config.GameConfig;
import dao.Data;
import dto.GameDto;
import dto.Player;
import service.GameService;
import service.GameTetris;
import ui.window.JFrameConfig;
import ui.window.JFrameGame;
import ui.window.JFrameSaveScore;
import ui.window.JPanelGame;
import util.TimerFunction;

/**
 * ������Ҽ����¼� ���ƻ��� ������Ϸ�߼�
 *
 */
public class GameControl {

	/**
	 * ���ݷ��ʽӿ�A
	 */
	private Data dataA;

	/**
	 * ���ݷ��ʽӿ�B
	 */
	private Data dataB;

	/**
	 * ��Ϸ�߼���
	 */
	private GameService gameService;

	/**
	 * ��Ϸ�����
	 */
	private JPanelGame panelGame;

	/**
	 * ��Ϸ���ƴ���
	 */
	private JFrameConfig frameConfig;

	/**
	 * �������洰��
	 */
	private JFrameSaveScore frameSaveScore;
	
	/**
	 * ��Ϸ��Ϊ����
	 */
	private Map<Integer, Method> actionList;

	private Thread gameThread;

	/**
	 * ��Ϸ����Դ
	 */
	private GameDto dto = null;

	public GameControl() {
		// ������Ϸ����Դ
		this.dto = new GameDto();
		// ������Ϸ�߼���
		this.gameService = new GameTetris(dto);
		// �����ݽӿ�A������ݿ��¼
		this.dataA = createDataObject(GameConfig.getDataConfig().getDataA());
		// �������ݿ��¼����Ϸ
		this.dto.setDbRecode(dataA.loadData());
		// �����ݿ�ӿ�B��ñ��ش��̼�¼
		this.dataB = createDataObject(GameConfig.getDataConfig().getDataB());
		// ���ñ��ش��̼�¼����Ϸ
		this.dto.setDiskRecode(dataB.loadData());
		// ��ȡ�û�����
		this.setControlConfig();
		// ��ʼ���û����ô���
		this.frameConfig = new JFrameConfig(this);
		// ������Ϸ���
		this.panelGame = new JPanelGame(this, dto);
		// ������Ϸ�����ڣ���װ��Ϸ���
		new JFrameGame(this.panelGame);
		//��ʼ���������洰��
		this.frameSaveScore = new JFrameSaveScore(this);
	}

	/**
	 * ��ȡ�û���������
	 */
	private void setControlConfig() {
		// �����������뷽������ӳ������
		this.actionList = new HashMap<Integer, Method>();
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data/control.dat"));
			HashMap<Integer, String> cfgSet = (HashMap<Integer, String>) ois.readObject();
			ois.close();
			Set<Entry<Integer, String>> entryhset = cfgSet.entrySet();
			for (Entry<Integer, String> e : entryhset) {
				actionList.put(e.getKey(), this.gameService.getClass().getMethod(e.getValue()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * �������ݶ���
	 */
	private Data createDataObject(DataInterfaceConfig cfg) {
		try {
			// ��������
			Class<?> cls = Class.forName(cfg.getClassName());
			// ��ù�����
			Constructor<?> ctr = cls.getConstructor(HashMap.class);
			return (Data) ctr.newInstance(cfg.getParam());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * ������Ұ�����������Ϊ
	 */
	public void actionByKeyCode(int keyCode) {
		try {
			if (this.actionList.containsKey(keyCode)) {
				this.actionList.get(keyCode).invoke(this.gameService);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.panelGame.repaint();
	}

	// TODO p)����ר�÷���=================

	public void testLevelUP() {
		this.gameService.cheat();
		this.panelGame.repaint();
	}

	/**
	 * ��ʾ��ҿ��ƴ���
	 */
	public void showUserConfig() {
		this.frameConfig.setVisible(true);
	}

	/**
	 * �Ӵ��ڹر��¼�
	 */
	public void setOver() {
		this.panelGame.repaint();
		this.setControlConfig();
	}

	/**
	 * ��ʼ��ť�¼�
	 */
	public void start() {
		// ��尴ť����Ϊ���ɵ��
		this.panelGame.buttonSwitch(false);
		//�رմ���
		this.frameConfig.setVisible(false);
		this.frameSaveScore.setVisible(false);
		// ��Ϸ���ݳ�ʼ��
		this.gameService.startGame();
		// �����̶߳���
		this.gameThread = new MainThread();
		this.gameThread.start();
		// ˢ�»���
		panelGame.repaint();
	}

	private void afterOver() {
		// ��ʾ����÷ִ���
		this.frameSaveScore.show(this.dto.getNowScores());	
		// ʹ��ť���Ե��
		this.panelGame.buttonSwitch(true);
	}
	
	private class MainThread extends Thread {
		public void run() {
			while (true) {
				if(!dto.isStart()){
					afterOver();
					break;
				}
				try {
					Thread.sleep(dto.getSleepTime());
					// �����½�
					if(dto.isPause()){
						continue;
					}
					gameService.mainAction();
					// ˢ�»���
					panelGame.repaint();

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * �������
	 */
	public void saveScore(String name) {
		Player pla = new Player(name, this.dto.getNowScores());
		//�����¼�����ݿ�
		this.dataA.saveData(pla);
		//�����¼������
		this.dataB.saveData(pla);
		//�������ݿ��¼����Ϸ
		dto.setDbRecode(dataA.loadData());
		//���ô��̼�¼����Ϸ
		dto.setDiskRecode(dataB.loadData());
		//ˢ�»���
		this.panelGame.repaint();
	}
}
