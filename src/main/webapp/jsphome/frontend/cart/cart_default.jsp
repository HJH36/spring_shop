<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.*"%>
<%@page import="kr.co.mtshop.frontend.dao.*"%>
<%@page import="kr.co.mtshop.common.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.JSONArray"%>

<%
   LocalValue lv = new LocalValue();
	
	//세션을 리스트로 만들기
	ArrayList<String> product_list = (ArrayList)session.getAttribute("product_list");
	ArrayList<String> product_number = (ArrayList)session.getAttribute("product_number");
	ArrayList<String> product_price_array = (ArrayList)session.getAttribute("product_price_array");
	
	if(product_list==null){
		product_list = new ArrayList<String>();
		session.setAttribute("product_list", product_list);
	}

	if(product_number==null){
		product_number = new ArrayList<String>();
		session.setAttribute("product_number", product_number);
	}
	
	if(product_price_array==null){
		product_price_array = new ArrayList<String>();
		session.setAttribute("product_price_array", product_price_array);
	}

   ProductFrontendDAO PD = new ProductFrontendDAO();

   JSONObject product_info = new JSONObject();
   
   //변수 선언
   int product_idx;
   String product_name=null;
   int product_cost;
   int product_price;
   int product_discount;
   String product_image=null;
   String product_contents=null;
   String reg_dt = null;
   String mod_dt = null;
   
   //상품 개수
   int product_num;

   //구매 제품 총 가격
   int order_total_price = 0;

   //전체 개수
   int total_count = product_list.size();
   
   //숫자 천자리 쉼표 찍기
   DecimalFormat df = new DecimalFormat("###,###");
   
%>   

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 리스트</title>

   <!-- 합쳐지고 최소화된 최신 CSS -->
   <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
   
   <!-- 부가적인 테마 -->
   <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">

</head>
<body>

   <jsp:include page="/jsphome/frontend/top.jsp" flush="true" />
   
   <div class="container">
   
      <h3>장바구니</h3>
      
      <div style="text-align:right;margin:10px;">
         장바구니 상품 개수 : <%=total_count %> 개
      </div>
      
   	  <form name="OrderForm" method="post">
   	  
      <table class="table table-bordered">
      
         <thead>
            <tr>
               <th class="text-center" style="background-color:ivory;">No.</th>
               <th class="text-center" style="background-color:ivory;">사진</th>
               <th class="text-center" style="background-color:ivory;">제품 이름</th>
               <th class="text-center" style="background-color:ivory;">원가</th>
               <th class="text-center" style="background-color:ivory;">판매가</th>
               <th class="text-center" style="background-color:ivory;">할인가</th>
               <th class="text-center" style="background-color:ivory;">개수</th>
               <th class="text-center" style="background-color:ivory;">등록일</th>
               <th class="text-center" style="background-color:ivory;">주문</th>
            </tr>
         </thead>
         
         <%if(total_count>0){ %>
            <%for(int i=0;i<product_list.size();i++){ %>
            <% 
               product_idx = Integer.parseInt(product_list.get(i));
               product_info = PD.ProductInfo(product_idx);
            		   
               product_name = (String)product_info.get("product_name");
               product_cost = (Integer)product_info.get("product_cost");
               product_price = (Integer)product_info.get("product_price");
               product_discount = (Integer)product_info.get("product_discount");
               product_image = (String)product_info.get("product_image");
               product_contents = (String)product_info.get("product_contents");
               reg_dt = (String)product_info.get("reg_dt");
               mod_dt = (String)product_info.get("mod_dt");
               
               //상품 개수
               product_num = Integer.parseInt(product_number.get(i));
               
               //총 금액 합계
               order_total_price += Integer.parseInt(product_price_array.get(i));
            %>
            
            
               <tr>
                  <td style = "text-align:center; vertical-align:middle; "><%=total_count-i%></td>
                  <td style = "text-align:center; vertical-align:middle; ">
                     <%if(product_image.equals("")){ %>
                        <img src="/jsphome/frontend/images/noimage.png" width="150px">
                     <%}else{ %>
                        <img src="<%=lv.FILEUPLOAD_ROOT_PATH %>/<%=product_image %>" width="150px">
                     <%} %>
                  </td>
                  <td style="vertical-align:middle;font-weight:700;color:#5858FA;">
                     <a href="javascript:send_view('<%=product_idx%>', '1', '', '','');"><%=product_name %></a>
                  </td>
                  <td style="vertical-align:middle; text-align:right;"><%=df.format(product_cost)%> 원</td>
                  <td style="vertical-align:middle; text-align:right;"><del><%=df.format(product_price) %></del> 원</td>
                  <td style="vertical-align:middle;color:red;font-weight:700; text-align:right;"><%=df.format(product_discount) %> 원</td>
                  <td style="vertical-align:middle; text-align:center;"><%=product_num %></td>
                  <td style="vertical-align:middle; text-align:center;"><%=reg_dt.substring(0, 10) %></td>
                  <td style="vertical-align:middle; text-align:center;">
                  
                  	<div style="margin-bottom:10px;">
                  		 <button type="button" class="btn btn-danger btn-sm" OnClick="send_delete('<%=i%>');">삭제하기</button>
                  	</div>
                     
                  </td>
               </tr>

				<input type="hidden" name="product_idx" value="<%=product_idx%>" >
				<input type="hidden" name="product_discount" value="<%=product_discount%>" >
				<input type="hidden" name="order_total_price" value="<%=order_total_price%>" >

            <%} %>
            
         <%}else{ %>
   
            <tr>
               <td colspan="9" style="height:150px;text-align:center;vertical-align:middle;">
                  현재 장바구니에 상품이 없습니다.
               </td>
            </tr>
         <%} %>
   
   			 <tr>
               <td colspan="9" style="height:150px; text-align:right; vertical-align:middle; font-weight : 900;">
               		전체 주문 금액 : <span style="color:red;"><%=df.format(order_total_price) %></span> 원
               </td>
            </tr>
   	
   
      </table>
      
      <div class="text-center">
      	<button type = "button" class = "btn btn-primary btn-sm" onclick="send_order();">주문하기</button>
      </div>
      
   </form>   
      
   </div>

   <jsp:include page="/jsphome/frontend/footer.jsp" flush="true" />

   <!-- Jquery CDN -->
   <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.js"></script>
   <!-- 합쳐지고 최소화된 최신 자바스크립트 -->
   <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

</body>
</html>

<script>

   function send_view(product_idx, current_page, searchtitle, searchstring, type){
      var url="product_frontend_view.do?product_idx="+product_idx;
      url+="&current_page="+current_page;
      url+="&searchtitle="+searchtitle;
      url+="&searchstring="+searchstring;
      url+="&type="+type;
      
      location.href=url;
      
   }
   
	//바로 구매하기
	function send_order(product_idx){
		
		var obj = document.OrderForm;
		obj.action = "/order_frontend_default.do";
		obj.submit();
		
	}
	
	//장바구니 삭제하기
	function send_delete(key_idx){
		var ans = confirm("장바구니에서 삭제하시겠습니까?");
		
		if(ans){
			location.href="/cart_delete.do?key_idx="+key_idx;	
		}else{
			return false;
		}
	}

</script>