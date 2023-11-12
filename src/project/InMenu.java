package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.*;

import javax.swing.plaf.basic.BasicScrollBarUI;

public class InMenu extends JFrame implements ActionListener {
	JPanel[] jpn = new JPanel[10];
	JButton[] btn = new JButton[10];
	JTextArea[] jta = new JTextArea[10];
	ArrayList<JButton> filterButton = new ArrayList<JButton>();
	Vector<String> filterName = new Vector<String>();
	ArrayList<JButton> productButton = new ArrayList<JButton>();
	ArrayList<JTextArea> productDesc = new ArrayList<JTextArea>();
	ArrayList<JPanel> productPanel = new ArrayList<JPanel>();
	Vector<String> itemName = new Vector<String>();
	Vector<String> itemPrice = new Vector<String>();

	JButton backBtn;
	JLabel filterLbl;
	JScrollPane inMenuScroll;

	ImageIcon backIcon = new ImageIcon("icons/back1.png");

	// 윗 패널
	JPanel inMenuUpPanel, inMenuMiddlePanel, uupPnl, udwPnl, p1, p2, p3;

	String type_code = "WOMAN";

	// DB객체 불러오기
	ShoppingSiteDbHelper dh = new ShoppingSiteDbHelper();

	String idx1, idx2, idx3;

	Vector<String> itemName2 = new Vector<String>();
	Vector<String> itemPrice2 = new Vector<String>();
	ArrayList<JButton> productButton2 = new ArrayList<JButton>();

	String prodName, prodPrice, prodDesc, prodColor, prodCode;
	int inMenuFlag = 0;

	InMenu(String idx1, String idx2, String idx3) {
		this.idx1 = idx1;
		this.idx2 = idx2;
		this.idx3 = idx3;
		init();
	}

	void init() {
		filterName.add("모두보기");
		getFilter();
		upPnl();
		// 중간 패널
		mdlPnl();
		Runnable doScroll = new Runnable() {
			public void run() {
				inMenuScroll.getVerticalScrollBar().setValue(0);
			}
		};
		SwingUtilities.invokeLater(doScroll);
	}

	public void actionPerformed(ActionEvent ae) {

	}

	void getBackBtn() {
		MainGUI m1 = new MainGUI();
		m1.setmenuP();
	}

	// 여성(상품코드가 짝수), 남성(상품코드가 홀수)

	void getFilter() {
		getFilterName(idx1, idx2, idx3); // 20번대 여성 상의
	}

	// SQL100="select CG_NAME from CATEGORY where CG_CODE =? or CG_CODE =? or
	// CG_CODE =?";
	// SQL101="select distinct G_NAME, G_PRICE from GOODS where CG_CODE = ? or
	// CG_CODE =? or CG_CODE = ?"
	void getFilterName(String category_num1, String category_num2, String category_num3) {
		itemName.clear();
		itemPrice.clear();
		ResultSet rs = null;
		ResultSet rs1 = null;
		try {
			dh.pstmt100.setString(1, category_num1);
			dh.pstmt100.setString(2, category_num2);
			dh.pstmt100.setString(3, category_num3);
			rs = dh.pstmt100.executeQuery();
			while (rs.next()) {
				filterName.add(rs.getString(1));
			}
			dh.pstmt101.setString(1, category_num1);
			dh.pstmt101.setString(2, category_num2);
			dh.pstmt101.setString(3, category_num3);
			rs1 = dh.pstmt101.executeQuery();
			while (rs1.next()) {
				itemName.add(rs1.getString(1));
				itemPrice.add(rs1.getString(2));
			}

		} catch (SQLException se) {
			System.out.println("getFilterName() se: " + se);
		}
	}

	// SQL102="select distinct G_NAME, G_PRICE from goods natural join category
	// where cg_name = ? "
	void getFilterSelect1(String category_name) {
		itemName2.clear();
		itemPrice2.clear();
		productButton2.clear();
		inMenuMiddlePanel.removeAll();
		ResultSet rs = null;

		try {
			dh.pstmt102.setString(1, category_name);
			rs = dh.pstmt102.executeQuery();
			while (rs.next()) {
				itemName2.add(rs.getString(1));
				itemPrice2.add(rs.getString(2));
			}

		} catch (SQLException se) {
			System.out.println("getFilterSelect1() se: " + se);
		}

		for (int i = 0; i < itemName2.size(); i++) {
			String path = "product_images" + "/" + itemName2.get(i) + ".jpg";
			ImageIcon icon = new ImageIcon(path);
			Image img = icon.getImage();
			Image changeImg = img.getScaledInstance(190, 320, Image.SCALE_SMOOTH);
			ImageIcon changeIcon = new ImageIcon(changeImg);
			JButton listButton = new JButton(changeIcon);
			JPanel panel = new JPanel();
			JTextArea textArea = new JTextArea(itemName2.get(i) + "  \t \n " + itemPrice2.get(i) + " 원");
			textArea.setForeground(Color.white);
			textArea.setBackground(Color.black);
			panel.setBackground(Color.black);

			int rowcount = 0;
			if ((itemName2.size() % 2) == 1) {
				rowcount = (itemName2.size() / 2) + 1;
			} else {
				rowcount = (itemName2.size() / 2);
			}
			inMenuMiddlePanel.add(panel);
			inMenuMiddlePanel.setLayout(new GridLayout(rowcount, 2, 10, 10));

			panel.add(listButton);
			panel.add(textArea);

			panel.setPreferredSize(new Dimension(190, 380));
			listButton.setPreferredSize(new Dimension(190, 320));
			textArea.setBounds(0, 0, 200, 30);
			panel.setLayout(new FlowLayout(FlowLayout.LEFT, 1, 1));

			productButton2.add(listButton);
			productPanel.add(panel);
			productDesc.add(textArea);
		}
		inMenuMiddlePanel.revalidate(); // 갱신
		inMenuMiddlePanel.repaint();

		for (int i = 0; i < productButton2.size(); i++) {
			productButton2.get(i).addActionListener(this);
		}

	}

	void getFilterSelect2(String category_num1, String category_num2, String category_num3) {
		itemName2.clear();
		itemPrice2.clear();
		productButton2.clear();
		inMenuMiddlePanel.removeAll();
		ResultSet rs = null;

		try {
			dh.pstmt101.setString(1, category_num1);
			dh.pstmt101.setString(2, category_num2);
			dh.pstmt101.setString(3, category_num3);
			rs = dh.pstmt101.executeQuery();
			while (rs.next()) {
				itemName2.add(rs.getString(1));
				itemPrice2.add(rs.getString(2));
			}

		} catch (SQLException se) {
			System.out.println("getFilterSelect2() se: " + se);
		}

		for (int i = 0; i < itemName2.size(); i++) {
			String path = "product_images" + "/" + itemName2.get(i) + ".jpg";
			ImageIcon icon = new ImageIcon(path);
			Image img = icon.getImage();
			Image changeImg = img.getScaledInstance(190, 320, Image.SCALE_SMOOTH);
			ImageIcon changeIcon = new ImageIcon(changeImg);
			JButton listButton = new JButton(changeIcon);
			JPanel panel = new JPanel();
			JTextArea textArea = new JTextArea(itemName2.get(i) + "  \t \n " + itemPrice2.get(i) + " 원");
			textArea.setForeground(Color.white);
			textArea.setBackground(Color.black);
			panel.setBackground(Color.black);
			int rowcount = 0;
			if (itemName2.size() % 2 == 1) {
				rowcount = (itemName2.size() / 2) + 1;
			} else {
				rowcount = (itemName2.size() / 2);
			}
			inMenuMiddlePanel.add(panel);
			inMenuMiddlePanel.setLayout(new GridLayout(rowcount, 2, 10, 10));
			panel.add(listButton);
			panel.add(textArea);
			panel.setPreferredSize(new Dimension(190, 380));
			listButton.setPreferredSize(new Dimension(190, 320));
			textArea.setBounds(0, 0, 200, 30);
			panel.setLayout(new FlowLayout(FlowLayout.LEFT, 1, 1));
			productButton2.add(listButton);
			productPanel.add(panel);
			productDesc.add(textArea);
		}
		inMenuMiddlePanel.revalidate(); // 갱신
		inMenuMiddlePanel.repaint();

		for (int i = 0; i < productButton2.size(); i++) {
			productButton2.get(i).addActionListener(this);
		}

	}

	void upPnl() {
		inMenuUpPanel = new JPanel();

		for (int i = 0; i < filterName.size(); i++) {
			JButton listButton = new JButton(filterName.get(i));
			listButton.setBackground(Color.white);
			listButton.setOpaque(true);
			filterButton.add(listButton);
		}

		inMenuUpPanel.setLayout(new GridLayout(2, 1));
		uupPnl = new JPanel();
		// 패널 백그라운드 색 바꾸기
		uupPnl.setLayout(new GridLayout(1, 3));
		udwPnl = new JPanel();
		// 패널 백그라운드 색 바꾸기
		udwPnl.setBackground(Color.white);

		// 필터 버튼
		for (int i = 0; i < filterButton.size(); i++) {
			udwPnl.add(filterButton.get(i));
		}

		p1 = new JPanel();
		p1.setLayout(new FlowLayout(FlowLayout.LEFT));
		p2 = new JPanel();
		p3 = new JPanel();
		p1.setBackground(Color.white);
		p2.setBackground(Color.white);
		p3.setBackground(Color.white);
		backBtn = new JButton(backIcon);
		backBtn.setBackground(Color.white);
		backBtn.setOpaque(true);
		backBtn.setBorderPainted(false);
		filterLbl = new JLabel("필터");
		p1.add(backBtn);
		p2.add(filterLbl);
		// 윗패널의 윗윗패널 back 버튼 및 '필터'라벨 붙힘
		uupPnl.add(p1);
		uupPnl.add(p2);
		uupPnl.add(p3);
		// 윗패널 패널 2개 붙힘
		inMenuUpPanel.add(uupPnl);
		inMenuUpPanel.add(udwPnl);
	}

	void mdlPnl() {
		productButton.clear();
		productPanel.clear();
		productDesc.clear();
		inMenuMiddlePanel = new JPanel();
		inMenuScroll = new JScrollPane(inMenuMiddlePanel);
		Runnable doScroll = new Runnable() {
			public void run() {
				inMenuScroll.getVerticalScrollBar().setValue(0);
			}
		};
		SwingUtilities.invokeLater(doScroll);
		inMenuScroll.getVerticalScrollBar().setUI(new TransparentScrollBarUI());
		inMenuScroll.getHorizontalScrollBar().setUI(new TransparentScrollBarUI());
		inMenuScroll.getVerticalScrollBar().setUnitIncrement(20);

		int rowcount = 0;
		if (itemName.size() % 2 == 1) {
			rowcount = (itemName.size() / 2) + 1;
		} else {
			rowcount = (itemName.size() / 2);
		}

		inMenuMiddlePanel.setBorder(BorderFactory.createEmptyBorder(0, 18, 0, 0));
		inMenuMiddlePanel.setLayout(new GridLayout(rowcount, 2, 10, 10));
		inMenuMiddlePanel.setBackground(Color.black);

		for (int i = 0; i < itemName.size(); i++) {
			String path = "product_images" + "/" + itemName.get(i) + ".jpg";
			ImageIcon icon = new ImageIcon(path);
			Image img = icon.getImage();
			Image changeImg = img.getScaledInstance(190, 320, Image.SCALE_SMOOTH);
			ImageIcon changeIcon = new ImageIcon(changeImg);
			JButton listButton = new JButton(changeIcon);
			JPanel panel = new JPanel();
			JTextArea textArea = new JTextArea(itemName.get(i) + "  \t \n " + itemPrice.get(i) + " 원");
			textArea.setForeground(Color.white);
			textArea.setBackground(Color.black);
			panel.setBackground(Color.black);
			inMenuMiddlePanel.add(panel);
			panel.add(listButton);
			panel.add(textArea);
			panel.setPreferredSize(new Dimension(190, 380));
			listButton.setPreferredSize(new Dimension(190, 320));
			textArea.setBounds(0, 0, 200, 30);// 높이를 30으로 설정
			panel.setLayout(new FlowLayout(FlowLayout.LEFT, 1, 1));
			productButton.add(listButton);
			productPanel.add(panel);
			productDesc.add(textArea);
		}
		for (int i = 0; i < productButton.size(); i++) {
			productButton.get(i).addActionListener(this);
		}

	}
}
