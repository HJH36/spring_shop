<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%

	int member_idx=0;
	String member_id = "";
	String member_name = "";
	String member_kind = "C";

%>    
    
<% if (session.getAttribute("member_idx") == null || session.getAttribute("member_idx").equals("")) { %>

	<script language="javascript">
		alert("로그인이 필요합니다. \n 로그인해 주시기 바랍니다.");
		location.href = "/login_default.do";
	</script>

<% }else{ %>
<%
		member_idx = (Integer)session.getAttribute("member_idx");
		member_id = (String)session.getAttribute("member_id");
		member_name = (String)session.getAttribute("member_name");
		member_kind = (String)session.getAttribute("member_kind");
%>
<%} %>
    
 <% if(member_kind.equals("C")){ %>

<script language="javascript">
	alert("관리자 회원이 아닙니다. \n 확인 부탁드립니다.");
	location.href = "/";
</script>

<% } %>     
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>질문과 답변 작성하기</title>

	<!-- 합쳐지고 최소화된 최신 CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
	
	<!-- 부가적인 테마 -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
	
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.js"></script>

</head>

<body>

	<jsp:include page="/jsphome/backend/top.jsp" flush="true" />
	
	<div class="container">

	    <h3>질문과 답변 작성하기</h3>
		<form name="WriteForm" id="WriteForm" method="post" enctype="multipart/form-data">
	    <table class="table table-bordered">
	        <tr>
	            <td style="width: 20%;">제목</td>
	            <td style="width:80%">
	                <input class = "form-control" type="text" style="width:350px;max-width: 350px;" name="faq_title" id="faq_title">
	            </td>
	        </tr>
	

	        <tr>
	            <td style="width: 20%;">내용</td>
	            <td style="width:80%">
	            
	            	<script type="text/javascript" src="/jsphome/backend/Scripts/SE/js/HuskyEZCreator.js" charset="utf-8"></script>
	            	<textarea name="faq_contents" id="faq_contents" style="width:100%;height:300px;display:none;"></textarea>
	            	
	            </td>
	        </tr>
	    </table>
	    
	    <div class="text-center">
		    <button class="btn btn-primary btn-sm" type="button" onclick="send_write();">등록하기</button>
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
	    elPlaceHolder: "faq_contents",
	    sSkinURI: "/jsphome/backend/Scripts/SE/SmartEditor2Skin.html",
	    fCreator: "createSEditor2"
	});	

	function send_write(){
		var obj = document.WriteForm;
		
		if(obj.faq_title.value==""){
			alert("게시글의 제목을 넣어주세요");
			obj.faq_title.focus();
			return false;
		}
		
		oEditors.getById["faq_contents"].exec("UPDATE_CONTENTS_FIELD", []);
		
		obj.action="/faq_backend_write_ok.do";
		obj.submit();
		
	}
	
	//리스트로 이동
	function send_list(){
		location.href="/faq_backend_default.do";
	}

</script>
