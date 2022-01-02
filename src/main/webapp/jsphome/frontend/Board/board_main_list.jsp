<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.*"%>
<%@page import="kr.co.mtshop.frontend.dao.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	int member_idx=0;
	String member_id = "";
	String member_name = "";
	int current_page = 1;
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
BoardFrontendDAO boardDAO = new BoardFrontendDAO();
	LinkedHashMap board_list = new LinkedHashMap();
	LinkedHashMap board_info = new LinkedHashMap();
	
	board_list = boardDAO.BoardList2();
	
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

	
<div class="row">
	<div class="col-lg-6">
		<h3>자유 게시판</h3>
	</div>
	<div class="col-lg-6 text-right">
		<a href="/board_default.do">더보기</a>
	</div>
</div>

<div style="text-align:left;margin:10px;">
	전체 게시글 : <%=total_count %> 개
</div>

<table class="table table-hover">

	<thead>
		<tr>
			<th>No.</th>
			<th>게시글 제목</th>
			<th>등록일</th>
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
				
				int marginSize = 10*depth;
		%>
			<tr>
				<td><%=total_count-(((current_page-1)*10)+(cursor+1))+1%></td>
				<td style="padding-left:<%=marginSize%>px;">
					<a href="/board_view.do?board_idx=<%=board_idx%>"><%=board_title %></a>
				</td>
				<td><%=reg_dt.substring(0, 10) %></td>
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
		
	
	
	



<script>

	function send_write(){
		location.href="/board_write.do";
	}

</script>
