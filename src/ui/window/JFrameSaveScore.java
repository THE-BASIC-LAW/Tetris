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
		this.setTitle("�����¼��");
		this.setSize(256, 128);
		FrameUtil.setFrameCenter(this);
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		this.createCom();
		this.createAction();
	}

	/**
	 * ��ʾ����
	 */
	public void show(int score){
		this.lbscore.setText("���ĵ÷�:"+ "         "+score);
		this.setVisible(true);
	}
	
	/**
	 * �����¼�����
	 */
	private void createAction() {
		this.btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int len = txName.getText().length();
				if (len > 16 || len == 0) {
					errMsg.setText("�����������");
				} else {
					setVisible(false);
					gameControl.saveScore(txName.getText());
				}
			}
		});
	}

	/**
	 * ��ʼ���ؼ�
	 */
	private void createCom() {
		// �����������
		JPanel north = new JPanel(new FlowLayout(FlowLayout.LEFT));
		// ������������
		this.lbscore = new JLabel();
		// ��ӷ������ֵ��������
		north.add(lbscore);
		// ����������Ϣ�ؼ�
		this.errMsg = new JLabel();
		this.errMsg.setForeground(Color.RED);
		// ��Ӵ�����Ϣ�ؼ����������
		north.add(this.errMsg);
		// ���������ӵ������
		this.add(north, BorderLayout.NORTH);

		JPanel center = new JPanel(new FlowLayout(FlowLayout.LEFT));
		this.txName = new JTextField(10);
		center.add(new Label("��������:"));
		center.add(this.txName);
		this.add(center, BorderLayout.CENTER);

		// ����ȷ����ť
		this.btnOk = new JButton("ȷ��");
		// �����ϲ����
		JPanel south = new JPanel(new FlowLayout(FlowLayout.CENTER));
		// ��ť��ӵ��ϲ����
		south.add(btnOk);
		// �ϲ������ӵ������
		this.add(south, BorderLayout.SOUTH);
	}
}
