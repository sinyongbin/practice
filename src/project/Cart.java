package project;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

public class Cart extends JFrame implements ActionListener {
	Container cp;
	JPanel cartUpPanel, cartMdlPnl, cartDownPnl, uupPnl1, udwPnl1, inPnl1;
	JButton cartBtn, jjim, payment, closeBtn;
	JScrollPane cartScroll;
	JTextArea cost;
	JLabel sumF, emptyP, lb1, lb2;
	Font fontSum;
	Font plusFont, minusFont;
	JButton cartPlus, cartMinus, cartD, cartS;
	Vector<JButton> cartDelete = new Vector<JButton>();
	Vector<JButton> cartSave = new Vector<JButton>();
	Vector<JPanel> btnPnl = new Vector<JPanel>();

	ArrayList<JButton> quanPlus = new ArrayList<JButton>();
	ArrayList<JButton> quanMinus = new ArrayList<JButton>();
	ArrayList<Integer> qunna = new ArrayList<Integer>();
	ArrayList<JTextArea> jta2Controll = new ArrayList<JTextArea>();
	ArrayList<JTextArea> jtaPriceControll = new ArrayList<JTextArea>();
	// 장바구니 추가 되면 바뀌는지 모션 보기 위한 임시 int
	int cartFlag = 0;

	ImageIcon closeIcon = new ImageIcon("icons/close.png");
	ImageIcon emptyCart = new ImageIcon("icons/empty.png");

	int quan = 1;
	int count = 1; // 수량
	static int totaltotal = 0;// 총 주문금액
	int countPlus = 1;
	int countMinus = -1;

	String memberEmail = "";

	Vector<String> pName = new Vector<String>();
	Vector<String> pColor = new Vector<String>();
	Vector<String> pPrice = new Vector<String>();
	Vector<String> pQty = new Vector<String>();
	Vector<String> pSize = new Vector<String>();
	Vector<String> cSec = new Vector<String>();
	Vector<String> pCode = new Vector<String>();

	Vector<String> nameInWish = new Vector<String>();

	Vector<Integer> pdSeq = new Vector<Integer>();
	Vector<Integer> goodsQty = new Vector<Integer>();

	String statement = "주문완료";

	int purchaseSum;

	int QtySum = 0;

	static int purchaseCount = 1;
	static int cartPCount = 1;
	static String pcCount = Integer.toString(cartPCount);
	// DB객체 불러오기
	ShoppingSiteDbHelper dh = new ShoppingSiteDbHelper();

	Cart(String M_MAIL) {
		memberEmail = M_MAIL;
		System.out.println(memberEmail);
		checkCartQty(memberEmail);
		getCartName(memberEmail);
		init();
	}

	void init() {

		cp = getContentPane();
		upPnl();
		cartMdlPnl = new JPanel();
		cartScroll = new JScrollPane(cartMdlPnl);
		mdlPnl();
	}

	public void actionPerformed(ActionEvent ae) {
		for (int i = 0; i < cartDelete.size(); i++) {

			if (ae.getSource() == quanPlus.get(i)) { // 수량추가 버튼
				int pRice = Integer.parseInt(pPrice.get(i));
				int sss = Integer.parseInt(pQty.get(i));
				sss++;
				String rrr = Integer.toString(sss);
				pQty.set(i, rrr);
				System.out.println("pQty.get(k): " + pQty.get(i));
				jta2Controll.get(i).setText("\n          " + pQty.get(i));// 누를 때마다 숫자칸 갱신
				Cart.totaltotal += pRice;
				cost.setText("\t\t" + Cart.totaltotal + " 원");
			}

			if (ae.getSource() == quanMinus.get(i)) { // 수량감소 버튼
				int pRiceminus = Integer.parseInt(pPrice.get(i));
				int ccc = Integer.parseInt(pQty.get(i));
				if (ccc != 1) {
					ccc--;
					String yyy = Integer.toString(ccc);
					pQty.set(i, yyy);
					System.out.println("pQty.get(i): " + pQty.get(i));
					jta2Controll.get(i).setText("\n          " + pQty.get(i));// 누를 때마다 숫자칸 갱신
					Cart.totaltotal -= pRiceminus;
					cost.setText("\t\t" + Cart.totaltotal + " 원");
				}
			}
		}
	}

	// SQL305="select G_NAME from WISH JOIN GOODS using(G_CODE, S_NAME) where
	// M_EMAIL = ?";
	void getWishItems(String wishEmail) {
		ResultSet rs = null;
		try {
			dh.pstmt305.setString(1, wishEmail);
			rs = dh.pstmt305.executeQuery();
			while (rs.next()) {
				nameInWish.add(rs.getString(1));
			}
		} catch (SQLException se) {
			System.out.println("getWishItems() se: " + se);
		}
	}

	// SQL302="delete from cart where g_code=(select distinct g_code from goods
	// where g_name = ?) and m_email=?";
	void deleteItem(String itemName, String M_MAIL) {
		try {
			dh.pstmt302.setString(1, itemName);
			dh.pstmt302.setString(2, M_MAIL);
			int i = dh.pstmt302.executeUpdate();
			if (i > 0) {
				System.out.println("삭제 성공");
			} else {
				System.out.println("삭제 실패");
			}
		} catch (SQLException se) {
			System.out.println("delteItem() se: " + se);
		}

	}

	// SQL304="insert into WISH values(WISH_SEQ.nextval,?(장바구니_시퀀스),?,?,?)";
	// "insert into WISH values(WISH_SEQ.nextval,1,2025,'M','aa@naver.com')";
	void insertWishItem(String cartSec, String cartCode, String cartSize, String mEmail) {
		ResultSet rs = null;
		try {
			dh.pstmt304.setString(1, cartSec);
			dh.pstmt304.setString(2, cartCode);
			dh.pstmt304.setString(3, cartSize);
			dh.pstmt304.setString(4, mEmail);
			rs = dh.pstmt304.executeQuery();
			System.out.println("Wish 인서트성공");
		} catch (SQLException se) {
			System.out.println("insertWishItem() se: " + se);
		}
	}

	// SQL306="insert into PURCHASE values(PURCHASE_SEQ.nextval,?,SYSDATE,?,1,?,?)";
	// 장바구니코드, 금액, 이메일, 주문상세seq
	void insertPurchase(int cartSec, int price, String mEmail, int pdSeq) {
		ResultSet rs = null;
		try {
			dh.pstmt306.setInt(1, cartSec);
			dh.pstmt306.setInt(2, price);
			dh.pstmt306.setString(3, mEmail);
			dh.pstmt306.setInt(4, pdSeq);
			rs = dh.pstmt306.executeQuery();
			System.out.println("Purchase 인서트성공");
			purchaseCount++;
			cartPCount++;
			pcCount = Integer.toString(cartPCount);
		} catch (SQLException se) {
			System.out.println("insertPurchase() se: " + se);
		}
	}

	// SQL312="insert into PURCHASE_DETAIL
	// values(PURCHASE_DETAIL_SEQ.nextval,?,?,?,?,?)"
	// 수량, 장바구니코드, 상품코드, 사이즈명, 이메일
	void insertPurchaseDetail(int pQty, int cartSec, int pCode, String pSize, String mEmail) {
		ResultSet rs = null;
		try {
			dh.pstmt312.setInt(1, pQty);
			dh.pstmt312.setInt(2, cartSec);
			dh.pstmt312.setInt(3, pCode);
			dh.pstmt312.setString(4, pSize);
			dh.pstmt312.setString(5, mEmail);
			rs = dh.pstmt312.executeQuery();
			System.out.println("Purchase_Detail 인서트성공");
			purchaseCount++;
			cartPCount++;
			pcCount = Integer.toString(cartPCount);
		} catch (SQLException se) {
			System.out.println("insertPurchaseDetail() se: " + se);
		}
	}

	// SQL313=select pd_seq from purchase_detail where c_seq=? and m_email=?;
	void selectPDetailCode(int cartSec, String mEmail) {
		ResultSet rs = null;
		try {
			dh.pstmt313.setInt(1, cartSec);
			dh.pstmt313.setString(2, mEmail);
			rs = dh.pstmt313.executeQuery();
			while (rs.next()) {
				pdSeq.add(rs.getInt(1));
			}
		} catch (SQLException se) {
			System.out.println("selectPDetailCode() se: " + se);
		}
	}

	// SQL310="select g_quan from goods where g_name= ? and s_name= ?";
	void checkGoodsQty(String pName, String sizeName) {
		// goodsQty.clear();
		ResultSet rs = null;
		try {
			dh.pstmt310.setString(1, pName);
			dh.pstmt310.setString(2, sizeName);
			rs = dh.pstmt310.executeQuery();
			while (rs.next()) {
				goodsQty.add(rs.getInt(1)); // 이름
			}
		} catch (SQLException se) {
			System.out.println("checkGoodsQty() se: " + se);
		}
	}

	// SQL311="update goods set g_quan=? where g_name= ? and s_name= ?";
	void updateGoodsQty(int totalQty, String pName, String sizeName) {
		try {
			dh.pstmt311.setInt(1, totalQty);
			dh.pstmt311.setString(2, pName);
			dh.pstmt311.setString(3, sizeName);
			int i = dh.pstmt311.executeUpdate();
			if (i > 0) {
				System.out.println("updateGoodsQty 업데이트");
			} else {
				System.out.println("updateGoodsQty 실패");
			}
		} catch (SQLException se) {
			System.out.println("pdateGoodsQty() se: " + se);
		}
	}

	// SQL301="select G_NAME, G_COLOR, G_PRICE, C_QUAN, S_NAME, C_SEQ, G_CODE from
	// CART JOIN GOODS using(G_CODE, S_NAME) where M_EMAIL =?";
	void getCartName(String M_MAIL) {
		pName.clear();
		pColor.clear();
		pPrice.clear();
		pQty.clear();
		pSize.clear();
		cSec.clear();
		pCode.clear();
		ResultSet rs = null;
		try {
			dh.pstmt301.setString(1, M_MAIL);
			rs = dh.pstmt301.executeQuery();
			while (rs.next()) {
				pName.add(rs.getString(1)); // 이름
				pColor.add(rs.getString(2));// 색상
				pPrice.add(rs.getString(3));// 가격
				pQty.add(rs.getString(4));// 수량
				pSize.add(rs.getString(5));// 사이즈
				cSec.add(rs.getString(6));// 장바구니 시퀀스
				pCode.add(rs.getString(7));// 상품코드
			}
		} catch (SQLException se) {
			System.out.println("getCartName() se: " + se);
		}
	}

	// SQL307="select sum(c_quan) from cart where m_email= ?
	void checkCartQty(String M_MAIL) { // 로그인되면 카트 안에 제품의 갯수가 1개 이상 인지 확인하고 'cartFlag'를 1으로 바꾼다
		QtySum = 0;
		ResultSet rs = null;
		try {
			dh.pstmt307.setString(1, M_MAIL);
			rs = dh.pstmt307.executeQuery();
			while (rs.next()) {
				QtySum = rs.getInt(1); // 이름
			}

		} catch (SQLException se) {
			System.out.println("getCartName() se: " + se);
		}
		if (QtySum >= 1) {
			cartFlag = 1;
		}
		//System.out.println("QtySum: " + QtySum);
		//System.out.println("cartFlag: " + cartFlag);
	}

	void upPnl() {
		cartUpPanel = new JPanel();
		cartBtn = new JButton("쇼핑 바구니");
		jjim = new JButton("마음에 드는 제품");
		// JButton 색상 white로 변경
		cartBtn.setBackground(Color.white);
		jjim.setBackground(Color.white);
		// JButton 투명하게
		cartBtn.setOpaque(true);
		jjim.setOpaque(true);
		cartUpPanel.setLayout(new GridLayout(2, 1));
		uupPnl1 = new JPanel();
		// 패널 백그라운드 색 바꾸기
		uupPnl1.setBackground(Color.white);
		uupPnl1.setLayout(new FlowLayout(FlowLayout.LEFT));
		udwPnl1 = new JPanel();
		// 패널 백그라운드 색 바꾸기
		udwPnl1.setBackground(Color.white);
		udwPnl1.setLayout(new FlowLayout(FlowLayout.LEFT));
		closeBtn = new JButton(closeIcon);
		closeBtn.setBackground(Color.white);
		closeBtn.setOpaque(true);
		closeBtn.setBorderPainted(false);
		// 윗패널에 윗윗패널 close 버튼 붙힘
		uupPnl1.add(closeBtn);
		// 윗패널에 아래패널 '쇼핑바구니'및'마음에 드는 제품' 버튼 붙힘
		udwPnl1.add(cartBtn);
		udwPnl1.add(jjim);
		// 윗패널 패널 2개 붙힘
		cartUpPanel.add(uupPnl1);
		cartUpPanel.add(udwPnl1);
	}

	void mdlPnl() {
		// 기존에는 '장바구니에 아무것도 없다는 패널 표시' + 메뉴에서 상품을 추가하면 바뀜
		if (cartFlag == 0) { // 장바구니에 아무것도 없다면
			emptyCart();
		} else { // 장바구니에 상품이 하나라도 추가되어 있다면
			addCart();
		}
	}

	// void mdlPnl()에서 장바구니 아무것도 없을때
	void emptyCart() {
		cartMdlPnl.setLayout(new GridLayout(3, 1));
		cartMdlPnl.setBackground(Color.white);
		inPnl1 = new JPanel();
		inPnl1.setBackground(Color.white);
		inPnl1.setLayout(new GridLayout(2, 1));
		lb1 = new JLabel("장바구니가 비어 있습니다", JLabel.CENTER);
		lb2 = new JLabel();
		emptyP = new JLabel(emptyCart, JLabel.CENTER);
		inPnl1.add(emptyP);
		inPnl1.add(lb1);
		cartMdlPnl.add(lb2);
		cartMdlPnl.add(inPnl1);
	}

	// void mdlPnl()에서 장바구니에 하나 이상 있을 때
	void addCart() {
		if (pName.size() == 1) {
			cartMdlPnl.setLayout(new GridLayout((pName.size() + 1), 1));
			cartMdlPnl.setBackground(Color.white);
		} else if (pName.size() > 1) {
			cartMdlPnl.setLayout(new GridLayout(pName.size(), 1));
		}
		for (int i = 0; i < pName.size(); i++) {
			String path = "product_images" + "/" + pName.get(i) + ".jpg";
			JPanel jpn1 = new JPanel();
			JPanel jpn2 = new JPanel();
			JPanel jpn3 = new JPanel();
			JPanel jpn4 = new JPanel();
			ImageIcon icon = new ImageIcon(path);
			Image img = icon.getImage();
			Image changeImg = img.getScaledInstance(220, 330, Image.SCALE_SMOOTH);
			ImageIcon changeIcon = new ImageIcon(changeImg);
			JButton listButton = new JButton(changeIcon);
			JTextArea jtaName = new JTextArea("\n" + pName.get(i));
			
			JTextArea jtaSizenColor = new JTextArea("\n" + pSize.get(i) + "  | " + pColor.get(i));
			JTextArea jtaPrice = new JTextArea(pPrice.get(i) + " 원");
			JTextArea jta2 = new JTextArea("\n          " + pQty.get(i));
			jtaName.setEditable(false);
			jtaPrice.setEditable(false);
			jtaSizenColor.setEditable(false);
			jta2.setEditable(false);
			cartS = new JButton("저장");
			cartSave.add(cartS);
			cartD = new JButton("삭제");
			cartDelete.add(cartD);
			plusFont = new Font("+", Font.BOLD, 20);
			minusFont = new Font("+", Font.BOLD, 20);
			JButton cartPlus = new JButton("+");
			cartPlus.setFont(plusFont);
			JButton cartMinus = new JButton("-");
			cartMinus.setFont(minusFont);
			cartPlus.setBackground(Color.white);
			cartPlus.setOpaque(true);
			cartPlus.setBorderPainted(false);
			cartMinus.setBackground(Color.white);
			cartMinus.setOpaque(true);
			cartMinus.setBorderPainted(false);
			cartS.setBackground(Color.white);
			cartS.setOpaque(true);
			cartS.setBorderPainted(false);
			cartD.setBackground(Color.white);
			cartD.setOpaque(true);
			cartD.setBorderPainted(false);
			listButton.setPreferredSize(new Dimension(220,330));
			cartMdlPnl.add(jpn1);
			jpn1.setLayout(new GridLayout(1, 2));
			jpn1.setPreferredSize(new Dimension(300, 300));
			jpn1.add(jpn2);
			jpn2.add(listButton);
			jpn1.add(jpn3);
			jpn3.setLayout(new GridLayout(6, 1));
			jpn3.add(jtaName);
			jpn3.add(jtaSizenColor);
			jta2Controll.add(jta2);
			jpn3.add(jtaPrice);
			jtaPriceControll.add(jtaPrice);
			jpn3.add(jpn4);
			jpn3.add(cartS);
			jpn3.add(cartD);
			cartS.setHorizontalAlignment(SwingConstants.LEFT);
			cartD.setHorizontalAlignment(SwingConstants.LEFT);
			//jpn2.setPreferredSize(new Dimension(220,400));
			jpn1.setBackground(Color.white);
			jpn2.setBackground(Color.white);
			jpn3.setBackground(Color.white);
			cp.setBackground(Color.white);
			jpn4.setLayout(new GridLayout(1, 3));
			quanPlus.add(cartPlus);
			quanMinus.add(cartMinus);
			jpn4.add(cartMinus);
			jpn4.add(jta2);
			jpn4.add(cartPlus);
		}
		for (int i = 0; i < pName.size(); i++) {
			quanPlus.get(i).addActionListener(this);
			quanMinus.get(i).addActionListener(this);
		}
		for (int i = 0; i < cartDelete.size(); i++) {
			cartDelete.get(i).addActionListener(this);
		}
		for (int i = 0; i < cartSave.size(); i++) {
			cartSave.get(i).addActionListener(this);
		}
	}

	void cartDownPnl() {
		totaltotal = 0;
		cartDownPnl = new JPanel();
		cartDownPnl.setBackground(Color.white);
		sumF = new JLabel("합계");
		for (int i = 0; i < pPrice.size(); i++) {// 1
			Cart.totaltotal += (Integer.parseInt(pPrice.get(i)));// 1
		} // 1
		cost = new JTextArea("\t\t" + Cart.totaltotal + "원");// 1
		payment = new JButton("계속");
		// 버튼 사이즈 조정
		payment.setPreferredSize(new Dimension(130, 30));
//		JTextArea 입력 막기 + 폰트 색상 검은색으로 변경		
		cost.setEnabled(false);
		cost.setDisabledTextColor(Color.BLACK);
		payment.setForeground(Color.white);
		payment.setBackground(Color.black);
		payment.setOpaque(true);
		cartDownPnl.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
		cartDownPnl.add(payment);
		cartDownPnl.add(sumF);
		cartDownPnl.add(cost);
	}

}
