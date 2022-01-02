<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@page import="java.util.LinkedHashMap"%>
<%@page import="kr.co.mtshop.frontend.dao.*"%>
<%@page import="kr.co.mtshop.common.*"%>

<%
	
	CommonUtil CU = new CommonUtil();

	int member_idx = 0;
	
	if(session.getAttribute("member_idx")!=null){
		member_idx = (Integer)session.getAttribute("member_idx");
	}
	
	if(request.getParameter("member_idx")!=null){
		member_idx = Integer.parseInt(request.getParameter("member_idx"));
	}

%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀 번호 수정</title>

	<!-- 합쳐지고 최소화된 최신 CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
	
	<!-- 부가적인 테마 -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">

</head>
<body>

 	<jsp:include page="/jsphome/frontend/top.jsp"/>	
 	
 	<div class="container">

	    <h3>회원 비밀번호 수정</h3>
	    
	    <form name="WriteForm" method="POST">
	    <table class="table table-bordered" style="width: 100%;">

	        <tr>
	            <td style="width: 20%;">이전 비밀번호</td>
	            <td style="width:80%">
	                <input class="form-control input-sm"  type="password" style="width:200px;max-width: 150px;" name="member_pwd0" id="member_pwd0" >
	            </td>
	        </tr>
	
	        <tr>
	            <td style="width: 20%;">새 비밀번호</td>
	            <td style="width:80%">
	                <input class="form-control input-sm"  type="password" style="width:200px;max-width: 150px;" name="member_pwd1" id="member_pwd1" >
	            </td>
	        </tr>
	
	        <tr>
	            <td style="width: 20%;">새 비밀번호 확인</td>
	            <td style="width:80%">
	                <input class="form-control input-sm"  type="password" style="width:200px;max-width: 150px;" name="member_pwd2" id="member_pwd2" >
	            </td>
	        </tr>

	    </table>
	    
	    <div class="text-center">
		    <button class="btn btn-danger btn-sm" type="button" onclick="send_modify();">수정하기</button>
		    <button class="btn btn-default btn-sm" type="button" onclick="reset();">새로작성</button>
		    <button class="btn btn-default btn-sm" type="button" onclick="location.href='/'">홈</button>
		    
		    <input type="hidden" name="member_idx" value="<%=member_idx%>">

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

    //회원수정하기
    function send_modify(){

        var obj = document.WriteForm;
        
        if(obj.member_pwd0.value==""){
        	alert("이전 비밀번호를 입력해 주세요.");
        	obj.member_pwd0.focus();
        	return false;
        }
		
        if(obj.member_pwd1.value==""){
        	alert("새 비밀번호를 입력해 주세요.");
        	obj.member_pwd1.focus();
        	return false;
        }

        if(obj.member_pwd2.value==""){
        	alert("새 비밀번호 확인을 입력해 주세요.");
        	obj.member_pwd2.focus();
        	return false;
        }

        if(obj.member_pwd1.value!=obj.member_pwd2.value){
        	alert("새 비밀번호 확인 틀립니다. 다시 입력해 주세요.");
        	obj.member_pwd2.focus();
        	return false;
        }

        var ans = confirm("정말 수정하시겠습니까?");
        
        if(ans){
            obj.action="/pwd_modify_ok.do";
            obj.submit();
        }else{
        	return false;
        }
        
    }

</script>
