package kr.co.mtshop.backend.dao;

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


public class ProductBackendDAO {
	
	
	/**
	 * 제품 리스트
	 * @param current_page
	 * @return
	 * @throws SQLException
	 */
	public JSONArray ProductList(int current_page, String searchtitle, String searchstring, int gainCounter) throws SQLException{
		
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

		JSONObject product_info = new JSONObject();
		JSONArray product_list = new JSONArray();
		
		
		try {
			conn = connectionDB.YesConnectionDB();
			sql = "select * from product ";
			
			if(!searchtitle.equals("")) {
				sql+="where "+searchtitle+" like '%"+searchstring+"%'  ";
			}
			
			sql += "order by product_idx desc ";
			
			if(current_page!=0) {
				sql+= "limit "+iStartPage+", "+iEndPage+" ";
			}
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				product_info = new JSONObject();
				product_info.put("product_idx", rs.getInt("product_idx"));
				product_info.put("product_name", new String( rs.getString("product_name") ));
				product_info.put("product_cost", rs.getInt("product_cost"));
				product_info.put("product_price", rs.getInt("product_price"));
				product_info.put("product_discount", rs.getInt("product_discount"));
				product_info.put("product_image", new String( rs.getString("product_image") ));
				product_info.put("product_contents", new String( rs.getString("product_contents") ));
				product_info.put("reg_dt", new String( rs.getString("reg_dt") ));
				product_info.put("mod_dt", new String( rs.getString("mod_dt") ));
				
				product_list.add(product_info);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return product_list;
	}

	
	
	/**
	 * 제품 등록
	 * @param params
	 * @throws Exception
	 */
	public void ProductInsert(HashMap<String, String> params) throws Exception{
		
		//변수 받아서 변수 처리
		String product_name = params.get("product_name");
		String product_cost = params.get("product_cost");
		String product_price = params.get("product_price");
		String product_discount = params.get("product_discount");
		String product_image = params.get("product_image");
		String product_contents = params.get("product_contents");

		Connection conn = null;
		ConnectionDB connectionDB = new ConnectionDB();
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = connectionDB.YesConnectionDB();
			
			sql = "insert into product("
					+ "product_name, "
					+ "product_cost, "
					+ "product_price, "
					+ "product_discount, "
					+ "product_image, "
					+ "product_contents, "
					+ "reg_dt, "
					+ "mod_dt ) values( "
					+ "?, ?, ?, ?, ?, ?, now(), now() )";
			
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, product_name);
			pstmt.setString(2, product_cost);
			pstmt.setString(3, product_price);
			pstmt.setString(4, product_discount);
			pstmt.setString(5, product_image);
			pstmt.setString(6, product_contents);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	
	/**
	 * 제품 정보 보기
	 * @param product_idx
	 * @return
	 * @throws SQLException
	 */
	public JSONObject ProductInfo(int product_idx) throws SQLException{
		
		Connection conn = null;
		ConnectionDB connectionDB = new ConnectionDB();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		JSONObject product_info = new JSONObject();
		
		try {
			conn = connectionDB.YesConnectionDB();
			sql = "select * from product where product_idx=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, product_idx);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				product_info.put("product_idx", rs.getInt("product_idx") );
				product_info.put("product_name", new String( rs.getString("product_name") ));
				product_info.put("product_cost", rs.getInt("product_cost") );
				product_info.put("product_price", rs.getInt("product_price") );
				product_info.put("product_discount", rs.getInt("product_discount") );
				product_info.put("product_image", new String( rs.getString("product_image") ));
				product_info.put("product_contents", new String( rs.getString("product_contents") ));
				product_info.put("reg_dt", new String( rs.getString("reg_dt") ));
				product_info.put("mod_dt", new String( rs.getString("mod_dt") ));
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return product_info;
	}

	
	/**
	 * 제품 전체 개수
	 * @return
	 * @throws SQLException
	 */
	public int ProductTotal(String searchtitle, String searchstring) throws SQLException{

		Connection conn = null;
		ConnectionDB connectionDB = new ConnectionDB();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		int total_count = 0;
		
		try {
			conn = connectionDB.YesConnectionDB();
			sql = "select count(*) as total_count from product";
			
			if(!searchtitle.equals("")) {
				sql+="where "+searchtitle+" like '%"+searchstring+"%'  ";
			}
			
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
	 * 제품 정보 수정
	 * @param params
	 * @throws Exception
	 */
	public void ProductModify(HashMap<String, String> params) throws Exception{
		
		//변수 받아서 변수 처리
		int product_idx = Integer.parseInt(params.get("product_idx"));
		String product_name = params.get("product_name");
		String product_cost = params.get("product_cost");
		String product_price = params.get("product_price");
		String product_discount = params.get("product_discount");
		String product_image = params.get("product_image");
		String product_contents = params.get("product_contents");

		Connection conn = null;
		ConnectionDB connectionDB = new ConnectionDB();
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = connectionDB.YesConnectionDB();
			
			sql = "update product set "
					+ "product_name = ?, "
					+ "product_cost = ?, "
					+ "product_price = ?, "
					+ "product_discount = ?, "
					+ "product_image = ?, "
					+ "product_contents = ?, "
					+ "mod_dt = now() "
					+ "where product_idx = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, product_name);
			pstmt.setString(2, product_cost);
			pstmt.setString(3, product_price);
			pstmt.setString(4, product_discount);
			pstmt.setString(5, product_image);
			pstmt.setString(6, product_contents);
			pstmt.setInt(7, product_idx);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	/**
	 * 제품 삭제
	 * @param product_idx
	 * @throws Exception
	 */
	public void ProductDelete(int product_idx) throws Exception{
		
		Connection conn = null;
		ConnectionDB connectionDB = new ConnectionDB();
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = connectionDB.YesConnectionDB();
			
			sql = "delete from product where product_idx = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, product_idx);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	
}
