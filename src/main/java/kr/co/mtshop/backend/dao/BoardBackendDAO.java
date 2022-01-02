package kr.co.mtshop.backend.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import kr.co.mtshop.common.ConnectionDB;

public class BoardBackendDAO {
	
	/**
	 * 게시판 글 전체 가져오기
	 * @return
	 * @throws SQLException
	 */
	public LinkedHashMap BoardList(int current_page) throws SQLException{
		
		//변수 선언
		Connection conn = null;
		ConnectionDB connectionDB = new ConnectionDB();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		LinkedHashMap board_list = new LinkedHashMap();
		LinkedHashMap board_info = new LinkedHashMap();
		
		int iEndPage = 10;
		int iStartPage = (current_page*iEndPage)-10;
		
//		ArrayList<LinkedHashMap> board_list2 = new ArrayList<LinkedHashMap>();
		
		try {
			conn = connectionDB.YesConnectionDB();
			sql = "select * from board "
					+ "join member on member.member_idx=board.member_idx "
					+ "order by ref desc, depth asc limit "+iStartPage+", "+iEndPage+" ";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				//게시글 정보
				board_info.put("board_idx", new Integer(rs.getInt("board_idx")));
				board_info.put("member_idx", new Integer(rs.getInt("member_idx")));
				board_info.put("board_title", new String(rs.getString("board_title")));
				
				board_info.put("ref", new Integer(rs.getInt("ref")));
				board_info.put("subref", new Integer(rs.getInt("subref")));
				board_info.put("depth", new Integer(rs.getInt("depth")));
				board_info.put("visit", new Integer(rs.getInt("visit")));
				
				board_info.put("board_contents", new String(rs.getString("board_contents")));
				board_info.put("photo", new String(rs.getString("photo")));
				board_info.put("reg_dt", new String(rs.getString("reg_dt")));
				board_info.put("mod_dt", new String(rs.getString("mod_dt")));
				
				//회원 정보
				board_info.put("member_idx", rs.getInt("member_idx"));
				board_info.put("member_id", new String( rs.getString("member_id") ));
				board_info.put("member_pwd", new String( rs.getString("member_pwd") ));
				board_info.put("member_kind", new String( rs.getString("member_kind") ));
				board_info.put("member_name", new String( rs.getString("member_name") ));
				board_info.put("member_phone", new String( rs.getString("member_phone") ));
				board_info.put("member_email", new String( rs.getString("member_email") ));
				board_info.put("zipcode", new String( rs.getString("zipcode") ));
				board_info.put("member_raddress", new String( rs.getString("member_raddress") ));
				board_info.put("member_jaddress", new String( rs.getString("member_jaddress") ));
				board_info.put("member_address", new String( rs.getString("member_address") ));

				board_list.put(String.valueOf(rs.getInt("board_idx")) , new LinkedHashMap(board_info));
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return board_list;
	}
	
	
	/**
	 * 메인 게시글 리스트
	 * @return
	 * @throws SQLException
	 */
	public LinkedHashMap BoardList2() throws SQLException{
		
		//변수 선언
		Connection conn = null;
		ConnectionDB connectionDB = new ConnectionDB();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		LinkedHashMap board_list = new LinkedHashMap();
		LinkedHashMap board_info = new LinkedHashMap();
		
		try {
			conn = connectionDB.YesConnectionDB();
			sql = "select * from board "
					+ "join member on member.member_idx=board.member_idx "
					+ "order by ref desc, depth asc limit 5 ";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				//게시글 정보
				board_info.put("board_idx", new Integer(rs.getInt("board_idx")));
				board_info.put("member_idx", new Integer(rs.getInt("member_idx")));
				board_info.put("board_title", new String(rs.getString("board_title")));
				
				board_info.put("ref", new Integer(rs.getInt("ref")));
				board_info.put("subref", new Integer(rs.getInt("subref")));
				board_info.put("depth", new Integer(rs.getInt("depth")));
				board_info.put("visit", new Integer(rs.getInt("visit")));
				
				board_info.put("board_contents", new String(rs.getString("board_contents")));
				board_info.put("photo", new String(rs.getString("photo")));
				board_info.put("reg_dt", new String(rs.getString("reg_dt")));
				board_info.put("mod_dt", new String(rs.getString("mod_dt")));
				
				//회원 정보
				board_info.put("member_idx", rs.getInt("member_idx"));
				board_info.put("member_id", new String( rs.getString("member_id") ));
				board_info.put("member_pwd", new String( rs.getString("member_pwd") ));
				board_info.put("member_kind", new String( rs.getString("member_kind") ));
				board_info.put("member_name", new String( rs.getString("member_name") ));
				board_info.put("member_phone", new String( rs.getString("member_phone") ));
				board_info.put("member_email", new String( rs.getString("member_email") ));
				board_info.put("zipcode", new String( rs.getString("zipcode") ));
				board_info.put("member_raddress", new String( rs.getString("member_raddress") ));
				board_info.put("member_jaddress", new String( rs.getString("member_jaddress") ));
				board_info.put("member_address", new String( rs.getString("member_address") ));

				board_list.put(String.valueOf(rs.getInt("board_idx")) , new LinkedHashMap(board_info));
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return board_list;
	}

	
	/**
	 * 게시글 정보 가져오기
	 * @param board_idx
	 * @return
	 * @throws SQLException
	 */
	public LinkedHashMap BoardInfo(int board_idx) throws SQLException{
		
		Connection conn = null;
		ConnectionDB connectionDB = new ConnectionDB();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		LinkedHashMap board_info = new LinkedHashMap();
		
		try {
			conn = connectionDB.YesConnectionDB();
			sql = "select * from board "
					+ "join member on member.member_idx = board.member_idx "
					+ "where board_idx = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board_idx);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				//게시글 정보
				board_info.put("board_idx", new Integer(rs.getInt("board_idx")));
				board_info.put("member_idx", new Integer(rs.getInt("member_idx")));
				board_info.put("board_title", new String(rs.getString("board_title")));
				board_info.put("ref", new Integer(rs.getInt("ref")));
				board_info.put("subref", new Integer(rs.getInt("subref")));
				board_info.put("depth", new Integer(rs.getInt("depth")));
				board_info.put("visit", new Integer(rs.getInt("visit")));
				board_info.put("board_contents", new String(rs.getString("board_contents")));
				board_info.put("photo", new String(rs.getString("photo")));
				board_info.put("reg_dt", new String(rs.getString("reg_dt")));
				board_info.put("mod_dt", new String(rs.getString("mod_dt")));
				
				//회원 정보
				board_info.put("member_idx", rs.getInt("member_idx"));
				board_info.put("member_id", new String( rs.getString("member_id") ));
				board_info.put("member_pwd", new String( rs.getString("member_pwd") ));
				board_info.put("member_kind", new String( rs.getString("member_kind") ));
				board_info.put("member_name", new String( rs.getString("member_name") ));
				board_info.put("member_phone", new String( rs.getString("member_phone") ));
				board_info.put("member_email", new String( rs.getString("member_email") ));
				board_info.put("zipcode", new String( rs.getString("zipcode") ));
				board_info.put("member_raddress", new String( rs.getString("member_raddress") ));
				board_info.put("member_jaddress", new String( rs.getString("member_jaddress") ));
				board_info.put("member_address", new String( rs.getString("member_address") ));

			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return board_info;
	}
	
	/**
	 * 게시글 전체 개수
	 * @return
	 * @throws SQLException
	 */
	public int BoardTotal() throws SQLException{
		
		Connection conn = null;
		ConnectionDB connectionDB = new ConnectionDB();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;

		int total_count = 0;
		try {
			conn = connectionDB.YesConnectionDB();
			sql = "select count(*) from board";
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

	
	/**
	 * 게시글 등록하기
	 * @param params
	 * @throws SQLException
	 */
	public void BoardInsert(HashMap<String, String> params) throws SQLException{
		
		int board_idx = Integer.parseInt((String)params.get("board_idx"));
		int member_idx = Integer.parseInt((String)params.get("member_idx"));
		String board_title = (String)params.get("board_title");
		
		int ref = Integer.parseInt((String)params.get("ref"));
		int subref = Integer.parseInt((String)params.get("subref"));
		int depth = Integer.parseInt((String)params.get("depth"));
		int visit = Integer.parseInt((String)params.get("visit"));
		
		String board_contents = (String)params.get("board_contents");
		String photo = (String)params.get("photo");
		
		Connection conn = null;
		ConnectionDB connectionDB = new ConnectionDB();
		String sql = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = connectionDB.YesConnectionDB();
			sql = "insert into board ("
					+ "board_idx,"
					+ "member_idx,"
					+ "board_title, "
					+ "ref, "
					+ "subref, "
					+ "depth, "
					+ "visit, "
					+ "board_contents, "
					+ "photo, "
					+ "reg_dt, "
					+ "mod_dt) "
					+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?,now(), now())";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board_idx);
			pstmt.setInt(2, member_idx);
			pstmt.setString(3, board_title);
			pstmt.setInt(4, ref);
			pstmt.setInt(5, subref);
			pstmt.setInt(6, depth);
			pstmt.setInt(7, visit);
			pstmt.setString(8, board_contents);
			pstmt.setString(9, photo);
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * 게시글 수정하기
	 * @param params
	 * @throws SQLException
	 */
	public void BoardModify(HashMap<String, String>params) throws SQLException{
		
		int board_idx = Integer.parseInt((String)params.get("board_idx"));
		String board_title = (String)params.get("board_title");
		String board_contents = (String)params.get("board_contents");
		String photo = (String)params.get("photo");
		
		Connection conn = null;
		ConnectionDB connectionDB = new ConnectionDB();
		String sql = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = connectionDB.YesConnectionDB();
			sql = "update board set "
					+ "board_title = ?, "
					+ "board_contents = ?, "
					+ "photo = ?, "
					+ "mod_dt = now() "
					+ "where board_idx=?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, board_title);
			pstmt.setString(2, board_contents);
			pstmt.setString(3, photo);
			pstmt.setInt(4, board_idx);
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * 게시글 삭제
	 * @param board_idx
	 * @throws SQLException
	 */
	public void BoardDelete(int board_idx) throws SQLException{
		
		Connection conn = null;
		ConnectionDB connectionDB = new ConnectionDB();
		PreparedStatement pstmt = null;
		String sql = null;
		
		
		try {
			conn = connectionDB.YesConnectionDB();
			sql = "delete from board where board_idx = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board_idx);
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * 게시글 방문자 업데이트
	 * @param board_idx
	 * @throws SQLException
	 */
	public void BoardVisitUpdate(int board_idx) throws SQLException{
		
		Connection conn = null;
		ConnectionDB connectionDB = new ConnectionDB();
		PreparedStatement pstmt = null;
		String sql = null;
		
		
		try {
			conn = connectionDB.YesConnectionDB();
			sql = "update board set visit=visit+1 where board_idx=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board_idx);
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 가장 큰 IDX 가져오기
	 * @return
	 * @throws SQLException
	 */
	public int BoardMaxId() throws SQLException{
		
		Connection conn = null;
		ConnectionDB connectionDB = new ConnectionDB();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;

		int max_number = 0;
		try {
			conn = connectionDB.YesConnectionDB();
			sql = "select max(board_idx) from board";
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
	 * 계층형 게시판 업데이트
	 * @param ref
	 * @param depth
	 * @throws SQLException
	 */
	public void BoardDepthUpdate(int ref, int depth) throws SQLException{
		
		Connection conn = null;
		ConnectionDB connectionDB = new ConnectionDB();
		PreparedStatement pstmt = null;
		String sql = null;
		
		
		try {
			conn = connectionDB.YesConnectionDB();
			sql = "update board set depth=depth+1 where ref=? and depth > ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ref);
			pstmt.setInt(2, depth);
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
