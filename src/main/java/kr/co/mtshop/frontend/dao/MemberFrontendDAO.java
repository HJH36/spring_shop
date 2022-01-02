package kr.co.mtshop.frontend.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import kr.co.mtshop.common.CommonUtil;
import kr.co.mtshop.common.ConnectionDB;

public class MemberFrontendDAO {
	
	
	/**
	 * 회원 정보 입력하기
	 * @param params
	 * @throws Exception
	 */
	public void MemberInsert(HashMap<String, String> params) throws Exception{
		
		//변수 처리
		String member_id = params.get("member_id");
		String member_pwd = params.get("member_pwd");
		String member_kind = params.get("member_kind");
		String member_name = params.get("member_name");
		String member_email = params.get("member_email");
		String member_phone = params.get("member_phone");
		String zipcode = params.get("zipcode");
		String member_raddress = params.get("member_raddress");
		String member_jaddress = params.get("member_jaddress");
		String member_address = params.get("member_address");

		Connection conn = null;
		ConnectionDB connectionDB = new ConnectionDB();
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = connectionDB.YesConnectionDB();
			
			sql = "insert into member("
					+ "member_id, "
					+ "member_pwd, "
					+ "member_kind, "
					+ "member_name, "
					+ "member_email, "
					+ "member_phone, "
					+ "zipcode, "
					+ "member_raddress, "
					+ "member_jaddress, "
					+ "member_address, "
					+ "reg_dt, "
					+ "mod_dt ) values( "
					+ "?, ?, ?, ?, ?, "
					+ "?, ?, ?, ?, ?, "
					+ "now(), now() )";
			
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member_id);
			pstmt.setString(2, member_pwd);
			pstmt.setString(3, member_kind);
			pstmt.setString(4, member_name);
			pstmt.setString(5, member_email);
			pstmt.setString(6, member_phone);
			pstmt.setString(7, zipcode);
			pstmt.setString(8, member_raddress);
			pstmt.setString(9, member_jaddress);
			pstmt.setString(10, member_address);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	/**
	 * 회원 리스트 전체 가져오기
	 * @return
	 * @throws SQLException
	 */
	public LinkedHashMap MemberList(int current_page) throws Exception{
		
		Connection conn = null;
		ConnectionDB connectionDB = new ConnectionDB();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		int iEndPage = 10;
		int iStartPage = (current_page*iEndPage)-10;

		LinkedHashMap member_info = new LinkedHashMap();
		LinkedHashMap member_list = new LinkedHashMap();
		
		ArrayList<LinkedHashMap> member_list2 = new ArrayList<LinkedHashMap>();
		
		
		try {
			conn = connectionDB.YesConnectionDB();
			sql = "select * from member "
					+ "order by member_idx desc limit "+iStartPage+", "+iEndPage+" ";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				member_info.put("member_idx", new Integer( rs.getInt("member_idx") ));
				member_info.put("member_id", new String( rs.getString("member_id") ));
				member_info.put("member_pwd", new String( rs.getString("member_pwd") ));
				member_info.put("member_kind", new String( rs.getString("member_kind") ));
				member_info.put("member_name", new String( rs.getString("member_name") ));
				member_info.put("member_email", new String( rs.getString("member_email") ));
				member_info.put("member_phone", new String( rs.getString("member_phone") ));
				member_info.put("zipcode", new String( rs.getString("zipcode") ));
				member_info.put("member_raddress", new String( rs.getString("member_raddress") ));
				member_info.put("member_jaddress", new String( rs.getString("member_jaddress") ));
				member_info.put("member_address", new String( rs.getString("member_address") ));
				member_info.put("reg_dt", new String( rs.getString("reg_dt") ));
				member_info.put("mod_dt", new String( rs.getString("mod_dt") ));
				
				member_list.put( String.valueOf((rs.getInt("member_idx"))), new LinkedHashMap(member_info) );
				member_list2.add(member_info);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return member_list;
	}
	
	
	/**
	 * 회원 정보 가져오기
	 * @param member_idx
	 * @return
	 * @throws SQLException
	 */
	public LinkedHashMap MemberInfo(int member_idx) throws Exception{
		
		Connection conn = null;
		ConnectionDB connectionDB = new ConnectionDB();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		LinkedHashMap member_info = new LinkedHashMap();
		
		try {
			conn = connectionDB.YesConnectionDB();
			sql = "select * from member where member_idx=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, member_idx);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				member_info.put("member_idx", new Integer( rs.getInt("member_idx") ));
				member_info.put("member_id", new String( rs.getString("member_id") ));
				member_info.put("member_pwd", new String( rs.getString("member_pwd") ));
				member_info.put("member_kind", new String( rs.getString("member_kind") ));
				member_info.put("member_name", new String( rs.getString("member_name") ));
				member_info.put("member_email", new String( rs.getString("member_email") ));
				member_info.put("member_phone", new String( rs.getString("member_phone") ));
				member_info.put("zipcode", new String( rs.getString("zipcode") ));
				member_info.put("member_raddress", new String( rs.getString("member_raddress") ));
				member_info.put("member_jaddress", new String( rs.getString("member_jaddress") ));
				member_info.put("member_address", new String( rs.getString("member_address") ));
				member_info.put("reg_dt", new String( rs.getString("reg_dt") ));
				member_info.put("mod_dt", new String( rs.getString("mod_dt") ));
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return member_info;
	}

	/**
	 * 회원 정보(회원 아이디와 회원 비밀번호 이용)
	 * @param member_id
	 * @param member_pwd
	 * @return
	 * @throws SQLException
	 */
	public LinkedHashMap MemberInfo2(String member_id, String member_pwd) throws Exception{
		
		Connection conn = null;
		ConnectionDB connectionDB = new ConnectionDB();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		LinkedHashMap member_info = new LinkedHashMap();
		
		try {
			conn = connectionDB.YesConnectionDB();
			sql = "select * from member where member_id=? and member_pwd=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member_id);
			pstmt.setString(2, member_pwd);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				member_info.put("member_idx", new Integer( rs.getInt("member_idx") ));
				member_info.put("member_id", new String( rs.getString("member_id") ));
				member_info.put("member_pwd", new String( rs.getString("member_pwd") ));
				member_info.put("member_kind", new String( rs.getString("member_kind") ));
				member_info.put("member_name", new String( rs.getString("member_name") ));
				member_info.put("member_email", new String( rs.getString("member_email") ));
				member_info.put("member_phone", new String( rs.getString("member_phone") ));
				member_info.put("zipcode", new String( rs.getString("zipcode") ));
				member_info.put("member_raddress", new String( rs.getString("member_raddress") ));
				member_info.put("member_jaddress", new String( rs.getString("member_jaddress") ));
				member_info.put("member_address", new String( rs.getString("member_address") ));
				member_info.put("reg_dt", new String( rs.getString("reg_dt") ));
				member_info.put("mod_dt", new String( rs.getString("mod_dt") ));
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return member_info;
	}
	
	/**
	 * 회원정보 보기 (member_id 이용)
	 * @param member_id
	 * @return
	 * @throws Exception
	 */
	public JSONObject MemberInfo3(String member_id) throws Exception{
		
		Connection conn = null;
		ConnectionDB connectionDB = new ConnectionDB();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		JSONObject member_info = new JSONObject();
		
		try {
			conn = connectionDB.YesConnectionDB();
			sql = "select * from member where member_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				member_info.put("member_idx", new Integer( rs.getInt("member_idx") ));
				member_info.put("member_id", new String( rs.getString("member_id") ));
				member_info.put("member_pwd", new String( rs.getString("member_pwd") ));
				member_info.put("member_kind", new String( rs.getString("member_kind") ));
				member_info.put("member_name", new String( rs.getString("member_name") ));
				member_info.put("member_email", new String( rs.getString("member_email") ));
				member_info.put("member_phone", new String( rs.getString("member_phone") ));
				member_info.put("zipcode", new String( rs.getString("zipcode") ));
				member_info.put("member_raddress", new String( rs.getString("member_raddress") ));
				member_info.put("member_jaddress", new String( rs.getString("member_jaddress") ));
				member_info.put("member_address", new String( rs.getString("member_address") ));
				member_info.put("reg_dt", new String( rs.getString("reg_dt") ));
				member_info.put("mod_dt", new String( rs.getString("mod_dt") ));
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return member_info;
	}

	/**
	 * 회원정보 보기 ( 회원 이름, 이메일 이용하기)
	 * @param member_name
	 * @param member_email
	 * @return
	 * @throws Exception
	 */
	public JSONObject MemberInfo4(String member_name, String member_email) throws Exception {

		Connection conn = null;
		ConnectionDB connectionDB = new ConnectionDB();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;

		JSONObject member_info = new JSONObject();

		try {
			conn = connectionDB.YesConnectionDB();
			sql = "select * from member where member_name=? and member_email=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member_name);
			pstmt.setString(2, member_email);
			rs = pstmt.executeQuery();
			
			
			while (rs.next()) {
				member_info.put("member_idx", new Integer(rs.getInt("member_idx")));
				member_info.put("member_id", new String(rs.getString("member_id")));
				member_info.put("member_pwd", new String(rs.getString("member_pwd")));
				member_info.put("member_kind", new String(rs.getString("member_kind")));
				member_info.put("member_name", new String(rs.getString("member_name")));
				member_info.put("member_email", new String(rs.getString("member_email")));
				member_info.put("member_phone", new String(rs.getString("member_phone")));
				member_info.put("zipcode", new String(rs.getString("zipcode")));
				member_info.put("member_raddress", new String(rs.getString("member_raddress")));
				member_info.put("member_jaddress", new String(rs.getString("member_jaddress")));
				member_info.put("member_address", new String(rs.getString("member_address")));
				member_info.put("reg_dt", new String(rs.getString("reg_dt")));
				member_info.put("mod_dt", new String(rs.getString("mod_dt")));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return member_info;
	}
	
	/**
	 * 회원 전체 개수 가져오기
	 * @return
	 * @throws SQLException
	 */
	public int MemberTotal() throws Exception{

		Connection conn = null;
		ConnectionDB connectionDB = new ConnectionDB();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		int total_count = 0;
		
		try {
			conn = connectionDB.YesConnectionDB();
			sql = "select count(*) as total_count from member";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				total_count = rs.getInt(1);
//				total_count = rs.getInt("total_count");
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		return total_count;
	}

	
	/**
	 * 회원 정보 수정
	 * @param params
	 * @throws Exception
	 */
	public void MemberModify(HashMap<String, String> params) throws Exception{
		
		//변수 받아서 변수 처리
		int member_idx = Integer.parseInt(params.get("member_idx"));
		String member_id = params.get("member_id");
		String member_pwd = params.get("member_pwd");
		String member_kind = params.get("member_kind");
		String member_name = params.get("member_name");
		String member_email = params.get("member_email");
		String member_phone = params.get("member_phone");
		String zipcode = params.get("zipcode");
		String member_raddress = params.get("member_raddress");
		String member_jaddress = params.get("member_jaddress");
		String member_address = params.get("member_address");

		Connection conn = null;
		ConnectionDB connectionDB = new ConnectionDB();
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = connectionDB.YesConnectionDB();
			
			sql = "update member set "
					+ "member_id = ?, "
					+ "member_pwd = ?, "
					+ "member_kind = ?, "
					+ "member_name = ?, "
					+ "member_email = ?, "
					+ "member_phone = ?, "
					+ "zipcode = ?, "
					+ "member_raddress = ?, "
					+ "member_jaddress = ?, "
					+ "member_address = ?, "
					+ "mod_dt = now() "
					+ "where member_idx = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member_id);
			pstmt.setString(2, member_pwd);
			pstmt.setString(3, member_kind);
			pstmt.setString(4, member_name);
			pstmt.setString(5, member_email);
			pstmt.setString(6, member_phone);
			pstmt.setString(7, zipcode);
			pstmt.setString(8, member_raddress);
			pstmt.setString(9, member_jaddress);
			pstmt.setString(10, member_address);
			pstmt.setInt(11, member_idx);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	
	/**
	 * 회원 삭제
	 * @param member_idx
	 * @throws Exception
	 */
	public void MemberDelete(int member_idx) throws Exception{
		
		Connection conn = null;
		ConnectionDB connectionDB = new ConnectionDB();
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = connectionDB.YesConnectionDB();
			
			sql = "delete from member where member_idx = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, member_idx);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	
	/**
	 * 회원 비밀번호 체크
	 * @param member_idx
	 * @param member_pwd
	 * @return
	 * @throws SQLException
	 */
	public int MemberPwdCheck(int member_idx, String member_pwd) throws Exception{

		Connection conn = null;
		ConnectionDB connectionDB = new ConnectionDB();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		CommonUtil commonUtil = new CommonUtil();
		member_pwd = commonUtil.getEncrypt(member_pwd);
		
		int nflag = 0;
		
		try {
			conn = connectionDB.YesConnectionDB();
			sql = "select count(*) as total_count from member "
					+ "where member_idx=? and member_pwd=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, member_idx);
			pstmt.setString(2, member_pwd);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				nflag = rs.getInt(1);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return nflag;
	}
	
	
	/**
	 * 회원 비밀번호 수정
	 * @param member_idx
	 * @param member_pwd
	 * @throws Exception
	 */
	public void PwdUpdate(int member_idx, String member_pwd) throws Exception{

		Connection conn = null;
		ConnectionDB connectionDB = new ConnectionDB();
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = connectionDB.YesConnectionDB();
			
			sql = "update member set "
					+ "member_pwd = ? "
					+ "where member_idx = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member_pwd);
			pstmt.setInt(2, member_idx);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			pstmt.close();
			conn.close();
		}

	}
	
	/**
	 * 회원 주문 정보 가져오기
	 * @param member_idx
	 * @param current_page
	 * @param searchtitle
	 * @param searchstring
	 * @param gainCounter
	 * @return
	 * @throws SQLException
	 */
	public JSONArray MemberOrderList(int member_idx, int current_page, String searchtitle, String searchstring, int gainCounter) throws SQLException{
	      
	      Connection conn = null;
	      ConnectionDB connectionDB = new ConnectionDB();
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      String sql = null;
	      
	      if(gainCounter<10) {
	         gainCounter=10;
	      }
	      
	      int iEndPage = gainCounter;
	      int iStartPage = (current_page*iEndPage)-gainCounter;

	      JSONObject order_info = new JSONObject();
	      JSONArray order_list = new JSONArray();
	      
	      
	      try {
	         conn = connectionDB.YesConnectionDB();
	         sql = "select * from shop_order as so "
	               + "join product_order as po on po.order_group_idx = so.order_group_idx "
	               + "join product as pd on pd.product_idx=po.product_idx "
	               + "where so.member_idx = '"+member_idx+"' ";
	         
	         if(!searchtitle.equals("")) {
	            sql+="and "+searchtitle+" like '%"+searchstring+"%'  ";
	         }
	         
	         sql += "group by so.order_group_idx order by so.order_group_idx desc ";
	         
	         if(current_page!=0) {
	            sql+= "limit "+iStartPage+", "+iEndPage+" ";
	         }
	         System.out.println(sql);
	         
	         pstmt = conn.prepareStatement(sql);
	         rs = pstmt.executeQuery();
	         
	         while(rs.next()) {
	            order_info = new JSONObject();
	            order_info.put("order_group_idx", rs.getInt("order_group_idx"));
	            order_info.put("member_idx", rs.getInt("member_idx"));
	            order_info.put("order_name", new String( rs.getString("order_name") ));
	            order_info.put("order_phone", new String( rs.getString("order_phone") ));
	            order_info.put("order_zipcode", new String( rs.getString("order_zipcode") ));
	            order_info.put("order_raddress", new String( rs.getString("order_raddress") ));
	            order_info.put("order_jaddress", new String( rs.getString("order_jaddress") ));
	            order_info.put("order_address", new String( rs.getString("order_address") ));
	            
	            order_info.put("delivery_name", new String( rs.getString("delivery_name") ));
	            order_info.put("delivery_phone", new String( rs.getString("delivery_phone") ));
	            order_info.put("delivery_zipcode", new String( rs.getString("delivery_zipcode") ));
	            order_info.put("delivery_raddress", new String( rs.getString("delivery_raddress") ));
	            order_info.put("delivery_jaddress", new String( rs.getString("delivery_jaddress") ));
	            order_info.put("delivery_address", new String( rs.getString("delivery_address") ));
	            order_info.put("delivery_message", new String( rs.getString("delivery_message") ));
	            order_info.put("reg_dt", new String( rs.getString("reg_dt") ));
	            order_info.put("mod_dt", new String( rs.getString("mod_dt") ));
	            
	            order_list.add(order_info);
	            
	         }
	         
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	      
	      
	      return order_list;
	   }
	/**
	 * 회원 주문 전체 개수
	 * @param member_idx
	 * @param searchtitle
	 * @param searchstring
	 * @return
	 * @throws SQLException
	 */
	 public int MemberOrderTotal(int member_idx, String searchtitle, String searchstring) throws SQLException{

	      Connection conn = null;
	      ConnectionDB connectionDB = new ConnectionDB();
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      String sql = null;
	      
	      int total_count = 0;
	      
	      try {
	         conn = connectionDB.YesConnectionDB();
	         sql = "select count(*) from( ";
	         sql += "select count(*) as total_count "
	               + "from shop_order as so "
	               + "join product_order as po on so.order_group_idx=po.order_group_idx "
	               + "join product as pd on pd.product_idx=po.product_idx "
	               + "where so.member_idx= '"+member_idx+"'";
	         
	         if(!searchtitle.equals("")) {
	            sql+="and "+searchtitle+" like '%"+searchstring+"%'  ";
	         }
	         
	         sql += "group by so.order_group_idx) as product_counter ";
	         
	         pstmt = conn.prepareStatement(sql);
	         rs = pstmt.executeQuery();
	         
	         while(rs.next()) {
	            total_count = rs.getInt(1);
//	            total_count = rs.getInt("total_count");
	         }
	         
	         
	      } catch (Exception e) {
	         e.printStackTrace();
	      }

	      
	      return total_count;
	   }
}
