package kr.co.mtshop.frontend.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import kr.co.mtshop.common.ConnectionDB;

public class EmailFrontendDAO {
	
	/**
	 * 이메일 등록하기
	 * @param params
	 * @throws SQLException
	 */
	public void EmailInsert(HashMap<String, String> params) throws SQLException{
		
		String from_email = (String)params.get("from_email");
		String from_name = (String)params.get("from_name");
		String to_email = (String)params.get("to_email");
		String subject = (String)params.get("subject");
		String email_contents = (String)params.get("email_contents");
		
		Connection conn = null;
		ConnectionDB connectionDB = new ConnectionDB();
		String sql = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = connectionDB.YesConnectionDB();
			sql = "insert into email ("
					+ "from_email,"
					+ "from_name,"
					+ "to_email, "
					+ "subject, "
					+ "email_contents, "
					+ "reg_dt) "
					+ "values (?, ?, ?, ?, ?, now())";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, from_email);
			pstmt.setString(2, from_name);
			pstmt.setString(3, to_email);
			pstmt.setString(4, subject);
			pstmt.setString(5, email_contents);
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
