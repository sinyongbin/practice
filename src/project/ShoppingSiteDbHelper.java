package project;

import java.sql.*;

import javax.swing.JOptionPane;

public class ShoppingSiteDbHelper {
	Connection con;
	Statement stmt;
	PreparedStatement pstmt1, pstmt2, pstmt3, pstmt4;
	PreparedStatement pstmt9;
	PreparedStatement pstmt100, pstmt101, pstmt102, pstmt103, pstmt104, pstmt105, pstmt106, pstmt107;
	PreparedStatement pstmt201, pstmt202, pstmt203, pstmt204, pstmt313, pstmt400;
	PreparedStatement pstmt300, pstmt301, pstmt302, pstmt303, pstmt304, pstmt305, pstmt306, pstmt307, pstmt308,
			pstmt309, pstmt310, pstmt311, pstmt312;
	ResultSet rs;

	ShoppingSiteDbHelper() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(Info.URL, Info.USR, Info.PWD);

			stmt = con.createStatement();

			pstmt1 = con.prepareStatement(Sql.SQL1);
			pstmt2 = con.prepareStatement(Sql.SQL2);
			pstmt3 = con.prepareStatement(Sql.SQL3); // 이메일 아이디가 존재하는지 확인
			pstmt4 = con.prepareStatement(Sql.SQL4);
			pstmt9 = con.prepareStatement(Sql.SQL9); // 이메일을 통해서 이름 찾기

			pstmt100 = con.prepareStatement(Sql.SQL100); // 카테고리에 상품 카테고리 이름 찾기
			pstmt101 = con.prepareStatement(Sql.SQL101);
			pstmt102 = con.prepareStatement(Sql.SQL102);
			pstmt103 = con.prepareStatement(Sql.SQL103);
			pstmt104 = con.prepareStatement(Sql.SQL104); // 검색창 여성버튼 클릭시 여성상품
			pstmt105 = con.prepareStatement(Sql.SQL105); // 검색창 남성버튼 클릭시 남성상품
			pstmt106 = con.prepareStatement(Sql.SQL106); // 검색창 여성 (색상, 상품이름)
			pstmt107 = con.prepareStatement(Sql.SQL107); // 검색창 남성 (색상, 상품이름)
			pstmt201 = con.prepareStatement(Sql.SQL201); // 로그인후 계정 들어가면 로그인한 사람의 정보
			pstmt202 = con.prepareStatement(Sql.SQL202); // 비밀번호 설정
			pstmt203 = con.prepareStatement(Sql.SQL203);
			pstmt204 = con.prepareStatement(Sql.SQL204);

			pstmt300 = con.prepareStatement(Sql.SQL300);
			pstmt301 = con.prepareStatement(Sql.SQL301);
			pstmt302 = con.prepareStatement(Sql.SQL302);
			pstmt303 = con.prepareStatement(Sql.SQL303);
			pstmt304 = con.prepareStatement(Sql.SQL304);
			pstmt305 = con.prepareStatement(Sql.SQL305);
			pstmt306 = con.prepareStatement(Sql.SQL306);
			pstmt307 = con.prepareStatement(Sql.SQL307);
			pstmt308 = con.prepareStatement(Sql.SQL308);
			pstmt309 = con.prepareStatement(Sql.SQL309);
			pstmt310 = con.prepareStatement(Sql.SQL310);
			pstmt311 = con.prepareStatement(Sql.SQL311);
			pstmt312 = con.prepareStatement(Sql.SQL312);
			pstmt313 = con.prepareStatement(Sql.SQL313);

			pstmt400 = con.prepareStatement(Sql.SQL400);
			// pstmt104 = con.prepareStatement(Sql.SQL104);
			// pstmt105 = con.prepareStatement(Sql.SQL105);

			con.setAutoCommit(true);

		} catch (ClassNotFoundException cnfe) {
			System.out.println("예외발생 1 : " + cnfe);
		} catch (SQLException se) {
			System.out.println("예외발생 2 : " + se);
		}
	}

	public boolean isAlreadyId(String email) { // 아이디만 검사
		rs = null;
		try {
			pstmt3.setString(1, email);
			rs = pstmt3.executeQuery();
			if (rs.next()) {
				showBox("이미 사용 중인 이메일입니다.", "INFORMATION_MESSAGE");
				return false;
			} else if (!rs.next() && email.length() < 30) {
				showBox("회원가입 가능한 이메일입니다.", "INFORMATION_MESSAGE");
				return true;
			} else if (!rs.next() && email.length() >= 30) {
				showBox("올바르지 않은 이메일입니다.", "INFORMATION_MESSAGE");
				return false;
			}

		} catch (SQLException se) {
			System.out.println("에러발생 4:" + se);
			return false;
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException se1) {
				System.out.println("실패!");
			}
		}
		return false;
	}

	// SQL1="insert into MEMBER values(?,?,?,?,?,SYSDATE,?,?)"
	public void insertMember(String email, String psw, String name, String gender, String birth, String phoneN,
			String addr) {
		rs = null;
		try {
			pstmt1.setString(1, email);
			pstmt1.setString(2, psw);
			pstmt1.setString(3, name);
			pstmt1.setString(4, gender);
			pstmt1.setString(5, birth);
			pstmt1.setString(6, phoneN);
			pstmt1.setString(7, addr);

			rs = pstmt1.executeQuery();

			System.out.println("인서트성공");
			con.commit();

		} catch (SQLException se) {
			System.out.println("예외발생3" + se);
			if (se.getErrorCode() == 12899) { // 중복된 key가 있을때
				showBox("입력하신 정보가 올바르지 않습니다. 다시 확인해주세요.", "INFORMATION_MESSAGE");
			}
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException se1) {
				System.out.println("실패!");
			}
		}
	}

	public void showBox(String contents, String title) {
		JOptionPane.showMessageDialog(null, contents, title, JOptionPane.INFORMATION_MESSAGE);
	}

	public void setFieldEmpty() {
		CreateAccount.emailF.setText("");
		CreateAccount.pswF.setText("");
		CreateAccount.nameF.setText("");
		CreateAccount.phoneNF.setText("");
		CreateAccount.addrF.setText("");
		CreateAccount.emailF.requestFocus();
	}

	public String getName(String email) {
		ResultSet rs = null;
		String name = "";
		try {
			pstmt9.setString(1, email);
			rs = pstmt9.executeQuery();
			if (rs.next())
				name = rs.getString(1);
			return name;

		} catch (SQLException se) {
			System.out.println("getName() : " + se);
			return name;
		}
	}

	public boolean checkId(String email) {
		rs = null;
		try {
			pstmt3.setString(1, email);
			rs = pstmt3.executeQuery();
			if (rs.next())
				return true;
			else if (!rs.next()) {
				showBox("존재하지 않는 아이디입니다.", "");
				LoginAccount.emailF.setText("");
				LoginAccount.passwordF.setText("");
				LoginAccount.emailF.requestFocus();

				return false;
			}

		} catch (SQLException se) {
			System.out.println("에러발생 4:" + se);
			return false;
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException se1) {
				System.out.println("실패!");
			}
		}
		return false;
	}

	// SQL2 = "select M_PWD from MEMBER where M_EMAIL=?"
	public boolean checkPw(String email, String pw) {
		rs = null;
		try {
			pstmt2.setString(1, email);
			rs = pstmt2.executeQuery();

			String pwDB = null;

			if (rs.next())
				pwDB = rs.getString(1).trim();
			System.out.println("pw:" + pw);
			System.out.println("pwDB:" + pwDB);

			if (pw.equals(pwDB)) {
				return true;
			} else if (!pw.equals(pwDB)) {
				showBox("비밀번호가 일치하지 않습니다.", "INFORMATION_MESSAGE");
				return false;
			}

		} catch (SQLException se) {
			System.out.println("예외발생3" + se);
			return false;
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException se1) {
				System.out.println("실패!");
			}
		}

		return false;
	}

	public void makeSeq(String email) { // 이메일에 맞춰서 카트 안에 담기는 시퀀스 생성
		String sqlSeq = "create sequence CART_SEQ_" + email + " start with 1 increment by 1 nocache";
		try {
			stmt.executeUpdate(sqlSeq);
			System.out.println("시퀀스" + email + "생성성공");
		} catch (SQLException se) {
			System.out.println("makeSeq()" + se);
		}
	}

	// SQL> select c_seq from purchase where m_email=?;
	public void checkAccountFlag(String email) {
		rs = null;
		try {
			pstmt4.setString(1, email);
			rs = pstmt4.executeQuery();
			if (rs.next()) {
			} else if (!rs.next()) {
				MainGUI.accountFlag = 0;
			}

		} catch (SQLException se) {
			System.out.println("checkAccountFlag() se: " + se);
		}
	}

}
