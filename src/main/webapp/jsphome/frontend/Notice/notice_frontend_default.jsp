<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.*"%>
<%@page import="kr.co.mtshop.frontend.dao.*"%>
<%@page import="org.json.simple.JSONArray"%>
<%@page import="org.json.simple.JSONObject"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<%
   int current_page = 1;
   if(request.getParameter("current_page")!=null){
      current_page = Integer.parseInt(request.getParameter("current_page"));
   }

   NoticeFrontendDAO noticeDAO = new NoticeFrontendDAO();
   JSONArray notice_list = new JSONArray();
   JSONObject notice_info = new JSONObject();
   
   notice_list = noticeDAO.NoticeList(current_page);
   
   int notice_idx;
   String notice_title = null;
   String title_check = null;
   String notice_edt = null;
   String notice_contents = null;
   int notice_visit;
   String reg_dt = null;
   String mod_dt = null;

   int total_count = noticeDAO.NoticeTotal();
   
   int total_page = (int)Math.ceil(total_count/(10*1d));
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항 리스트</title>

<!-- 합쳐지고 최소화된 최신 CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">

<!-- 부가적인 테마 -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">

</head>
<body>

	<jsp:include page="/jsphome/frontend/top.jsp" flush="true" />

	<div class="container">

		<h3>공지사항</h3>

		<div style="text-align: right; margin: 10px;">
			현재 페이지 :<%=current_page %>/<%=total_page %>
			| 전체 게시글 :
			<%=total_count %>
			개
		</div>

		<table class="table table-bordered">

			<thead>
				<tr>
					<th
						style="text-align: center; background-color: ivory; width: 80px;">No.</th>
					<th style="text-align: center; background-color: ivory;">공지사항 제목</th>
					<th style="text-align: center; background-color: ivory; width:100px;">마감일</th>
					<th style="text-align: center; background-color: ivory; width:70px;">방문 수</th>
					<th
						style="text-align: center; background-color: ivory; width: 120px;">등록일</th>
				</tr>
			</thead>

			<%if(total_count>0){ %>
			<%for(int i=0;i<notice_list.size();i++){ %>
			<%
               notice_info = (JSONObject)notice_list.get(i);
               notice_idx = (Integer)notice_info.get("notice_idx");
               notice_title = (String)notice_info.get("notice_title");
               notice_edt = (String)notice_info.get("notice_edt");
               title_check = (String)notice_info.get("title_check");
               notice_visit = (Integer)notice_info.get("notice_visit");
               reg_dt = (String)notice_info.get("reg_dt");
            %>

			<tr>
				<td style="text-align: center;"><%=total_count-(((current_page-1)*10)+(i+1))+1%></td>
				<td>
					<%if(title_check.equals("Y")){ %>
						<a href="javascript:send_view('<%=current_page%>', '<%=notice_idx%>');"><strong><%=notice_title %></strong></a>
					<%} else{%>
						<a href="javascript:send_view('<%=current_page%>', '<%=notice_idx%>');"><%=notice_title %></a>
					<%} %>
					
				</td>
				<td style="text-align: center;"><%=notice_edt.substring(0, 10) %></td>
				<td style="text-align: center;"><%=notice_visit %></td>
				<td style="text-align: center;"><%=reg_dt.substring(0, 10) %></td>
			</tr>

			<%} %>

			<%}else{ %>

			<tr>
				<td colspan="6"
					style="height: 150px; text-align: center; vertical-align: middle;">
					현재 등록된 공지사항이 없습니다.</td>
			</tr>
			<%} %>

		</table>

		<div class="text-center">

			<nav>
				<ul class="pagination">
					<li><a href="/notice_backend_default.do?current_page=1"
						aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
					</a></li>

					<%for(int i=0;i<total_page;i++){ %>
					<%if(current_page==i+1){ %>
					<li class="active"><a
						href="/notice_backend_default.do?current_page=<%=i+1%>"><%=i+1 %></a></li>
					<%}else{ %>
					<li><a href="/notice_backend_default.do?current_page=<%=i+1%>"><%=i+1 %></a></li>
					<%} %>
					<%} %>

					<li><a href="/notice_backend_default.do?current_page=<%=total_page%>"
						aria-label="Next"> <span aria-hidden="true">&raquo;</span>
					</a></li>
				</ul>
			</nav>

		</div>


	</div>

	<jsp:include page="/jsphome/frontend/footer.jsp" flush="true" />

	<!-- Jquery CDN -->
	<script type="text/javascript"
		src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.js"></script>
	<!-- 합쳐지고 최소화된 최신 자바스크립트 -->
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

</body>
</html>

<script>

   
	//공지사항 정보 보기
   function send_view(current_page, notice_idx){
	   location.href="/notice_frontend_view_default.do?current_page="+current_page+"&notice_idx="+notice_idx+"";
	   
   }
</script>