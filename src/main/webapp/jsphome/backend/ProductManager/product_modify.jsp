<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.*"%>
<%@page import="kr.co.mtshop.backend.dao.*"%>
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
	
	//한페이지에 가져오는 리스트 개수
	int gainCounter = 10;
	if(request.getParameter("gainCounter")!=null){
		gainCounter = Integer.parseInt(request.getParameter("gainCounter"));
	}

	ProductBackendDAO PD = new ProductBackendDAO();

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
	
%>	

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 수정하기</title>

	<!-- 합쳐지고 최소화된 최신 CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
	
	<!-- 부가적인 테마 -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
	
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.js"></script>

</head>


<body>

	<jsp:include page="/jsphome/backend/top.jsp" flush="true" />
	
	<div class="container">

	    <h3>상품 수정하기</h3>
		<form name="WriteForm" id="WriteForm" method="post" enctype="multipart/form-data">
	    <table class="table">
	        <tr>
	            <td style="width: 20%;">상품 이름</td>
	            <td style="width:80%">
	                <input type="text" style="width:350px;max-width: 350px;" name="product_name" id="product_name" value="<%=product_name%>">
	            </td>
	        </tr>
	
	        <tr>
	            <td style="width: 20%;">상품 원가</td>
	            <td style="width:80%">
	                <input type="number" style="width:200px;max-width: 150px;" name="product_cost" id="product_cost" value="<%=product_cost%>">
	            </td>
	        </tr>
	        
	        <tr>
	            <td style="width: 20%;">상품 판매가</td>
	            <td style="width:80%">
	                <input type="number" style="width:200px;max-width: 150px;" name="product_price" id="product_price" value="<%=product_price%>">
	            </td>
	        </tr>
	        
	        <tr>
	            <td style="width: 20%;">상품 할인가</td>
	            <td style="width:80%">
	                <input type="number" style="width:200px;max-width: 150px;" name="product_discount" id="product_discount" value="<%=product_discount%>">
	            </td>
	        </tr>
	        
	        <tr>
	            <td style="width: 20%;">이전 이미지</td>
	            <td style="width:80%">
	            	<%if(product_image.equals("")){ %>
	            		상품 이미지 없음
	            	<%}else{ %>
	            		<img src="<%=lv.FILEUPLOAD_ROOT_PATH %>/<%=product_image %>" width="200px">
	            	<%} %>
	            </td>
	        </tr>
	        
	        <tr>
	            <td style="width: 20%;">상품 사진</td>
	            <td style="width:80%">
	                <input type="file" name="product_image">
	            </td>
	        </tr>
	
	        <tr>
	            <td style="width: 20%;">내용</td>
	            <td style="width:80%">
	            
	            	<script type="text/javascript" src="/jsphome/backend/Scripts/SE/js/HuskyEZCreator.js" charset="utf-8"></script>
	            	<textarea name="product_contents" id="product_contents" style="width:100%;height:300px;display:none;"><%=product_contents %></textarea>
	            	
					            	
	            	<!-- 
	            	<textarea name="board_contents" rows="10" cols="70"></textarea>
	            	-->
	            	
	            	
	            </td>
	        </tr>
	    </table>
	    
	    <div class="text-center">
		    <button class="btn btn-primary btn-sm" type="button" onclick="send_modify();">수정하기</button>
		    <button class="btn btn-default btn-sm" type="button" onclick="reset();">새로작성</button>
		    <button class="btn btn-default btn-sm" type="button" onclick="send_list();">리스트</button>
	    </div>
	    
	    <input type="hidden" name="product_idx" value="<%=product_idx%>">
	    <input type="hidden" name="current_page" value="<%=current_page%>">
	    <input type="hidden" name="searchtitle" value="<%=searchtitle%>">
	    <input type="hidden" name="searchstring" value="<%=searchstring%>">
	    <input type="hidden" name="gainCounter" value="<%=gainCounter%>">
	    </form>
	
	</div>


	<jsp:include page="/jsphome/backend/footer.jsp" flush="true" />

	<!-- Jquery CDN -->
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.js"></script>
	<!-- 합쳐지고 최소화된 최신 자바스크립트 -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

</body>
</html>

<script>

	$(document).ready(function(){
		
	})
	
	var oEditors = [];
	nhn.husky.EZCreator.createInIFrame({
	    oAppRef: oEditors,
	    elPlaceHolder: "product_contents",
	    sSkinURI: "/jsphome/backend/Scripts/SE/SmartEditor2Skin.html",
	    fCreator: "createSEditor2"
	});	

	function send_modify(){
		var obj = document.WriteForm;
		
		var ans = confirm("정말 수정 하시겠습니까?");
		
		if(obj.product_name.value==""){
			alert("상품의 이름을 넣어주세요");
			obj.product_name.focus();
			return false;
		}
		
		if(obj.product_cost.value==""){
			alert("상품의 원가를 넣어주세요");
			obj.product_cost.focus();
			return false;
		}

		if(obj.product_price.value==""){
			alert("상품의 판매가를 넣어주세요");
			obj.product_price.focus();
			return false;
		}

		if(obj.product_discount.value==""){
			alert("상품의 할인가를 넣어주세요");
			obj.product_discount.focus();
			return false;
		}

		oEditors.getById["product_contents"].exec("UPDATE_CONTENTS_FIELD", []);

		if(ans){
			obj.action="/product_backend_modify_ok.do";
			obj.submit();
			
		}else{
			return false;
		}
		
		
	}
	
	//리스트로 이동
	function send_list(){
		document.WriteForm.action="/product_backend_default.do";
		document.WriteForm.submit();
	}

</script>
