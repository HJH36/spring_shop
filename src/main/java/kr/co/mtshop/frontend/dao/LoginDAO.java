package kr.co.mtshop.frontend.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.mtshop.common.ConnectionDB;

public class LoginDAO {

	/**
	 * 로그인 체크 확인
	 * @param member_id
	 * @param member_pwd
	 * @return
	 * @throws SQLException
	 */
	public int LoginCheck(String member_id, String member_pwd) throws Exception{

		Connection conn = null;
		ConnectionDB connectionDB = new ConnectionDB();
		PreparedStatement pstmt1 = null;
		ResultSet rs1 = null;
		String sql = null;
		
		PreparedStatement pstmt2 = null;
		ResultSet rs2 = null;
		
		int nflag = 0;
		
		try {
			conn = connectionDB.YesConnectionDB();
			sql = "select count(*) as check_id from member where member_id='"+member_id+"' ";
			pstmt1 = conn.prepareStatement(sql);
			rs1 = pstmt1.executeQuery();
			System.out.println(sql);
			
			while(rs1.next()) {
				
				if(rs1.getInt(1)>0) {
					nflag = 1;
				}
			}
			
			sql = "select count(*) as check_id from member where member_id='"+member_id+"' and member_pwd='"+member_pwd+"' ";
			pstmt2 = conn.prepareStatement(sql);
			rs2 = pstmt2.executeQuery();
			System.out.println(sql);
			
			while(rs2.next()) {
				
				if(rs2.getInt(1)>0) {
					nflag = 2;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			rs1.close();
			rs2.close();
			pstmt1.close();
			pstmt2.close();
			conn.close();
		}
		
		return nflag;
		
	}
	


}
