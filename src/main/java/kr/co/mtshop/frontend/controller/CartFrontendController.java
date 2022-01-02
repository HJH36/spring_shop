package kr.co.mtshop.frontend.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


/**
 * Handles requests for the application home page.
 */
@Controller
public class CartFrontendController {
	
	private static final Logger logger = LoggerFactory.getLogger(CartFrontendController.class);
	

	/**
	 * 장바구니 상품 리스트
	 * @param request
	 * @param respone
	 * @return
	 */
	@RequestMapping(value = "/cart_default", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView CartDefault(HttpServletRequest request, HttpServletResponse respone) {
		ModelAndView mv = new ModelAndView("/frontend/cart/cart_default");
		return mv;
		
	}

	/**
	 * 장바구니 담기
	 * @param request
	 * @param respone
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/cart_write_default", method = {RequestMethod.GET, RequestMethod.POST})
	public void CartWriteDefault(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//html을 만들기 위해
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
				
		//세션을 만들기 위해
		HttpSession session = request.getSession();
		
		//세션을 리스트로 만들기
		/*********************************************************************************/
		ArrayList<String> product_list = (ArrayList)session.getAttribute("product_list");
		ArrayList<String> product_number = (ArrayList)session.getAttribute("product_number");
		ArrayList<String> product_price_array = (ArrayList)session.getAttribute("product_price_array");
		
		if(product_list==null){
			product_list = new ArrayList<String>();
			session.setAttribute("product_list", product_list);
		}
		
		if(product_number==null){
			product_number = new ArrayList<String>();
			session.setAttribute("product_number", product_number);
		}
		if(product_price_array==null){
			product_price_array = new ArrayList<String>();
			session.setAttribute("product_price_array", product_price_array);
		}
		/*********************************************************************************/
		
		
		/*********************************************************************************/
		String product_idx = request.getParameter("product_idx");
		String product_num = "1";
		if(request.getParameter("product_num")!=null) {
			product_num = request.getParameter("product_num");
		}
		
		System.out.println("product_num : "+ product_num);
		
		String product_discount =  request.getParameter("product_discount");

		product_list.add(product_idx);
		product_price_array.add(product_discount);
		product_number.add(product_num);
		
		session.setAttribute("product_list", product_list);
		session.setAttribute("product_number", product_number);
		session.setAttribute("product_price_array", product_price_array);
		/*********************************************************************************/
		
		
		out.println("<script>");
		out.println("var ans = confirm('장바구니로 이동 하시겠습니까?');");
		out.println("if(ans){");
		out.println("location.href='/cart_default.do';");
		out.println("}else{");
		out.println("history.back();");
		out.println("}");
		out.println("</script>");
		
	//	response.sendRedirect("/cart_default");
		
	}

	/**
	 * 장바구니 상품 삭제하기
	 * @param request
	 * @param respone
	 * @return
	 */
	@RequestMapping(value = "/cart_delete", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView CartDelete(HttpServletRequest request, HttpServletResponse respone) {
		ModelAndView mv = new ModelAndView("/frontend/cart/cart_default");
		
		int key_idx = Integer.parseInt(request.getParameter("key_idx"));
		
		//세션을 만들기 위해
		HttpSession session = request.getSession();
		
		//상품 IDX 지우기
		ArrayList<String> product_list = (ArrayList)session.getAttribute("product_list");
		product_list.remove(key_idx);

		//상품 개수 지우기
		ArrayList<String> product_number = (ArrayList)session.getAttribute("product_number");
		product_number.remove(key_idx);
		
		return mv;
		
	}
	
	
}
