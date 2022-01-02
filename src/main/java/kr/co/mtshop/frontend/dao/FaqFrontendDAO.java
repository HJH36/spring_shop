package kr.co.mtshop.frontend.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import kr.co.mtshop.common.ConnectionDB;

public class FaqFrontendDAO {
	
	/**
	 * 자주하는 질문 리스트 보기
	 * @return
	 * @throws SQLException
	 */
	public JSONArray FaqList(int current_page) throws SQLException{
		
		//변수 선언
		Connection conn = null;
		ConnectionDB connectionDB = new ConnectionDB();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		JSONArray faq_list = new JSONArray();
		JSONObject faq_info = new JSONObject();
		
		int iEndPage = 10;
		int iStartPage = (current_page*iEndPage)-10;
		
		
		try {
			conn = connectionDB.YesConnectionDB();
			sql = "select * from faq "
					+ "order by faq_idx desc limit "+iStartPage+", "+iEndPage+" ";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				//질문과 답변 정보
				faq_info = new JSONObject();
				faq_info.put("faq_idx", new Integer(rs.getInt("faq_idx")));
				faq_info.put("faq_title", new String(rs.getString("faq_title")));
				faq_info.put("faq_contents", new String(rs.getString("faq_contents")));
				faq_info.put("faq_reg_dt", new String(rs.getString("faq_reg_dt")));
				faq_info.put("faq_mod_dt", new String(rs.getString("faq_mod_dt")));
				
				faq_list.add(faq_info);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return faq_list;
	}
	
	

	
	/**
	 * 질문과 답변 정보 가져오기
	 * @param faq_idx
	 * @return
	 * @throws SQLException
	 */
	public JSONObject FaqInfo(int faq_idx) throws SQLException{
		
		Connection conn = null;
		ConnectionDB connectionDB = new ConnectionDB();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		JSONObject faq_info = new JSONObject();
		
		try {
			conn = connectionDB.YesConnectionDB();
			sql = "select * from faq "
					+ "where faq_idx = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, faq_idx);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				faq_info = new JSONObject();
				faq_info.put("faq_idx", new Integer(rs.getInt("faq_idx")));
				faq_info.put("faq_title", new String(rs.getString("faq_title")));
				faq_info.put("faq_contents", new String(rs.getString("faq_contents")));
				faq_info.put("faq_reg_dt", new String(rs.getString("faq_reg_dt")));
				faq_info.put("faq_mod_dt", new String(rs.getString("faq_mod_dt")));
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return faq_info;
	}
	
	/**
	 * 질문과 답변 전체 개수
	 * @return
	 * @throws SQLException
	 */
	public int FaqTotal() throws SQLException{
		
		Connection conn = null;
		ConnectionDB connectionDB = new ConnectionDB();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;

		int total_count = 0;
		try {
			conn = connectionDB.YesConnectionDB();
			sql = "select count(*) from faq";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				total_count = rs.getInt(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return total_count;
	}
}


