<%@page import="kr.co.mtshop.backend.dao.FaqBackendDAO"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.JSONArray"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	
	int current_page = Integer.parseInt(request.getParameter("current_page"));
	int faq_idx = Integer.parseInt(request.getParameter("faq_idx"));
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
    
    <%
    	JSONObject faq_info = new JSONObject();
    	FaqBackendDAO FD = new FaqBackendDAO();
    	faq_info = FD.FaqInfo(faq_idx);
    %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>질문과 답변 정보보기</title>

	<!-- 합쳐지고 최소화된 최신 CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
	
	<!-- 부가적인 테마 -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
	
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.js"></script>

</head>

<body>

	<jsp:include page="/jsphome/backend/top.jsp" flush="true" />
	
	<div class="container">

	    <h3>질문과 답변 정보보기</h3>
		<form name="ModifyForm" id="ModifyForm" method="post" enctype="multipart/form-data">
	    <table class="table table-bordered">
	        <tr>
	            <td style="width: 20%; font-weight:900;vertical-align:middle;">제목</td>
	            <td style="width:80%">
	                <%=(String)faq_info.get("faq_title")%>
	            </td>
	        </tr>
	

	        <tr>
	            <td style="width: 20%; font-weight:900;vertical-align:middle;">내용</td>
	            <td style="width:80%" height="300px;">
	            	<%=(String)faq_info.get("faq_contents") %>
	            </td>
	        </tr>
	    </table>
	    
	    <div class="text-center">
		    <button class="btn btn-primary btn-sm" type="button" onclick="send_modify();">수정하기</button>
		    <button class="btn btn-primary btn-sm" type="button" onclick="send_delete();">삭제하기</button>
		    <button class="btn btn-default btn-sm" type="button" onclick="send_list();">리스트</button>
	    </div>
	   
	   <input type="hidden" name = "current_page" value="<%=current_page %>">
	   <input type="hidden" name = "faq_idx" value="<%=faq_idx %>">
	    
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
	
	//질문과 답변 수정 페이지 이동
	function send_modify(){
		var obj = document.ModifyForm;
		
		obj.action="/faq_backend_modify_default.do";
		obj.submit();
		
	}

	//질문과 답변 삭제하기
	function send_delete(){
		
		var ans = confirm("정말 삭제하시겠습니까?");
		
		if(ans){
			var obj = document.ModifyForm;
			obj.action="/faq_backend_delete_ok.do";
			obj.submit();
		} else {
			return false;
		}
		
	}
	

	//리스트로 이동
	function send_list() {
		var obj = document.ModifyForm;

		obj.action = "/faq_backend_default.do";
		obj.submit();
	}
</script>
