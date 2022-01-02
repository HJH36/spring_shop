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


public class OrderBackendDAO {
   
   
   /**
    * 주문 그룹 리스트
    * @param current_page
    * @param searchtitle
    * @param searchstring
    * @param gainCounter
    * @return
    * @throws SQLException
    */
   public JSONArray OrderList(int current_page, String searchtitle, String searchstring, int gainCounter) throws SQLException{
      
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
               + "join product as pd on pd.product_idx=po.product_idx ";
         
         if(!searchtitle.equals("")) {
            sql+="where "+searchtitle+" like '%"+searchstring+"%'  ";
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
      } finally {
			rs.close();
			pstmt.close();
			conn.close();
		}
      
      
      return order_list;
   }

   
   /**
    * 주문 리스트
    * @param order_group_idx
    * @return
    * @throws SQLException
    */
   public JSONArray OrderProductList(int order_group_idx) throws SQLException{
      
      Connection conn = null;
      ConnectionDB connectionDB = new ConnectionDB();
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      String sql = null;
      
      JSONObject order_info = new JSONObject();
      JSONArray order_list = new JSONArray();
      
      
      try {
         conn = connectionDB.YesConnectionDB();
         sql = "select * from product_order "
               + "join product on product.product_idx=product_order.product_idx "
               + "where order_group_idx = ? "
               + "order by product_order.order_group_idx desc, product_order.order_idx asc";
         
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, order_group_idx);
         rs = pstmt.executeQuery();
         
         while(rs.next()) {
            order_info = new JSONObject();
            
            /* 주문 정보 */
            order_info.put("order_idx", rs.getInt("order_idx"));
            order_info.put("order_group_idx", rs.getInt("order_group_idx"));
            order_info.put("order_state", new String( rs.getString("order_state") ));
            order_info.put("order_reg_dt", new String( rs.getString("order_reg_dt") ));
            order_info.put("order_mod_dt", new String( rs.getString("order_mod_dt") ));
            
            /* 주문 제품 정보 */
            order_info.put("product_idx", rs.getInt("product_idx"));
            order_info.put("product_name", new String( rs.getString("product_name") ));
            order_info.put("product_cost", rs.getInt("product_cost"));
            order_info.put("product_price", rs.getInt("product_price"));
            order_info.put("product_discount", rs.getInt("product_discount"));
            order_info.put("product_image", new String( rs.getString("product_image") ));
            order_info.put("product_contents", new String( rs.getString("product_contents") ));
            order_info.put("reg_dt", new String( rs.getString("reg_dt") ));
            order_info.put("mod_dt", new String( rs.getString("mod_dt") ));
            
            order_list.add(order_info);
            
         }
         
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
			rs.close();
			pstmt.close();
			conn.close();
		}
      
      
      return order_list;
   }

   
   /**
    * 주문 정보 보기
    * @param order_group_idx
    * @return
    * @throws SQLException
    */
   public JSONObject OrderInfo(int order_group_idx) throws SQLException{
      
      Connection conn = null;
      ConnectionDB connectionDB = new ConnectionDB();
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      String sql = null;
      
      JSONObject order_info = new JSONObject();
      
      try {
         conn = connectionDB.YesConnectionDB();
         sql = "select * from shop_order where order_group_idx=?";
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, order_group_idx);
         rs = pstmt.executeQuery();
         
         while(rs.next()) {
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
            
         }
         
         
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
			rs.close();
			pstmt.close();
			conn.close();
		}
      
      
      return order_info;
   }

   
   /**
    * 주문 전체 개수
    * @return
    * @throws SQLException
    */
   public int OrderTotal(String searchtitle, String searchstring) throws SQLException{

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
               + "join product as pd on pd.product_idx=po.product_idx ";
         
         if(!searchtitle.equals("")) {
            sql+="where "+searchtitle+" like '%"+searchstring+"%'  ";
         }
         
         sql += "group by so.order_group_idx) as product_counter ";
         
         pstmt = conn.prepareStatement(sql);
         rs = pstmt.executeQuery();
         
         while(rs.next()) {
            total_count = rs.getInt(1);
//            total_count = rs.getInt("total_count");
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
    * 주문 정보 수정
    * @param params
    * @throws Exception
    */
   public void OrderModify(HashMap<String, String> params) throws Exception{
      
      //변수 받아서 변수 처리
      int order_group_idx = Integer.parseInt(params.get("order_group_idx"));
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
         
         sql = "update shop_order set "
               + "order_name = ?, "
               + "order_phone = ?, "
               + "order_zipcode = ?, "
               + "order_raddress = ?, "
               + "order_jaddress = ?, "
               + "order_address = ?, "
               + "delivery_name = ?, "
               + "delivery_phone = ?, "
               + "delivery_zipcode = ?, "
               + "delivery_raddress = ?, "
               + "delivery_jaddress = ?, "
               + "delivery_address = ?, "
               + "delivery_message = ?, "
               + "mod_dt = now() "
               + "where order_group_idx = ? ";
         
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, order_name);
         pstmt.setString(2, order_phone);
         pstmt.setString(3, order_zipcode);
         pstmt.setString(4, order_raddress);
         pstmt.setString(5, order_jaddress);
         pstmt.setString(6, order_address);
         pstmt.setString(7, delivery_name);
         pstmt.setString(8, delivery_phone);
         pstmt.setString(9, delivery_zipcode);
         pstmt.setString(10, delivery_raddress);
         pstmt.setString(11, delivery_jaddress);
         pstmt.setString(12, delivery_address);
         pstmt.setString(13, delivery_message);
         pstmt.setInt(14, order_group_idx);
         pstmt.executeUpdate();
         
      } catch (SQLException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } finally {
			pstmt.close();
			conn.close();
		}

   }


   /**
    * 주문 정보 삭제
    * @param order_group_idx
    * @throws Exception
    */
	public void OrderDelete(int idx_temp, String type) throws Exception {

		Connection conn = null;
		ConnectionDB connectionDB = new ConnectionDB();
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		String sql1 = null;
		String sql2 = null;

		try {
			conn = connectionDB.YesConnectionDB();

			if (type.equals("S")) {
				
				sql1 = "delete from product_order where order_idx = " + idx_temp + "";
				pstmt1 = conn.prepareStatement(sql1);
				pstmt1.executeUpdate();
				
				pstmt1.close();
				conn.close();
				
			} else if (type.equals("M")) {
				
				sql2 = "delete from product_order where order_group_idx = " + idx_temp + "";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.executeUpdate();
				
				sql1 = "delete from shop_order where order_group_idx = " + idx_temp;
				pstmt1 = conn.prepareStatement(sql1);
				pstmt1.executeUpdate();
				
				System.out.println(sql1);
				System.out.println(sql2);
				
				pstmt1.close();
				pstmt2.close();
				conn.close();
				
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
   
   /**
    * 주문 상태 변경하기
    * @param order_group_idx
    * @param order_idx
    * @param order_state
    * @param type
    * @throws Exception
    */
   public void OrderStateModify(int idx_temp, String order_state, String type) throws Exception{
      
      Connection conn = null;
      ConnectionDB connectionDB = new ConnectionDB();
      PreparedStatement pstmt = null;
      
      try {
         conn = connectionDB.YesConnectionDB();
         
         String sql1 = "update product_order set "
               + "order_state = '"+order_state+"' "
               + "where order_idx = '"+idx_temp+"' ";
         
         String sql2 = "update product_order set "
               + "order_state = '"+order_state+"' "
               + "where order_group_idx = '"+idx_temp+"' ";
         
         if(type.equals("S")) {
            pstmt = conn.prepareStatement(sql1);
         }else {
            pstmt = conn.prepareStatement(sql2);
         }
         pstmt.executeUpdate();
         
      } catch (SQLException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } finally {
			pstmt.close();
			conn.close();
		}

   }
   
   /**
    * 주문 전체 금액
    * @param state
    * @return
    * @throws SQLException
    */
   public int OrderTotalPrice(String state) throws SQLException{

	      Connection conn = null;
	      ConnectionDB connectionDB = new ConnectionDB();
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      String sql = null;
	      
	      int total_sum = 0;
	      
	      try {
	         conn = connectionDB.YesConnectionDB();
	         
	         sql = "select sum(order_current_price) from product_order ";
	         
	         if(!state.equals("")) {
	        	 sql += "where order_state = '"+state+"'";
	         }
	        		 
	         		
	         pstmt = conn.prepareStatement(sql);
	         rs = pstmt.executeQuery();
	         
	         while(rs.next()) {
	        	 total_sum = rs.getInt(1);
//	            total_sum = rs.getInt("total_sum");
	         }
	         
	         
	      } catch (Exception e) {
	         e.printStackTrace();
	      } finally {
				rs.close();
				pstmt.close();
				conn.close();
			}

	      
	      return total_sum;
	   }
   
}