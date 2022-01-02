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
	ArrayList<String> product_list1 = (ArrayList)session.getAttribute("product_list");
	
	if(product_list1==null){
		product_list1 = new ArrayList<String>();
		session.setAttribute("product_list", product_list1);
	}
	
   //현재 페이지
   int current_page = 1;
   if(request.getParameter("current_page")!=null){
      current_page = Integer.parseInt(request.getParameter("current_page"));
   }
   
   //검색 컬럼 이름
   String searchtitle="";
   if(request.getParameter("searchtitle")!=null){
      searchtitle = request.getParameter("searchtitle");
   }
   
   //검색 키워드
   String searchstring="";
   if(request.getParameter("searchstring")!=null){
      searchstring = request.getParameter("searchstring");
   }

   //type is list or gallery
   String type="gallery";
   if(request.getParameter("type")!=null){
	   type = request.getParameter("type");
   }
   
   //한페이지에 가져오는 리스트 개수
   int gainCounter = 10;
   if(request.getParameter("gainCounter")!=null){
      gainCounter = Integer.parseInt(request.getParameter("gainCounter"));
   }

   ProductFrontendDAO PD = new ProductFrontendDAO();

   JSONArray product_list = new JSONArray();
   JSONObject product_info = new JSONObject();
   
   product_list = PD.ProductList1(current_page, searchtitle, searchstring, gainCounter);
   
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

   //전체 개수
   int total_count = PD.ProductTotal(searchtitle, searchstring);
   
   //전체 페이지
   int total_page = (int)Math.ceil(total_count/(10*1d));
   
   
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
   
      <h3>상품 리스트</h3>
      
      <div style="text-align:right;margin:10px;">
         현재 페이지 : <%=current_page %>/<%=total_page %> | 전체 상품 수 : <%=total_count %> 개
      </div>

      <div class = "form-inline" style="text-align:right;margin:10px;">
      	<select class = "form-control input" name="type" OnChange="send_list_gallery(this);">
      		<%if(type.equals("list")){ %>
      			<option value="list" selected>list</option>
      			<option value="gallery">gallery</option>
      		<%}else{ %>
      			<option value="list">list</option>
      			<option value="gallery" selected>gallery</option>
      		<%} %>
      	</select>
      </div>

		
		
			
			
	 <%if(total_count>0){ %>
         
         <div class="row">

            <%for(int i=0;i<product_list.size();i++){ %>
            <%
               product_info = (JSONObject)product_list.get(i);
               product_idx = (Integer)product_info.get("product_idx");
               product_name = (String)product_info.get("product_name");
               product_cost = (Integer)product_info.get("product_cost");
               product_price = (Integer)product_info.get("product_price");
               product_discount = (Integer)product_info.get("product_discount");
               product_image = (String)product_info.get("product_image");
               product_contents = (String)product_info.get("product_contents");
               reg_dt = (String)product_info.get("reg_dt");
               mod_dt = (String)product_info.get("reg_dt");
            
            %>

              <div class="col-sm-6 col-md-4">
                <div class="thumbnail">
                  <%if(product_image.equals("")){ %>
                     <img src="/jsphome/backend/images/noimage.png" width="150px">
                  <%}else{ %>
                     <img src="<%=lv.FILEUPLOAD_ROOT_PATH %>/<%=product_image %>" width="150px">
                  <%} %>

				<div class="caption text-center">
						<h3><%=product_name%></h3>
						<p><%=df.format(product_discount)%> 원</p>
						<p>
							<button type="button" class="btn btn-default btn-sm" OnClick="send_cart('<%=product_idx%>', '<%=product_discount%>');">장바구니</button> 
							<button type="button" class="btn btn-danger btn-sm" OnClick="send_order('<%=product_idx%>', '<%=product_discount%>');">바로구매</button>
						</p>
					</div>
				</div>
			</div>
			
            <%} %>
           </div>
         <%}else{ %>
   			<div class="text-center">
           	 현재 등록된 상품이 없습니다.
        	 </div>
         <%} %>
   			
      <div class="text-center" >
      
         <nav>
           <ul class="pagination">
             <li>
               <a href="/product_frontend_default.do?current_page=1" aria-label="Previous">
                 <span aria-hidden="true">&laquo;</span>
               </a>
             </li>

            <%for(int i=0;i<total_page;i++){ %>
               <%if(current_page==i+1){ %>
                  <li class="active"><a href="/product_frontend_default.do?current_page=<%=i+1%>"><%=i+1 %></a></li>
               <%}else{ %>
                  <li><a href="/product_frontend_default.do?current_page=<%=i+1%>"><%=i+1 %></a></li>
               <%} %>
            <%} %>

             <li>
               <a href="/product_frontend_default.do?current_page=<%=total_page%>" aria-label="Next">
                 <span aria-hidden="true">&raquo;</span>
               </a>
             </li>
           </ul>
         </nav>

      </div>
   
   
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
	function send_order(product_idx, product_discount){
		location.href="/order_frontend_default.do?product_idx="+product_idx+"&product_discount="+product_discount;
	}
	
	//장바구니 담기
	function send_cart(product_idx, product_discount){
		location.href="/cart_write_default.do?product_idx="+product_idx+"&product_discount="+product_discount;
	}
	
	//리스트,갤러리 변경
	function send_list_gallery(obj){
		var type = obj.value;
		if(type=="list"){
			location.href="/product_frontend_list_default.do";
		}else{
			location.href="/product_frontend_default.do";
		}
	}
	
</script>