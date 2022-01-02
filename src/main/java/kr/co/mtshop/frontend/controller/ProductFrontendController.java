package kr.co.mtshop.frontend.controller;

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

import kr.co.mtshop.frontend.dao.ProductFrontendDAO;
import kr.co.mtshop.common.CommonUtil;
import kr.co.mtshop.common.FileUtiles;
import kr.co.mtshop.common.LocalValue;


/**
 * Handles requests for the application home page.
 */
@Controller
public class ProductFrontendController {
	
	private static final Logger logger = LoggerFactory.getLogger(ProductFrontendController.class);
	

	/**
	 * 상품 사진 리스트 페이지
	 * @param request
	 * @param respone
	 * @return
	 */
	@RequestMapping(value = "/product_frontend_default", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView ProductFrontendDefault(HttpServletRequest request, HttpServletResponse respone) {
		ModelAndView mv = new ModelAndView("/frontend/product/product_default");
		return mv;
		
	}

	/**
	 * 상품 일반 리스트 페이지
	 * @param request
	 * @param respone
	 * @return
	 */
	@RequestMapping(value = "/product_frontend_list_default", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView ProductFrontendListDefault(HttpServletRequest request, HttpServletResponse respone) {
		ModelAndView mv = new ModelAndView("/frontend/product/product_list_default");
		return mv;
		
	}
	
	/**
	 * 상품 정보 보기
	 * @param request
	 * @param respone
	 * @return
	 */
	@RequestMapping(value = "/product_frontend_view", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView ProductFrontendViewDefault(HttpServletRequest request, HttpServletResponse respone) {
		ModelAndView mv = new ModelAndView("/frontend/product/product_view");
		return mv;
		
	}
	


}
