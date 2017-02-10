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
 * 接受玩家键盘事件 控制画面 控制游戏逻辑
 *
 */
public class GameControl {

	/**
	 * 数据访问接口A
	 */
	private Data dataA;

	/**
	 * 数据访问接口B
	 */
	private Data dataB;

	/**
	 * 游戏逻辑层
	 */
	private GameService gameService;

	/**
	 * 游戏界面层
	 */
	private JPanelGame panelGame;

	/**
	 * 游戏控制窗口
	 */
	private JFrameConfig frameConfig;

	/**
	 * 分数保存窗口
	 */
	private JFrameSaveScore frameSaveScore;
	
	/**
	 * 游戏行为控制
	 */
	private Map<Integer, Method> actionList;

	private Thread gameThread;

	/**
	 * 游戏数据源
	 */
	private GameDto dto = null;

	public GameControl() {
		// 创建游戏数据源
		this.dto = new GameDto();
		// 创建游戏逻辑块
		this.gameService = new GameTetris(dto);
		// 从数据接口A获得数据库记录
		this.dataA = createDataObject(GameConfig.getDataConfig().getDataA());
		// 设置数据库记录到游戏
		this.dto.setDbRecode(dataA.loadData());
		// 从数据库接口B获得本地磁盘记录
		this.dataB = createDataObject(GameConfig.getDataConfig().getDataB());
		// 设置本地磁盘记录到游戏
		this.dto.setDiskRecode(dataB.loadData());
		// 读取用户配置
		this.setControlConfig();
		// 初始化用户配置窗口
		this.frameConfig = new JFrameConfig(this);
		// 创建游戏面板
		this.panelGame = new JPanelGame(this, dto);
		// 创建游戏主窗口，安装游戏面板
		new JFrameGame(this.panelGame);
		//初始化分数保存窗口
		this.frameSaveScore = new JFrameSaveScore(this);
	}

	/**
	 * 读取用户控制设置
	 */
	private void setControlConfig() {
		// 创建键盘码与方法名的映射数组
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
	 * 创建数据对象
	 */
	private Data createDataObject(DataInterfaceConfig cfg) {
		try {
			// 获得类对象
			Class<?> cls = Class.forName(cfg.getClassName());
			// 获得构造器
			Constructor<?> ctr = cls.getConstructor(HashMap.class);
			return (Data) ctr.newInstance(cfg.getParam());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 根据玩家按键来控制行为
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

	// TODO p)测试专用方法=================

	public void testLevelUP() {
		this.gameService.cheat();
		this.panelGame.repaint();
	}

	/**
	 * 显示玩家控制窗口
	 */
	public void showUserConfig() {
		this.frameConfig.setVisible(true);
	}

	/**
	 * 子窗口关闭事件
	 */
	public void setOver() {
		this.panelGame.repaint();
		this.setControlConfig();
	}

	/**
	 * 开始按钮事件
	 */
	public void start() {
		// 面板按钮设置为不可点击
		this.panelGame.buttonSwitch(false);
		//关闭窗口
		this.frameConfig.setVisible(false);
		this.frameSaveScore.setVisible(false);
		// 游戏数据初始化
		this.gameService.startGame();
		// 创建线程对象
		this.gameThread = new MainThread();
		this.gameThread.start();
		// 刷新画面
		panelGame.repaint();
	}

	private void afterOver() {
		// 显示保存得分窗口
		this.frameSaveScore.show(this.dto.getNowScores());	
		// 使按钮可以点击
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
					// 方块下降
					if(dto.isPause()){
						continue;
					}
					gameService.mainAction();
					// 刷新画面
					panelGame.repaint();

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 保存分数
	 */
	public void saveScore(String name) {
		Player pla = new Player(name, this.dto.getNowScores());
		//保存记录到数据库
		this.dataA.saveData(pla);
		//保存记录到磁盘
		this.dataB.saveData(pla);
		//设置数据库记录到游戏
		dto.setDbRecode(dataA.loadData());
		//设置磁盘记录到游戏
		dto.setDiskRecode(dataB.loadData());
		//刷新画面
		this.panelGame.repaint();
	}
}
