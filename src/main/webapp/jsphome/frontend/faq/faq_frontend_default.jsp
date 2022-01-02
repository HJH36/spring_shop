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

   FaqFrontendDAO faqDAO = new FaqFrontendDAO();
   JSONArray faq_list = new JSONArray();
   JSONObject faq_info = new JSONObject();
   
   faq_list = faqDAO.FaqList(current_page);
   
   int faq_idx;
   String faq_title = null;
   String faq_contents = null;
   String faq_reg_dt = null;
   String faq_mod_dt = null;

   int total_count = faqDAO.FaqTotal();
   
   int total_page = (int)Math.ceil(total_count/(10*1d));
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>질문과 답변 리스트</title>

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

		<h3>질문과 답변</h3>

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
					<th style="text-align: center; background-color: ivory;">질문 제목</th>
					<th
						style="text-align: center; background-color: ivory; width: 120px;">등록일</th>
				</tr>
			</thead>

			<%if(total_count>0){ %>
			<%for(int i=0;i<faq_list.size();i++){ %>
			<%
               faq_info = (JSONObject)faq_list.get(i);
               faq_idx = (Integer)faq_info.get("faq_idx");
               faq_title = (String)faq_info.get("faq_title");
               faq_reg_dt = (String)faq_info.get("faq_reg_dt");
            %>

			<tr>
				<td style="text-align: center;"><%=total_count-(((current_page-1)*10)+(i+1))+1%></td>
				<td><a href="javascript:send_view('<%=current_page%>', '<%=faq_idx%>');"><%=faq_title %></a></td>
				<td style="text-align: center;"><%=faq_reg_dt.substring(0, 10) %></td>
			</tr>

			<%} %>

			<%}else{ %>

			<tr>
				<td colspan="6"
					style="height: 150px; text-align: center; vertical-align: middle;">
					현재 등록된 FAQ가 없습니다.</td>
			</tr>
			<%} %>

		</table>

		<div class="text-center">

			<nav>
				<ul class="pagination">
					<li><a href="/faq_frontend_default.do?current_page=1"
						aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
					</a></li>

					<%for(int i=0;i<total_page;i++){ %>
					<%if(current_page==i+1){ %>
					<li class="active"><a
						href="/faq_frontend_default.do?current_page=<%=i+1%>"><%=i+1 %></a></li>
					<%}else{ %>
					<li><a href="/faq_frontend_default.do?current_page=<%=i+1%>"><%=i+1 %></a></li>
					<%} %>
					<%} %>

					<li><a href="/faq_frontend_default.do?current_page=<%=total_page%>"
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

	//질문과 답변 정보 보기
   function send_view(current_page, faq_idx){
	   location.href="/faq_frontend_view_default.do?current_page="+current_page+"&faq_idx="+faq_idx;
	   
   }
</script>