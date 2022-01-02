<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.*"%>
<%@page import="kr.co.mtshop.frontend.dao.*"%>
<%@page import="kr.co.mtshop.backend.dao.*"%>
<%@page import="kr.co.mtshop.common.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.JSONArray"%>

<%
   LocalValue lv = new LocalValue();

   //현재 페이지
   int current_page = 1;
   if(request.getParameter("current_page")!=null){
      current_page = Integer.parseInt(request.getParameter("current_page"));
   }
   
   //검색 컬럼 이름
   String searchtitle="";
   if(request.getParameter("searchtitle")!=null){
      searchtitle = request.getParameter("searchtitle");
   }
   
   //검색 키워드
   String searchstring="";
   if(request.getParameter("searchstring")!=null){
      searchstring = request.getParameter("searchstring");
   }
   
   //한페이지에 가져오는 리스트 개수
   int gainCounter = 10;
   if(request.getParameter("gainCounter")!=null){
      gainCounter = Integer.parseInt(request.getParameter("gainCounter"));
   }
   
   //주문상태
   String state="";
   if(request.getParameter("state")!=null){
	   state = request.getParameter("state");
   }
   
   int member_idx = (Integer)session.getAttribute("member_idx");

   MemberFrontendDAO MD = new MemberFrontendDAO();
   OrderBackendDAO OD = new OrderBackendDAO();

   JSONArray order_list = new JSONArray();
   JSONObject order_info = new JSONObject();
   
   JSONArray order_list2 = new JSONArray();
   JSONObject order_info2 = new JSONObject();

   order_list = MD.MemberOrderList(member_idx, current_page, searchtitle, searchstring, gainCounter);
   
   //shop_order 변수 선언
   /**************************************/
   int order_group_idx;
   String order_name = null;
   String order_phone = null;
   String order_zipcode = null;
   String order_raddress = null;
   String order_jaddress = null;
   String order_address = null;
   
   String delivery_name = null;
   String delivery_phone = null;
   String delivery_zipcode = null;
   String delivery_raddress = null;
   String delivery_jaddress = null;
   String delivery_address = null;
   String delivery_message = null;
   String reg_dt = null;
   String mod_dt = null;
   /**************************************/
   
   //product_order 변수 선언
   /**************************************/
   int product_idx;
   int order_idx;
   String product_name = null;
   int product_cost;
   int product_price;
   int product_discount;
   String product_image = null;
   String product_contents = null;   
   String order_state = null;
   /**************************************/ 
   
   //전체 개수
   int total_count = MD.MemberOrderTotal(member_idx, searchtitle, searchstring);
   
   //전체 페이지
   int total_page = (int)Math.ceil(total_count/(10*1d));
   
   
   //숫자 천자리 쉼표 찍기
   DecimalFormat df = new DecimalFormat("###,###");
   
%>   

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>나의 주문 리스트</title>

<!-- 아이콘 -->
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css" />

<!-- 합쳐지고 최소화된 최신 CSS -->
   <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
   
   <!-- 부가적인 테마 -->
   <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">

</head>
<body>

   <jsp:include page="/jsphome/frontend/top.jsp" flush="true" />
   
   <div class="container">
   		
       <h3>주문 리스트</h3>
     
     <form name="SearchForm" method="post">
      <div class="text-right">
            <div class="form-group form-inline">
               <select class="form-control" name="searchtitle">
                  <option value="" selected>==선택해주세요==</option>
                  <%if(searchtitle.equals("product_name")){ %>
                     <option value="product_name" selected>이름</option>
                  <%}else{ %>
                     <option value="product_name">이름</option>
                  <%} %>
                  
                  <%if(searchtitle.equals("product_contents")){ %>
                     <option value="product_contents" selected>설명</option>
                  <%}else{ %>
                     <option value="product_contents">설명</option>
                  <%} %>
                  
                  <%if(searchtitle.equals("total")){ %>
                     <option value="total" selected>이름+설명</option>
                  <%}else{ %>
                     <option value="total">이름+설명</option>
                  <%} %>

               </select>
               <input type="text" class="form-control" name="searchstring" placeholder="키워드" value="">
               <select class="form-control" name="gainCounter">

                  <%if(gainCounter==10){ %>
                     <option value="10" selected>10개</option>
                  <%}else{ %>
                     <option value="10">10개</option>
                  <%} %>

                  <%if(gainCounter==50){ %>
                     <option value="50" selected>50개</option>
                  <%}else{ %>
                     <option value="50">50개</option>
                  <%} %>

                  <%if(gainCounter==100){ %>
                     <option value="100" selected>100개</option>
                  <%}else{ %>
                     <option value="100">100개</option>
                  <%} %>

               </select>
               <button type="button" class="btn btn-default btn-sm" OnClick="send_search();">검색</button>
            </div>
      </div>
      
      <div class = "form-group form-inline text-right">
      	 <select class="form-control" name="state" onchange= "send_search();">
			
				  <option value="">전체보기</option>

                  <%if(state.equals("A")){ %>
                     <option value="A" selected>결제완료</option>
                  <%}else{ %>
                     <option value="A">결제완료</option>
                  <%} %>
                  
                  <%if(state.equals("B")){ %>
                     <option value="B" selected>배송준비</option>
                  <%}else{ %>
                     <option value="B">배송준비</option>
                  <%} %>
                  
                  <%if(state.equals("C")){ %>
                     <option value="C" selected>배송중</option>
                  <%}else{ %>
                     <option value="C">배송중</option>
                  <%} %>
                 
                  <%if(state.equals("D")){ %>
                     <option value="D" selected>배송완료</option>
                  <%}else{ %>
                     <option value="D">배송완료</option>
                  <%} %>
                  
                  <%if(state.equals("E")){ %>
                     <option value="E" selected>주문취소</option>
                  <%}else{ %>
                     <option value="E">주문취소</option>
                  <%} %>
                 
                  <%if(state.equals("F")){ %>
                     <option value="F" selected>교환</option>
                  <%}else{ %>
                     <option value="F">교환</option>
                  <%} %>
                  
                  <%if(state.equals("G")){ %>
                     <option value="G" selected>환불</option>
                  <%}else{ %>
                     <option value="G">환불</option>
                  <%} %>


               </select>
      </div>
     </form>

      <div style="text-align:right;margin:10px;">
         현재 페이지 :<%=current_page %>/<%=total_page %> | 전체 주문 : <%=total_count %> 개
      </div>
   
      <table class="table table-bordered">
      
         <thead>
            <tr>
               <th>No.</th>
               <th>주문 그룹 IDX</th>
               <th>주문자 이름</th>
               <th>주문자 연락처</th>
               <th>받는 사람 이름</th>
               <th>받는 사람 연락처</th>
               <th>주문날짜</th>
            </tr>
         </thead>
         
         <%if(total_count>0){ %>
            <%for(int i=0;i<order_list.size();i++){ %>
            <%
               order_info = (JSONObject)order_list.get(i);
               order_group_idx = (Integer)order_info.get("order_group_idx");
               order_name = (String)order_info.get("order_name");
               order_phone = (String)order_info.get("order_phone");
               order_zipcode = (String)order_info.get("order_zipcode");
               order_raddress = (String)order_info.get("order_raddress");
               order_jaddress = (String)order_info.get("order_jaddress");
               order_address = (String)order_info.get("order_address");
               delivery_name = (String)order_info.get("delivery_name");
               delivery_phone = (String)order_info.get("delivery_phone");
               delivery_zipcode = (String)order_info.get("order_zipcode");
               delivery_raddress = (String)order_info.get("delivery_deliveryess");
               delivery_jaddress = (String)order_info.get("delivery_jaddress");
               delivery_address = (String)order_info.get("delivery_address");
               reg_dt = (String)order_info.get("reg_dt");
               mod_dt = (String)order_info.get("mod_dt");
            
            %>
            
            
               <tr>
                  <td rowspan="2">
                     <%=total_count-(((current_page-1)*gainCounter)+(i+1))+1%>
                  </td>
                  <td>
                     <%=order_group_idx %>
                  </td>
                  <td>
                     <%=order_name %>
                  </td>
                  <td>
                     <%=order_phone %>
                  </td>
                  <td>
                     <%=delivery_name %>
                  </td>
                  <td>
                     <%=delivery_phone %>
                  </td>
                  <td style="vertical-align:middle;"><%=reg_dt.substring(0, 10) %></td>
               </tr>

               <tr>
                  <td colspan="6">
                     <%
                   		 order_list2 = OD.OrderProductList(order_group_idx);
                     	int total_price = 0;
                     %>
                     <!-- ***************************************************************** -->
                     <table class="table table-bordered">
                     
                        <thead>
                           <tr>
                              <th>No.</th>
                              <th>사진</th>
                              <th>제품 이름</th>
                              <th>원가</th>
                              <th>판매가</th>
                              <th>할인가</th>
                              <th>주문 상태</th>
                              <th>상태 수정</th>
                           </tr>
                        </thead>
                        
                        <%for(int j=0;j<order_list2.size();j++){ %>
                        <%
                           order_info2 = (JSONObject)order_list2.get(j);
                           order_idx = (Integer)order_info2.get("order_idx");
                           product_idx = (Integer)order_info2.get("product_idx");
                           product_name = (String)order_info2.get("product_name");
                           product_cost = (Integer)order_info2.get("product_cost");
                           product_price = (Integer)order_info2.get("product_price");
                           product_discount = (Integer)order_info2.get("product_discount");
                           product_image = (String)order_info2.get("product_image");
                           product_contents = (String)order_info2.get("product_contents");
                           order_state = (String)order_info2.get("order_state");
                           total_price = total_price+product_discount;
                        
                        %>
                        
                           <tr>
                              <td><%=j+1 %></td>
                              <td>
                                 <%if(product_image.equals("")){ %>
                                    <img src="/jsphome/backend/images/noimage.png" width="50px">
                                 <%}else{ %>
                                    <img src="<%=lv.FILEUPLOAD_ROOT_PATH %>/<%=product_image %>" width="50px">
                                 <%} %>
                              </td>
                              <td style="vertical-align:middle;font-weight:700;color:#5858FA;">
                                 <%=product_name %>
                              </td>
                              <td style="vertical-align:middle;"><%=df.format(product_cost)%> 원</td>
                              <td style="vertical-align:middle;"><del><%=df.format(product_price) %></del> 원</td>
                              <td style="vertical-align:middle;color:red;font-weight:700;"><%=df.format(product_discount) %> 원</td>
                              <td style="text-align:center;vertical-align:middle;font-weight:700;">
                              	<%if(order_state.equals("A")){ %>
                              		결제완료
                              	<%} else if(order_state.equals("B")){ %>
                              		배송준비
                              	<%} else if(order_state.equals("C")){ %>
                              		배송중
                              	<%} else if(order_state.equals("D")){ %>
                              		배송완료
                              	<%} else if(order_state.equals("E")){ %>
                              		주문취소
                              	<%} else if(order_state.equals("F")){ %>
                              		교환
                              	<%} else if(order_state.equals("G")){ %>
                              		환불
                              	<%}else{ %>
                              		기타
                              	<%} %>
                              </td>
                              <td style = "text-align:center; vertical-align:middle;">
                              	<button type="button" class="btn btn-default btn-xs" OnClick="send_state('<%=order_group_idx%>', 'E', 'S');">취소</button>
                                 <button type="button" class="btn btn-default btn-xs" OnClick="send_state('<%=order_group_idx%>', 'F', 'S');">교환</button>
                                 <button type="button" class="btn btn-default btn-xs" OnClick="send_state('<%=order_group_idx%>', 'G', 'S');">환불</button>
                              </td>
                           </tr>
            			   
                        <%} %>
                          <tr>
            			   	<td colspan="8" class="text-right" style="font-weight:900;">
								전체 합계 금액 : <span style="color:red;"><%=df.format(total_price) %></span> 원            			   		
            			   		
            			   	</td>
            			  </tr>
            			  
						<!-- 결제완료 : A
							 배송준비 : B
							 배송중 : C
							 배송완료 : D
							 주문취소 : E
							 교환 : F
							 환불 : G
						 -->
						 
                          <tr>
            			   	<td colspan="8" class="text-right">
            			         <button type="button" class="btn btn-default btn-sm" OnClick="send_state('<%=order_group_idx%>', 'E', 'M');">전체 취소</button>
                                 <button type="button" class="btn btn-default btn-sm" OnClick="send_state('<%=order_group_idx%>', 'F', 'M');">전체 교환</button>
                                 <button type="button" class="btn btn-default btn-sm" OnClick="send_state('<%=order_group_idx%>', 'G', 'M');">전체 환불</button>
            			   	</td>
            			  </tr>
                  
                     </table>
                     <!-- ***************************************************************** -->
                  
                  
                  </td>
               </tr>

            <%} %>
            
         <%}else{ %>
   
            <tr>
               <td colspan="8" style="height:150px;text-align:center;vertical-align:middle;">
                  현재 주문 내역이 없습니다.
               </td>
            </tr>
         <%} %>
   
      </table>
      
      
      <div class="text-center" >
      
         <nav>
           <ul class="pagination">
             <li>
               <a href="/product_backend_default.do?current_page=1" aria-label="Previous">
                 <span aria-hidden="true">&laquo;</span>
               </a>
             </li>

            <%for(int i=0;i<total_page;i++){ %>
               <%if(current_page==i+1){ %>
                  <li class="active"><a href="/product_backend_default.do?current_page=<%=i+1%>"><%=i+1 %></a></li>
               <%}else{ %>
                  <li><a href="/product_backend_default.do?current_page=<%=i+1%>"><%=i+1 %></a></li>
               <%} %>
            <%} %>

             <li>
               <a href="/product_backend_default.do?current_page=<%=total_page%>" aria-label="Next">
                 <span aria-hidden="true">&raquo;</span>
               </a>
             </li>
           </ul>
         </nav>

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
      location.href="/product_backend_write.do";
   }
   
   function send_view(product_idx, current_page, searchtitle, searchstring){
      var url="product_backend_view.do?product_idx="+product_idx;
      url+="&current_page="+current_page;
      url+="&searchtitle="+searchtitle;
      url+="&searchstring="+searchstring;
      
      location.href=url;
      
   }
   
	//상태 변경하기
	function send_state(idx_temp, state, type){
		location.href="/order_state_modify.do?idx_temp="+idx_temp+"&state="+state+"&type="+type;
	}
	
	function send_search(){
		var obj = document.SearchForm;
		obj.action="/order_backend_default.do"
		obj.submit();
	}

</script>