<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@page import="kr.co.mtshop.frontend.dao.*"%>
<%@page import="kr.co.mtshop.common.*"%>
<%@page import="org.json.simple.JSONArray"%>
<%@page import="org.json.simple.JSONObject"%>

<%

   //방문자 등록하기
   VisitDAO visitDAO = new VisitDAO();
   visitDAO.VisitReg(request);

   JSONArray visit_list = new JSONArray();
   JSONObject visit_info = new JSONObject();
   visit_list = visitDAO.VisitList("", "2021-10-01", "");
   String visit_temp = visit_list.toJSONString();

%>
   
   <div class="container">   

      <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
        <!-- Indicators -->
        <ol class="carousel-indicators">
          <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
          <li data-target="#carousel-example-generic" data-slide-to="1"></li>
        </ol>
      
        <!-- Wrapper for slides -->
        <div class="carousel-inner" role="listbox">
          <div class="item active container1">
            <img src="/jsphome/frontend/images/test1.jpg" alt="공공도서관" style="width:1280px;height:500px;">
            <div class="carousel-caption">
              
            </div>
          </div>
          <div class="item container1">
            <img src="/jsphome/frontend/images/test2.jpg" alt="공공도서관" style="width:1280px;height:500px;">
            <div class="carousel-caption">
              
            </div>
          </div>
          ...
        </div>
      
        <!-- Controls -->
        <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
          <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
          <span class="sr-only">Previous</span>
        </a>
        <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
          <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
          <span class="sr-only">Next</span>
        </a>
      </div>
      
      <!-- 차트 있는 부분 -->
      <div class="row" style="margin-top:20px;margin-bottom:20px;">
      
         <%
            int current_page = 1;
            if(request.getParameter("current_page")!=null){
               current_page = Integer.parseInt(request.getParameter("current_page"));
            }
            
            NoticeFrontendDAO noticeDAO = new NoticeFrontendDAO();
            JSONArray notice_list = new JSONArray();
            JSONObject notice_info = new JSONObject();
            
            notice_list = noticeDAO.NoticeList2();
            
            int notice_idx;
            int notice_visit;
            String notice_title = null;
            String title_check = null;
            String notice_edt = null;
            String notice_contents = null;
            String reg_dt = null;
            String mod_dt = null;
            
            int total_count = noticeDAO.NoticeTotal();
            int total_page = (int)Math.ceil(total_count/(10*1d));
         %>   
         
         
         <!-- 공지사항 부분 -->
         <div class="col-lg-6 col-md-6" >
         
            <table class="table table-bordered">
            
               <thead>
                  <tr>
                     <th style="text-align:center;background-color:ivory;width:80px;">No.</th>
                     <th style="text-align:center;background-color:ivory;">공지사항 타이틀</th>
                     <th style="text-align:center;background-color:ivory;width:120px;">등록일</th>
                  </tr>
               </thead>
               
               <%if(total_count>0){ %>
                  <%for(int i=0;i<notice_list.size();i++){ %>
                  <%
                     notice_info = (JSONObject)notice_list.get(i);
                     notice_idx = (Integer)notice_info.get("notice_idx");
                     notice_title = (String)notice_info.get("notice_title");
                     title_check = (String)notice_info.get("title_check");
                     notice_edt = (String)notice_info.get("notice_edt");
                     notice_visit = (Integer)notice_info.get("notice_visit");
                     reg_dt = (String)notice_info.get("reg_dt");
                  %>
                  
                     <tr>
                        <td style="text-align:center;"><%=total_count-(((current_page-1)*10)+(i+1))+1%></td>
                        <td>
                           <%if(title_check.equals("Y")){ %>
                              <a href="/notice_frontend_view_default.do?notice_idx=<%=notice_idx%>"><strong><%=notice_title %></strong></a>
                           <%}else{ %>
                              <a href="/notice_frontend_view_default.do?notice_idx=<%=notice_idx%>"><%=notice_title %></a>
                           <%} %>
                        </td>
                        <td style="text-align:center;"><%=reg_dt.substring(0, 10) %></td>
                     </tr>
      
                  <%} %>
               
               <%}else{ %>
         
                  <tr>
                     <td colspan="3" style="height:150px;text-align:center;vertical-align:middle;">
                        현재 등록된 공지사항이 없습니다.
                     </td>
                  </tr>
               <%} %>
         
            </table>
         
         </div>

         <!-- 차트 부분 -->
         <div class="col-lg-6 col-md-6" >
            <div style="margin-bottom:10px;">
               <button class="btn btn-default btn-sm" OnClick="visitDay('7');">1주일</button>
               <button class="btn btn-default btn-sm" OnClick="visitDay('30');">1개월</button>
               <button class="btn btn-default btn-sm" OnClick="visitDay('180');">6개월</button>
               <button class="btn btn-default btn-sm" OnClick="visitDay('365');">12개월</button>
               <button class="btn btn-default btn-sm" OnClick="visitDay('A');">전체</button>
            </div>
            <div >
               <div id = "chartContainer" style="width:100%;height:370px;"></div>
            </div>
         </div>

         
      </div>

   </div>
   


<script langauge="javascript">

   $(document).ready(function(){
      visitDay("A");
   });

   //방문자 함수
   function visitDay(type){
      
      var params = {"type":type};   
      $.ajax({
         type:"get",
         url:"/visit_statistics_default.do",
         timeout:30000,
         cache:false,
         data:params,
         success : function(data){
            
            var data_json = JSON.parse(data);
            var dataPoints = [];
            
            for(var i=0;i<data_json.length;i++){
               dataPoints.push({
                  x: new Date(data_json[i].str_date),
                  y: data_json[i].counter
               });
            };
            
            var chart = new CanvasJS.Chart("chartContainer", {
               animationEnabled: true,
               theme: "light2",
               title: {
                  text: "일별 방문자수",
                  fontSize:20,
                  fontWeight: "bolder"
               },
               axisX: {
                  title: "방문 날짜",
                  titleFontSize: 16,
                  titleFontWeight:"bold",
                  valueFormatString: "YYYY-MM-DD"
               },
               axisY: {
                  title: "Visiter",
                  titleFontSize: 16
               },
               data: [{
                  type: "column",
                  yValueFormatString: "#,### 방문",
                  dataPoints: dataPoints,
               }]
            });
            
            chart.render();
         },
         error:function(data, status, error){
            alert("통신데이터 값 : "+data);
         }
      })
      
   }
   

   //방문자 함수2
   function visitDay2(type){
      
      var params = {"type":type};   
      var dataPoints = [];
      
      <%for(int i=0;i<visit_list.size();i++){ %>
      <%
         visit_info = (JSONObject)visit_list.get(i);
         String x = (String)visit_info.get("str_date");
         int y = (Integer)visit_info.get("counter");
      %>
         dataPoints.push({
            x:new Date('<%=x%>'),
            y:<%=y%>
         });
         
      <%}%>

      
      var chart = new CanvasJS.Chart("chartContainer", {
         animationEnabled: true,
         theme: "light2",
         title: {
            text: "일별 방문자수",
            fontSize:20,
            fontWeight: "bolder"
         },
         axisX: {
            title: "방문 날짜",
            titleFontSize: 16,
            titleFontWeight:"bold",
            valueFormatString: "YYYY-MM-DD"
         },
         axisY: {
            title: "Visiter",
            titleFontSize: 16
         },
         data: [{
            type: "column",
            yValueFormatString: "#,### 방문",
            dataPoints: dataPoints,
         }]
      });
      
      chart.render();      
      
   }

</script>
