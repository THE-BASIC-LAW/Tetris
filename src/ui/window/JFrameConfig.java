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

	private JButton btnOk = new JButton("ȷ��");

	private JButton btnCancel = new JButton("ȡ��");

	private JButton btnUser = new JButton("Ӧ��");

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
		//�����Ϸ����������
		this.gameControl = gameControl;
		// ���ò��ֹ�����Ϊ"�߽粼��"
		this.setLayout(new BorderLayout());
		this.setTitle("����");
		// ��ʼ�����������
		this.initkeyTexts();
		// ��������
		this.add(this.createMainPanel(), BorderLayout.CENTER);
		// ��Ӱ�ť���
		this.add(this.createButtonPanel(), BorderLayout.SOUTH);
		// ���ô��ڴ�С
		this.setSize(640, 350);
		// ����
		FrameUtil.setFrameCenter(this);
	}

	/**
	 * ��ʼ�����������
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
	 * ������ť���
	 */
	private JPanel createButtonPanel() {
		// ���ò��ֹ�����Ϊ��ʽ����
		JPanel jp = new JPanel(new FlowLayout());
		// ��ȷ����ť�����¼�����
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
		jtp.addTab("��������", this.createControlPanel());
		jtp.addTab("Ƥ������", this.createSkinPanel());
		return jtp;
	}

	/**
	 * ���Ƥ�����
	 */
	private JPanel createSkinPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		this.skinData.addElement("Ĭ��");
		this.skinData.addElement("Ƥ��1");
		this.skinData.addElement("Ƥ��2");
		this.skinData.addElement("Ƥ��3");
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
	 * ������ÿ������
	 */
	private JPanel createControlPanel() {
		JPanel jp = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				g.drawImage(IMG_PSP, 0, 0, null);
			}
		};
		// ���ò��ֹ�����
		jp.setLayout(null);
		for (int i = 0; i < keyTexts.length; i++) {
			jp.add(keyTexts[i]);
		}
		return jp;
	}


	/**
	 * д����Ϸ����
	 */
	private boolean writeConfig() {
		HashMap<Integer, String> keySet = new HashMap<Integer, String>();
		for (int i = 0; i < keyTexts.length; i++) {
			int keyCode = this.keyTexts[i].getKeyCode();
			if(keyCode == 0){
				this.errorMsg.setText("���󰴼�");
				return false;
			}
			keySet.put(this.keyTexts[i].getKeyCode(), this.keyTexts[i].getMethodName());
		}
		if(keySet.size()!=8){
			this.errorMsg.setText("�ظ�����");
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
