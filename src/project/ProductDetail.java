package project;

import javax.naming.spi.DirStateFactory.Result;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ProductDetail extends JFrame {
	Container cp;
	JPanel prod1, prod2, prod3;
	JPanel productUpPnl, productUpPnlLeft, productUpPnlRight, productMdlPnl, downPnl, downPnlup, downPnldown;
	JPanel productM1;
	JLabel label, lb1, lb2, lb3, lb4, lb5;
	GridBagConstraints gbc;
	static JPanel[] jpn10 = new JPanel[3];
	static JButton[] btn10 = new JButton[3];
	static JTextArea[] jta10 = new JTextArea[3];
	//
	Vector<String> SizeText = new Vector<String>();
	String idx1, idx2, idx3, idx4;
	//
	JTextArea show;
	JButton addButton, exit, wishButton;
	JScrollPane proDetailScroll;
	ImageIcon prodImgs[] = new ImageIcon[3];

	// DB객체 불러오기
	ShoppingSiteDbHelper dh = new ShoppingSiteDbHelper();

	String itemName, itemPrice, itemDesc, itemColor, itemCode;
	
	String productQty, productSizeN, productCode, mMail;
	
	ProductDetail(String itemName) {
		this.itemName = itemName;	
		init();
	}

	void init() {
		getItem();
	}

	void getItem() {
		getItemName(itemName);
	}

//SQL103="select g_name, g_price, g_exp, g_color, g_code from goods where g_name = ?";	
	void getItemName(String itemName) {
		ResultSet rs = null;
		try {
			dh.pstmt103.setString(1, itemName);
			rs = dh.pstmt103.executeQuery();
			while (rs.next()) {
				itemName = rs.getString(1);
				itemPrice = rs.getString(2);
				itemDesc = rs.getString(3);
				itemColor = rs.getString(4);
				itemCode = rs.getString(5);
			}

		} catch (SQLException se) {
			System.out.println("getItemName() se: " + se);
		}
	}


	void productM1(String itemCode) {
		int val =Integer.valueOf(itemCode);
		String[] buttons= new String[4];
		if(val<4999)//옷카테고리
		{
		 buttons[0]= "S";
		 buttons[1]= "M";
		 buttons[2]= "L";
		 buttons[3]= "XL";
			int num = JOptionPane.showOptionDialog(null, "Select Size", "TEST", JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, buttons, "");
			// 좌측부터 0~3
			if (num == 0) {// size s
				idx2 = "S";
				getFilter(itemCode);
				JOptionPane.showMessageDialog(null, "장바구니 이동 완료");
			} else if (num == 1) {// m
				idx2 = "M";
				getFilter(itemCode);
				JOptionPane.showMessageDialog(null, "장바구니 이동 완료");
			} else if (num == 2) {// l
				idx2 = "L";
				getFilter(itemCode);
				JOptionPane.showMessageDialog(null, "장바구니 이동 완료");
			} else if (num == 3) {// xl
				idx2 = "XL";
				getFilter(itemCode);
				JOptionPane.showMessageDialog(null, "장바구니 이동 완료");
			}
		}
		else if(val>5000 && val<7000)//신발 카테고리
		{
		//String[] buttons = { "38", "39", "40", "XL" };
			 buttons[0]= "38";
			 buttons[1]= "39";
			 buttons[2]= "40.5";
			 buttons[3]= "42";
				int num = JOptionPane.showOptionDialog(null, "Select Size", "TEST", JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, buttons, "");
				System.out.println(num);// 좌측부터 0~3
				if (num == 0) {// size s
					idx2 = "38";
					getFilter(itemCode);
					JOptionPane.showMessageDialog(null, "장바구니 이동 완료");
				} else if (num == 1) {// m
					idx2 = "39";
					getFilter(itemCode);
					JOptionPane.showMessageDialog(null, "장바구니 이동 완료");
				} else if (num == 2) {// l
					idx2 = "40.5";
					getFilter(itemCode);
					JOptionPane.showMessageDialog(null, "장바구니 이동 완료");
				} else if (num == 3) {// xl
					idx2 = "42";
					getFilter(itemCode);
					JOptionPane.showMessageDialog(null, "장바구니 이동 완료");
				}
			}
		
		else if(val>7000)//액세서리		
		{
			idx2="FREE";
			getFilter(itemCode);
			JOptionPane.showMessageDialog(null, "장바구니 이동 완료");
		}
	}

	void getFilter(String itemCode) {
		getFilterName("1", idx2, itemCode, LoginAccount.email);//수량,사이즈,상품코드,이메일
		//LoginAccount에서 이메일 받아옴	
		
	}

	void getFilterName(String G_QUAN, String S_NAME, String G_CODE, String M_MAIL) {
		productQty = G_QUAN;
		productSizeN = S_NAME;
		productCode = G_CODE;
		mMail= M_MAIL;
		ResultSet rs = null;
		try {
			dh.pstmt300.setString(1, G_QUAN);
			dh.pstmt300.setString(2, S_NAME);
			dh.pstmt300.setString(3, G_CODE);
			dh.pstmt300.setString(4, M_MAIL);

			int insert = dh.pstmt300.executeUpdate();
			if (insert > 0) {
				System.out.println("주문 성공");
			} else {
				System.out.println("주문 실패");
			}
		} catch (SQLException se) {
			System.out.println(se);
		}
		
	}

}