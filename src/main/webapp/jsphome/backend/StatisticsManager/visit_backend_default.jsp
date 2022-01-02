<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.JSONArray"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.*"%>
<%@page import="kr.co.mtshop.backend.dao.*"%>
<%@page import="kr.co.mtshop.common.*"%>
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
   int current_page = 1;
   if(request.getParameter("current_page")!=null){
      current_page = Integer.parseInt(request.getParameter("current_page"));
   }

   VisitDAO VD = new VisitDAO();
   JSONArray visit_list = new JSONArray(); 
   JSONObject visit_info = new JSONObject();
   
   visit_list = VD.VisitList2(current_page);
   
   int visit_idx;
   int visit_member_idx;
   int year;
   int month;
   int day;
   int hour;
   int minute;
   
   String type = null;
   String url_page = null;
   String ip = null;
   String browser = null;
   String visit_reg_dt = null;

   int total_count = VD.VisitTotal();
   
   int total_page = (int)Math.ceil(total_count/(10*1d));
%>   

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>방문자 리스트</title>

   <!-- 합쳐지고 최소화된 최신 CSS -->
   <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
   
   <!-- 부가적인 테마 -->
   <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">

</head>
<body>

   <jsp:include page="/jsphome/backend/top.jsp" flush="true" />
   
   <div class="container">
   
      <h3>방문자 리스트</h3>
      
      <div style="text-align:right;margin:10px;">
         현재 페이지 :<%=current_page %>/<%=total_page %> | 전체 게시글 : <%=total_count %> 개
      </div>
   
      <table class="table table-bordered">
      
         <thead>
            <tr>
               <th style="text-align:center;background-color:ivory;">No.</th>
               <th style="text-align:center;background-color:ivory;">type</th>
               <th style="text-align:center;background-color:ivory;">page</th>
               <th style="text-align:center;background-color:ivory;">ip</th>
               <th style="text-align:center;background-color:ivory;">browser</th>
               <th style="text-align:center;background-color:ivory;">년</th>
               <th style="text-align:center;background-color:ivory;">월</th>
               <th style="text-align:center;background-color:ivory;">일</th>
               <th style="text-align:center;background-color:ivory;">시간</th>
               <th style="text-align:center;background-color:ivory;">분</th>
               <th style="text-align:center;background-color:ivory;">등록일</th>
            </tr>
         </thead>
         
         <%if(total_count>0){ %>
            <%for(int i=0;i<visit_list.size();i++){ %>
               <%
                  visit_info = (JSONObject)visit_list.get(i);
                  visit_idx = (Integer)visit_info.get("visit_idx");
                  year = (Integer)visit_info.get("year");
                  month = (Integer)visit_info.get("month");
                  day = (Integer)visit_info.get("day");
                  hour = (Integer)visit_info.get("hour");
                  minute = (Integer)visit_info.get("minute");
                  
                  type = (String)visit_info.get("type");
                  url_page = (String)visit_info.get("page");
                  ip = (String)visit_info.get("ip");
                  browser = (String)visit_info.get("browser");
                  visit_reg_dt = (String)visit_info.get("visit_reg_dt");
               %>
               <tr>
                  <td style="text-align:center;"><%=total_count-(((current_page-1)*10)+(i+1))+1%></td>
                  <td style="text-align:center;"><%=type %></td>
                  <td style="text-align:center;"><%=url_page %></td>
                  <td style="text-align:center;"><%=ip %></td>
                  <td style="text-align:center;"><%=browser %></td>

                  <td style="text-align:center;"><%=year %></td>
                  <td style="text-align:center;"><%=month %></td>
                  <td style="text-align:center;"><%=day %></td>
                  <td style="text-align:center;"><%=hour %></td>
                  <td style="text-align:center;"><%=minute %></td>

                  <td style="text-align:center;"><%=visit_reg_dt.substring(0, 10) %></td>
               </tr>
            
            
            <%} %>
         <%}else{ %>
   
            <tr>
               <td colspan="6" style="height:150px;text-align:center;vertical-align:middle;">
                  현재 방문자가 없습니다.
               </td>
            </tr>
         <%} %>
   
      </table>
      
      <div class="text-center" >
      
         <nav>
           <ul class="pagination">
             <li>
               <a href="/visit_backend_default.do?current_page=1" aria-label="Previous">
                 <span aria-hidden="true">&laquo;</span>
               </a>
             </li>

            <%for(int i=0;i<total_page;i++){ %>
               <%if(current_page==i+1){ %>
                  <li class="active"><a href="/visit_backend_default.do?current_page=<%=i+1%>"><%=i+1 %></a></li>
               <%}else{ %>
                  <li><a href="/visit_backend_default.do?current_page=<%=i+1%>"><%=i+1 %></a></li>
               <%} %>
            <%} %>

             <li>
               <a href="/visit_backend_default.do?current_page=<%=total_page%>" aria-label="Next">
                 <span aria-hidden="true">&raquo;</span>
               </a>
             </li>
           </ul>
         </nav>

      </div>
   
   
   </div>

   <jsp:include page="/jsphome/backend/footer.jsp" flush="true" />

   <!-- Jquery CDN -->
   <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.js"></script>
   <!-- 합쳐지고 최소화된 최신 자바스크립트 -->
   <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

</body>
</html>

<script>


</script>