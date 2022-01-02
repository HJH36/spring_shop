package kr.co.mtshop.backend.controller;

import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import kr.co.mtshop.backend.dao.OrderBackendDAO;
import kr.co.mtshop.backend.dao.ProductBackendDAO;
import kr.co.mtshop.common.CommonUtil;
import kr.co.mtshop.common.FileUtiles;
import kr.co.mtshop.common.LocalValue;


/**
 * Handles requests for the application home page.
 */
@Controller
public class OrderBackendController {
	
	private static final Logger logger = LoggerFactory.getLogger(OrderBackendController.class);
	

	/**
	 * 주문 리스트
	 * @param request
	 * @param respone
	 * @return
	 */
	@RequestMapping(value = "/order_backend_default", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView OrderBackendDefault(HttpServletRequest request, HttpServletResponse respone) {
		ModelAndView mv = new ModelAndView("/backend/OrderManager/order_backend_default");
		return mv;
		
	}

	/**
	 * 주문 정보 보기
	 * @param request
	 * @param respone
	 * @return
	 */
	@RequestMapping(value = "/order_backend_view", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView OrderBackendViewDefault(HttpServletRequest request, HttpServletResponse respone) {
		ModelAndView mv = new ModelAndView("/backend/OrderManager/order_backend_view");
		return mv;
		
	}


	/**
	 * 주문 정보 수정 페이지
	 * @param request
	 * @param respone
	 * @return
	 */
	@RequestMapping(value = "/order_backend_modify", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView OrderBackendModifyDefault(HttpServletRequest request, HttpServletResponse respone) {
		ModelAndView mv = new ModelAndView("/backend/OrderManager/order_backend_modify");
		return mv;
		
	}

	/**
	 * 주문 정보 수정 완료
	 * @param request
	 * @param respone
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/order_backend_modify_ok", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView OrderBackendModifyOk(HttpServletRequest request, HttpServletResponse respone) throws Exception {
		ModelAndView mv = new ModelAndView("redirect:order_backend_view");
		
		LocalValue lv = new LocalValue();
		CommonUtil CU = new CommonUtil();
		FileUtiles FU = new FileUtiles();
		
		int current_page = Integer.parseInt(request.getParameter("current_page"));
		int product_idx = Integer.parseInt(request.getParameter("product_idx"));

		String searchtitle =  request.getParameter("searchtitle");
		String searchstring =  request.getParameter("searchstring");
		
		
		String product_name =  request.getParameter("product_name");
		product_name = product_name.replaceAll("'", "''");
		int product_cost = Integer.parseInt(request.getParameter("product_cost"));
		int product_price = Integer.parseInt(request.getParameter("product_price"));
		int product_discount = Integer.parseInt(request.getParameter("product_discount"));
		String product_image =  "";
		
		ProductBackendDAO PD = new ProductBackendDAO();
		
		JSONObject product_info = new JSONObject();
		product_info = PD.ProductInfo(product_idx);
		
		//파일 받기
		/**************************************************************/
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
		MultipartFile product_image_temp = (MultipartFile)multipartRequest.getFile("product_image");
		
		ServletContext context = request.getSession().getServletContext();
		String uploadPath = context.getRealPath("/")+lv.FILEUPLOAD_ROOT_PATH;
		String strFolder = "product";
		
		if(!product_image_temp.getOriginalFilename().isEmpty()) {
			FU.delete(uploadPath+"/"+ (String)product_info.get("product_image") );
			product_image = FU.setSingleFileUpload(product_image_temp, uploadPath, strFolder);
		}else {
			product_image = (String)product_info.get("product_image");
		}
		/**************************************************************/

		String product_contents = request.getParameter("product_contents");
//		board_contents = board_contents.replace("\r\n", "<br>");
//		board_contents = board_contents.replaceAll(System.getProperty("line.separator"), "<br>");
//		board_contents = board_contents.replaceAll("(\r\n|\r|\n|\n\r)", "<br>");
		
		HashMap<String, String> params = new HashMap<>();
		params.put("product_idx", String.valueOf(product_idx));
		params.put("product_name", product_name);
		params.put("product_cost", String.valueOf(product_cost));
		params.put("product_price", String.valueOf(product_price));
		params.put("product_discount", String.valueOf(product_discount));
		params.put("product_image", product_image);
		params.put("product_contents", product_contents);
		
		System.out.println(params);
		
		PD.ProductModify(params);
		
		mv.addObject("current_page", current_page);
		mv.addObject("product_idx", product_idx);
		mv.addObject("searchtitle", searchtitle);
		mv.addObject("searchstring", searchstring);
		return mv;
		
	}

	
	/**
	 * 주문 정보 삭제하기
	 * @param request
	 * @param respone
	 * @return
	 */
	@RequestMapping(value = "/order_backend_delete_ok", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView OrderBackendDeleteOk(HttpServletRequest request, HttpServletResponse respone) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:order_backend_default");
		
		LocalValue lv = new LocalValue();
		FileUtiles FU = new FileUtiles();
		
		int current_page = Integer.parseInt(request.getParameter("current_page"));
		String searchtitle =  request.getParameter("searchtitle");
		String searchstring =  request.getParameter("searchstring");
		String gainCounter =  request.getParameter("gainCounter");

		int idx_temp = Integer.parseInt(request.getParameter("idx_temp"));
		String type =  request.getParameter("type");
		
		OrderBackendDAO OD = new OrderBackendDAO();
		OD.OrderDelete(idx_temp, type);
		
		mv.addObject("current_page", current_page);
		mv.addObject("searchtitle", searchtitle);
		mv.addObject("searchstring", searchstring);
		mv.addObject("gainCounter", gainCounter);
		return mv;
		
	}
	
	/**
	 *배송 상태 수정 
	 * @param request
	 * @param respone
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/order_state_modify", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView OrderStateModify(HttpServletRequest request, HttpServletResponse respone) throws Exception {
		ModelAndView mv = new ModelAndView("/backend/OrderManager/order_backend_default");
		
		int idx_temp = Integer.parseInt(request.getParameter("idx_temp"));
		String state = request.getParameter("state");
		String type = request.getParameter("type");
		
		OrderBackendDAO OD = new OrderBackendDAO();
		OD.OrderStateModify(idx_temp, state, type);
		return mv;
		
	}


}
