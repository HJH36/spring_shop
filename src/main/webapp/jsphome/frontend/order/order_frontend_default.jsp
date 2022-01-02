<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.*"%>
<%@page import="kr.co.mtshop.frontend.dao.*"%>
<%@page import="kr.co.mtshop.common.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.JSONArray"%>

<%
	LocalValue lv = new LocalValue();


	int member_idx = 0;
	if(session.getAttribute("member_idx")!=null){
		member_idx = (Integer)session.getAttribute("member_idx");
	}

	//제품 고유 아이디
	String[] product_idx_str = request.getParameterValues("product_idx");
	
	
	//주문 총 금액
	int order_total_price = 0;
	

	ProductFrontendDAO PD = new ProductFrontendDAO();

	JSONObject product_info = new JSONObject();
	
	//숫자 천자리 쉼표 찍기
	DecimalFormat df = new DecimalFormat("###,###");
	
%>	

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>바로 구매</title>

	<!-- 합쳐지고 최소화된 최신 CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
	
	<!-- 부가적인 테마 -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">

</head>
<body>

	<jsp:include page="/jsphome/frontend/top.jsp" flush="true" />
	
	<div class="container">
	
		<h3>바로 구매 주문하기</h3>
		
		<form name="OrderForm" method="post">
		<table class="table table-bordered">
		
			<thead>
				<tr>
					<th style="text-align:center;font-weight:900;background-color:ivory;">사진</th>
					<th style="text-align:center;font-weight:900;background-color:ivory;">제품 이름</th>
					<th style="text-align:center;font-weight:900;background-color:ivory;">원가</th>
					<th style="text-align:center;font-weight:900;background-color:ivory;">판매가</th>
					<th style="text-align:center;font-weight:900;background-color:ivory;">할인가</th>
				</tr>
			</thead>
			
			<tbody>
				
				<%for(int i=0;i<product_idx_str.length;i++){ %>
				<%
					int product_idx = Integer.parseInt( product_idx_str[i] );
					product_info = PD.ProductInfo(product_idx);
				
					//변수 선언
					String product_name = (String)product_info.get("product_name");
					int product_cost = (Integer)product_info.get("product_cost");
					int product_price = (Integer)product_info.get("product_price");
					int product_discount = (Integer)product_info.get("product_discount");
					String product_image = (String)product_info.get("product_image");
					String product_contents = (String)product_info.get("product_contents");
					
					order_total_price += product_discount;

				%>
					<tr>
						<td>
							<%if(product_image.equals("")){ %>
								<img src="/jsphome/backend/images/noimage.png" width="150px">
							<%}else{ %>
								<img src="<%=lv.FILEUPLOAD_ROOT_PATH %>/<%=product_image %>" width="150px">
							<%} %>
						</td>
						<td style="vertical-align:middle;font-weight:700;color:#5858FA;">
							<a href="javascript:send_view('<%=product_idx%>');"><%=product_name %></a>
						</td>
						<td style="vertical-align:middle;"><%=df.format(product_cost)%> 원</td>
						<td style="vertical-align:middle;"><del><%=df.format(product_price) %></del> 원</td>
						<td style="vertical-align:middle;color:red;font-weight:700;"><%=df.format(product_discount) %> 원</td>
					</tr>

					<input type="hidden" name="product_idx" value="<%=product_idx%>">
					<input type="hidden" name="product_discount" value="<%=product_discount%>">

				<%} %>
				
				<tr>
					<td colspan="5" style="text-align:right;vertical-align:middle;font-weight:900;">
						주문 총 금액 : <%=df.format(order_total_price) %>원
					</td>
				</tr>	
			</tbody>
		</table>
		
		<h3>주문자</h3>

		<table class="table table-bordered">
			<tr>
				<td style="width:20%;">
					이름
				</td>
				<td style="width:80%;">
					<input type="text" class="form-control input-sm" name="order_name" style="width:200px;">
				</td>
			</tr>
			
			<tr>
				<td style="width:20%;">
					전화번호
				</td>
				<td style="width:80%;">
					<input type="number" class="form-control input-sm" name="order_phone" style="width:200px;">
				</td>
			</tr>

			<tr>
				<td style="width:20%;">
					우편번호
				</td>
				<td style="width:80%;">
					<div class="form-group form-inline">
						<input type="text" class="form-control input-sm" name="order_zipcode" id="order_zipcode" style="width:100px;">
						<button type="button" class="btn btn-default btn-sm" OnClick="sample4_execDaumPostcode();">검색</button>
					</div>
				</td>
			</tr>

			<tr>
				<td style="width:20%;">
					도로명 주소
				</td>
				<td style="width:80%;">
					<input type="text" class="form-control input-sm" name="order_raddress" id="order_raddress" style="width:300px;">
				</td>
			</tr>

			<tr>
				<td style="width:20%;">
					지번 주소
				</td>
				<td style="width:80%;">
					<input type="text" class="form-control input-sm" name="order_jaddress" id="order_jaddress" style="width:300px;">
				</td>
			</tr>

			<tr>
				<td style="width:20%;">
					상세주소
				</td>
				<td style="width:80%;">
					<input type="text" class="form-control input-sm" name="order_address" id="order_address" style="width:300px;">
				</td>
			</tr>

		</table>
		
		<div>
			<input type="checkbox" name="same_check" id="same_check" OnClick="send_check(event);">&nbsp;주문자와 동일
		</div>
		
		<h3>배송지</h3>		
		<table class="table table-bordered">
			<tr>
				<td style="width:20%;">
					이름
				</td>
				<td style="width:80%;">
					<input type="text" class="form-control input-sm" name="delivery_name" style="width:200px;">
				</td>
			</tr>
			
			<tr>
				<td style="width:20%;">
					전화번호
				</td>
				<td style="width:80%;">
					<input type="number" class="form-control input-sm" name="delivery_phone" style="width:200px;">
				</td>
			</tr>

			<tr>
				<td style="width:20%;">
					우편번호
				</td>
				<td style="width:80%;">
					<div class="form-group form-inline">
						<input type="text" class="form-control input-sm" name="delivery_zipcode" id="delivery_zipcode" style="width:100px;">
						<button type="button" class="btn btn-default btn-sm" OnClick="sample4_execDaumPostcode();">검색</button>
					</div>
				</td>
			</tr>

			<tr>
				<td style="width:20%;">
					도로명 주소
				</td>
				<td style="width:80%;">
					<input type="text" class="form-control input-sm" name="delivery_raddress" id="delivery_raddress" style="width:300px;">
				</td>
			</tr>

			<tr>
				<td style="width:20%;">
					지번 주소
				</td>
				<td style="width:80%;">
					<input type="text" class="form-control input-sm" name="delivery_jaddress" id="delivery_jaddress" style="width:300px;">
				</td>
			</tr>

			<tr>
				<td style="width:20%;">
					상세주소
				</td>
				<td style="width:80%;">
					<input type="text" class="form-control input-sm" name="delivery_address" id="delivery_address" style="width:300px;">
				</td>
			</tr>

			<tr>
				<td style="width:20%;">
					배송 메세지
				</td>
				<td style="width:80%;">
					<textarea class="form-control input-sm" rows="10" name="delivery_message"></textarea>
				</td>
			</tr>

		</table>
		
		<div class="text-center">
			<button type="button" class="btn btn-primary btn-sm" OnClick="send_order();">주문하기</button>
			<button type="button" class="btn btn-default btn-sm" OnClick="reset();">새로고침</button>
		</div>
		
		
		<input type="hidden" name="member_idx" value="<%=member_idx%>">
		<input type="hidden" name="order_total_price" value="<%=order_total_price%>">
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

	function send_view(product_idx){
		var url="product_frontend_view.do?product_idx="+product_idx;
		location.href=url;
		
	}

	function send_check(event){
		
		var obj = document.OrderForm;
		
		if(event.target.checked){
			obj.delivery_name.value = obj.order_name.value;
			obj.delivery_phone.value = obj.order_phone.value;
			obj.delivery_zipcode.value = obj.order_zipcode.value;
			obj.delivery_raddress.value = obj.order_raddress.value;
			obj.delivery_jaddress.value = obj.order_jaddress.value;
			obj.delivery_address.value = obj.order_address.value;
		}
		
			
	}
	
	//주문하기
	function send_order(){
		var obj = document.OrderForm;
		
		if(obj.order_name.value==""){
			alert("주문자의 이름을 넣어주세요");
			obj.order_name.focus();
			return false;
		}
		
		if(obj.order_phone.value==""){
			alert("주문자의 전화번호를 넣어주세요");
			obj.order_phone.focus();
			return false;
		}
		
		if(obj.order_zipcode.value==""){
			alert("주문자의 우편번호를 넣어주세요");
			obj.order_zipcode.focus();
			return false;
		}
		
		if(obj.order_raddress.value==""){
			alert("주문자의 도로명 주소를 넣어주세요");
			obj.order_raddress.focus();
			return false;
		}
		
		if(obj.order_jaddress.value==""){
			alert("주문자의 지번 주소를 넣어주세요");
			obj.order_jaddress.focus();
			return false;
		}

		if(obj.order_address.value==""){
			alert("주문자의 상세주소를 넣어주세요");
			obj.order_address.focus();
			return false;
		}

		if(obj.delivery_name.value==""){
			alert("배송 받는 사람 이름을 넣어주세요");
			obj.delivery_name.focus();
			return false;
		}
		
		if(obj.delivery_phone.value==""){
			alert("배송 받는 사람 전화번호를 넣어주세요");
			obj.delivery_phone.focus();
			return false;
		}
		
		if(obj.delivery_zipcode.value==""){
			alert("배송 받는 사람 우편번호를 넣어주세요");
			obj.delivery_zipcode.focus();
			return false;
		}
		
		if(obj.delivery_raddress.value==""){
			alert("배송 받는 사람 도로명 주소를 넣어주세요");
			obj.delivery_raddress.focus();
			return false;
		}
		
		if(obj.delivery_jaddress.value==""){
			alert("배송 받는 사람 지번 주소를 넣어주세요");
			obj.delivery_jaddress.focus();
			return false;
		}

		if(obj.delivery_address.value==""){
			alert("배송 받는 사람 상세주소를 넣어주세요");
			obj.delivery_address.focus();
			return false;
		}
		
		obj.action="/order_direct_confirm.do";
		obj.submit();

	}
	
	
</script>

<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
    //본 예제에서는 도로명 주소 표기 방식에 대한 법령에 따라, 내려오는 데이터를 조합하여 올바른 주소를 구성하는 방법을 설명합니다.
    function sample4_execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 도로명 주소의 노출 규칙에 따라 주소를 표시한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var roadAddr = data.roadAddress; // 도로명 주소 변수
                var extraRoadAddr = ''; // 참고 항목 변수

                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraRoadAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                   extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraRoadAddr !== ''){
                    extraRoadAddr = ' (' + extraRoadAddr + ')';
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('order_zipcode').value = data.zonecode;
                document.getElementById("order_raddress").value = roadAddr;
                document.getElementById("order_jaddress").value = data.jibunAddress;
                
            }
        }).open();
    }
</script>
