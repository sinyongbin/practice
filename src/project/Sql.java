package project;

class Sql {
	public static final String SQL1="insert into MEMBER values(?,?,?,?,?,SYSDATE,?,?)";
	public static final String SQL2="select M_PWD from MEMBER where M_EMAIL=?";
	public static final String SQL3="select M_EMAIL from MEMBER where M_EMAIL=?";
	public static final String SQL4="select c_seq from purchase where m_email=?"; 
	
	public static final String SQL9="select M_NAME from MEMBER where M_EMAIL=?";
	
	public static final String SQL100="select CG_NAME from CATEGORY where CG_CODE =? or CG_CODE =? or CG_CODE =?"; //카테고리 코드에서 메뉴에 맞게 상품이름 불러오기

	public static final String SQL101="select distinct G_NAME, G_PRICE from GOODS where CG_CODE = ? or CG_CODE =? or CG_CODE = ?";
	public static final String SQL102="select distinct G_NAME, G_PRICE from goods natural join category where cg_name = ? ";
	public static final String SQL103="select g_name, g_price, g_exp, g_color, g_code from goods where g_name = ?";
	public static final String SQL104 = "select distinct G_NAME, G_PRICE from GOODS where G_SEX = '여성'";
	public static final String SQL105 = "select distinct G_NAME, G_PRICE from GOODS where G_SEX = '남성'";
	public static final String SQL106 = "select distinct G_NAME, G_PRICE from GOODS where (G_COLOR like ? and G_SEX = '여성') or (G_NAME like ? and G_SEX = '여성')";
	public static final String SQL107 = "select distinct G_NAME, G_PRICE from GOODS where (G_COLOR like ? and G_SEX = '남성') or (G_NAME like ? and G_SEX = '남성')";	
	
	public static final String SQL201 = "select M_NAME, M_EMAIL, M_ADDR from MEMBER where M_EMAIL = ?";
	public static final String SQL202 = "update MEMBER set M_PWD = ? where M_PWD = ? and M_EMAIL = ?";
	public static final String SQL203 = "select distinct to_char(p_date, 'YYYY-MM-DD HH24:MI') from purchase where M_EMAIL = ? order by to_char(p_date, 'YYYY-MM-DD HH24:MI') desc";
	public static final String SQL204 = "SELECT G.G_NAME, P.P_SITU, SUM(G.G_PRICE) AS TOTAL_PRICE, P.PD_SEQ, PD.PD_QUAN FROM PURCHASE P JOIN PURCHASE_DETAIL PD ON P.PD_SEQ = PD.PD_SEQ JOIN GOODS G ON PD.G_CODE = G.G_CODE AND PD.S_NAME = G.S_NAME WHERE P.M_EMAIL = ? AND to_char(p_date, 'YYYY-MM-DD HH24:MI') = ? GROUP BY G.G_NAME, P.P_SITU, P.PD_SEQ, PD.PD_QUAN";

	public static final String SQL300="insert into CART values(CART_SEQ.nextval,?,?,?,?)";//상품 시퀀스 수량 사이즈 상품코드 이메일
	public static final String SQL301="select G_NAME, G_COLOR, G_PRICE, C_QUAN, S_NAME, C_SEQ, G_CODE from CART JOIN GOODS using(G_CODE, S_NAME) where M_EMAIL =? order by C_SEQ desc";
	public static final String SQL302="delete from cart where g_code=(select distinct g_code from goods where g_name = ?) and m_email=?";
	public static final String SQL303="select G_NAME, G_PRICE, S_NAME, G_CODE from WISH JOIN GOODS using(G_CODE, S_NAME) where M_EMAIL = ?";
	public static final String SQL304="insert into WISH values(WISH_SEQ.nextval,?,?,?,?)";
	public static final String SQL305="select distinct G_NAME from WISH JOIN GOODS using(G_CODE, S_NAME) where M_EMAIL = ?";
	public static final String SQL306="insert into PURCHASE values(PURCHASE_SEQ.nextval,?,SYSDATE,?,1,?,?)"; //장바구니코드, 금액, 이메일, 주문상세seq
	public static final String SQL307="select sum(c_quan) from cart where m_email= ? ";
	public static final String SQL308="delete from wish where g_code=(select distinct g_code from goods where g_name = ?) and m_email=?";
	public static final String SQL309="insert into Cart values(CART_SEQ.nextval,1,?,?,?)";
	public static final String SQL310="select g_quan from goods where g_name= ? and s_name= ?";
	public static final String SQL311="update goods set g_quan=? where g_name= ? and s_name= ?";
	public static final String SQL312="insert into PURCHASE_DETAIL values(PURCHASE_DETAIL_SEQ.nextval,?,?,?,?,?)";
	public static final String SQL313="select pd_seq from purchase_detail where c_seq=? and m_email=?";
	
	public static final String SQL400="delete from cart where M_EMAIL=?";
	
}
