package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;;

public class Wish extends JFrame implements ActionListener {
	Container cp;
	JPanel upPanel, MiddelPnl, downPnl, uupPnl, udwPnl;
	JLabel label, lb1, lb2, lb3, lb4, lb5;
	JPanel[] jpn = new JPanel[10];
	JButton[] btn = new JButton[10];
	JTextArea[] jta = new JTextArea[10];
	JButton[] gocart = new JButton[10];
	JButton cart, jjim, closeBtn;
	JScrollPane scroll;
	ImageIcon closeIcon = new ImageIcon("icons/close.png");

	ArrayList<JButton> wishBtn = new ArrayList<JButton>();
	ArrayList<JButton> wishListButton = new ArrayList<JButton>();
	ArrayList<JTextArea> wishDesc = new ArrayList<JTextArea>();
	ArrayList<JPanel> wishPanel = new ArrayList<JPanel>();

	// DB객체 불러오기
	ShoppingSiteDbHelper dh = new ShoppingSiteDbHelper();
	static String wishEmail;

	Vector<String> wishName = new Vector<String>();
	Vector<String> wishPrice = new Vector<String>();
	Vector<String> pSize = new Vector<String>();
	Vector<String> pCode = new Vector<String>();
	GridBagConstraints gbc;

	int wishFlag = 1;
	ImageIcon emptyCart = new ImageIcon("icons/empty.png");

	Wish(String wishEmail) {

		this.wishEmail = wishEmail;
		getWishItems(this.wishEmail);
		init();
	}

	void init() {
		cp = getContentPane();
		// 윗 패널
		upPnl();
		// 중간 패널
		mdlPnl();

	}

	public void actionPerformed(ActionEvent ae) {

	}

	// SQL303="select G_NAME, G_PRICE, S_NAME, G_CODE from WISH JOIN GOODS
	// using(G_CODE, S_NAME) where M_EMAIL = ?";
	void getWishItems(String wishEmail) {

		ResultSet rs = null;
		try {
			dh.pstmt303.setString(1, wishEmail);
			rs = dh.pstmt303.executeQuery();
			while (rs.next()) {
				wishName.add(rs.getString(1));
				wishPrice.add(rs.getString(2));
				pSize.add(rs.getString(3));
				pCode.add(rs.getString(4));
			}

			for (String name : wishName) // Enhanced Loop:
				System.out.println(name);

		} catch (SQLException se) {
			System.out.println("getWishItems() se: " + se);
		}
	}

	// SQL309="insert into Cart values(CART_SEQ.nextval,1,?,?,?)"; //사이즈 이름, 상품코드,
	// 이메일
	void insertCartItem(String itemSize, String itemCode, String mEmail) {
		ResultSet rs = null;
		try {
			dh.pstmt309.setString(1, itemSize);
			dh.pstmt309.setString(2, itemCode);
			dh.pstmt309.setString(3, mEmail);

			rs = dh.pstmt309.executeQuery();

			System.out.println("Cart로 인서트성공");

		} catch (SQLException se) {
			System.out.println("insertCartItem() se: " + se);
		}

	}

	// SQL308=delete from wish where g_code=(select distinct g_code from goods where
	// g_name = ?) and m_email=?
	void deleteWishItems(String itemName, String M_MAIL) {
		try {
			dh.pstmt308.setString(1, itemName);
			dh.pstmt308.setString(2, M_MAIL);
			int i = dh.pstmt308.executeUpdate();
			if (i > 0) {
				System.out.println("wish삭제 성공");
			} else {
				System.out.println("wish삭제 실패");
			}
			// dh.con.commit();
		} catch (SQLException se) {
			System.out.println("deleteWishItem() se: " + se);
		}

	}

	void upPnl() {
		upPanel = new JPanel();
		cart = new JButton("쇼핑 바구니");
		jjim = new JButton("마음에 드는 제품");
		// JButton 색상 white로 변경
		cart.setBackground(Color.white);
		jjim.setBackground(Color.white);
		// JButton 투명하게
		cart.setOpaque(true);
		jjim.setOpaque(true);
		upPanel.setLayout(new GridLayout(2, 1));
		uupPnl = new JPanel();
		// 패널 백그라운드 색 바꾸기
		uupPnl.setBackground(Color.white);
		uupPnl.setLayout(new FlowLayout(FlowLayout.LEFT));
		udwPnl = new JPanel();
		// 패널 백그라운드 색 바꾸기
		udwPnl.setBackground(Color.white);
		udwPnl.setLayout(new FlowLayout(FlowLayout.LEFT));
		closeBtn = new JButton(closeIcon);
		closeBtn.setBackground(Color.white);
		closeBtn.setOpaque(true);
		closeBtn.setBorderPainted(false);
		// 윗패널에 윗윗패널 close 버튼 붙힘
		uupPnl.add(closeBtn);
		// 윗패널에 아래패널 '쇼핑바구니'및'마음에 드는 제품' 버튼 붙힘
		udwPnl.add(cart);
		udwPnl.add(jjim);
		// 윗패널 패널 2개 붙힘
		upPanel.add(uupPnl);
		upPanel.add(udwPnl);
	}

	void mdlPnl() {
		MiddelPnl = new JPanel();
		scroll = new JScrollPane(MiddelPnl);
		if (wishFlag == 0) {
			MiddelPnl.setLayout(new GridLayout(3, 1));
			MiddelPnl.setBackground(Color.white);
			JPanel inPnl1 = new JPanel();
			inPnl1.setBackground(Color.white);
			inPnl1.setLayout(new GridLayout(2, 1));
			lb1 = new JLabel("찜한 목록이 비어 있습니다", JLabel.CENTER);
			lb2 = new JLabel();
			JLabel emptyP = new JLabel(emptyCart, JLabel.CENTER);
			inPnl1.add(emptyP);
			inPnl1.add(lb1);
			MiddelPnl.add(lb2);
			MiddelPnl.add(inPnl1);
		} else {
			int rowcount = 0;
			if (wishName.size() % 2 == 1) {
				rowcount = (wishName.size() / 2) + 1;
			} else {
				rowcount = (wishName.size() / 2);
			}

			MiddelPnl.setBorder(BorderFactory.createEmptyBorder(0, 18, 0, 0));
			MiddelPnl.setLayout(new GridLayout(rowcount, 2, 10, 10));
			MiddelPnl.setBackground(Color.white);

			if (wishName.size() > 1) {// 패널이 하나 이상일때
				for (int i = 0; i < wishName.size(); i++) {
					String path = "product_images" + "/" + wishName.get(i) + ".jpg";
					ImageIcon icon = new ImageIcon(path);
					Image img = icon.getImage();
					Image changeImg = img.getScaledInstance(190, 320, Image.SCALE_SMOOTH);
					ImageIcon changeIcon = new ImageIcon(changeImg);
					JButton listButton = new JButton(changeIcon);
					JPanel panel = new JPanel();

					JPanel panel2 = new JPanel();
					panel2.setLayout(new GridBagLayout());
					gbc = new GridBagConstraints();
					gbc.fill = GridBagConstraints.BOTH;
					gbc.weightx = 1.0;
					gbc.weighty = 1.0;
					JButton addCartBtn = new JButton("추가");
					addCartBtn.setForeground(Color.yellow);
					addCartBtn.setBackground(Color.black);
					// JButton 투명하게
					addCartBtn.setOpaque(true);

					JTextArea textArea = new JTextArea(wishName.get(i) + "  \t \n " + wishPrice.get(i) + " 원");
					// textArea 크기 고정
					textArea.setEditable(false);

					gbc.weighty = 0;
					gbc.gridx = 0;
					gbc.gridy = 0;
					panel2.add(textArea, gbc);
					gbc.weighty = 0.1;
					gbc.gridx = 0;
					gbc.gridy = 1;
					panel2.add(addCartBtn, gbc);

					textArea.setBackground(Color.white);
					panel.setBackground(Color.white);

					panel.add(listButton, BorderLayout.CENTER);
					panel.add(panel2, BorderLayout.SOUTH);

					panel.setPreferredSize(new Dimension(190, 380));
					listButton.setPreferredSize(new Dimension(190, 320));
					addCartBtn.setPreferredSize(new Dimension(190, 21));
					textArea.setBounds(0, 0, 0, 30);// 높이를 30으로 설정
					panel.setLayout(new FlowLayout(FlowLayout.LEFT, 1, 1));

					MiddelPnl.add(panel);

					wishListButton.add(listButton);
					wishPanel.add(panel);
					wishDesc.add(textArea);
					wishBtn.add(addCartBtn);
				}
			} else if (wishName.size() == 1) {// 패널이 하나일때
				for (int i = 0; i < wishName.size(); i++) {
					String path = "product_images" + "/" + wishName.get(i) + ".jpg";
					ImageIcon icon = new ImageIcon(path);
					Image img = icon.getImage();
					Image changeImg = img.getScaledInstance(190, 320, Image.SCALE_SMOOTH);
					ImageIcon changeIcon = new ImageIcon(changeImg);
					JButton listButton = new JButton(changeIcon);
					JPanel panel = new JPanel();

					JPanel panel2 = new JPanel();
					panel2.setLayout(new GridBagLayout());
					gbc = new GridBagConstraints();
					gbc.fill = GridBagConstraints.BOTH;
					gbc.weightx = 1.0;
					gbc.weighty = 1.0;
					JButton addCartBtn = new JButton("추가");
					addCartBtn.setForeground(Color.yellow);
					addCartBtn.setBackground(Color.black);
					// JButton 투명하게
					addCartBtn.setOpaque(true);

					JTextArea textArea = new JTextArea(wishName.get(i) + "  \t \n " + wishPrice.get(i) + " 원");
					// textArea 크기 고정
					textArea.setEditable(false);

					gbc.weighty = 0;
					gbc.gridx = 0;
					gbc.gridy = 0;
					panel2.add(textArea, gbc);
					gbc.weighty = 0.1;
					gbc.gridx = 0;
					gbc.gridy = 1;
					panel2.add(addCartBtn, gbc);
					textArea.setBackground(Color.white);
					panel.setBackground(Color.white);

					panel.add(listButton, BorderLayout.CENTER);
					panel.add(panel2, BorderLayout.SOUTH);

					panel.setPreferredSize(new Dimension(190, 380));
					listButton.setPreferredSize(new Dimension(190, 320));
					addCartBtn.setPreferredSize(new Dimension(190, 21));
					textArea.setBounds(0, 0, 0, 30);// 높이를 30으로 설정
					panel.setLayout(new FlowLayout(FlowLayout.LEFT, 1, 1));

					MiddelPnl.add(panel);

					wishListButton.add(listButton);
					wishPanel.add(panel);
					wishDesc.add(textArea);
					wishBtn.add(addCartBtn);
				}

				JPanel panel1 = new JPanel();
				panel1.setBackground(Color.white);
				MiddelPnl.add(panel1);
			}
		}
	}

	void setUI() {
		setTitle("Online ShoppingMall1");
		setSize(450, 650);
		setVisible(true);
		setLocation(200, 300);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		Wish m1 = new Wish("aa@naver.com");
	}
}
