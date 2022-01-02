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

import kr.co.mtshop.common.ConnectionDB;


public class OrderFrontendDAO {
	
	
	/**
	 * 주문 정보 등록하기
	 * @param params
	 * @throws Exception
	 */
	public void OrderInsert(HashMap<String, String> params) throws Exception{
		
		 //변수 처리
	      String order_group_idx = params.get("order_group_idx");
	      String member_idx = params.get("member_idx");
	      String order_total_price = params.get("order_total_price");
	      String order_name = params.get("order_name");
	      String order_phone = params.get("order_phone");
	      String order_zipcode = params.get("order_zipcode");
	      String order_raddress = params.get("order_raddress");
	      String order_jaddress = params.get("order_jaddress");
	      String order_address = params.get("order_address");
	      
	      String delivery_name = params.get("delivery_name");
	      String delivery_phone = params.get("delivery_phone");
	      String delivery_zipcode = params.get("delivery_zipcode");
	      String delivery_raddress = params.get("delivery_raddress");
	      String delivery_jaddress = params.get("delivery_jaddress");
	      String delivery_address = params.get("delivery_address");
	      String delivery_message = params.get("delivery_message");

	      
	      Connection conn = null;
	      ConnectionDB connectionDB = new ConnectionDB();
	      PreparedStatement pstmt = null;
	      String sql = null;
	      
	      try {
	         conn = connectionDB.YesConnectionDB();
	         
	         sql = "insert into shop_order("
	               + "order_group_idx, "
	               + "member_idx, "
	               + "order_total_price, "
	               + "order_name, "
	               + "order_phone, "
	            
	               + "order_zipcode, "
	               + "order_raddress, "
	               + "order_jaddress, "
	               + "order_address, "
	               + "delivery_name, "
	              
	               + "delivery_phone, "
	               + "delivery_zipcode, "
	               + "delivery_raddress, "
	               + "delivery_jaddress, "
	               + "delivery_address, "
	             
	               + "delivery_message, "
	               + "reg_dt, "
	               + "mod_dt ) values( "
	               + "?, ?, ?, ?, ?, "
	               + "?, ?, ?, ?, ?, "
	               + "?, ?, ?, ?, ?, "
	               + "?, now(), now() )";
	         
	         
	         pstmt = conn.prepareStatement(sql);
	         pstmt.setString(1, order_group_idx);
	         pstmt.setString(2, member_idx);
	         pstmt.setString(3, order_total_price);
	         pstmt.setString(4, order_name);
	         pstmt.setString(5, order_phone);
	         pstmt.setString(6, order_zipcode);
	         pstmt.setString(7, order_raddress);
	         pstmt.setString(8, order_jaddress);
	         pstmt.setString(9, order_address);
	         
	         pstmt.setString(10, delivery_name);
	         pstmt.setString(11, delivery_phone);
	         pstmt.setString(12, delivery_zipcode);
	         pstmt.setString(13, delivery_raddress);
	         pstmt.setString(14, delivery_jaddress);
	         pstmt.setString(15, delivery_address);
	         pstmt.setString(16, delivery_message);

	         pstmt.executeUpdate();
	         
	      } catch (SQLException e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	      }

	}
	
	/**
	 * 주문 그룹 아이디
	 * @return
	 * @throws SQLException
	 */
	public int OrderMaxId() throws Exception{
		
		Connection conn = null;
		ConnectionDB connectionDB = new ConnectionDB();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;

		int max_number = 0;
		try {
			conn = connectionDB.YesConnectionDB();
			sql = "select max(order_group_idx) from shop_order";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				max_number = rs.getInt(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return max_number;
	}
	
	/**
	 * 주문 제품 아이디 넣기
	 * @param product_idx
	 * @param order_group_idx
	 * @param order_state
	 * @throws Exception
	 */
	public void ProductOrderInsert(int product_idx, int order_group_idx, int order_current_price, String order_state) throws Exception{
		
	      
	      Connection conn = null;
	      ConnectionDB connectionDB = new ConnectionDB();
	      PreparedStatement pstmt = null;
	      String sql = null;
	      
	      try {
	         conn = connectionDB.YesConnectionDB();
	         
	         sql = "insert into product_order("
	               + "product_idx, "
	               + "order_group_idx, "
	               + "order_current_price, "
	               + "order_state, "
	               + "order_reg_dt, "
	               + "order_mod_dt ) values( "
	               + "?, ?, ?, ?, "
	               + "now(), now() )";
	         
	         
	         pstmt = conn.prepareStatement(sql);
	         pstmt.setInt(1, product_idx);
	         pstmt.setInt(2, order_group_idx);
	         pstmt.setInt(3, order_current_price);
	         pstmt.setString(4, order_state);

	         pstmt.executeUpdate();
	         
	      } catch (SQLException e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	      }

	}
	
}

