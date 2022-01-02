<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="kr.co.mtshop.backend.dao.NoticeBackendDAO"%>
<%@page import="org.json.simple.JSONArray" %>
<%@page import="org.json.simple.JSONObject" %>
<%
	int current_page = Integer.parseInt(request.getParameter("current_page"));
	int notice_idx = Integer.parseInt(request.getParameter("notice_idx"));
	
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
	NoticeBackendDAO ND = new NoticeBackendDAO();
 
	JSONObject notice_info = new JSONObject();
	notice_info = ND.NoticeInfo(notice_idx);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항 수정하기</title>

	<!-- 합쳐지고 최소화된 최신 CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
	
	<!-- 부가적인 테마 -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
	
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.js"></script>

</head>

<body>

	<jsp:include page="/jsphome/backend/top.jsp" flush="true" />
	
	<div class="container">

	    <h3>공지사항 수정하기</h3>
		<form name="WriteForm" id="WriteForm" method="post" enctype="multipart/form-data">
	    <table class="table table-bordered">
	        <tr>
	            <td style="width: 20%;">제목</td>
	            <td style="width:80%">
	                <input class = "form-control" type="text" style="width:350px;max-width: 350px;" name="notice_title" id="notice_title" value = "<%=(String)notice_info.get("notice_title")%>">
	            </td>
	        </tr>

	       <tr>
	            <td style="width: 20%; font-weight:900;vertical-align:middle;">제목 진하게</td>
	            <td style="width:80%">
	            	<%
	            		String title_check = (String)notice_info.get("title_check");
	            		String notice_edt = (String)notice_info.get("notice_edt");
	            	%>
	            	<%if(title_check.equals("Y")) {%>
	            		<input type="checkbox" name="title_check" id="title_check" checked > 제목 진하게
	            	<%}else{ %>
	            		<input type="checkbox" name="title_check" id="title_check" > 제목 진하게
	            	<%} %>
	            </td>
	        </tr>
	
			<tr>
	            <td style="width: 20%; font-weight:900;vertical-align:middle;">탑공지 기간</td>
	            <td style="width:80%">
	            	<input class="form-control input-sm" type="date" name = "notice_edt" id = "notice_edt" style="width:200px;" value = "<%=notice_edt.substring(0, 10)%>">
	            </td>
	        </tr>
	
	        <tr>
	            <td style="width: 20%;">내용</td>
	            <td style="width:80%" >
	            
	            	<script type="text/javascript" src="/jsphome/backend/Scripts/SE/js/HuskyEZCreator.js" charset="utf-8"></script>
	            	<textarea name="notice_contents" id="notice_contents" style="width:100%;height:300px;display:none;"><%=(String)notice_info.get("notice_title")%></textarea>
	            	
	            </td>
	        </tr>
	    </table>
	    
	    <div class="text-center">
		    <button class="btn btn-primary btn-sm" type="button" onclick="send_modify();">수정하기</button>
		    <button class="btn btn-default btn-sm" type="button" onclick="reset();">새로작성</button>
		    <button class="btn btn-default btn-sm" type="button" onclick="send_list(<%=current_page %>);">리스트</button>
	    </div>
	    
	    <input type = "hidden" name="current_page" value="<%=current_page %>">
	    <input type = "hidden" name="notice_idx" value="<%=notice_idx %>">
	    
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
	    elPlaceHolder: "notice_contents",
	    sSkinURI: "/jsphome/backend/Scripts/SE/SmartEditor2Skin.html",
	    fCreator: "createSEditor2"
	});	

	function send_modify(){
		var obj = document.WriteForm;
		
		if(obj.notice_title.value==""){
			alert("게시글의 제목을 넣어주세요");
			obj.notice_title.focus();
			return false;
		}
		
		oEditors.getById["notice_contents"].exec("UPDATE_CONTENTS_FIELD", []);
		
		var ans = confirm("정말 수정 하시겠습니까?");
		if(ans){
			obj.action = "/notice_backend_modify_ok.do";
			obj.submit();
		}else{
			return false;
		}
		
	}
	
	//리스트로 이동
	function send_list(current_page){
		location.href="/notice_backend_default.do?current_page="+current_page;
	}

</script>
