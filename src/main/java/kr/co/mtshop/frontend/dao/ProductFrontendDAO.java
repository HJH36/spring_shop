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


public class ProductFrontendDAO {
	
	
	/**
	 * 제품 리스트
	 * @param current_page
	 * @return
	 * @throws SQLException
	 */
	public JSONArray ProductList1(int current_page, String searchtitle, String searchstring, int gainCounter) throws SQLException{
		
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

	
	
}
