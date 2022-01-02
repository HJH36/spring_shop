package kr.co.mtshop.backend.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import kr.co.mtshop.common.ConnectionDB;

public class NoticeBackendDAO {
	
	/**
	 * 공지사항 리스트
	 * @return
	 * @throws SQLException
	 */
	public JSONArray NoticeList(int current_page) throws SQLException{
		
		//변수 선언
		Connection conn = null;
		ConnectionDB connectionDB = new ConnectionDB();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		JSONArray notice_list = new JSONArray();
		JSONObject notice_info = new JSONObject();
		
		int iEndPage = 10;
		int iStartPage = (current_page*iEndPage)-10;
		
		try {
			conn = connectionDB.YesConnectionDB();
			sql = "select * from notice where notice_edt > now() "
					+ "union "
					+ "select * from notice where notice_edt < now() "
					+ "limit "+iStartPage+", "+iEndPage+" ";
			
			//sql = "select * from notice "
			//		+ "order by notice_idx desc "
			//		+ "limit "+iStartPage+", "+iEndPage+" ";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				//공지사항 정보
				notice_info = new JSONObject();
				notice_info.put("notice_idx", new Integer(rs.getInt("notice_idx")));
				notice_info.put("notice_title", new String(rs.getString("notice_title")));
				notice_info.put("title_check", new String(rs.getString("title_check")));
				notice_info.put("notice_edt", new String(rs.getString("notice_edt")));
				notice_info.put("notice_contents", new String(rs.getString("notice_contents")));
				notice_info.put("notice_visit", new Integer(rs.getInt("notice_visit")));
				notice_info.put("reg_dt", new String(rs.getString("reg_dt")));
				notice_info.put("mod_dt", new String(rs.getString("mod_dt")));
				
				notice_list.add(notice_info);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			rs.close();
			pstmt.close();
			conn.close();
		}
		
		return notice_list;
	}
	
	
	/**
	 * 공지사항 가져오기
	 * @param board_idx
	 * @return
	 * @throws SQLException
	 */
	public JSONObject NoticeInfo(int notice_idx) throws SQLException{
		
		Connection conn = null;
		ConnectionDB connectionDB = new ConnectionDB();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		JSONObject notice_info = new JSONObject();
		
		try {
			conn = connectionDB.YesConnectionDB();
			sql = "select * from notice "
					+ "where notice_idx = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, notice_idx);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				//공지사항 정보
				notice_info = new JSONObject();
				notice_info.put("notice_idx", new Integer(rs.getInt("notice_idx")));
				notice_info.put("notice_title", new String(rs.getString("notice_title")));
				notice_info.put("title_check", new String(rs.getString("title_check")));
				notice_info.put("notice_edt", new String(rs.getString("notice_edt")));
				notice_info.put("notice_contents", new String(rs.getString("notice_contents")));
				notice_info.put("notice_visit", new Integer(rs.getInt("notice_visit")));
				notice_info.put("reg_dt", new String(rs.getString("reg_dt")));
				notice_info.put("mod_dt", new String(rs.getString("mod_dt")));

			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			rs.close();
			pstmt.close();
			conn.close();
		}
		
		return notice_info;
	}
	
	/**
	 * 공지사항 전체 개수
	 * @return
	 * @throws SQLException
	 */
	public int NoticeTotal() throws SQLException{
		
		Connection conn = null;
		ConnectionDB connectionDB = new ConnectionDB();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;

		int total_count = 0;
		try {
			conn = connectionDB.YesConnectionDB();
			sql = "select count(*) from notice";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				total_count = rs.getInt(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			rs.close();
			pstmt.close();
			conn.close();
		}
		
		return total_count;
	}

	
	/**
	 * 공지사항 등록하기
	 * @param notice_title
	 * @param title_check
	 * @param notice_contents
	 * @throws SQLException
	 */
	public void NoticeInsert(String notice_title, String title_check,  String notice_edt, String notice_contents) throws SQLException{
		
		int notice_visit = 0;
		
		Connection conn = null;
		ConnectionDB connectionDB = new ConnectionDB();
		String sql = null;
		PreparedStatement pstmt = null;
		
		
		try {
			conn = connectionDB.YesConnectionDB();
			sql = "insert into notice ("
					+ "notice_title,"
					+ "title_check, "
					+ "notice_edt, "
					+ "notice_contents, "
					+ "notice_visit, "
					+ "reg_dt, "
					+ "mod_dt) "
					+ "values (?, ?, ?, ?, ?, now(), now())";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, notice_title);
			pstmt.setString(2, title_check);
			pstmt.setString(3, notice_edt);
			pstmt.setString(4, notice_contents);
			pstmt.setInt(5, notice_visit);
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pstmt.close();
			conn.close();
		}
	}

	
	/**
	 * 공지사항 수정하기
	 * @param notice_idx
	 * @param notice_title
	 * @param title_check
	 * @param notice_contents
	 * @throws SQLException
	 */
	public void NoticeModify(int notice_idx, String notice_title, String title_check, String notice_edt, String notice_contents) throws SQLException{
		
		Connection conn = null;
		ConnectionDB connectionDB = new ConnectionDB();
		String sql = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = connectionDB.YesConnectionDB();
			sql = "update notice set "
					+ "notice_title = ?, "
					+ "title_check = ?, "
					+ "notice_edt = ?, "
					+ "notice_contents = ?, "
					+ "mod_dt = now() "
					+ "where notice_idx=?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, notice_title);
			pstmt.setString(2, title_check);
			pstmt.setString(3, notice_edt);
			pstmt.setString(4, notice_contents);
			pstmt.setInt(5, notice_idx);
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pstmt.close();
			conn.close();
		}
	}

	
	/**
	 * 공지사항 삭제
	 * @param notice_idx
	 * @throws SQLException
	 */
	public void NoticeDelete(int notice_idx) throws SQLException{
		
		Connection conn = null;
		ConnectionDB connectionDB = new ConnectionDB();
		PreparedStatement pstmt = null;
		String sql = null;
		
		
		try {
			conn = connectionDB.YesConnectionDB();
			sql = "delete from notice where notice_idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, notice_idx);
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pstmt.close();
			conn.close();
		}
	}

	
	/**
	 * 공지사항 방문자 업데이트
	 * @param board_idx
	 * @throws SQLException
	 */
	public void NoticeVisitUpdate(int notice_idx) throws SQLException{
		
		Connection conn = null;
		ConnectionDB connectionDB = new ConnectionDB();
		PreparedStatement pstmt = null;
		String sql = null;
		
		
		try {
			conn = connectionDB.YesConnectionDB();
			sql = "update notice set notice_visit=notice_visit+1 where notice_idx=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, notice_idx);
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pstmt.close();
			conn.close();
		}
	}
	
}
