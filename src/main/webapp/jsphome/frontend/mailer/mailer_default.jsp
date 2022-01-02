<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.*"%>
<%@page import="kr.co.mtshop.frontend.dao.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
   int member_idx = 0;
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
//   if (session.getAttribute("member_idx") != null || !session.getAttribute("member_idx").equals("")) { 
      member_idx = (Integer)session.getAttribute("member_idx");
      member_id = (String)session.getAttribute("member_id");
      member_name = (String)session.getAttribute("member_name");
//   }
%>
<%} %>


<%


%>   

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메일 보내기</title>

   <!-- 합쳐지고 최소화된 최신 CSS -->
   <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
   
   <!-- 부가적인 테마 -->
   <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">

</head>
<body>

   <jsp:include page="/jsphome/frontend/top.jsp" flush="true" />
   
   <div class="container">
   
      <h3>메일 보내기</h3>
      
      <form name="EmailForm" method="post">   
      <table class="table table-bordered">
      
         <tr>
            <td style="text-align:center;vertical-align:middle;background-color:ivory;width:20%;font-weight:900;">보내는 사람 이메일</td>
            <td>
               <input class="form-control" type="email" name="from_email" style="width:350px;">
            </td>
         </tr>
   
         <tr>
            <td style="text-align:center;vertical-align:middle;background-color:ivory;width:20%;font-weight:900;">받는 사람 이메일</td>
            <td>
               <input class="form-control" type="email" name="to_email" style="width:350px;" value="guswnghks0703@gmail.com" readonly>
            </td>
         </tr>
   
         <tr>
            <td style="text-align:center;vertical-align:middle;background-color:ivory;width:20%;font-weight:900;">보내는 사람 이름</td>
            <td>
               <input class="form-control" type="text" name="from_name" style="width:200px;">
            </td>
         </tr>

         <tr>
            <td style="text-align:center;vertical-align:middle;background-color:ivory;width:20%;font-weight:900;">메일 내용</td>
            <td>
               <textarea class="form-control" name="from_contents" rows="15" cols="100%"></textarea>
            </td>
         </tr>

      </table>
      
      <div style="margin-bottom:10px;text-align:center;">
         <button type="button" class="btn btn-primary btn-sm" OnClick="send_email();">메일 보내기</button>
         <button type="button" class="btn btn-default btn-sm" OnClick="reset();">취소하기</button>
      </div>
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
   
   //이메일 보내기
   function send_email(){
      
      var obj = document.EmailForm;

      if(obj.from_email.value==""){
         alert("보내는 사람의 이메일을 넣어주세요.");
         obj.from_email.focus();
         return false;
      }
      
      if(obj.from_name.value==""){
         alert("보내는 사람의 이름을 넣어주세요.");
         obj.from_name.focus();
         return false;
      }
      
      if(obj.from_contents.value==""){
         alert("이메일 내용을 넣어주세요.");
         obj.from_contents.focus();
         return false;
      }
      
      var ans = confirm("정말 이메일을 보내시겠습니까?");
      if(ans){
         obj.action="/mailer_confirm_ok.do";
         obj.submit();
      }else{
         return false;
      }
      
   }

</script>