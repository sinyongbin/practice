package project;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.*;

public class UpdatePwd extends JFrame implements ActionListener, FocusListener {
	Container cp;
	JPanel uup1, uup2, uup3, uup4;
	JPanel curPwdP, newPwdP, confirmPwdP;
	JLabel curPwd, newPwd, confirmPwd, bla, bla1;
	JButton update, backBtn;
	ImageIcon backIcon = new ImageIcon("icons/back.png");
	FlowLayout f1 = new FlowLayout(FlowLayout.LEFT);
	JLabel updateLabel, curPwdLabel, newPwdLabel, confirmPwdLabel;
	JTextField curPwdField, newPwdField, confirmPwdField;

	UpdatePwd() {
		init();
	}

	void init() {
		cp = getContentPane();

		uup1 = new JPanel();
		uup1.setLayout(new BorderLayout()); // BorderLayout으로 설정
		uup1.setBackground(Color.white);

		uup2 = new JPanel();

		upPnl(); // upPnl() 메서드 호출하여 uup3에 FlowLayout 적용
		mdlPnl();

		cp.add(uup1, BorderLayout.NORTH);
		cp.add(uup2, BorderLayout.CENTER);

		uup2.setBorder(BorderFactory.createEmptyBorder(20, 0, 100, 0)); // 아래쪽 여백 추가
		uup2.setBackground(Color.white);
		curPwdField.addFocusListener(this);
		newPwdField.addFocusListener(this);
		confirmPwdField.addFocusListener(this);
		update.addActionListener(this);

	}
	void upPnl() {

		uup1.setLayout(new GridLayout(8, 1));

		uup3 = new JPanel(f1);
		uup3.setBackground(Color.white);
		backBtn = new JButton(backIcon);
		uup3.add(backBtn);

		backBtn.setBackground(Color.white);
		backBtn.setOpaque(true);
		backBtn.setBorderPainted(false);

		bla = new JLabel();
		updateLabel = new JLabel("비밀번호 업데이트");

		curPwdP = new JPanel(new BorderLayout()); // BorderLayout으로 설정
		curPwdP.setBackground(Color.white);
		curPwdP.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		curPwdField = new JTextField("현재 비밀번호");
		curPwdField.setBorder(BorderFactory.createLineBorder(Color.black)); // 윤곽선 추가
		curPwdP.add(curPwdField);

		newPwdP = new JPanel(new BorderLayout()); // BorderLayout으로 설정
		newPwdP.setBackground(Color.white);
		newPwdP.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		newPwdField = new JTextField("새 비밀번호");
		newPwdField.setBorder(BorderFactory.createLineBorder(Color.black)); // 윤곽선 추가
		newPwdP.add(newPwdField);

		confirmPwdP = new JPanel(new BorderLayout());
		confirmPwdP.setBackground(Color.white);
		confirmPwdP.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		confirmPwdField = new JTextField("새 비밀번호 확인");
		confirmPwdField.setBorder(BorderFactory.createLineBorder(Color.black)); // 윤곽선 추가
		confirmPwdP.add(confirmPwdField);

		uup1.add(uup3, BorderLayout.NORTH); // uup3를 uup1의 북쪽에 배치
		uup1.add(updateLabel);
		uup1.add(curPwdP);
		uup1.add(newPwdP);
		uup1.add(confirmPwdP);
		uup1.setPreferredSize(new Dimension(400, 500));

	}

	void mdlPnl() {
		JPanel mdlPanel = new JPanel();
		mdlPanel.setLayout(new GridBagLayout()); // GridBagLayout으로 설정
		mdlPanel.setBackground(Color.white);
		uup2.setLayout(new FlowLayout(FlowLayout.CENTER)); // 레이아웃 매니저 변경

		update = new JButton("비밀번호 수정");
		update.setPreferredSize(new Dimension(400, 30)); // 버튼 크기 조정
		update.setBackground(Color.yellow);
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.CENTER; // 가운데 정렬
		gbc.insets = new Insets(20, 0, 0, 0); // 위쪽 여백 설정
		mdlPanel.add(update, gbc);

		uup2.add(mdlPanel);
		uup2.add(update);

	}

	void setUI() {
		setTitle("Update PWD");
		setSize(450, 650);
		setPreferredSize(new Dimension(450, 650));
		setVisible(true);
		setLocation(200, 300);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void actionPerformed(ActionEvent ae) {

	}

	@Override
	public void focusGained(FocusEvent e) {
		if (e.getSource() == curPwdField) {
			String currentText = curPwdField.getText();
			if (currentText.equals("현재 비밀번호")) {
				curPwdField.setText("");
			}
		} else if (e.getSource() == newPwdField) {
			String newText = newPwdField.getText();
			if (newText.equals("새 비밀번호")) {
				newPwdField.setText("");
			}
		} else if (e.getSource() == confirmPwdField) {
			String confirmText = confirmPwdField.getText();
			if (confirmText.equals("새 비밀번호 확인")) {
				confirmPwdField.setText("");
			}
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		if (e.getSource() == curPwdField) {
			String currentText = curPwdField.getText();
			if (currentText.isEmpty()) {
				curPwdField.setText("현재 비밀번호");
			}
		} else if (e.getSource() == newPwdField) {
			String newText = newPwdField.getText();
			if (newText.isEmpty()) {
				newPwdField.setText("새 비밀번호");
			}
		} else if (e.getSource() == confirmPwdField) {
			String confirmText = confirmPwdField.getText();
			if (confirmText.isEmpty()) {
				confirmPwdField.setText("새 비밀번호 확인");
			}
		}
	}
}