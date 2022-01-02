<%@page import="kr.co.mtshop.backend.dao.NoticeBackendDAO"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.JSONArray"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	int current_page = 1;
	if(request.getParameter("current_page")!=null){
		current_page = Integer.parseInt(request.getParameter("current_page"));
	}
	
	int notice_idx = Integer.parseInt(request.getParameter("notice_idx"));

    NoticeBackendDAO ND = new NoticeBackendDAO();
    	
    //방문자 수 업데이트
    ND.NoticeVisitUpdate(notice_idx);
    
    JSONObject notice_info = new JSONObject();
    notice_info = ND.NoticeInfo(notice_idx);
    	
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

	<jsp:include page="/jsphome/frontend/top.jsp" flush="true" />
	
	<div class="container">

	    <h3>공지사항 정보보기</h3>
		<form name="ModifyForm" id="ModifyForm" method="post" enctype="multipart/form-data">
	    <table class="table table-bordered">
	        <tr>
	            <td style="width: 20%; font-weight:900;vertical-align:middle;">제목</td>
	            <td style="width:80%">
	            	<%if(((String)notice_info.get("title_check")).equals("Y") ){ %>
	            		<strong><%=(String)notice_info.get("notice_title")%></strong>
	            	<%}else{ %>
	            		<%=(String)notice_info.get("notice_title")%>
	            	<%} %>
	                
	            </td>
	        </tr>

			<tr>
	            <td style="width: 20%; font-weight:900;vertical-align:middle;">탑공지 기간</td>
	            <td style="width:80%">
	            	<%=((String)notice_info.get("notice_edt")).substring(0, 10) %>
	            </td>
	        </tr>
	
			<tr>
	            <td style="width: 20%; font-weight:900;vertical-align:middle;">방문 수</td>
	            <td style="width:80%">
	                <%=(Integer)notice_info.get("notice_visit")%> 명
	            </td>
	        </tr>

	        <tr>
	            <td style="width: 20%; font-weight:900;vertical-align:middle;">내용</td>
	            <td style="width:80%" height="300px;">
	            	<%=(String)notice_info.get("notice_contents") %>
	            </td>
	        </tr>
	    </table>
	    
	    <div class="text-center">
		    <button class="btn btn-default btn-sm" type="button" onclick="send_list();">리스트</button>
	    </div>
	   
	   <input type="hidden" name = "current_page" value="<%=current_page %>">
	   <input type="hidden" name = "notice_idx" value="<%=notice_idx %>">
	    
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
	function send_list() {
		var obj = document.ModifyForm;

		obj.action = "/notice_frontend_default.do";
		obj.submit();
	}
</script>
