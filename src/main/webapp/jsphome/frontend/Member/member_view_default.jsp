<%@page import="java.util.LinkedHashMap"%>
<%@page import="kr.co.mtshop.frontend.dao.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	
	int current_page = 1;
	if(request.getParameter("current_page")!=null){
		current_page = Integer.parseInt(request.getParameter("current_page"));
	}
	
	int member_idx = 0;
	
	if(session.getAttribute("member_idx")!=null){
		member_idx = (Integer)session.getAttribute("member_idx");
	}
	
	if(request.getParameter("member_idx")!=null){
		member_idx = Integer.parseInt(request.getParameter("member_idx"));
	}

	MemberFrontendDAO memberDAO = new MemberFrontendDAO();
	LinkedHashMap member_info = new LinkedHashMap();
	member_info = memberDAO.MemberInfo(member_idx);
//	out.println(member_info);


	String member_id = (String)member_info.get("member_id");
	String member_pwd = (String)member_info.get("member_pwd");
	String member_name = (String)member_info.get("member_name");
	String member_phone = (String)member_info.get("member_phone");
	String member_email = (String)member_info.get("member_email");
	String zipcode = (String)member_info.get("zipcode");
	String member_raddress = (String)member_info.get("member_raddress");
	String member_jaddress = (String)member_info.get("member_jaddress");
	String member_address = (String)member_info.get("member_address");
	String reg_dt = (String)member_info.get("reg_dt");
	String mod_dt = (String)member_info.get("mod_dt");


%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 정보 보기</title>
    
	<!-- 합쳐지고 최소화된 최신 CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
	
	<!-- 부가적인 테마 -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">

</head>
<body>

	<jsp:include page="/jsphome/frontend/top.jsp"/>	

	<div class="container">
	
	    <h3>회원 정보</h3>
	    <table  class="table table-bordered" style="width:100%;">
	        <tr>
	            <td style="width: 20%; font-weight:900; background-color:ivory;">회원 아이디</td>
	            <td style="width:80%">
	            	<%=member_id %>
	            </td>
	        </tr>
	
	        <tr>
	            <td style="width: 20%; font-weight:900; background-color:ivory;">비밀번호</td>
	            <td style="width:80%">
	            	<%=member_pwd %>
	            </td>
	        </tr>
	
	        <tr>
	            <td style="width: 20%; font-weight:900; background-color:ivory;">이름</td>
	            <td style="width:80%">
	            	<%=member_name %>
	            </td>
	        </tr>
	
	        <tr>
	            <td style="width: 20%; font-weight:900; background-color:ivory;">연락처</td>
	            <td style="width:80%">
	            	<%=member_phone %>
	            </td>
	        </tr>
	     
	        <tr>
	            <td style="width: 20%; font-weight:900; background-color:ivory;">이메일</td>
	            <td style="width:80%">
	            	<%=member_email %>
	            </td>
	        </tr>

	        <tr>
	            <td style="width: 20%; font-weight:900; background-color:ivory;">우편번호</td>
	            <td style="width:80%">
	            	<%=zipcode %>
	            </td>
	        </tr>
	
			 <tr>
	            <td style="width: 20%; font-weight:900; background-color:ivory;" rowspan="3">주소</td>
	            <td style="width:80%">
	            	<%=member_raddress %>
	            </td>
	        </tr>
			
			 <tr>
	            <td style="width:80%">
	            	<%=member_jaddress %>
	            </td>
	        </tr>
	        
	        <tr>
	            <td style="width:80%">
	            	<%=member_address %>
	            </td>
	        </tr>
	
	    </table>
	    
	    <div class="text-center">
		    <button class="btn btn-primary btn-sm" type="button" onclick="send_modify('<%=member_idx%>','<%=current_page%>');">수정하기</button>
		    <button class="btn btn-default btn-sm" type="button" onclick="location.href='/'">홈</button>
		    <!-- 
		    <button class="btn btn-default btn-sm" type="button" onclick="send_delete('<%=member_idx%>');">삭제하기</button>
		    <button class="btn btn-default btn-sm" type="button" onclick="send_list('<%=current_page%>');">리스트</button>
		    -->
	    </div>
	
	
	</div>

	<jsp:include page="/jsphome/frontend/footer.jsp"/>	

	<!-- Jquery CDN -->
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.js"></script>
	
	<!-- 합쳐지고 최소화된 최신 자바스크립트 -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>        

</body>
</html>

<script>

    //회원수정하기
    function send_modify(member_idx, current_page){
    	location.href="/member_modify_default.do?member_idx="+member_idx+"&current_page="+current_page;
    }

</script>

