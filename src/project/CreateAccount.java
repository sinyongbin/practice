package project;

import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

public class CreateAccount extends JFrame {
	// 윗 패널
	JPanel createAPanel, btPanel, birthPnl, phonePnl;
	ImageIcon backIcon = new ImageIcon("icons/back1" + ".png");
	JComboBox yCombo, mCombo, dCombo;
	Vector<String> yName = new Vector<String>();
	Vector<String> mName = new Vector<String>();
	Vector<String> dName = new Vector<String>();
	JLabel ye, mo, da;
	FlowLayout f = new FlowLayout(FlowLayout.LEFT);

	JRadioButton rbM, rbF;
	JButton backBtn;
	public JButton createAct;
	JLabel blank, blank1, lab1, lab2, lab3, hyp1, hyp2;
	JLabel b1, b2, b3;
	static public JTextField emailF, pswF, nameF, phoneNF, addrF;

	JLabel lbl1, lbl2, lbl3, lbl4;

	CreateAccount() {
		init();
	}

	ShoppingSiteDbHelper dh = new ShoppingSiteDbHelper();

	void init() {
		// 윗 패널
		upPnl();
		// 아래 패널
		// downPnl();
		// 패널 (위,왼쪽,아래,오른쪽) 공백 주기
		createAPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
		// 패널 백그라운드 색 바꾸기
		createAPanel.setBackground(Color.white);

		// 아래 패널 버튼 리스너
		// 윗 패널 버튼 리스너
		createAct.addActionListener(new btnSignup());

	}

	void upPnl() {
		createAPanel = new JPanel();

		createAPanel.setLayout(new GridLayout(18, 1));
		btPanel = new JPanel(f);
		btPanel.setBackground(Color.white);
		backBtn = new JButton(backIcon);
		btPanel.add(backBtn);
		backBtn.setBackground(Color.white);
		backBtn.setOpaque(true);
		backBtn.setBorderPainted(false);
		lab1 = new JLabel("개인 정보");
		blank = new JLabel();
		lbl1 = new JLabel("이메일");
		emailF = new JTextField();
		emailF.setSize(130, 300);
		lbl2 = new JLabel("비밀번호");
		pswF = new JTextField();
		pswF.setSize(130, 300);
		lbl3 = new JLabel("이름");
		nameF = new JTextField();
		nameF.setSize(130, 300);
		lab2 = new JLabel("생년월일");
		birthPnl = new JPanel();
		birthPnl.setBackground(Color.white);
		yCombo = new JComboBox();
		mCombo = new JComboBox();
		dCombo = new JComboBox();
		for (int i = 2022; i >= 1900; i--) { // 년
			yCombo.addItem(i);
		}
		for (int j = 1; j < 13; j++) { // 월
			mCombo.addItem(j);
		}
		for (int k = 1; k <= 31; k++) { // 일
			dCombo.addItem(k);
		}

		ye = new JLabel("년");
		mo = new JLabel("월");
		da = new JLabel("일");
		birthPnl.add(yCombo);
		birthPnl.add(ye);
		birthPnl.add(mCombo);
		birthPnl.add(mo);
		birthPnl.add(dCombo);
		birthPnl.add(da);

		lab3 = new JLabel("핸드폰 번호 (- 제외하고 입력)");

		phoneNF = new JTextField();
		lbl4 = new JLabel("주소");
		addrF = new JTextField();
		JLabel lGender = new JLabel("성별");
		rbM = new JRadioButton("남성");
		rbF = new JRadioButton("여성");
		rbM.setBackground(Color.white); // 버튼 색 넣어준다(불투명 상태)
		rbF.setBackground(Color.white);
		rbM.setBorderPainted(false);
		rbM.setFocusPainted(false);
		rbF.setBorderPainted(false);
		rbF.setFocusPainted(false);
		ButtonGroup bgGender = new ButtonGroup();
		bgGender.add(rbM);
		bgGender.add(rbF);
		JPanel pGender = new JPanel(new GridLayout(1, 3));
		pGender.setBackground(new Color(255, 0, 0, 0)); // 패널 투명하게
		pGender.add(lGender);
		pGender.add(rbM);
		pGender.add(rbF);

		createAct = new JButton("계정생성");
		// JButton 색상 white로 변경
		createAct.setForeground(Color.white);
		createAct.setBackground(Color.black);
		// JButton 투명하게
		createAct.setOpaque(true);
		// JButton의 외곽석 없애준다.
		b1 = new JLabel();

		// JButton 색상 white로 변경
		blank.setBackground(Color.white);
		lab1.setBackground(Color.white);
		// JButton 투명하게
		blank.setOpaque(true);
		lab1.setOpaque(true);

		// 버튼 크기 조정

		createAPanel.add(btPanel);
		createAPanel.add(lab1);
		createAPanel.add(lbl1);
		createAPanel.add(emailF);
		createAPanel.add(lbl2);
		createAPanel.add(pswF);
		createAPanel.add(lbl3);
		createAPanel.add(nameF);
		createAPanel.add(lab2);
		createAPanel.add(birthPnl);
		createAPanel.add(lab3);
		createAPanel.add(phoneNF);
		createAPanel.add(lbl4);
		createAPanel.add(addrF);
		createAPanel.add(pGender);
		createAPanel.add(b1);
		createAPanel.add(createAct);
		createAPanel.add(blank);
		backBtn.setPreferredSize(new Dimension(30,20));
		backBtn.setFocusable(false);

	}

	class Key extends KeyAdapter {
		public void KeyPressed(KeyEvent ke) {
			int key = ke.getKeyCode();
			if (key == KeyEvent.VK_ENTER) {
				createAct.doClick();
			}
		}
	}

	class btnSignup implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			String email = emailF.getText().trim(); // 이메일
			String psw = pswF.getText().trim(); // 비밀번호
			String name = nameF.getText().trim(); // 이름
			String birth = combineDate(); // 생일
			String phoneN = phoneNF.getText().trim(); // 휴대폰번호
			String addr = addrF.getText().trim(); // 주소

			String gender = null; // 성별

			if (rbM.isSelected())
				gender = "남성";
			if (rbF.isSelected())
				gender = "여성";

			if (ae.getSource() == createAct) { // '계정등록'버튼을 누름
				if (!emailF.getText().trim().isEmpty() && emailF.getText().trim().contains("@") == false) { // 이메일에 @가
																											// 안들어가 있으면
																											// 다시
																											// 입력하라고한다
					dh.showBox("이메일 형식이 잘못되었습니다. 다시 입력하세요.", "INFORMATION_MESSAGE");
				} else if (emailF.getText().trim().isEmpty()) {
					dh.showBox("이메일을 입력하세요.", "INFORMATION_MESSAGE");
				} else if (!emailF.getText().trim().isEmpty()) {
					if (pswF.getText().trim().isEmpty()) {
						dh.showBox("비밀번호를 입력하세요.", "INFORMATION_MESSAGE");
						/// **********
						pswF.requestFocus();
					} else if (!pswF.getText().trim().isEmpty()) {
						if (nameF.getText().trim().isEmpty()) {
							dh.showBox("이름을 입력하세요.", "INFORMATION_MESSAGE");
							nameF.requestFocus();
						} else if (!nameF.getText().trim().isEmpty()) {
							if (phoneNF.getText().trim().isEmpty()) {
								dh.showBox("휴대폰번호를 입력하세요.", "INFORMATION_MESSAGE");
								phoneNF.requestFocus();
							} else if (!phoneNF.getText().trim().isEmpty()) {
								if (addrF.getText().trim().isEmpty()) {
									dh.showBox("주소를 입력하세요.", "INFORMATION_MESSAGE");
									addrF.requestFocus();
								} else if (!addrF.getText().trim().isEmpty()) {
									if (rbM.isSelected() == false && rbF.isSelected() == false) {
										dh.showBox("성별을 클릭하세요.", "INFORMATION_MESSAGE");
									} else if (rbM.isSelected() || rbF.isSelected()) {
										if (dh.isAlreadyId(email)) {
											dh.insertMember(email, psw, name, gender, birth, phoneN, addr); 
																											
											setVisible(false);
											// new CreateAccount(); ************ 로그인 페이지로 이동한다
										} else {
											// showBox("이미 존재하는 휴대폰번호입니다","회원가입");
											dh.setFieldEmpty();
										}
									}
								}
							}
						}
					}

				}
			}
		}

	}

	String combineDate() {
		String birth = null;
		birth = yCombo.getSelectedItem().toString() + "/" + mCombo.getSelectedItem().toString() + "/"
				+ dCombo.getSelectedItem().toString();
		return birth.trim();
	}

}
