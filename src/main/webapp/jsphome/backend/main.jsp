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

		<div class="row" style="margin-top:20px;margin-bottom:20px;">
			<div class="col-lg-12 col-md-12">
				<button class="btn btn-default btn-sm" OnClick="visitDay('7');">1주일</button>
				<button class="btn btn-default btn-sm" OnClick="visitDay('30');">1개월</button>
				<button class="btn btn-default btn-sm" OnClick="visitDay('180');">6개월</button>
				<button class="btn btn-default btn-sm" OnClick="visitDay('365');">12개월</button>
				<button class="btn btn-default btn-sm" OnClick="visitDay('A');">전체</button>
			</div>
			<div class="col-lg-12 col-md-12">
				<div id = "chartContainer" style="width:100%;height:370px;"></div>
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

