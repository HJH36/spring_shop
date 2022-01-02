<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.*"%>
<%@page import="kr.co.mtshop.frontend.dao.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	int member_idx=0;
	String member_id = "";
	String member_name = "";
%>    
    
<% if (session.getAttribute("member_idx") == null || session.getAttribute("member_idx").equals("")) { %>

	<script language="javascript">
		alert("로그인이 필요합니다. \n 로그인해 주시기 바랍니다.");
		location.href = "login_default.do";
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
int current_page = 1;
	if(request.getParameter("current_page")!=null){
		current_page = Integer.parseInt(request.getParameter("current_page"));
	}

	BoardFrontendDAO boardDAO = new BoardFrontendDAO();
	LinkedHashMap board_list = new LinkedHashMap();
	LinkedHashMap board_info = new LinkedHashMap();
	
	board_list = boardDAO.BoardList(current_page);
	
	int board_idx;
	int ref;
	int subref;
	int depth;
	int visit;
	String board_title = null;
	String board_contents = null;
	String reg_dt = null;
	String mod_dt = null;

	int total_count = boardDAO.BoardTotal();
	
	int total_page = (int)Math.ceil(total_count/(10*1d));
%>	

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 리스트</title>

		<!-- 합쳐지고 최소화된 최신 CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
	
	<!-- 부가적인 테마 -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.js"></script>
	


</head>
<body>

	<jsp:include page="/jsphome/frontend/top.jsp" flush="true" />
	
	<div class= "container">
	
	<h3>자유 게시판</h3>
	
	<div style="text-align:right;margin:10px;">
		현재 페이지 :<%=current_page %>/<%=total_page %> | 전체 게시글 : <%=total_count %> 개
	</div>

	<table class="table table-bordered">
	
		<thead>
			<tr>
				<th style = "text-align:center; background-color:ivory;">No.</th>
				<th style = "text-align:center; background-color:ivory;">게시글 제목</th>
				<th style = "text-align:center; background-color:ivory;">아이디</th>
				<th style = "text-align:center; background-color:ivory;">이름</th>
				<th style = "text-align:center; background-color:ivory;">방문자 수</th>
				<th style = "text-align:center; background-color:ivory;">등록일</th>
			</tr>
		</thead>
		
		
		
		<%if(total_count>0){ %>
			<%
				Iterator iter = board_list.keySet().iterator();
				String str_idx = null;
			%>
			
			<%
				int cursor = 0;
				while(iter.hasNext()){
					str_idx = (String)iter.next();
					board_info = (LinkedHashMap)board_list.get(str_idx);
					
					board_idx = (Integer)board_info.get("board_idx");
					ref = (Integer)board_info.get("ref");
					subref = (Integer)board_info.get("subref");
					depth = (Integer)board_info.get("depth");
					visit = (Integer)board_info.get("visit");

					board_title = (String)board_info.get("board_title");
					board_contents = (String)board_info.get("board_contents");
					member_id = (String)board_info.get("member_id");
					member_name = (String)board_info.get("member_name");
					reg_dt = (String)board_info.get("reg_dt");
					mod_dt = (String)board_info.get("mod_dt");
					
					int marginSize = 10*subref;
			%>
				<tr>
					<td style = "text-align:center;"><%=total_count-(((current_page-1)*10)+(cursor+1))+1%></td>
					<td style="padding-left:<%=marginSize+5%>px;">
						<a href="/board_view.do?board_idx=<%=board_idx%>"><%=board_title %></a>
					</td>
					<td style = "text-align:center;"><%=member_id %></td>
					<td style = "text-align:center;"><%=member_name %></td>
					<td style = "text-align:center;"><%=visit %></td>
					<td style = "text-align:center;"><%=reg_dt.substring(0,10) %></td>
				</tr>
			<%
				cursor++;
				}
			%>
		<%}else{ %>

			<tr>
				<td colspan="6" style="height:150px;text-align:center;vertical-align:middle;">
					현재 등록된 게시글이 없습니다.
				</td>
			</tr>
		<%} %>

	</table>
	
	<div class="text-center">
		
			<nav>
			  <ul class="pagination">
			    <li>
			      <a href="/board_default.do?current_page=1" aria-label="Previous">
			        <span aria-hidden="true">&laquo;</span>
			      </a>
			    </li>

				<%for(int i=0;i<total_page;i++){ %>
					<%if(current_page==i+1){ %>
						<li class="active"><a href="/board_default.do?current_page=<%=i+1%>"><%=i+1 %></a></li>
					<%}else{ %>
						<li><a href="/board_default.do?current_page=<%=i+1%>"><%=i+1 %></a></li>
					<%} %>
				<%} %>

			    <li>
			      <a href="/board_default.do?current_page=<%=total_page%>" aria-label="Next">
			        <span aria-hidden="true">&raquo;</span>
			      </a>
			    </li>
			  </ul>
			</nav>
			
		<div style = "margin-bottom : 10px; text-align: center;">
			<button type="button" class = "btn btn-primary btn-sm" OnClick="send_write();">등록하기</button>
			<button type="button" class="btn btn-default btn-sm" OnClick="send_home();">HOME</button>
		</div>	
		
		
		</div>
	
	</div>
	
	<jsp:include page="/jsphome/frontend/footer.jsp" flush="true" />
	
	<!-- Jquery CDN -->
		<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.js"></script>
	<!-- 합쳐지고 최소화된 최신 자바스크립트 -->
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>	

</body>
</html>

<script>

	function send_write(){
		location.href="/board_write.do";
	}
	
	function send_home(){
		location.href="/";
	}
	

</script>
