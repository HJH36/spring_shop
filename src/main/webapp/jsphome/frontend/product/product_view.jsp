<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.*"%>
<%@page import="kr.co.mtshop.frontend.dao.*"%>
<%@page import="kr.co.mtshop.common.*"%>

<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.JSONArray"%>

<%
	LocalValue lv = new LocalValue();

	//현재 페이지
	int current_page = 1;
	if(request.getParameter("current_page")!=null){
		current_page = Integer.parseInt(request.getParameter("current_page"));
	}
	
	int product_idx = 1;
	if(request.getParameter("product_idx")!=null){
		product_idx = Integer.parseInt(request.getParameter("product_idx"));
	}

	int product_num = 1;
	if(request.getParameter("product_num")!=null){
		product_num = Integer.parseInt(request.getParameter("product_num"));
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

	//검색 키워드
	String type="list";
	if(request.getParameter("type")!=null){
		type = request.getParameter("type");
	}
	
	//한페이지에 가져오는 리스트 개수
	int gainCounter = 10;
	if(request.getParameter("gainCounter")!=null){
		gainCounter = Integer.parseInt(request.getParameter("gainCounter"));
	}

	ProductFrontendDAO PD = new ProductFrontendDAO();

	JSONObject product_info = new JSONObject();
	
	product_info = PD.ProductInfo(product_idx);
	
	//변수 선언
	String product_name = (String)product_info.get("product_name");
	int product_cost = (Integer)product_info.get("product_cost");
	int product_price = (Integer)product_info.get("product_price");
	int product_discount = (Integer)product_info.get("product_discount");
	String product_image = (String)product_info.get("product_image");
	String product_contents = (String)product_info.get("product_contents");
	String reg_dt = (String)product_info.get("reg_dt");
	String mod_dt = (String)product_info.get("reg_dt");
	
	
	//숫자 천자리 쉼표 찍기
	DecimalFormat df = new DecimalFormat("###,###");
	
	String strParams = "";
	strParams+="&current_page="+current_page;
	strParams+="&searchtitle="+searchtitle;
	strParams+="&searchstring="+searchstring;
	strParams+="&searchstring="+searchstring;
	strParams+="&gainCounter="+gainCounter;
	
%>	
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 정보 보기</title>

	<!-- 합쳐지고 최소화된 최신 CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
	
	<!-- 부가적인 테마 -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
	
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.js"></script>

</head>


<body>

	<jsp:include page="/jsphome/frontend/top.jsp" flush="true" />
	
	<div class="container">

		<form name="OrderForm" id="WriteForm" method="post" enctype="multipart/form-data">

		<!-- 상세페이지 테이블 -->
		<table class="table">
			<tr>
				<td colspan="2">
					<span style = "font-size:20px; font-weight:800; color: blue;">
						<%=product_name %>
					</span>
				</td>
			</tr>
				
			<tr>
				<td>
					<%if(product_image.equals("")) { %>
						<img src="/jsphome/frontend/images/noimage.png" width="250px">
					<%}else{ %>
						<img src="<%=lv.FILEUPLOAD_ROOT_PATH %>/<%=product_image %>" width="250px">
					<%} %>
				</td>
				
				<td>
					<table class="table">
						<tr>
							<td style="width: 20%;">상품 원가</td>
							<td style="width: 80%"><%=df.format(product_cost)%> 원</td>
						</tr>
						<tr>
							<td style="width: 20%;">상품 판매가</td>
							<td style="width: 80%"><del><%=df.format(product_price)%></del>
								원</td>
						</tr>
						<tr>
							<td style="width: 20%;">상품 할인가</td>
							<td style="width: 80%"><%=df.format(product_discount)%> 원
							</td>
						</tr>
						<tr>
							<td style="width: 20%;">개수</td>
							<td style="width: 80%">
								<select name="product_num">
									<option value="1">1</option>
									<option value="2">2</option>
									<option value="3">3</option>
									<option value="4">4</option>
									<option value="5">5</option>
									<option value="6">6</option>
									<option value="7">7</option>
									<option value="8">8</option>
									<option value="9">9</option>
									<option value="10">10</option>
								</select>
							
						</tr>
						
						<tr>
							<td colspan="2">
								<button type="button" class="btn btn-primary btn-sm" onclick = "send_cart();">장바구니</button>
								<button type="button" class="btn btn-primary btn-sm" Onclick="send_order('<%=product_idx %>');">바로구매</button>
								<button type="button" class="btn btn-default btn-sm" Onclick="send_list('<%=type %>','<%=strParams %>');">리스트</button>
							</td>
						</tr>
						
						
					</table>
				</td>
			</tr>
			
			<tr>
				<td colspan="2" style="padding-top:20px; paddint-bottom:20px;">
					<%=product_contents %>				
				</td>
			</tr>
		</table>
	    
	    
	    <input type="hidden" name="product_idx" value="<%=product_idx%>">
	    <input type="hidden" name="product_discount" value="<%=product_discount%>">
	    <input type="hidden" name="current_page" value="<%=current_page%>">
	    <input type="hidden" name="searchtitle" value="<%=searchtitle%>">
	    <input type="hidden" name="searchstring" value="<%=searchstring%>">
	    <input type="hidden" name="gainCounter" value="<%=gainCounter%>">
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

	$(document).ready(function(){
		
	})
	
		//리스트로 이동
	function send_list(type, strParams){
		if(type="list"){
			location.href="/product_frontend_list_default.do?key="+strParams
		}else{
			location.href="/product_frontend_default.do?key="+strParams
		}
		
	}
	
		//바로 구매하기
	function send_order(product_idx){
		location.href="/order_frontend_default.do?product_idx="+product_idx;
	}

		//장바구니 이동
	function send_cart(product_idx){
		var obj = document.OrderForm;
		obj.action="cart_write_default.do";
		obj.submit();
	}

</script>
