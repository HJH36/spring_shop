<%@page import="java.util.LinkedHashMap"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="kr.co.mtshop.frontend.dao.*"%>
<%@page import="kr.co.mtshop.common.*"%>

<%
	int member_idx=0;
	String member_id = "";
	String member_name = "";
%>    
    
<% if (session.getAttribute("member_idx") == null || session.getAttribute("member_idx").equals("")) { %>

	<script language="javascript">
		alert("로그인이 필요합니다. \n 로그인해 주시기 바랍니다.");
		location.href = "/login_default.do";
	</script>

<% }else{ %>
<%
//	if (session.getAttribute("member_idx") != null || !session.getAttribute("member_idx").equals("")) { 
		member_idx = (Integer)session.getAttribute("member_idx");
		member_id = (String)session.getAttribute("member_id");
		member_name = (String)session.getAttribute("member_name");
//	}
%>
<%} %>

<%
LocalValue lv = new LocalValue();
	
	
	int current_page = 1;
	if(request.getParameter("current_page")!=null){
		current_page = Integer.parseInt(request.getParameter("current_page"));
	}

	int board_idx = Integer.parseInt( request.getParameter("board_idx") );
	BoardFrontendDAO BD = new BoardFrontendDAO();
	LinkedHashMap board_info = new LinkedHashMap();
	board_info = BD.BoardInfo(board_idx);
	
	//게시글 정보
	int author_idx = (Integer)board_info.get("member_idx");
	String board_title = (String)board_info.get("board_title");
	String board_contents = (String)board_info.get("board_contents");
	String photo = (String)board_info.get("photo");
	int visit = (Integer)board_info.get("visit");
	
	//작성자 정보
	String author_name = (String)board_info.get("member_name");
	String author_id = (String)board_info.get("member_id");
	String author_phone = (String)board_info.get("member_name");
	
	BD.BoardVisitUpdate(board_idx);
%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 보기</title>

	<!-- 합쳐지고 최소화된 최신 CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
	
	<!-- 부가적인 테마 -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">

</head>

<%
	
//	int member_idx = (Integer)session.getAttribute("member_idx");
//	String member_id = (String)session.getAttribute("member_id");
//	String member_name = (String)session.getAttribute("member_name");

%>
<body>

	<jsp:include page="/jsphome/frontend/top.jsp"/>	
	
	<div class="container">
	
	    <h3>게시글 보기</h3>
	    <form name="WriteForm" method="POST">
	    <table class="table table-bordered">
	        <tr>
	            <td style="width: 20%; vertical-align:middle;">제목</td>
	            <td style="width:80%;">
	            	<%=board_title %>
	            </td>
	        </tr>
	
	        <tr>
	            <td style="width: 20%; vertical-align:middle;">아이디</td>
	            <td style="width:80%">
	            	<%=author_id %>
	            </td>
	        </tr>
	        
	        <tr>
	            <td style="width: 20%; vertical-align:middle;">이름</td>
	            <td style="width:80%">
	            	<%=author_name %>
	            </td>
	        </tr>
	
	        <tr>
	            <td style="width: 20%; vertical-align:middle;">방문자</td>
	            <td style="width:80%">
	            	<%=visit %> 명
	            </td>
	        </tr>
	
	        <tr>
	            <td style="width: 20%; vertical-align:middle;">사진</td>
	            <td style="width:80%;height:200px; vertical-align:middle;">
	            	<%if(photo.equals("")){ %>
	            		사진 없음
	            	<%}else{ %>
		            	<img src="<%=lv.FILEUPLOAD_ROOT_PATH %>/<%=photo %>" width="500px">
	            	<%} %>
	            </td>
	        </tr>

	        <tr>
	            <td style="width: 20%; vertical-align:middle;">내용</td>
	            <td style="width:80%;height:200px; vertical-align:middle;">
	            	<%=board_contents %>
	            </td>
	        </tr>
	    </table>
	    
	    <div class="text-center">
		    <button class="btn btn-primary btn-sm" type="button" onclick="send_reply();">답글쓰기</button>
		    
		    <%if(member_idx==author_idx){ %>
			    <button class="btn btn-primary btn-sm" type="button" onclick="send_modify();">수정하기</button>
			    <button class="btn btn-danger btn-sm" type="button" onclick="send_delete();" style="background:red;">삭제하기</button>
		    <%} %>
		
		    <button class="btn btn-default btn-sm" type="button" onclick="send_list();">리스트</button>
		    <input type="hidden" name="current_page" value="<%=current_page %>">
		    <input type="hidden" name="board_idx" value="<%=board_idx %>">
		    <input type="hidden" name="member_idx" value="<%=member_idx %>">
	    </div>
	    
	    </form>


	</div>


	<jsp:include page="/jsphome/frontend/footer.jsp"/>	

	<!-- Jquery CDN -->
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.js"></script>
	
	<!-- 합쳐지고 최소화된 최신 자바스크립트 -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>        

</body>
</html>

<script>
	
	//답글 작성하기
	function send_reply(){
		var obj = document.WriteForm;
		obj.action="/board_reply.do";
		obj.submit();
	}

	
	//수정하기 페이지 이동
	function send_modify(){
		var obj = document.WriteForm;
		obj.action="/board_modify.do";
		obj.submit();
	}
	
	//게시글 삭제하기
	function send_delete(){
		var obj = document.WriteForm;
		var ans = confirm("정말 삭제하시겠습니까?");
		if(ans){
			obj.action="/board_delete_ok.do";
			obj.submit();
		}else{
			return false;
		}
	}

	//리스트로 이동
	function send_list(){
		location.href="/board_default.do";
	}

</script>
