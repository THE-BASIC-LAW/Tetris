package ui.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import control.GameControl;
import ui.Img;
import util.FrameUtil;

public class JFrameConfig extends JFrame {

	private JButton btnOk = new JButton("确定");

	private JButton btnCancel = new JButton("取消");

	private JButton btnUser = new JButton("应用");

	private TextCtrl[] keyTexts = new TextCtrl[8];
	
	private JLabel errorMsg = new JLabel();
	
	private JList skinList = null;
	
	private DefaultListModel skinData = new DefaultListModel();
	
	private JPanel skinView = null;

	private final static Image IMG_PSP = new ImageIcon().getImage();

	private final static String[] METHOD_NAMES = { "keyUp", "keyDown", "keyLeft", "keyRight", "cheat","downButtom" ,"shadowOnOff","pause"};

	private static final String PATH = "data/control.dat";
	
	private GameControl gameControl;

	/**
	 * 
	 */
	public JFrameConfig(GameControl gameControl) {
		//获得游戏控制器对象
		this.gameControl = gameControl;
		// 设置布局管理器为"边界布局"
		this.setLayout(new BorderLayout());
		this.setTitle("设置");
		// 初始化按键输入框
		this.initkeyTexts();
		// 添加主面板
		this.add(this.createMainPanel(), BorderLayout.CENTER);
		// 添加按钮面板
		this.add(this.createButtonPanel(), BorderLayout.SOUTH);
		// 设置窗口大小
		this.setSize(640, 350);
		// 居中
		FrameUtil.setFrameCenter(this);
	}

	/**
	 * 初始化按键输入框
	 */
	private void initkeyTexts() {
		// (0, 50, 64, 32)
		int x = 0;
		int y = 10;
		int w = 64;
		int h = 20;
		for (int i = 0; i < keyTexts.length; i++) {
			keyTexts[i] = new TextCtrl(x, y, w, h, METHOD_NAMES[i]);
			y += 32;
		}
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PATH));
			HashMap<Integer, String> cfgSet = (HashMap<Integer, String>) ois.readObject();
			ois.close();
			Set<Entry<Integer, String>> entryhset = cfgSet.entrySet();
			for (Entry<Integer, String> e : entryhset) {
				for (TextCtrl tc : keyTexts) {
					if(tc.getMethodName().equals(e.getValue())){
						tc.setKeyCode(e.getKey());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建按钮面板
	 */
	private JPanel createButtonPanel() {
		// 设置布局管理器为流式布局
		JPanel jp = new JPanel(new FlowLayout());
		// 给确定按钮增加事件监听
		this.btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(writeConfig()){;
				setVisible(false);
				gameControl.setOver();
				}
			}
		});
		this.errorMsg.setForeground(Color.RED);
		jp.add(this.errorMsg);
		jp.add(this.btnOk);
		this.btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				gameControl.setOver();
			}
		});
		jp.add(this.btnCancel);
		this.btnUser.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				writeConfig();
			}
		});
		jp.add(this.btnUser);
		return jp;
	}

	private JTabbedPane createMainPanel() {
		JTabbedPane jtp = new JTabbedPane();
		jtp.addTab("控制设置", this.createControlPanel());
		jtp.addTab("皮肤设置", this.createSkinPanel());
		return jtp;
	}

	/**
	 * 玩家皮肤面板
	 */
	private JPanel createSkinPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		this.skinData.addElement("默认");
		this.skinData.addElement("皮肤1");
		this.skinData.addElement("皮肤2");
		this.skinData.addElement("皮肤3");
		this.skinList = new JList<>(skinData);
		this.skinView = new JPanel(){
			@Override
			public void paintComponent(Graphics g){
				g.drawImage(Img.SHADOW, 0, 0, null);
			}
		};
		panel.add(new JScrollPane(this.skinList),BorderLayout.WEST);
		return panel;
	}

	/**
	 * 玩家设置控制面板
	 */
	private JPanel createControlPanel() {
		JPanel jp = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				g.drawImage(IMG_PSP, 0, 0, null);
			}
		};
		// 设置布局管理器
		jp.setLayout(null);
		for (int i = 0; i < keyTexts.length; i++) {
			jp.add(keyTexts[i]);
		}
		return jp;
	}


	/**
	 * 写入游戏配置
	 */
	private boolean writeConfig() {
		HashMap<Integer, String> keySet = new HashMap<Integer, String>();
		for (int i = 0; i < keyTexts.length; i++) {
			int keyCode = this.keyTexts[i].getKeyCode();
			if(keyCode == 0){
				this.errorMsg.setText("错误按键");
				return false;
			}
			keySet.put(this.keyTexts[i].getKeyCode(), this.keyTexts[i].getMethodName());
		}
		if(keySet.size()!=8){
			this.errorMsg.setText("重复按键");
			return false;
		}
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PATH));
			oos.writeObject(keySet);
			oos.close();
		} catch (Exception e) {
			this.errorMsg.setText(e.getMessage());
			return false;
		}
		this.errorMsg.setText(null);
		return true;
	}
}
