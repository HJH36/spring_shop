<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 작성하기</title>

	<!-- 합쳐지고 최소화된 최신 CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
	
	<!-- 부가적인 테마 -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
	
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.js"></script>

</head>


<body>

	<jsp:include page="/jsphome/backend/top.jsp" flush="true" />
	
	<div class="container">

	    <h3>상품 등록하기</h3>
		<form name="WriteForm" id="WriteForm" method="post" enctype="multipart/form-data">
	    <table class="table">
	        <tr>
	            <td style="width: 20%;">상품 이름</td>
	            <td style="width:80%">
	                <input type="text" style="width:350px;max-width: 350px;" name="product_name" id="product_name">
	            </td>
	        </tr>
	
	        <tr>
	            <td style="width: 20%;">상품 원가</td>
	            <td style="width:80%">
	                <input type="number" style="width:200px;max-width: 150px;" name="product_cost" id="product_cost">
	            </td>
	        </tr>
	        
	        <tr>
	            <td style="width: 20%;">상품 판매가</td>
	            <td style="width:80%">
	                <input type="number" style="width:200px;max-width: 150px;" name="product_price" id="product_price">
	            </td>
	        </tr>
	        
	        <tr>
	            <td style="width: 20%;">상품 할인가</td>
	            <td style="width:80%">
	                <input type="number" style="width:200px;max-width: 150px;" name="product_discount" id="product_discount">
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
	            	<textarea name="product_contents" id="product_contents" style="width:100%;height:300px;display:none;"></textarea>
	            	
					            	
	            	<!-- 
	            	<textarea name="board_contents" rows="10" cols="70"></textarea>
	            	-->
	            	
	            	
	            </td>
	        </tr>
	    </table>
	    
	    <div class="text-center">
		    <button class="btn btn-default btn-sm" type="button" onclick="send_write();">등록하기</button>
		    <button class="btn btn-default btn-sm" type="button" onclick="reset();">새로작성</button>
		    <button class="btn btn-default btn-sm" type="button" onclick="send_list();">리스트</button>
	    </div>
	    
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

	function send_write(){
		var obj = document.WriteForm;
		
		if(obj.product_name.value==""){
			alert("상품의 이름을 넣어주세요");
			obj.product_name.focus();
			return false;
		}
		
		oEditors.getById["product_contents"].exec("UPDATE_CONTENTS_FIELD", []);
		
		obj.action="/product_backend_write_ok.do";
		obj.submit();
		
	}
	
	//리스트로 이동
	function send_list(){
		location.href="/product_backend_default.do";
	}

</script>
