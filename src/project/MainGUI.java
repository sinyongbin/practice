package project;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;
import javax.swing.Timer;
import javax.swing.table.*;

public class MainGUI extends JFrame implements ActionListener, FocusListener, KeyListener {

	ShoppingSiteDbHelper dh = new ShoppingSiteDbHelper();

	// 패널 구현
	Container cp, cp1;
	JPanel bottomP;

	// *홈패널 컴포넌트*
	JPanel homeP;
	JButton main1, main2;
	ImageIcon img1 = new ImageIcon("main_images/main1.png");
	ImageIcon img2 = new ImageIcon("main_images/main2.png");
	Timer timer;
	private boolean isMain1Visible = true;

	// *** 공통된 하단 버튼 컴포넌트
	JButton menuButton;
	JButton bIcon[] = new JButton[4];
	String path[] = { "icons/home.png", "icons/search.png", "icons/myAccount.png", "icons/cart.png" };

	// *검색 컴포넌트*
	JPanel searchP;
	Vector<String> manname = new Vector<String>();
	Vector<String> manprice = new Vector<String>();
	Vector<String> womanname = new Vector<String>();
	Vector<String> womanprice = new Vector<String>();
	Vector<JButton> manSearchBtn = new Vector<JButton>();
	Vector<JButton> womanSearchBtn = new Vector<JButton>();
	JPanel upPnl, p1, p2, p3;
	JButton WBtn, MBtn, SBtn;
	JTextField textF;
	JPanel middelPnl;
	JPanel mInPnl[] = new JPanel[6];
	JTextArea btnTxt[] = new JTextArea[6];
	JLabel label, lb1, lb2, lb3, lb4, lb5;
	JButton btn[] = new JButton[6];
	JScrollPane scroll;

	// *메뉴 컴포넌트*
	JPanel menuP;
	JPanel menup1_1, menup2_1, menup3_1;
	JLabel lb6, lb7;
	JButton woman, man;
	JButton menuBtn[] = new JButton[6];
	String menuName[] = { "NEW COLLECTION", "상의", "하의", "신발", "가방", "악세사리" };
	JButton downtest;
	int mark = 0;

	// *비밀번호 업데이트 컴포넌트(UpdatePwd)
	UpdatePwd up = new UpdatePwd();

	// *로그인 컴포넌트(LoginAccount)*
	LoginAccount loginA = new LoginAccount();

	// *메뉴안에 있는 컴포넌트(InMenu)*
	InMenu inMenuA = new InMenu("201", "202", "203");

	// *계정생성 버튼안에 있는 컴포넌트(CreateAccount)*
	CreateAccount createA = new CreateAccount();

	// *계정 컴포넌트(MyAccount1)-구매내역*
	JPanel accountUpPanel, accountMiddlePanel, accountInPnl;
	JPanel[] jpn = new JPanel[10];
	JPanel[] jpn1 = new JPanel[10];
	Vector<JButton> btn1 = new Vector<JButton>();
	JTextArea[] jta = new JTextArea[10];
	JButton listBtn, accountBtn;
	JScrollPane accountScroll;
	JTextArea orderndeliver;
	static int accountFlag = 1; // 나의 계정에서 장바구니 아무것도 없을 때, 하나라도 있을 때
	JLabel emptyL;
	ImageIcon emptyAccount = new ImageIcon("icons/empty.png");
	Vector<String> dateName = new Vector<String>();
	Vector<String> pdName = new Vector<String>();
	Vector<Integer> pdSitu = new Vector<Integer>();
	Vector<Integer> pdPrice = new Vector<Integer>();
	Vector<String> pdDate = new Vector<String>();
	Vector<Integer> pdSeq = new Vector<Integer>();
	Vector<Integer> pdQty = new Vector<Integer>();
	Vector<Integer> SumpdPrice = new Vector<Integer>();

	// *계정 컴포넌트(MyAccount2)-계정*
	JPanel accountUpPanel2, accountMiddlePanel2;
	JButton listBtn2, accountBtn2;
	// 중간 패널
	JPanel mmPnel[] = new JPanel[8];
	JLabel nameLbl;
	JLabel emailLbl1;
	JLabel emailLbl2;
	JLabel pwdLbl;
	JLabel addrLb1, addrLb2;
	JPanel blank1, blank2;
	JButton addrBtn, pwdBtn, outBtn;
	ImageIcon pwd = new ImageIcon("icons/pwd.png");
	ImageIcon search = new ImageIcon("icons/search.png");
	JScrollPane accountScroll2;
	String lname, lemail, laddr, lpwd; // 로그인후 로그인한 사람의 이름, 이메일, 주소
	int rowSize = 200, culSize = 300;

	// ****ProductDetail 상품 정보
	JPanel prod1, prod2, prod3;
	JPanel productUpPnl, productUpPnlLeft, productUpPnlRight, productMdlPnl, downPnl, downPnlup, downPnldown;
	JPanel productM1;
	GridBagConstraints gbc;
	static JPanel[] jpn10 = new JPanel[3];
	static JButton[] btn10 = new JButton[3];
	static JTextArea[] jta10 = new JTextArea[3];
	Vector<String> SizeText = new Vector<String>();
	String idx1, idx2, idx3, idx4;
	JTextArea show;
	JButton addButton, exit, wishButton;
	JScrollPane proDetailScroll;
	ImageIcon prodImgs[] = new ImageIcon[3];
	String itemName, itemPrice, itemDesc, itemColor, itemCode;
	JPanel bottomPnl = new JPanel();
	ProductDetail pd = new ProductDetail("없음");

	// *장바구니 컴포넌트(Cart)
	Cart newCart = new Cart("");
	Vector<String> purchaseName = new Vector<String>();

	// *구매내역 컴포넌트(MyAccount1)
	Vector<JButton> phButton = new Vector<JButton>();
	Vector<JPanel> phPanel = new Vector<JPanel>();
	Vector<JTextArea> phDesc = new Vector<JTextArea>();

	// *내가 찜한 상품 컴포넌트(Wish)
	Wish newWish = new Wish("");

	// product_detail flag(검색 or 메뉴)
	int pdFlag = 0;

	// 로그인 확인하는 변수
	int loginFlag = 0;
	String loginEmail = null;

	int check = 0;

	public MainGUI() {
		init();
	}

	void init() {

		bottomButtonsP(); // 하단의 버튼 생성

		createPanels(); // 모든 패널 만들기

		Runnable doScroll = new Runnable() {
			public void run() {
				scroll.getVerticalScrollBar().setValue(0);
			}
		};
		SwingUtilities.invokeLater(doScroll);
		main1.addActionListener(this);
		main2.addActionListener(this);
		// 아래 버튼 리스너
		bIcon[0].addActionListener(this); // '홈'버튼
		bIcon[1].addActionListener(this); // '검색'버튼
		menuButton.addActionListener(this); // '메뉴'버튼
		bIcon[2].addActionListener(this);// '계정'버튼
		bIcon[3].addActionListener(this);// '장바구니'버튼

		// 검색 버튼 리스너 (여셩, 남성버튼)
		WBtn.addActionListener(this);
		MBtn.addActionListener(this);

		// '계정'버튼 , 버튼 리스너 (구매내역, 계정 버튼)
		listBtn.addActionListener(this); // '구매내역'버튼
		accountBtn.addActionListener(this); // '구매내역'버튼
		listBtn2.addActionListener(this); // '계정'버튼
		accountBtn2.addActionListener(this);// '계정'버튼

		// '메뉴'버튼, 버튼 리스너
		for (int i = 1; i < menuBtn.length; i++) {
			menuBtn[i].addActionListener(this);
		}
		// '메뉴'에서 woman, man 버튼
		woman.addActionListener(this);
		man.addActionListener(this);

		for (int i = 0; i < manSearchBtn.size(); i++) {
			manSearchBtn.get(i).addActionListener(this);
		}
		for (int i = 0; i < womanSearchBtn.size(); i++) {
			womanSearchBtn.get(i).addActionListener(this);
		}

		// '계정'버튼에서 첫 로그인 페이지에서 '계정등록 버튼'
		loginA.create.addActionListener(this);
		loginA.login.addActionListener(this);

		// CreateAccount 에서 '뒤로가기'버튼
		createA.backBtn.addActionListener(this);

		// '검색'에서 텍스트필드 지우기, 키리스너 추가
		textF.addFocusListener(this);
		textF.addKeyListener(this);

		// '비밀번호 변경'에서 뒤로가기, 수정하기 버튼
		up.backBtn.addActionListener(this);
		up.update.addActionListener(this);
		up.addFocusListener(this);

		// '장바구니'에서 닫기 버튼
		// cart.closeBtn.addActionListener(this);
		newCart.cartBtn.addActionListener(this);
		newCart.jjim.addActionListener(this);
		newCart.closeBtn.addActionListener(this);

		// '찜하기'에서 '장바구니'버튼
		newWish.cart.addActionListener(this);
		// '찜하기'에서 '상품'버튼
		for (int i = 0; i < newWish.wishBtn.size(); i++) {
			newWish.wishBtn.get(i).addActionListener(this);
		}

		setUI();
	}

	public void actionPerformed(ActionEvent ae) {
		// 하단 공통 버튼
		if (ae.getSource() == bIcon[0]) {
			setHomeP();
		} else if (ae.getSource() == bIcon[1]) {
			setSearchP();
		} else if (ae.getSource() == menuButton) {
			setmenuP();
		} else if (ae.getSource() == bIcon[2]) {
			if (loginFlag == 0) {
				setLoginP();
			} else if (loginFlag == 1) {
				for (int j = 0; j < 1; j++) {
					createAccount1();
				}
				setAccount1P();
			}
		} else if (ae.getSource() == bIcon[3]) {
			if (loginFlag == 1) {
				newCart = new Cart(loginEmail);
				System.out.println(newCart.cartFlag);
				if (newCart.cartFlag == 0) {
					setCart1();
				} else {
					setCart2();
				}
			} else if (loginFlag == 0) {
				System.out.println("loginFlag: " + loginFlag);
				setBlankCart();
			}
		}
		//main1,2 버튼
		if(ae.getSource() == main1) {
			menuButton.doClick();
		}
		if(ae.getSource() == main2) {
			menuButton.doClick();
		}
		// '검색'에서 여성, 남성 버튼
		WBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textF.addKeyListener(new KeyAdapter() {
					public void keyPressed(KeyEvent e) {
						textF = (JTextField) e.getSource();
						String searchText = textF.getText();
						if (searchText.equals(textF.getText())) {
							wms(searchText);
						}
					}
				});
			}
		});

		MBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textF.addKeyListener(new KeyAdapter() {
					public void keyPressed(KeyEvent e) {
						textF = (JTextField) e.getSource();
						String searchText1 = textF.getText();
						if (searchText1.equals(textF.getText())) {
							ms(searchText1);
						}
					}
				});
			}
		});

		// '검색'에서 여성, 남성 버튼
		if (ae.getSource() == WBtn) {
			WBtn.setBackground(Color.yellow);
			MBtn.setBackground(Color.white);
			// JButton 투명하게
			WBtn.setOpaque(true);
			// JButton의 외곽석 없애준다.
			WBtn.setBorderPainted(false);
			Swoman();
			Runnable doScroll = new Runnable() {
				public void run() {
					scroll.getVerticalScrollBar().setValue(0);
				}
			};
			SwingUtilities.invokeLater(doScroll);

		} else if (ae.getSource() == MBtn) {
			WBtn.setBackground(Color.white);
			MBtn.setBackground(Color.yellow);
			MBtn.setOpaque(true);
			MBtn.setBorderPainted(false);
			Sman();
			Runnable doScroll = new Runnable() {
				public void run() {
					scroll.getVerticalScrollBar().setValue(0);
				}
			};
			SwingUtilities.invokeLater(doScroll);
		}

		// '메뉴'에서 WOMAN, MAN 버튼
		if (ae.getSource() == woman) {
			for (int i = 0; i < menuBtn.length; i++) {
				// 버튼 색상 바꾸기
				menuBtn[i].setBackground(Color.yellow);
				menuBtn[i].setOpaque(true);
				menuBtn[i].setBorderPainted(false);
			}
			mark = 0; // mark=0일때 여자탭 실행
			setmenuP();
		} else if (ae.getSource() == man) {
			for (int i = 0; i < menuBtn.length; i++) {
				// 버튼 색상 바꾸기
				menuBtn[i].setBackground(Color.gray);
				menuBtn[i].setOpaque(true);
				menuBtn[i].setBorderPainted(false);
			}
			mark = 1;// mark=1일때 여자탭 실행
			setmenuP();
		}

		// '검색'에서 남자탭에서 상세제품 누를때
		for (int i = 0; i < manSearchBtn.size(); i++) {
			if (ae.getSource() == manSearchBtn.get(i)) {
				pd = new ProductDetail(manname.get(i));
				itemName = pd.itemName;
				itemPrice = pd.itemPrice;
				itemDesc = pd.itemDesc;
				itemColor = pd.itemColor;
				itemCode = pd.itemCode;
				createProductDetail();
				upPnl.setVisible(false);
				scroll.setVisible(false);
				bottomP.setVisible(false);
				setProductDetailP();
				pdFlag = 0;

			}
		}
		// '검색'에서 여자탭에서 상세제품 누를때
		for (int i = 0; i < womanSearchBtn.size(); i++) {
			if (ae.getSource() == womanSearchBtn.get(i)) {
				pdFlag = 0;
				pd = new ProductDetail(womanname.get(i));
				itemName = pd.itemName;
				itemPrice = pd.itemPrice;
				itemDesc = pd.itemDesc;
				itemColor = pd.itemColor;
				itemCode = pd.itemCode;

				createProductDetail();
				upPnl.setVisible(false);
				scroll.setVisible(false);
				bottomP.setVisible(false);
				setProductDetailP();

			}
		}

		// '계정'에서 구매내역, 계정 버튼
		if (ae.getSource() == listBtn) {
			setAccount1P();
		} else if (ae.getSource() == accountBtn) {
			info(loginEmail);
			createAccount2();
			setAccount2P();
		} else if (ae.getSource() == listBtn2) {
			setAccount1P();
		} else if (ae.getSource() == accountBtn2) {
			setAccount2P();
		} else if (ae.getSource() == outBtn) {
			if (loginA.loginFlag == 1) {
				loginFlag = 0;
				loginA.emailF.setText("");
				loginA.passwordF.setText("");
				setLoginP();
			}
		} else if (ae.getSource() == pwdBtn) {
			setupdatePwd();

		}
		// 비밀번호변경 페이지에서 뒤로가기 버튼
		if (ae.getSource() == up.backBtn) {
			bottomP.setVisible(true);
			setAccount2P();
			up.uup1.setVisible(false);
			up.uup2.setVisible(false);
			// 비밀번호 수정 버튼
		} else if (ae.getSource() == up.update) {
			if (!loginA.myPass.equals(up.curPwdField.getText())) {
				JOptionPane.showMessageDialog(null, "현재 비밀번호가 일치하지 않습니다!!");
				JOptionPane.showMessageDialog(null, "비밀번호를 다시 입력하세요!!");
				up.uup1.setVisible(true);
				up.uup2.setVisible(true);
				up.addFocusListener(this);
				up.curPwdField.setText("현재 비밀번호");
				up.newPwdField.setText("새 비밀번호");
				up.confirmPwdField.setText("새 비밀번호 확인");
			} else if (up.curPwdField.getText().equals(up.newPwdField.getText())) {
				JOptionPane.showMessageDialog(null, "현재 비밀번호와 수정하려는 비밀번호가 같습니다!!");
				JOptionPane.showMessageDialog(null, "비밀번호를 다시 입력하세요!!");
				up.uup1.setVisible(true);
				up.uup2.setVisible(true);
				up.addFocusListener(this);
				up.curPwdField.setText("현재 비밀번호");
				up.newPwdField.setText("새 비밀번호");
				up.confirmPwdField.setText("새 비밀번호 확인");
			} else if (!up.newPwdField.getText().equals(up.confirmPwdField.getText())) {
				JOptionPane.showMessageDialog(null, "비밀번호 확인이 일치하지 않습니다!!");
				JOptionPane.showMessageDialog(null, "비밀번호를 다시 입력하세요!!");
				up.uup1.setVisible(true);
				up.uup2.setVisible(true);
				up.addFocusListener(this);
				up.curPwdField.setText("현재 비밀번호");
				up.newPwdField.setText("새 비밀번호");
				up.confirmPwdField.setText("새 비밀번호 확인");
			} else {
				pwd(up.newPwdField.getText(), up.curPwdField.getText(), loginEmail);
				JOptionPane.showMessageDialog(null, "비밀번호 수정이 완료되었습니다!!");
				bottomP.setVisible(true);
				setAccount2P();
				up.uup1.setVisible(false);
				up.uup2.setVisible(false);
				up.addFocusListener(this);
				up.curPwdField.setText("현재 비밀번호");
				up.newPwdField.setText("새 비밀번호");
				up.confirmPwdField.setText("새 비밀번호 확인");
			}
		}

		// '메뉴'에서 세부 메뉴 탭으로 들어갈때
		// 해당 컬렉션에 있는 버튼을 클릭하면 InMenu로 이동한다
		if (ae.getSource() == menuBtn[1] && mark == 0) {
			inMenuA = new InMenu("201", "202", "203");
			setInMenuP();
		} else if (ae.getSource() == menuBtn[2] && mark == 0) {
			inMenuA = new InMenu("401", "402", "403");
			setInMenuP();
		} else if (ae.getSource() == menuBtn[3] && mark == 0) {
			inMenuA = new InMenu("601", "602", "603");
			setInMenuP();
		} else if (ae.getSource() == menuBtn[4] && mark == 0) {
			inMenuA = new InMenu("801", "802", "803");
			setInMenuP();
		} else if (ae.getSource() == menuBtn[5] && mark == 0) {
			inMenuA = new InMenu("1001", "1002", "1003");
			setInMenuP();
		} else if (ae.getSource() == menuBtn[1] && mark == 1) {
			inMenuA = new InMenu("101", "102", "103");
			setInMenuP();
		} else if (ae.getSource() == menuBtn[2] && mark == 1) {
			inMenuA = new InMenu("301", "302", "303");
			setInMenuP();
		} else if (ae.getSource() == menuBtn[3] && mark == 1) {
			inMenuA = new InMenu("501", "502", "503");
			setInMenuP();
		} else if (ae.getSource() == menuBtn[4] && mark == 1) {
			inMenuA = new InMenu("701", "702", "703");
			setInMenuP();
		} else if (ae.getSource() == menuBtn[5] && mark == 1) {
			inMenuA = new InMenu("901", "902", "903");
			setInMenuP();
		}
		// 인메뉴에서 '뒤로가기'버튼 누르는 리스너
		inMenuA.backBtn.addActionListener(this);
		if (ae.getSource() == inMenuA.backBtn) {
			setmenuP();
		}

		// 상품 클릭 버튼 리스너 InMenu 클릭해서 필드에 해당 상품별 Detail을 뜨게 한다
		for (int i = 0; i < inMenuA.productButton.size(); i++) {
			inMenuA.productButton.get(i).addActionListener(this);
		}

		for (int i = 0; i < inMenuA.productButton.size(); i++) { // 상품 버튼 누르면 해당 상품으로 이동하는 리스너 //초기값
			if (ae.getSource() == inMenuA.productButton.get(i)) {
				check++;

				pd = new ProductDetail(inMenuA.itemName.get(i));
				itemName = pd.itemName;
				itemPrice = pd.itemPrice;
				itemDesc = pd.itemDesc;
				itemColor = pd.itemColor;
				itemCode = pd.itemCode;
				createProductDetail();
				setProductDetailP();
				pdFlag = 1;

			}
		}

		for (int i = 0; i < inMenuA.filterButton.size(); i++) {
			inMenuA.filterButton.get(i).addActionListener(this);
		}
		if (ae.getSource() == inMenuA.filterButton.get(0)) { // 필터 '모두보기' 버튼 누르면 해당되는 제품이 '중간패널'에 보이도록 구현
			inMenuA.getFilterSelect2(inMenuA.idx1, inMenuA.idx2, inMenuA.idx3);
			Runnable doScroll = new Runnable() {
				public void run() {
					inMenuA.inMenuScroll.getVerticalScrollBar().setValue(0);
				}
			};
			SwingUtilities.invokeLater(doScroll);
		}

		for (int k = 1; k < inMenuA.filterButton.size(); k++) { // 필터 버튼 누르면 해당되는 제품이 '중간패널'에 보이도록 구현
			if (ae.getSource() == inMenuA.filterButton.get(k)) {
				inMenuA.getFilterSelect1(inMenuA.filterName.get(k));
			}
			Runnable doScroll = new Runnable() {
				public void run() {
					inMenuA.inMenuScroll.getVerticalScrollBar().setValue(0);
				}
			};
			SwingUtilities.invokeLater(doScroll);
		}

		for (int i = 0; i < inMenuA.productButton2.size(); i++) {
			inMenuA.productButton2.get(i).addActionListener(this);
		}

		for (int j = 0; j < inMenuA.productButton2.size(); j++) { // 상품 버튼 누르면 해당 상품으로 이동하는 리스너
			if (ae.getSource() == inMenuA.productButton2.get(j)) {
				pd = new ProductDetail(inMenuA.itemName2.get(j));
				itemName = pd.itemName;
				itemPrice = pd.itemPrice;
				itemDesc = pd.itemDesc;
				itemColor = pd.itemColor;
				itemCode = pd.itemCode;
				createProductDetail();
				setProductDetailP();
				pdFlag = 1;
			}
		}

		// ProductDetail1에서 '뒤로가기'버튼입니다
		exit.addActionListener(this);
		;
		if (ae.getSource() == exit) {
			if (pdFlag == 0) {
				productUpPnl.setVisible(false);
				productM1.setVisible(false);
				bottomP.setVisible(true);
				setSearchP();
			} else {
				productUpPnl.setVisible(false);
				productM1.setVisible(false);
				bottomP.setVisible(true);
				setInMenuP();
			}

		}
		// ProductDetail1에서 '추가'버튼입니다
		addButton.addActionListener(this);
		if (ae.getSource() == addButton) {
			// 추가 버튼에서 로그인 확인 loginFlag: 0이면 로그인 안됨/ 1이면 로그인 된 상태
			if (loginFlag == 0) { // 0이면 로그인 안됨, 로그인하라는 창 띄우고, 계정 로그인창으로 이동
				JOptionPane.showMessageDialog(null, "로그인 후에 이용해주세요");
			} else if (loginFlag == 1) { // 1이면 로그인 된 상태
				pd.productM1(itemCode);
				productUpPnl.setVisible(false);
				productM1.setVisible(false);
				setInMenuP();
			}
		}

		// LoginAccount 클래스에서 '계정생성' 버튼 클릭할 때
		if (ae.getSource() == loginA.create) {
			setCreateA();
		}
		// LoginAccount 클래스에서 '로그인' 버튼 클릭할 때
		loginA.myPass = String.valueOf(loginA.passwordF.getPassword());

		if (ae.getSource() == loginA.login) { // '로그인' 버튼
			if (loginA.emailF.getText().trim().isEmpty()) { // '아이디 입력안했을때'
				JOptionPane.showMessageDialog(null, "이메일을 입력하세요", "INFORMATION_MESSAGE",
						JOptionPane.INFORMATION_MESSAGE);
				loginA.emailF.requestFocus();
				loginA.loginFlag = 0;
			} else if (!loginA.emailF.getText().trim().isEmpty() && loginA.myPass.isEmpty()) { // 비밀번호 입력 안했을 때
				JOptionPane.showMessageDialog(null, "비밀번호를 입력하세요", "INFORMATION_MESSAGE",
						JOptionPane.INFORMATION_MESSAGE);
				loginA.passwordF.requestFocus();
				loginA.loginFlag = 0;
			} else if (!loginA.emailF.getText().trim().isEmpty() && !loginA.myPass.isEmpty()) {
				String email = loginA.emailF.getText().trim();
				LoginAccount.email = email;
				LoginAccount.name = loginA.dh.getName(email);
				loginA.loginFlag = 0;
				if (loginA.dh.checkId(email) && loginA.dh.checkPw(email, loginA.myPass)) { // 이메일과 비밀번호가 일치할 때 로그인하고 나서
																							// 화면 띄우기
					loginA.loginFlag = 1;
					// 로그인 확인하는 변수에 넣어주는구문
					this.loginFlag = loginA.loginFlag;
					this.loginEmail = email;
					// purchase에서 구매내역 있는지 확인하고 없으면 accountFlag=0, 있으면 =1
					dh.checkAccountFlag(loginEmail);
					setHomeP();
				}
			}
		}

		// CreateAccount 클래스에서 '뒤로가기' 버튼 클릭할 때
		if (ae.getSource() == createA.backBtn) {
			// LoginAccount 클래스로 이동
			setLoginP();
		}

		// Cart에서 '삭제' 버튼 리스너
		for (int i = 0; i < newCart.cartDelete.size(); i++) {
			newCart.cartDelete.get(i).addActionListener(this);
		}
		// Cart에서 '저장' 버튼 리스너
		for (int i = 0; i < newCart.cartSave.size(); i++) {
			newCart.cartSave.get(i).addActionListener(this);
		}

		for (int i = 0; i < newCart.cartDelete.size(); i++) { // '삭제' 버튼을 누를때
			if (ae.getSource() == newCart.cartDelete.get(i)) { // db에서 사라지고 다시 패널에서 호출 되도록 해야함
				newCart.deleteItem(newCart.pName.get(i), newCart.memberEmail);
				int pRice = Integer.parseInt(newCart.pPrice.get(i));
				int sss = Integer.parseInt(newCart.pQty.get(i));
				sss++;
				String rrr = Integer.toString(sss);
				newCart.pQty.set(i, rrr);
				//System.out.println("pQty.get(k): " + newCart.pQty.get(i));
				Cart.totaltotal -= pRice;
				newCart.cost.setText("\t\t" + Cart.totaltotal + " 원");
				System.out.println("삭제 되었습니다.");
				if (Cart.totaltotal == 0) {
					newCart.cartFlag = 0;
					newCart.cartUpPanel.setVisible(false);
					newCart.cartScroll.setVisible(false);
					newCart.cartDownPnl.setVisible(false);
					bIcon[3].doClick();
				} else {
					newCart.cartUpPanel.setVisible(false);
					newCart.cartScroll.setVisible(false);
					newCart.cartDownPnl.setVisible(false);
					bIcon[3].doClick();
				}
			}
		}

		for (int i = 0; i < newCart.cartSave.size(); i++) { // '저장' 버튼을 누를때
			if (ae.getSource() == newCart.cartSave.get(i)) {
				// Wish에 존재하는지 확인한다
				newCart.getWishItems(newCart.memberEmail);

				for (int k = 0; k < newCart.nameInWish.size(); k++) { // '마음에 드는 상품'에 이미 있으면 아래 팝업창이 나타나고 wish테이블에 저장안된다
					if (newCart.pName.get(i).equals(newCart.nameInWish.get(k))) {
						JOptionPane.showMessageDialog(null, "이미 '찜한상품'이 있습니다", "INFORMATION_MESSAGE",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
				}

				// 1. Cart 테이블에서 삭제 한다 (DB도 삭제한다)
				newCart.deleteItem(newCart.pName.get(i), newCart.memberEmail);
				// 2.WISH 테이블에 저장 한다
				newCart.insertWishItem(newCart.cSec.get(i), newCart.pCode.get(i), newCart.pSize.get(i),
						newCart.memberEmail);
				System.out.println("WISH에 저장되었습니다");
				if (newCart.cartSave.size() == 1) {
					newCart.cartFlag = 0;
					newCart.cartUpPanel.setVisible(false);
					newCart.cartScroll.setVisible(false);
					newCart.cartDownPnl.setVisible(false);
					bIcon[3].doClick();
				} else {
					newCart.cartUpPanel.setVisible(false);
					newCart.cartScroll.setVisible(false);
					newCart.cartDownPnl.setVisible(false);
					newCart = new Cart(loginEmail);
					setCart2();
				}

			}
		}

		if (ae.getSource() == newCart.payment) { // '계속'버튼 클릭할 때
			for (int j = 0; j < newCart.cSec.size(); j++) { // 'Cart'안에 들어 있는 제품 모두 검색해서 purchase에 insert한다
				// (1)장바구니에서 해당 상품의 이름과 사이즈를 검색해서 현재 주문한 수량보다 적으면 팝업창 띄우고 purchase로 넘어가게하면안됨
				

				newCart.checkGoodsQty(newCart.pName.get(j), newCart.pSize.get(j));
			}
			for (int k = 0; k < newCart.goodsQty.size(); k++) {
				if (newCart.goodsQty.get(k) - Integer.parseInt(newCart.pQty.get(k)) <= 0) {
					JOptionPane.showMessageDialog(null, "죄송합니다. 재고가 충분하지 않습니다.", "INFORMATION_MESSAGE",
							JOptionPane.INFORMATION_MESSAGE);
					newCart.cartUpPanel.setVisible(false);
					newCart.cartScroll.setVisible(false);
					newCart.cartDownPnl.setVisible(false);
					bIcon[3].doClick();
					return;

				} else {
					int qty = newCart.goodsQty.get(k) - Integer.parseInt(newCart.pQty.get(k));
					newCart.goodsQty.set(k, qty); // 마이너스 한 만큼 재고 수량이 바뀜
					System.out.println("모든 재고 수량이 충분합니다.");
				}
			}

			for (int i = 0; i < newCart.goodsQty.size(); i++) {
				System.out.println(newCart.pName.get(i) + "수량체크:" + newCart.goodsQty.get(i));
			}
			// (2) goods 테이블 과 size 테이블에 해당 사이즈 제품 수량 마이너스 되야하고
			for (int k = 0; k < newCart.goodsQty.size(); k++) {
				System.out.println(newCart.goodsQty.get(k) + newCart.pName.get(k));
				newCart.updateGoodsQty(newCart.goodsQty.get(k), newCart.pName.get(k), newCart.pSize.get(k));
				purchaseName.add(newCart.pName.get(k));
			}

			for (String name : purchaseName) // Enhanced Loop:
				System.out.println("purchaseName확인" + name);

			// (3) purchase_detail에 추가 되어야 함
			for (int k = 0; k < newCart.cSec.size(); k++) {
				int cSec = Integer.parseInt(newCart.cSec.get(k));
				int pCode = Integer.parseInt(newCart.pCode.get(k));
				int pQty = Integer.parseInt(newCart.pQty.get(k));
				newCart.insertPurchaseDetail(pQty, cSec, pCode, newCart.pSize.get(k), newCart.memberEmail);

			}

			// (4)purchase_detail에서 장바구니seq 와 이메일로 pd_seq를 뽑아내야함
			for (int k = 0; k < newCart.cSec.size(); k++) {
				int cSec = Integer.parseInt(newCart.cSec.get(k));
				newCart.selectPDetailCode(cSec, newCart.memberEmail);
			}

			// (5)상세 주문 테이블에도 추가 되어야 함

			for (int k = 0; k < newCart.cSec.size(); k++) {
				int cSec = Integer.parseInt(newCart.cSec.get(k));
				int pPrice = Integer.parseInt(newCart.pPrice.get(k));
				// newCart.insertPurchase(cSec, Cart.totaltotal, newCart.statement,
				// newCart.memberEmail, newCart.pCode.get(k));
				newCart.insertPurchase(cSec, pPrice, newCart.memberEmail, newCart.pdSeq.get(k));
			}

			// (5)테이블에서 삭제
			newCart.cartFlag = 0;
			afterPurchase(loginA.email);// 구매시 카트 삭제 기능
			accountFlag = 1;
		}

		// Cart에서 '닫기' 버튼 누를때
		if (ae.getSource() == newCart.closeBtn) {
			if (newCart.cartFlag == 0) {
				setHomeP();
			} else {
				newCart.cartUpPanel.setVisible(false);
				newCart.cartScroll.setVisible(false);
				newCart.cartDownPnl.setVisible(false);
				bIcon[3].doClick();
				bottomP.setVisible(true);
				setHomeP();
			}
		}

		// Cart에서 '마음에 드는 제품'누를 때
		if (ae.getSource() == newCart.jjim) {
			if (newCart.cartFlag == 0) {
				newCart.cartUpPanel.setVisible(false);
				newCart.cartScroll.setVisible(false);
				newWish = new Wish(loginEmail);
				setWish();
			} else {
				newCart.cartUpPanel.setVisible(false);
				newCart.cartScroll.setVisible(false);
				newCart.cartDownPnl.setVisible(false);
				newWish = new Wish(loginEmail);
				setWish();
			}

			if (loginFlag == 0) {
				newCart.cartUpPanel.setVisible(false);
				newCart.cartScroll.setVisible(false);
				setWish();
			}

		}

		// Wish에서 '장바구니'버튼 누를때
		if (ae.getSource() == newWish.cart) {
			if (loginFlag == 1) {
				if (newCart.cartFlag == 0) {
					newWish.upPanel.setVisible(false);
					newWish.scroll.setVisible(false);
					newCart = new Cart(loginEmail);
					setCart1();

				} else {
					newWish.upPanel.setVisible(false);
					newWish.scroll.setVisible(false);
					newCart = new Cart(loginEmail);
					setCart2();
				}
			} else if (loginFlag == 0) {
				setCart1();
			}
		}

		// Wish에서 '닫기'버튼 누를때
		if (ae.getSource() == newWish.closeBtn) {
			newWish.upPanel.setVisible(false);
			newWish.scroll.setVisible(false);
			bIcon[3].doClick();
			bottomP.setVisible(true);
			setHomeP();

		}

		// wish에서 '추가'버튼 누를 때
		for (int i = 0; i < newWish.wishBtn.size(); i++) {
			if (ae.getSource() == newWish.wishBtn.get(i)) {
				// Cart에 insert되고
				newWish.insertCartItem(newWish.pSize.get(i), newWish.pCode.get(i), Wish.wishEmail);
				// wish에서 삭제되고
				newWish.deleteWishItems(newWish.wishName.get(i), Wish.wishEmail);
				System.out.println("추가 되었습니다.");
				newCart.cartFlag = 1;
				newWish.upPanel.setVisible(false);
				newWish.scroll.setVisible(false);
				newWish = new Wish(loginEmail);
				setWish();

			}
		}

	}

	void setHomeP() {
		upPnl.setVisible(false);
		scroll.setVisible(false);
		menup1_1.setVisible(false);
		menup2_1.setVisible(false);
		accountUpPanel.setVisible(false);
		accountScroll.setVisible(false);
		accountUpPanel2.setVisible(false);
		accountScroll2.setVisible(false);
		loginA.loginP.setVisible(false);
		loginA.signupP.setVisible(false);
		inMenuA.inMenuUpPanel.setVisible(false);
		inMenuA.inMenuScroll.setVisible(false);
		createA.createAPanel.setVisible(false);
		newWish.upPanel.setVisible(false);
		newWish.scroll.setVisible(false);
		if (newCart.cartFlag == 0) {
			newCart.cartUpPanel.setVisible(false);
			newCart.cartScroll.setVisible(false);
		} else {
			newCart.cartUpPanel.setVisible(false);
			newCart.cartScroll.setVisible(false);
			newCart.cartDownPnl.setVisible(false);
		}
		cp.add(homeP, BorderLayout.CENTER);
		cp.add(bottomP, BorderLayout.SOUTH);
		homeP.setVisible(true);
		bottomP.setVisible(true);
	}

	void setSearchP() {
		homeP.setVisible(false);
		menup1_1.setVisible(false);
		menup2_1.setVisible(false);
		accountUpPanel.setVisible(false);
		accountScroll.setVisible(false);
		accountUpPanel2.setVisible(false);
		accountScroll2.setVisible(false);
		loginA.loginP.setVisible(false);
		loginA.signupP.setVisible(false);
		inMenuA.inMenuUpPanel.setVisible(false);
		inMenuA.inMenuScroll.setVisible(false);
		createA.createAPanel.setVisible(false);
		if (newCart.cartFlag == 0) {
			newCart.cartUpPanel.setVisible(false);
			newCart.cartScroll.setVisible(false);
		} else {
			newCart.cartUpPanel.setVisible(false);
			newCart.cartScroll.setVisible(false);
			newCart.cartDownPnl.setVisible(false);
		}

		cp.add(upPnl, BorderLayout.NORTH);
		cp.add(scroll, BorderLayout.CENTER);
		cp.add(bottomP, BorderLayout.SOUTH);
		upPnl.setVisible(true);
		scroll.setVisible(true);
		bottomP.setVisible(true);
	}

	void setmenuP() {
		homeP.setVisible(false);
		upPnl.setVisible(false);
		scroll.setVisible(false);
		accountUpPanel.setVisible(false);
		accountScroll.setVisible(false);
		accountUpPanel2.setVisible(false);
		accountScroll2.setVisible(false);
		loginA.loginP.setVisible(false);
		loginA.signupP.setVisible(false);
		inMenuA.inMenuUpPanel.setVisible(false);
		inMenuA.inMenuScroll.setVisible(false);
		createA.createAPanel.setVisible(false);
		if (newCart.cartFlag == 0) {
			newCart.cartUpPanel.setVisible(false);
			newCart.cartScroll.setVisible(false);
		} else {
			newCart.cartUpPanel.setVisible(false);
			newCart.cartScroll.setVisible(false);
			newCart.cartDownPnl.setVisible(false);
		}
		cp.add(menup1_1, BorderLayout.NORTH);
		cp.add(menup2_1, BorderLayout.CENTER);
		cp.add(bottomP, BorderLayout.SOUTH);
		menup1_1.setVisible(true);
		menup2_1.setVisible(true);
		bottomP.setVisible(true);

	}

	void setAccount1P() {
		homeP.setVisible(false);
		upPnl.setVisible(false);
		scroll.setVisible(false);
		menup1_1.setVisible(false);
		menup2_1.setVisible(false);
		accountUpPanel2.setVisible(false);
		accountScroll2.setVisible(false);
		loginA.loginP.setVisible(false);
		loginA.signupP.setVisible(false);
		inMenuA.inMenuUpPanel.setVisible(false);
		inMenuA.inMenuScroll.setVisible(false);
		createA.createAPanel.setVisible(false);
		accountUpPanel2.setVisible(false);
		accountScroll2.setVisible(false);
		cp.add(accountUpPanel, BorderLayout.NORTH);
		cp.add(accountScroll, BorderLayout.CENTER);
		accountUpPanel.setVisible(true);
		accountScroll.setVisible(true);

	}

	void setAccount2P() {
		homeP.setVisible(false);
		upPnl.setVisible(false);
		scroll.setVisible(false);
		menup1_1.setVisible(false);
		menup2_1.setVisible(false);
		accountUpPanel.setVisible(false);
		accountScroll.setVisible(false);
		loginA.loginP.setVisible(false);
		loginA.signupP.setVisible(false);
		inMenuA.inMenuUpPanel.setVisible(false);
		inMenuA.inMenuScroll.setVisible(false);
		createA.createAPanel.setVisible(false);
		accountUpPanel.setVisible(false);
		accountScroll.setVisible(false);
		cp.add(accountUpPanel2, BorderLayout.NORTH);
		cp.add(accountScroll2, BorderLayout.CENTER);
		accountUpPanel2.setVisible(true);
		accountScroll2.setVisible(true);
	}

	void setupdatePwd() {
		homeP.setVisible(false);
		upPnl.setVisible(false);
		scroll.setVisible(false);
		accountUpPanel.setVisible(false);
		accountScroll.setVisible(false);
		accountUpPanel2.setVisible(false);
		accountScroll2.setVisible(false);
		loginA.loginP.setVisible(false);
		loginA.signupP.setVisible(false);
		createA.createAPanel.setVisible(false);
		bottomP.setVisible(false);
		if (newCart.cartFlag == 0) {
			newCart.cartUpPanel.setVisible(false);
			newCart.cartScroll.setVisible(false);
		} else {
			newCart.cartUpPanel.setVisible(false);
			newCart.cartScroll.setVisible(false);
			newCart.cartDownPnl.setVisible(false);
		}
		cp.add(up.uup1, BorderLayout.NORTH);
		cp.add(up.uup2, BorderLayout.CENTER);
		up.uup1.setVisible(true);
		up.uup2.setVisible(true);

	}

	void setLoginP() {
		homeP.setVisible(false);
		upPnl.setVisible(false);
		scroll.setVisible(false);
		menup1_1.setVisible(false);
		menup2_1.setVisible(false);
		accountUpPanel.setVisible(false);
		accountScroll.setVisible(false);
		accountUpPanel2.setVisible(false);
		accountScroll2.setVisible(false);
		inMenuA.inMenuUpPanel.setVisible(false);
		inMenuA.inMenuScroll.setVisible(false);
		createA.createAPanel.setVisible(false);
		cp.add(loginA.loginP, BorderLayout.NORTH);
		cp.add(loginA.signupP, BorderLayout.CENTER);
		cp.add(bottomP, BorderLayout.SOUTH);
		loginA.loginP.setVisible(true);
		loginA.signupP.setVisible(true);

	}

	void setInMenuP() {
		homeP.setVisible(false);
		upPnl.setVisible(false);
		scroll.setVisible(false);
		menup1_1.setVisible(false);
		menup2_1.setVisible(false);
		accountUpPanel.setVisible(false);
		accountScroll.setVisible(false);
		accountUpPanel2.setVisible(false);
		accountScroll2.setVisible(false);
		loginA.loginP.setVisible(false);
		loginA.signupP.setVisible(false);
		createA.createAPanel.setVisible(false);
		if (newCart.cartFlag == 0) {
			newCart.cartUpPanel.setVisible(false);
			newCart.cartScroll.setVisible(false);
		} else {
			newCart.cartUpPanel.setVisible(false);
			newCart.cartScroll.setVisible(false);
			newCart.cartDownPnl.setVisible(false);
		}
		cp.add(inMenuA.inMenuUpPanel, BorderLayout.NORTH);
		cp.add(inMenuA.inMenuScroll, BorderLayout.CENTER);
		cp.add(bottomP, BorderLayout.SOUTH);
		inMenuA.inMenuUpPanel.setVisible(true);
		inMenuA.inMenuScroll.setVisible(true);
		bottomP.setVisible(true);
	}

	void setProductDetailP() {
		inMenuA.inMenuUpPanel.setVisible(false);
		inMenuA.inMenuScroll.setVisible(false);
		bottomP.setVisible(false);
		if (newCart.cartFlag == 0) {
			newCart.cartUpPanel.setVisible(false);
			newCart.cartScroll.setVisible(false);
		} else {
			newCart.cartUpPanel.setVisible(false);
			newCart.cartScroll.setVisible(false);
			newCart.cartDownPnl.setVisible(false);
		}
		bottomPnl.setBackground(Color.white);
		cp.add(productUpPnl, BorderLayout.NORTH);
		cp.add(productM1, BorderLayout.CENTER);
		cp.add(bottomPnl, BorderLayout.SOUTH);
		productUpPnl.setVisible(true);
		productM1.setVisible(true);
		bottomPnl.setVisible(true);
	}

	void setCreateA() {
		homeP.setVisible(false);
		upPnl.setVisible(false);
		scroll.setVisible(false);
		menup1_1.setVisible(false);
		menup2_1.setVisible(false);
		accountUpPanel.setVisible(false);
		accountScroll.setVisible(false);
		accountUpPanel2.setVisible(false);
		accountScroll2.setVisible(false);
		loginA.loginP.setVisible(false);
		loginA.signupP.setVisible(false);
		cp.add(createA.createAPanel, BorderLayout.CENTER);
		cp.add(bottomP, BorderLayout.SOUTH);
		createA.createAPanel.setVisible(true);
	}

	void setCart1() {
		homeP.setVisible(false);
		upPnl.setVisible(false);
		scroll.setVisible(false);
		accountUpPanel.setVisible(false);
		accountScroll.setVisible(false);
		accountUpPanel2.setVisible(false);
		accountScroll2.setVisible(false);
		loginA.loginP.setVisible(false);
		loginA.signupP.setVisible(false);
		inMenuA.inMenuUpPanel.setVisible(false);
		inMenuA.inMenuScroll.setVisible(false);
		createA.createAPanel.setVisible(false);
		menup1_1.setVisible(false);
		menup2_1.setVisible(false);
		inMenuA.inMenuUpPanel.setVisible(false);
		inMenuA.inMenuScroll.setVisible(false);
		newWish.upPanel.setVisible(false);
		newWish.scroll.setVisible(false);
		bottomP.setVisible(false);
		cp.add(newCart.cartUpPanel, BorderLayout.NORTH);
		cp.add(newCart.cartScroll, BorderLayout.CENTER);
		newCart.cartUpPanel.setVisible(true);
		newCart.cartScroll.setVisible(true);
		newCart.cartBtn.addActionListener(this);
		newCart.jjim.addActionListener(this);
		newCart.closeBtn.addActionListener(this);

	}

	void setCart2() {
		homeP.setVisible(false);
		upPnl.setVisible(false);
		scroll.setVisible(false);
		accountUpPanel.setVisible(false);
		accountScroll.setVisible(false);
		accountUpPanel2.setVisible(false);
		accountScroll2.setVisible(false);
		loginA.loginP.setVisible(false);
		loginA.signupP.setVisible(false);
		inMenuA.inMenuUpPanel.setVisible(false);
		inMenuA.inMenuScroll.setVisible(false);
		createA.createAPanel.setVisible(false);
		menup1_1.setVisible(false);
		menup2_1.setVisible(false);
		inMenuA.inMenuUpPanel.setVisible(false);
		inMenuA.inMenuScroll.setVisible(false);
		newWish.upPanel.setVisible(false);
		newWish.scroll.setVisible(false);
		bottomP.setVisible(false);
		newCart.cartDownPnl();
		cp.add(newCart.cartUpPanel, BorderLayout.NORTH);
		cp.add(newCart.cartScroll, BorderLayout.CENTER);
		cp.add(newCart.cartDownPnl, BorderLayout.SOUTH);
		newCart.cartUpPanel.setVisible(true);
		newCart.cartScroll.setVisible(true);
		newCart.cartDownPnl.setVisible(true);
		// '장바구니' 버튼 리스너
		newCart.cartBtn.addActionListener(this);
		newCart.jjim.addActionListener(this);
		newCart.payment.addActionListener(this);
		newCart.closeBtn.addActionListener(this);
		// cart에서 '삭제'버튼 리스너
		for (int i = 0; i < newCart.cartDelete.size(); i++) {
			newCart.cartDelete.get(i).addActionListener(this);
		}
		// cart에서 '저장'버튼 리스너
		for (int i = 0; i < newCart.cartSave.size(); i++) {
			newCart.cartSave.get(i).addActionListener(this);
		}
	}

	void setBlankCart() { // 로그인 안했을 때 '장바구니'에 아무것도 없다는 것을 표시
		homeP.setVisible(false);
		upPnl.setVisible(false);
		scroll.setVisible(false);
		accountUpPanel.setVisible(false);
		accountScroll.setVisible(false);
		accountUpPanel2.setVisible(false);
		accountScroll2.setVisible(false);
		loginA.loginP.setVisible(false);
		loginA.signupP.setVisible(false);
		inMenuA.inMenuUpPanel.setVisible(false);
		inMenuA.inMenuScroll.setVisible(false);
		createA.createAPanel.setVisible(false);
		menup1_1.setVisible(false);
		menup2_1.setVisible(false);
		newWish.upPanel.setVisible(false);
		newWish.scroll.setVisible(false);
		bottomP.setVisible(false);
		cp.add(newCart.cartUpPanel, BorderLayout.NORTH);
		cp.add(newCart.cartScroll, BorderLayout.CENTER);
		newCart.cartUpPanel.setVisible(true);
		newCart.cartScroll.setVisible(true);
		// '장바구니' 버튼 리스너
		newCart.cartBtn.addActionListener(this);
		newCart.jjim.addActionListener(this);
		newCart.closeBtn.addActionListener(this);
	}

	void setWish() {
		homeP.setVisible(false);
		upPnl.setVisible(false);
		scroll.setVisible(false);
		accountUpPanel.setVisible(false);
		accountScroll.setVisible(false);
		accountUpPanel2.setVisible(false);
		accountScroll2.setVisible(false);
		loginA.loginP.setVisible(false);
		loginA.signupP.setVisible(false);
		inMenuA.inMenuUpPanel.setVisible(false);
		inMenuA.inMenuScroll.setVisible(false);
		createA.createAPanel.setVisible(false);
		menup1_1.setVisible(false);
		menup2_1.setVisible(false);
		bottomP.setVisible(false);
		JPanel p = new JPanel();
		cp.add(newWish.upPanel, BorderLayout.NORTH);
		cp.add(newWish.scroll, BorderLayout.CENTER);
		newWish.upPanel.setVisible(true);
		newWish.scroll.setVisible(true);

		newWish.cart.addActionListener(this);
		newWish.closeBtn.addActionListener(this);

		for (int i = 0; i < newWish.wishBtn.size(); i++) {
			newWish.wishBtn.get(i).addActionListener(this);
		}

	}

	// 카트 삭제 메서드
	void afterPurchase(String email) {
		JOptionPane.showMessageDialog(null, "주문이 완료 되었습니다.");
		ResultSet rs = null;
		try {
			dh.pstmt400.setString(1, email);
			int i = dh.pstmt400.executeUpdate();
			if (i > 0) {
				System.out.println("삭제 성공");
			} else {
				System.out.println("삭제 실패");
			}
		} catch (SQLException se) {
			System.out.println("afterpurchase se" + se);
		}
		newCart.checkCartQty(loginA.email);
		Cart.totaltotal = 0;

		newCart.cartUpPanel.setVisible(false);
		newCart.cartScroll.setVisible(false);
		newCart.cartDownPnl.setVisible(false);
		bIcon[3].doClick();
	}

	void setUI() {
		setTitle("Online ShoppingMall");
		setSize(450, 650);
		setVisible(true);
		setLocation(rowSize, culSize);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	void createhomeP() {
		homeP = new JPanel();

		main1 = new JButton(img1);
		main2 = new JButton(img2);
		homeP.add(main1);
		homeP.add(main2);
		main1.setPreferredSize(new Dimension(450, 600));
		main2.setPreferredSize(new Dimension(450, 600));
		cp = getContentPane();

		cp.add(homeP);
		cp.add(bottomP, BorderLayout.SOUTH); // 하단 버튼

		ActionListener taskPerformer = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (isMain1Visible) {
					homeP.remove(main1);
					homeP.add(main2);
				} else {
					homeP.remove(main2);
					homeP.add(main1);
				}
				homeP.revalidate();
				homeP.repaint();
				isMain1Visible = !isMain1Visible;
			}
		};

		timer = new Timer(3000, taskPerformer);
		timer.start();

	}

	void createSearchP() {

		// 윗 패널
		upPnl = new JPanel();
		upPnl.setLayout(new GridLayout(3, 1));

		p1 = new JPanel();
		p1.setLayout(new GridLayout(1, 4));
		p2 = new JPanel();
		p2.setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		// JPanel 색상 white로 변경
		p2.setBackground(Color.white);
		p2.setBackground(Color.white);

		p3 = new JPanel();
		p3.setLayout(new GridLayout(1, 4));
		upPnl.add(p1);
		upPnl.add(p2);
		upPnl.add(p3);

		WBtn = new JButton("여성");
		MBtn = new JButton("남성");
		SBtn = new JButton(search);

		// JButton 색상 white로 변경
		WBtn.setBackground(Color.white);
		MBtn.setBackground(Color.white);
		SBtn.setBackground(Color.white);
		// JButton 투명하게
		WBtn.setOpaque(true);
		MBtn.setOpaque(true);
		SBtn.setOpaque(true);
		// JButton의 외곽석 없애준다.
		WBtn.setBorderPainted(false);
		MBtn.setBorderPainted(false);
		SBtn.setBorderPainted(false);
		SBtn.setPreferredSize(new Dimension(40, 30));

		lb1 = new JLabel();
		lb2 = new JLabel();
		// JButton 색상 white로 변경
		lb1.setBackground(Color.white);
		lb2.setBackground(Color.white);
		// JButton 투명하게
		lb1.setOpaque(true);
		lb2.setOpaque(true);
		p1.add(WBtn);
		p1.add(MBtn);
		p1.add(lb1);
		p1.add(lb2);
		textF = new JTextField("상품, 색상 등을 입력하세요", 35);
		p2.add(textF, gbc);
		p2.add(SBtn, gbc);
		label = new JLabel("추천 아이템");
		lb3 = new JLabel();
		lb4 = new JLabel();
		lb5 = new JLabel();
		// JLabel 색상 white로 변경
		label.setBackground(Color.white);
		lb3.setBackground(Color.white);
		lb4.setBackground(Color.white);
		lb5.setBackground(Color.white);
		// JLabel 투명하게
		label.setOpaque(true);
		lb3.setOpaque(true);
		lb4.setOpaque(true);
		lb5.setOpaque(true);
		p3.add(label);
		p3.add(lb3);
		p3.add(lb4);
		p3.add(lb5);

		// 가운데 패널
		middelPnl = new JPanel();
		middelPnl.setBorder(BorderFactory.createEmptyBorder(0, 18, 0, 0));
		middelPnl.setLayout(new GridLayout(3, 2, 10, 10));
		middelPnl.setBackground(Color.black);
		scroll = new JScrollPane(middelPnl);

		Runnable doScroll = new Runnable() {
			public void run() {
				scroll.getVerticalScrollBar().setValue(0);
			}
		};
		SwingUtilities.invokeLater(doScroll);

		scroll.getVerticalScrollBar().setUI(new TransparentScrollBarUI());
		scroll.getHorizontalScrollBar().setUI(new TransparentScrollBarUI());
		scroll.getVerticalScrollBar().setUnitIncrement(20);//스크롤 속도 조절
		ResultSet rs = null;

		try {
			rs = dh.pstmt104.executeQuery();
			while (rs.next()) {
				womanname.add(rs.getString(1));
				womanprice.add(rs.getString(2));
			}
		} catch (SQLException se) {
		}
		for (int i = 0; i < womanname.size(); i++) {
			String path = "product_images" + "/" + womanname.get(i) + ".jpg";
			ImageIcon icon = new ImageIcon(path);
			Image img = icon.getImage();
			Image changeImg = img.getScaledInstance(190, 320, Image.SCALE_SMOOTH);
			ImageIcon changeIcon = new ImageIcon(changeImg);
			JButton listButton = new JButton(changeIcon);
			JPanel panel = new JPanel();
			JTextArea textArea = new JTextArea(womanname.get(i) + "  \t \n " + womanprice.get(i) + " 원");
			textArea.setForeground(Color.white);
			textArea.setBackground(Color.black);
			panel.setBackground(Color.black);
			int rowcount = 0;
			if (womanname.size() % 2 == 1) {
				rowcount = (womanname.size() / 2) + 1;
			} else {
				rowcount = (womanname.size() / 2);
			}
			middelPnl.add(panel);
			middelPnl.setLayout(new GridLayout(rowcount, 2, 10, 10));

			panel.add(listButton);
			panel.add(textArea);

			panel.setPreferredSize(new Dimension(190, 380));
			listButton.setPreferredSize(new Dimension(190, 320));
			textArea.setBounds(0, 0, 200, 30);
			panel.setLayout(new FlowLayout(FlowLayout.LEFT, 1, 1));
		}
		middelPnl.revalidate();
		middelPnl.repaint();

	}

	void createMenu() {
		// 윗 패널
		menup1_1 = new JPanel();
		menup1_1.setBackground(Color.white);
		lb6 = new JLabel();
		lb7 = new JLabel();
		woman = new JButton("WOMAN");
		man = new JButton("MAN");
		// 버튼 색상 바꾸기
		woman.setBackground(Color.white);
		woman.setOpaque(true);
		woman.setBorderPainted(false);
		man.setBackground(Color.white);
		man.setOpaque(true);
		man.setBorderPainted(false);
		menup1_1.setLayout(new GridLayout(1, 4));
		// 패널 (위,왼쪽,아래,오른쪽) 공백 주기
		menup1_1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		// 버튼 사이즈 조절
		woman.setPreferredSize(new Dimension(81, 35));
		menup1_1.add(lb6);
		menup1_1.add(woman);
		menup1_1.add(man);
		menup1_1.add(lb7);

		// 중간 패널
		menup2_1 = new JPanel();
		menup2_1.setBackground(Color.white);
		menup2_1.setLayout(new GridLayout(20, 1));

		for (int i = 0; i < menuBtn.length; i++) {
			menuBtn[i] = new JButton(menuName[i]);
			menup2_1.add(menuBtn[i]);
			// 버튼 색상 바꾸기
			menuBtn[i].setBackground(Color.yellow);
			menuBtn[i].setOpaque(true);
			menuBtn[i].setBorderPainted(false);

		}

		for (int j = 1; j < menuBtn.length; j++) {
			menuBtn[j].setVisible(true);
		}

		menuBtn[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean isVisible = menuBtn[1].isVisible();

				for (int i = 1; i < menuBtn.length; i++) {
					menuBtn[i].setVisible(!isVisible);
				}
			}
		});
	}

	void createAccount1() {
		// 윗패널
		accountUpPanel = new JPanel();
		accountUpPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		listBtn = new JButton("구매내역");
		accountBtn = new JButton("계정");
		// 패널 백그라운드 색 바꾸기
		accountUpPanel.setBackground(Color.white);
		// JButton 색상 white로 변경
		listBtn.setBackground(Color.white);
		accountBtn.setBackground(Color.white);
		// JButton 투명하게
		listBtn.setOpaque(true);
		accountBtn.setOpaque(true);
		// JButton 선 없애기
		listBtn.setBorderPainted(false);
		accountBtn.setBorderPainted(false);
		// 윗패널 버튼 2개 붙힘
		accountUpPanel.add(listBtn);
		accountUpPanel.add(accountBtn);
		// 중간패널
		accountMiddlePanel = new JPanel();
		accountMiddlePanel.setBackground(Color.white);
		// 스크롤 투명
		accountScroll = new JScrollPane(accountMiddlePanel);

		int rowcount = 0;
		if (purchaseName.size() % 2 == 1) {
			rowcount = (purchaseName.size() / 2) + 1;
		} else {
			rowcount = (purchaseName.size() / 2);
		}

		if (accountFlag == 0) { // '구매내역'이 없을 때

			accountMiddlePanel.setLayout(new GridLayout(3, 1));
			accountMiddlePanel.setBackground(Color.white);
			JPanel inPnl1 = new JPanel();
			inPnl1.setBackground(Color.white);
			inPnl1.setLayout(new GridLayout(2, 1));
			lb1 = new JLabel("주문 내역이 없습니다.", JLabel.CENTER);
			lb2 = new JLabel();
			emptyL = new JLabel(emptyAccount, JLabel.CENTER);
			inPnl1.add(emptyL);
			inPnl1.add(lb1);
			accountMiddlePanel.add(lb2);
			accountMiddlePanel.add(inPnl1);
		} else { // 구매내역이 하나라도 있을 때
			accountMiddlePanel.setLayout(new GridLayout(10, 1));
			accountMiddlePanel.setPreferredSize(new Dimension(700, 2000));

			// *********************************구매내역 구현 ******
			selectPDate(loginEmail);
			for (int i = 0; i < dateName.size(); i++) {

				getPurchaseIfm(loginEmail, dateName.get(i));

				JPanel jpn1 = new JPanel();
				jpn1.setPreferredSize(new Dimension(1200, 1200));
				JPanel jpn2 = new JPanel();
				jpn2.setBackground(Color.white);
				JPanel jpn2_1 = new JPanel();// 제품 사진 들어가는 곳
				JPanel jpn3 = new JPanel();
				jpn3.setBackground(Color.white);
				int sumPrice = 0;
				for (int j = 0; j < pdPrice.size(); j++) {
					// System.out.println("가격 확인: " + pdPrice.get(j));
					sumPrice = (pdPrice.get(j) * pdQty.get(j)) + sumPrice;
				}

				JTextArea order = new JTextArea("주문완료\t " + dateName.get(i) + " \n총금액 " + sumPrice + " 원");

				jpn1.setLayout(new BorderLayout());

				jpn1.add(jpn3, BorderLayout.NORTH);
				jpn1.add(jpn2, BorderLayout.CENTER);
				jpn2.add(jpn2_1);
				jpn2_1.setLayout(new GridLayout(1, 10));
				for (int k = 0; k < pdName.size(); k++) { // 상품이름의 수만큼 버튼 생성

					String path = "product_images" + "/" + pdName.get(k) + ".jpg";
					ImageIcon icon = new ImageIcon(path);
					Image img = icon.getImage();
					Image changeImg = img.getScaledInstance(100, 200, Image.SCALE_SMOOTH);
					ImageIcon changeIcon = new ImageIcon(changeImg);
					JButton btn1 = new JButton(changeIcon);
					btn1.setPreferredSize(new Dimension(100, 200));
					jpn2_1.add(btn1);
				}
				jpn3.add(order);
				jpn2.setLayout(new FlowLayout(FlowLayout.LEFT));
				jpn3.setLayout(new FlowLayout(FlowLayout.LEFT));
				accountMiddlePanel.add(jpn1);
			}
		}

		listBtn.addActionListener(this);
		accountBtn.addActionListener(this);
	}

	void selectPDate(String loginEmail) {
		dateName.clear();
		ResultSet rs = null;
		try {
			dh.pstmt203.setString(1, loginEmail);
			rs = dh.pstmt203.executeQuery();
			while (rs.next()) {
				String name = (rs.getString(1));
				dateName.add(name);
			}

			System.out.println("selectPDate 성공");
		} catch (SQLException se) {
		}
	}

	// SQL204> "SELECT G.G_NAME, P.P_SITU, SUM(G.G_PRICE) AS TOTAL_PRICE, P.PD_SEQ,
	// PD.PD_QUAN FROM PURCHASE P JOIN PURCHASE_DETAIL PD ON P.PD_SEQ = PD.PD_SEQ
	// JOIN GOODS G ON PD.G_CODE = G.G_CODE AND PD.S_NAME = G.S_NAME WHERE P.M_EMAIL
	// = ? AND to_char(p_date, 'YYYY-MM-DD HH24:MI') = ? GROUP BY G.G_NAME,
	// P.P_SITU, P.PD_SEQ, PD.PD_QUAN";
	void getPurchaseIfm(String loginEmail, String pdate) {
		pdName.clear();
		pdPrice.clear();
		pdSitu.clear();
		pdSeq.clear();
		pdQty.clear();
		ResultSet rs = null;
		try {
			dh.pstmt204.setString(1, loginEmail);
			dh.pstmt204.setString(2, pdate);
			rs = dh.pstmt204.executeQuery();
			while (rs.next()) {
				pdName.add(rs.getString(1));
				pdSitu.add(rs.getInt(2));
				pdPrice.add(rs.getInt(3));
				pdSeq.add(rs.getInt(4));
				pdQty.add(rs.getInt(5));
			}

			System.out.println("getPurchaseIfm 성공");
		} catch (SQLException se) {
			System.out.println("getPurchaseIfm() se: " + se);
		}
	}

	void createAccount2() {
		// 윗패널
		accountUpPanel2 = new JPanel();
		accountUpPanel2.setLayout(new FlowLayout(FlowLayout.LEFT));
		listBtn2 = new JButton("구매내역");
		accountBtn2 = new JButton("계정");
		// 패널 백그라운드 색 바꾸기
		accountUpPanel2.setBackground(Color.white);
		// JButton 색상 white로 변경
		listBtn2.setBackground(Color.white);
		accountBtn2.setBackground(Color.white);
		// JButton 투명하게
		listBtn2.setOpaque(true);
		accountBtn2.setOpaque(true);
		// JButton 선 없애기
		listBtn2.setBorderPainted(false);
		accountBtn2.setBorderPainted(false);
		// 윗패널 버튼 2개 붙힘
		accountUpPanel2.add(listBtn2);
		accountUpPanel2.add(accountBtn2);

		// 중간패널
		accountMiddlePanel2 = new JPanel();
		accountScroll2 = new JScrollPane(accountMiddlePanel2);
		accountMiddlePanel2.setBackground(Color.white);
		accountMiddlePanel2.setLayout(new GridLayout(8, 1));
		for (int i = 0; i < mmPnel.length; i++) {
			mmPnel[i] = new JPanel();
			mmPnel[i].setBackground(Color.white);
			accountMiddlePanel2.add(mmPnel[i]);
		}
		nameLbl = new JLabel(lname);
		mmPnel[0].setLayout(new GridLayout(1, 1));
		mmPnel[0].add(nameLbl);

		emailLbl1 = new JLabel("이메일");
		emailLbl2 = new JLabel(loginEmail);
		mmPnel[1].setLayout(new GridLayout(2, 1));
		mmPnel[1].add(emailLbl1);
		mmPnel[1].add(emailLbl2);

		addrLb1 = new JLabel("주소");
		addrLb2 = new JLabel(laddr);
		mmPnel[2].setLayout(new GridLayout(2, 1));
		mmPnel[2].add(addrLb1);
		mmPnel[2].add(addrLb2);

		pwdLbl = new JLabel("*비밀번호*");
		pwdBtn = new JButton(pwd);
		mmPnel[3].setLayout(new GridLayout(1, 1));
		mmPnel[3].add(pwdLbl);
		mmPnel[3].add(pwdBtn);
		pwdBtn.setBackground(Color.white);
		pwdBtn.setOpaque(true);
		outBtn = new JButton("로그아웃");
		outBtn.setBackground(Color.white);
		outBtn.setOpaque(true);
		mmPnel[4].setLayout(new FlowLayout(FlowLayout.LEFT));
		mmPnel[4].add(outBtn);

		outBtn.addActionListener(this);
		pwdBtn.addActionListener(this);

		// 왼쪽 오른쪽 패딩 주는 메소드
		accountMiddlePanel2.setBorder(BorderFactory.createEmptyBorder(0, 18, 0, 18));
		for (int i = 0; i < mmPnel.length; i++) {
			accountMiddlePanel2.add(mmPnel[i]);
		}
		listBtn2.addActionListener(this);
		accountBtn2.addActionListener(this);

	}

	void createProductDetail() {
		productUpPnl = new JPanel();
		productUpPnl.setBackground(Color.white);
		productUpPnlLeft = new JPanel();
		productUpPnlLeft.setBackground(Color.white);
		productUpPnlRight = new JPanel();
		productUpPnl.setLayout(new BorderLayout());
		productUpPnl.add(productUpPnlLeft, BorderLayout.WEST);
		productUpPnl.add(productUpPnlRight, BorderLayout.EAST);
		productUpPnlLeft.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
		exit = new JButton("X");
		exit.setBackground(Color.white);
		exit.setOpaque(true);
		addButton = new JButton("추가");
		// JButton 색상 white로 변경
		addButton.setForeground(Color.white);
		addButton.setBackground(Color.black);
		// JButton 투명하게
		addButton.setOpaque(true);
		wishButton = new JButton("찜");
		productUpPnlLeft.add(exit);
		productUpPnlRight.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
		productUpPnlRight.add(wishButton);
		prod1 = new JPanel();
		prod2 = new JPanel();
		prod3 = new JPanel();

		// 중간패널

		downPnl = new JPanel();
		downPnlup = new JPanel();
		downPnlup.setBackground(Color.white);
		downPnldown = new JPanel();
		downPnldown.setBackground(Color.white);

		//////////////// 패널 고정되게 수정함 //////////////////
		StringBuilder sb = new StringBuilder();		//StringBuilder 배열내부 String 일정한 길이가 되었을 때 개행 실행하도록 변경후 저장
		if (itemDesc != null) {
			int length = itemDesc.length();
			int startIndex = 0;
			int max = 44;
			while (startIndex < length) {
				int endIndex = startIndex + max;
				if (endIndex > length) {
					endIndex = length;
				}

				sb.append(itemDesc.substring(startIndex, endIndex));
				sb.append("\n");

				startIndex = endIndex;
			}

		}
//sb == 개행된 itemDesc
		show = new JTextArea(
				itemName + " \n " + itemPrice + " \n\n " + sb.toString() + "\n " + itemColor + " | " + itemCode);
		show.setEditable(false);
		productMdlPnl = new JPanel();
		productMdlPnl.setBackground(Color.white);
		proDetailScroll = new JScrollPane(productMdlPnl);
		productMdlPnl.setLayout(new GridLayout(3, 1));
		productM1 = new JPanel();
		productM1.setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;

		for (int i = 0; i < jpn10.length; i++) {
			jpn10[i] = new JPanel();
			String path = "product_detail_images/" + itemName + i + ".jpg";
			ImageIcon icon = new ImageIcon(path);
			Image img = icon.getImage();
			Image changeImg = img.getScaledInstance(210, 330, Image.SCALE_SMOOTH);
			ImageIcon changeIcon = new ImageIcon(changeImg);
			JButton listButton = new JButton(changeIcon);
			btn10[i] = listButton;
			btn10[i].setBackground(Color.white);
			btn10[i].setOpaque(true);
			productMdlPnl.add(jpn10[i]);
			jpn10[i].add(btn10[i]);

			jpn10[i].setPreferredSize(new Dimension(416, 360));
			btn10[i].setPreferredSize(new Dimension(416, 360));
			jpn10[i].setLayout(new FlowLayout(FlowLayout.LEFT, 1, 1));

		}
		downPnl.setLayout(new GridLayout(2, 1));
		downPnl.setBackground(Color.white);
		downPnl.add(downPnlup);
		downPnlup.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 1));
		downPnlup.add(show);
		downPnl.add(downPnldown);
		downPnldown.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 1));
		downPnldown.add(addButton);
		addButton.setPreferredSize(new Dimension(130, 30));

		gbc.weighty = 0.9;// 비율이 0.2:0.1이므로 버튼의 크기는 가로축으로 2배
		gbc.gridx = 0;
		gbc.gridy = 0; // 버튼이 두개로 0,0 기준으로 생성
		productM1.add(proDetailScroll, gbc);
		gbc.weighty = 0.1; // 비율이 0.2:0.1이므로 버튼의 크기는 가로축으로 1배

		gbc.gridx = 0;

		gbc.gridy = 1; // 버튼이 두개로 1,0 가 버튼 생성시작점
		productM1.add(downPnl, gbc);
		wishButton.setVisible(false);// 찜 버튼 숨김
	}

// SQL106> select distinct G_NAME, G_PRICE from GOODS where (G_COLOR like ? and G_SEX = '여성') or (G_NAME like ? and G_SEX = '여성');
	void wms(String searchText) {
		ResultSet rs = null;
		middelPnl.removeAll();
		Vector<String> itemname = new Vector<String>();
		Vector<String> itemprice = new Vector<String>();
		try {
			dh.pstmt106.setString(1, "%" + searchText + "%");
			dh.pstmt106.setString(2, "%" + searchText + "%");
			rs = dh.pstmt106.executeQuery();
			while (rs.next()) {
				itemname.add(rs.getString(1));
				itemprice.add(rs.getString(2));
			}
		} catch (SQLException se) {
		}
		for (int i = 0; i < itemname.size(); i++) {
			String path = "product_images" + "/" + itemname.get(i) + ".jpg";
			ImageIcon icon = new ImageIcon(path);
			Image img = icon.getImage();
			Image changeImg = img.getScaledInstance(200, 330, Image.SCALE_SMOOTH);
			ImageIcon changeIcon = new ImageIcon(changeImg);
			JButton listButton = new JButton(changeIcon);
			JPanel panel = new JPanel();
			JTextArea textArea = new JTextArea(itemname.get(i) + "  \t \n " + itemprice.get(i) + " 원");
			textArea.setForeground(Color.white);
			textArea.setBackground(Color.black);
			panel.setBackground(Color.black);
			int rowcount = 0;
			if (itemname.size() % 2 == 1) {
				rowcount = (itemname.size() / 2) + 1;
			} else {
				rowcount = (itemname.size() / 2);
			}
			middelPnl.add(panel);
			middelPnl.setLayout(new GridLayout(rowcount, 2, 10, 10));

			panel.add(listButton);
			panel.add(textArea);

			panel.setPreferredSize(new Dimension(190, 380));
			listButton.setPreferredSize(new Dimension(190, 320));
			textArea.setBounds(0, 0, 200, 30);
			panel.setLayout(new FlowLayout(FlowLayout.LEFT, 1, 1));
		}
		middelPnl.revalidate();
		middelPnl.repaint();
	}

	// SQL107> select distinct G_NAME, G_PRICE from GOODS where (G_COLOR like ? and
	// G_SEX = '남성') or (G_NAME like ? and G_SEX = '남성')
	void ms(String searchText1) {
		ResultSet rs = null;
		middelPnl.removeAll();
		Vector<String> itemname = new Vector<String>();
		Vector<String> itemprice = new Vector<String>();
		try {
			dh.pstmt107.setString(1, "%" + searchText1 + "%");
			dh.pstmt107.setString(2, "%" + searchText1 + "%");
			rs = dh.pstmt107.executeQuery();
			while (rs.next()) {
				itemname.add(rs.getString(1));
				itemprice.add(rs.getString(2));
			}
		} catch (SQLException se) {
		}
		for (int i = 0; i < itemname.size(); i++) {
			String path = "product_images" + "/" + itemname.get(i) + ".jpg";
			ImageIcon icon = new ImageIcon(path);
			Image img = icon.getImage();
			Image changeImg = img.getScaledInstance(280, 400, Image.SCALE_SMOOTH);
			ImageIcon changeIcon = new ImageIcon(changeImg);
			JButton listButton = new JButton(changeIcon);
			listButton.setBackground(Color.white);
			JPanel panel = new JPanel();
			JTextArea textArea = new JTextArea(itemname.get(i) + "  \t \n " + itemprice.get(i) + " 원");
			textArea.setForeground(Color.white);
			textArea.setBackground(Color.black);
			panel.setBackground(Color.black);
			int rowcount = 0;
			if (itemname.size() % 2 == 1) {
				rowcount = (itemname.size() / 2) + 1;
			} else {
				rowcount = (itemname.size() / 2);
			}
			middelPnl.add(panel);
			middelPnl.setLayout(new GridLayout(rowcount, 2, 10, 10));

			panel.add(listButton);
			panel.add(textArea);

			panel.setPreferredSize(new Dimension(190, 380));
			listButton.setPreferredSize(new Dimension(190, 320));
			textArea.setBounds(0, 0, 200, 30);
			panel.setLayout(new FlowLayout(FlowLayout.LEFT, 1, 1));
		}
		middelPnl.revalidate();
		middelPnl.repaint();
	}

	// SQL104> "select distinct G_NAME, G_PRICE from GOODS where G_SEX = ?"
	void Swoman() {
		womanname.clear();
		womanSearchBtn.clear();
		womanprice.clear();
		ResultSet rs = null;
		middelPnl.removeAll();
		try {
			rs = dh.pstmt104.executeQuery();
			while (rs.next()) {
				womanname.add(rs.getString(1));
				womanprice.add(rs.getString(2));
			}
		} catch (SQLException se) {
		}
		for (int i = 0; i < womanname.size(); i++) {

			String path = "product_images" + "/" + womanname.get(i) + ".jpg";
			ImageIcon icon = new ImageIcon(path);
			Image img = icon.getImage();
			Image changeImg = img.getScaledInstance(190, 320, Image.SCALE_SMOOTH);
			ImageIcon changeIcon = new ImageIcon(changeImg);
			JButton listButton = new JButton(changeIcon);
			JPanel panel = new JPanel();
			JTextArea textArea = new JTextArea(womanname.get(i) + "  \t \n " + womanprice.get(i) + " 원");
			textArea.setForeground(Color.white);
			textArea.setBackground(Color.black);
			panel.setBackground(Color.black);
			int rowcount = 0;
			if (womanname.size() % 2 == 1) {
				rowcount = (womanname.size() / 2) + 1;
			} else {
				rowcount = (womanname.size() / 2);
			}
			middelPnl.add(panel);
			middelPnl.setLayout(new GridLayout(rowcount, 2, 10, 10));

			panel.add(listButton);
			panel.add(textArea);

			panel.setPreferredSize(new Dimension(190, 380));
			listButton.setPreferredSize(new Dimension(190, 320));
			textArea.setBounds(0, 0, 200, 30);
			panel.setLayout(new FlowLayout(FlowLayout.LEFT, 1, 1));
			womanSearchBtn.add(listButton);
		}
		for (int i = 0; i < womanSearchBtn.size(); i++) {
			womanSearchBtn.get(i).addActionListener(this);
		}
		middelPnl.revalidate();
		middelPnl.repaint();
	}

	// SQL105> select distinct G_NAME, G_PRICE from GOODS where G_SEX = '남성'
	void Sman() {
		manname.clear();
		manSearchBtn.clear();
		manprice.clear();
		ResultSet rs = null;
		middelPnl.removeAll();

		try {
			rs = dh.pstmt105.executeQuery();
			while (rs.next()) {
				manname.add(rs.getString(1));
				manprice.add(rs.getString(2));
			}
		} catch (SQLException se) {
		}
		for (int i = 0; i < manname.size(); i++) {

			String path = "product_images" + "/" + manname.get(i) + ".jpg";
			ImageIcon icon = new ImageIcon(path);
			Image img = icon.getImage();
			Image changeImg = img.getScaledInstance(190, 320, Image.SCALE_SMOOTH);
			ImageIcon changeIcon = new ImageIcon(changeImg);
			JButton listButton = new JButton(changeIcon);
			JPanel panel = new JPanel();
			JTextArea textArea = new JTextArea(manname.get(i) + "  \t \n " + manprice.get(i) + " 원");
			textArea.setForeground(Color.white);
			textArea.setBackground(Color.black);
			panel.setBackground(Color.black);
			int rowcount = 0;
			if (manname.size() % 2 == 1) {
				rowcount = (manname.size() / 2) + 1;
			} else {
				rowcount = (manname.size() / 2);
			}
			middelPnl.add(panel);
			middelPnl.setLayout(new GridLayout(rowcount, 2, 10, 10));

			panel.add(listButton);
			panel.add(textArea);

			panel.setPreferredSize(new Dimension(190, 380));
			listButton.setPreferredSize(new Dimension(190, 320));
			textArea.setBounds(0, 0, 200, 30);
			panel.setLayout(new FlowLayout(FlowLayout.LEFT, 1, 1));
			manSearchBtn.add(listButton);
		}

		for (int i = 0; i < manSearchBtn.size(); i++) {
			manSearchBtn.get(i).addActionListener(this);
		}
		middelPnl.revalidate();
		middelPnl.repaint();
	}

	// SQL201> select M_NAME, M_EMAIL, M_ADDR from MEMBER where M_EMAIL = ?
	void info(String email1) {
		ResultSet rs = null;
		try {
			dh.pstmt201.setString(1, loginEmail);
			rs = dh.pstmt201.executeQuery();
			while (rs.next()) {
				lname = rs.getString(1);
				lemail = rs.getString(2);
				laddr = rs.getString(3);
			}
		} catch (SQLException se) {
		}
	}

	// SQL202> update MEMBER set M_PWD = ? where M_EMAIL = ?
	void pwd(String pwd, String email) {
		try {
			dh.pstmt202.setString(1, pwd);
			dh.pstmt202.setString(2, loginEmail);
			int i = dh.pstmt202.executeUpdate();
			if (i > 0) {
				System.out.println("업데이트");
			} else {
				System.out.println("실패");
			}
		} catch (SQLException se) {
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyPressed(KeyEvent e) {
		textF = (JTextField) e.getSource();
		String searchText = textF.getText();
		if (searchText.equals(textF.getText())) {
			wms(searchText);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void focusGained(FocusEvent e) {
		if (e.getSource() == textF) {
			String currentText = textF.getText();
			if (currentText.equals("상품, 색상 등을 입력하세요")) {
				textF.setText("");
			}
		}

	}

	@Override
	public void focusLost(FocusEvent e) {
		if (e.getSource() == textF) {
			String currentText = textF.getText();
			if (currentText.isEmpty()) {
				textF.setText("상품, 색상 등을 입력하세요");
			}
		}
	}

	// SQL202> update MEMBER set M_PWD = ? where M_PWD = and M_EMAIL = ?
	void pwd(String updatepwd, String pwd, String email) {
		try {
			dh.pstmt202.setString(1, up.newPwdField.getText());
			dh.pstmt202.setString(2, up.curPwdField.getText());
			dh.pstmt202.setString(3, loginEmail);
			int i = dh.pstmt202.executeUpdate();
			if (i > 0) {
				System.out.println("업데이트");
			} else {
				System.out.println("실패");
			}
		} catch (SQLException se) {
		}
	}

	void createPanels() {
		createhomeP(); // 메인 패널 구현
		createSearchP(); // 검색 패널 구현
		createMenu();
		createAccount1();
		createAccount2();
		createProductDetail();
		cp = getContentPane();
		// 패널2개 중 가장 아래에 있음

		// CreateAccount 클래스에서 멤버변수 불러옴
		cp.add(createA.createAPanel);

		// LoginAccount 클래스에서 멤버변수 불러옴
		cp.add(loginA.signupP);
		cp.add(loginA.loginP);
		// InMenu 클래스에서 멤버변수 불러옴
		cp.add(accountUpPanel2);
		cp.add(accountScroll2);
		cp.add(accountScroll);
		cp.add(accountUpPanel);
		cp.add(menup2_1);
		cp.add(menup1_1);
		cp.add(scroll);
		cp.add(upPnl);
		cp.add(homeP);// 가장 위에 있음 -> 열자마자 보이는 패널
		cp.add(bottomP, BorderLayout.SOUTH);

	}

	void bottomButtonsP() { // 하단 버튼 구현
		bottomP = new JPanel();
		bottomP.setLayout(new GridLayout(1, 5));
		// 메뉴 외 나머지 4개 버튼
		for (int i = 0; i < path.length; i++) {
			bIcon[i] = new JButton(new ImageIcon(path[i]));
			// JButton 색상 white로 변경
			bIcon[i].setBackground(Color.white);

			// JButton 투명하게
			bIcon[i].setOpaque(true);
			// JButton의 외곽석 없애준다.
			bIcon[i].setBorderPainted(false);
			// JButton 내용영역 채우기 안함
		}

		// '메뉴' 버튼 구현
		menuButton = new JButton("메뉴");
		menuButton.setBackground(Color.white);
		menuButton.setOpaque(true);
		menuButton.setBorderPainted(false);

		bottomP.add(bIcon[0]);
		bottomP.add(bIcon[1]);
		bottomP.add(menuButton);
		bottomP.add(bIcon[2]);
		bottomP.add(bIcon[3]);

		// 버튼 사이즈 조절
		bIcon[0].setPreferredSize(new Dimension(59, 52));

	}

	void p(String str) {
		System.out.print(str);
	}

	void pln(String str) {
		System.out.println(str);
	}

	// public static void main(String[] args) {
	// 	MainGUI m1 = new MainGUI();
	// }
}
