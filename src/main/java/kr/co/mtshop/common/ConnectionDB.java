package kr.co.mtshop.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.LinkedHashMap;

public class ConnectionDB {
	
	private Connection conn;
	
	private LocalValue LV = new LocalValue();
	
	public ConnectionDB() {
	}
	
	public Connection YesConnectionDB() {
		
		try {
			
			//MariaDB
			/*===========================================================================*/
			String url = "jdbc:mariadb://localhost:3306/"+LV.DB_NAME+"?autoReconnect=true&useSSL=false";
			String id = LV.DB_ID; // SQL 사용자 이름
			String pw = LV.DB_PWD; // SQL 사용자 패스워드
			Class.forName("org.mariadb.jdbc.Driver"); // DB와 연동하기 위해 DriverManager에 등록한다.
			conn = DriverManager.getConnection(url, id, pw); // DriverManager 객체로부터 Connection 객체를 얻어온다.
			/*===========================================================================*/		

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	

}
