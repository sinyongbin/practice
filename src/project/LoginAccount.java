package project;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class LoginAccount extends JFrame implements ActionListener {
	// Container cp;
	// 윗 패널
	JPanel loginP;
	JPanel emailP, pwdP;
	JLabel emailLbl, pwdLbl;
	JLabel blank, blank1, lab1, lab2;
	static public JTextField emailF;
	static public JPasswordField passwordF;
	JButton login, create;
	// 가운데 패널
	JPanel signupP;

	static String email, pw, name;
	// DB객체 불러오기
	ShoppingSiteDbHelper dh = new ShoppingSiteDbHelper();

	int loginFlag = 0; // 비밀번호가 일치하여 로그인 되면 '1'으로 바뀜
	String myPass;

	LoginAccount() {
		init();
	}

	void init() {
		// 윗 패널
		upPnl();
		// 가운데 패널
		mdlPnl();
		// 패널 (위,왼쪽,아래,오른쪽) 공백 주기
		loginP.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
		signupP.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		// 패널 백그라운드 색 바꾸기
		loginP.setBackground(Color.white);
		signupP.setBackground(Color.white);

		// 버튼 리스너
		create.addActionListener(this);

	}

	public void actionPerformed(ActionEvent ae) {
		myPass = String.valueOf(passwordF.getPassword());
		System.out.println(myPass);

		if (ae.getSource() == login) { // '로그인' 버튼
			if (emailF.getText().trim().isEmpty()) { // '아이디 입력안했을때'
				JOptionPane.showMessageDialog(null, "이메일을 입력하세요", "INFORMATION_MESSAGE",
						JOptionPane.INFORMATION_MESSAGE);
				emailF.requestFocus();
				loginFlag = 0;
				System.out.println("loginFlag: " + loginFlag);
			} else if (!emailF.getText().trim().isEmpty() && myPass.isEmpty()) { // 비밀번호 입력 안했을 때
				JOptionPane.showMessageDialog(null, "비밀번호를 입력하세요", "INFORMATION_MESSAGE",
						JOptionPane.INFORMATION_MESSAGE);
				passwordF.requestFocus();
				loginFlag = 0;
			} else if (!emailF.getText().trim().isEmpty() && !myPass.isEmpty()) {
				String email = emailF.getText().trim();
				LoginAccount.email = email;
				LoginAccount.name = dh.getName(email);
				loginFlag = 0;
				if (dh.checkId(email) && dh.checkPw(email, myPass)) { // 이메일과 비밀번호가 일치할 때 로그인하고 나서 화면 띄우기
					loginFlag = 1;
					System.out.println("loginFlag: " + loginFlag);
					setVisible(false);
					dh.makeSeq(email); // ***********이메일에 맞춰서 카트 안에 담기는 시퀀스 생성
				}
			}
		}

	}

	void upPnl() {
		loginP = new JPanel();
		loginP.setLayout(new GridLayout(5, 1));
		blank = new JLabel();
		lab1 = new JLabel("고객님의 계정에 엑세스하세요");
		emailP = new JPanel();
		emailP.setBackground(Color.white);
		emailP.setLayout(new GridLayout(2, 1));
		emailLbl = new JLabel("이메일");
		emailF = new JTextField();
		emailF.setSize(130, 300);
		emailP.add(emailLbl);
		emailP.add(emailF);

		pwdP = new JPanel();
		pwdP.setBackground(Color.white);
		pwdP.setLayout(new GridLayout(2, 1));
		pwdLbl = new JLabel("비밀번호");
		passwordF = new JPasswordField();
		passwordF.setSize(130, 300);
		pwdP.add(pwdLbl);
		pwdP.add(passwordF);

		// JButton 색상 white로 변경
		blank.setBackground(Color.white);
		lab1.setBackground(Color.white);
		// JButton 투명하게
		blank.setOpaque(true);
		lab1.setOpaque(true);

		// 버튼 크기 조정

		loginP.add(blank);
		loginP.add(lab1);
		loginP.add(emailP);
		loginP.add(pwdP);

	}

	void mdlPnl() {
		signupP = new JPanel();
		signupP.setLayout(new GridLayout(8, 1));
		login = new JButton("로그인");
		blank1 = new JLabel();
		lab2 = new JLabel("계정이 필요하십니까?");

		create = new JButton("등록");

		// JButton 색상 white로 변경
		login.setForeground(Color.white);
		login.setBackground(Color.black);
		create.setBackground(Color.white);

		// JButton 투명하게
		login.setOpaque(true);
		create.setOpaque(true);

		signupP.add(login);
		signupP.add(blank1);
		signupP.add(lab2);
		signupP.add(create);

	}

}
