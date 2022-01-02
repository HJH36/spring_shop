package kr.co.mtshop.frontend.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import kr.co.mtshop.common.CommonUtil;
import kr.co.mtshop.common.LocalValue;
import kr.co.mtshop.frontend.dao.OrderFrontendDAO;


/**
 * Handles requests for the application home page.
 */
@Controller
public class OrderFrontendController {
	
	private static final Logger logger = LoggerFactory.getLogger(OrderFrontendController.class);
	
	/**
	 * 바로 구매 하기
	 * @param request
	 * @param respone
	 * @return
	 */
	@RequestMapping(value = "/order_frontend_default", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView OrderFrontendDefault(HttpServletRequest request, HttpServletResponse respone) {
		ModelAndView mv = new ModelAndView("/frontend/order/order_frontend_default");
		return mv;
		
	}
	
	/**
	 * 주문 완료
	 * @param request
	 * @param respone
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/order_direct_confirm", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView OrderFrontendConfirm(HttpServletRequest request, HttpServletResponse respone) throws Exception {
		ModelAndView mv = new ModelAndView("redirect:/");
		
		LocalValue lv = new LocalValue();
		CommonUtil CU = new CommonUtil();
		
		String[] product_idx_str = request.getParameterValues("product_idx");
		String[] product_discount_str = request.getParameterValues("product_discount");

		int member_idx = Integer.parseInt(request.getParameter("member_idx"));
		String order_state = "A";
		
		//주문자 정보
		String order_name = request.getParameter("order_name");
		String order_phone = request.getParameter("order_phone");
		String order_zipcode = request.getParameter("order_zipcode");
		String order_raddress = request.getParameter("order_raddress");
		String order_jaddress = request.getParameter("order_jaddress");
		String order_address = request.getParameter("order_address");

	    //배송지 정보
	    String delivery_name = request.getParameter("delivery_name");
	    String delivery_phone = request.getParameter("delivery_phone");
	    String delivery_zipcode = request.getParameter("delivery_zipcode");
        String delivery_raddress = request.getParameter("delivery_raddress");
	    String delivery_jaddress = request.getParameter("delivery_jaddress");
	    String delivery_address = request.getParameter("delivery_address");
	    String delivery_message ="";
	    
	    if(request.getParameter("delivery_message")!=null) {
	    	delivery_message = request.getParameter("delivery_message");
	    }
	    
	    int order_total_price = Integer.parseInt(request.getParameter("order_total_price"));
	    
	    OrderFrontendDAO OD = new OrderFrontendDAO();
	    int order_group_idx = OD.OrderMaxId()+1;
	    
	    HashMap<String, String> params = new HashMap();
	    params.put("order_group_idx", String.valueOf(order_group_idx));
	    params.put("member_idx", String.valueOf(member_idx));
	    params.put("order_total_price", String.valueOf(order_total_price));
	    params.put("order_name", order_name);
	    params.put("order_phone", order_phone);
	    params.put("order_zipcode", order_zipcode);
	    params.put("order_raddress", order_raddress);
	    params.put("order_jaddress", order_jaddress);
	    params.put("order_address", order_address);
		
	    params.put("delivery_name", delivery_name);
	    params.put("delivery_phone", delivery_phone);
	    params.put("delivery_zipcode", delivery_zipcode);
	    params.put("delivery_raddress", delivery_raddress);
	    params.put("delivery_jaddress", delivery_jaddress);
	    params.put("delivery_address", delivery_address);
	    params.put("delivery_message", delivery_message);
	    
	 
	    OD.OrderInsert(params);
	    
	    for(int i=0; i<product_idx_str.length; i++) {
	    	OD.ProductOrderInsert(Integer.parseInt(product_idx_str[i]), order_group_idx, Integer.parseInt(product_discount_str[i]), order_state);
		}
	    
	  //세션을 만들기 위해
	  HttpSession session = request.getSession();
	  session.removeAttribute("product_list");
	    
		return mv;
		
	}

}
