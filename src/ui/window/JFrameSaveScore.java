package ui.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import control.GameControl;
import util.FrameUtil;

public class JFrameSaveScore extends JFrame {

	private JButton btnOk = null;

	private JTextField txName = null;

	private JLabel lbscore = null;

	private JLabel errMsg = null;

	private GameControl gameControl = null;

	public JFrameSaveScore(GameControl gameControl) {
		this.gameControl = gameControl;
		this.setTitle("保存记录？");
		this.setSize(256, 128);
		FrameUtil.setFrameCenter(this);
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		this.createCom();
		this.createAction();
	}

	/**
	 * 显示窗口
	 */
	public void show(int score){
		this.lbscore.setText("您的得分:"+ "         "+score);
		this.setVisible(true);
	}
	
	/**
	 * 创建事件监听
	 */
	private void createAction() {
		this.btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int len = txName.getText().length();
				if (len > 16 || len == 0) {
					errMsg.setText("名字输入错误");
				} else {
					setVisible(false);
					gameControl.saveScore(txName.getText());
				}
			}
		});
	}

	/**
	 * 初始化控件
	 */
	private void createCom() {
		// 创建北部面板
		JPanel north = new JPanel(new FlowLayout(FlowLayout.LEFT));
		// 创建分数文字
		this.lbscore = new JLabel();
		// 添加分数文字到北部面板
		north.add(lbscore);
		// 创建错误信息控件
		this.errMsg = new JLabel();
		this.errMsg.setForeground(Color.RED);
		// 添加错误信息控件到北部面板
		north.add(this.errMsg);
		// 北部面板添加到主面板
		this.add(north, BorderLayout.NORTH);

		JPanel center = new JPanel(new FlowLayout(FlowLayout.LEFT));
		this.txName = new JTextField(10);
		center.add(new Label("您的名字:"));
		center.add(this.txName);
		this.add(center, BorderLayout.CENTER);

		// 创建确定按钮
		this.btnOk = new JButton("确定");
		// 创建南部面板
		JPanel south = new JPanel(new FlowLayout(FlowLayout.CENTER));
		// 按钮添加到南部面板
		south.add(btnOk);
		// 南部面板添加到主面板
		this.add(south, BorderLayout.SOUTH);
	}
}
