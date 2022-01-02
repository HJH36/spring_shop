package kr.co.mtshop.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Handles requests for the application home page.
 */
@Controller
public class MainController {
	
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	
	/**
	 * 사용자 페이지1
	 * @param request
	 * @param respone
	 * @return
	 */
	@RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView FrontendDefault1(HttpServletRequest request, HttpServletResponse respone) {
		ModelAndView mv = new ModelAndView("/frontend/index");
		return mv;
		
	}
	
	/**
	 * 사용자 페이지2
	 * @param request
	 * @param respone
	 * @return
	 */
	@RequestMapping(value = "/index", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView FrontendDefault2(HttpServletRequest request, HttpServletResponse respone) {
		ModelAndView mv = new ModelAndView("/frontend/index");
		return mv;
		
	}

	/**
	 * 관리자 페이지1
	 * @param request
	 * @param respone
	 * @return
	 */
	@RequestMapping(value = "/backend/", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView BackendDefault1(HttpServletRequest request, HttpServletResponse respone) {
		ModelAndView mv = new ModelAndView("/backend/index");
		return mv;
		
	}
	
	/**
	 * 관리자 페이지2
	 * @param request
	 * @param respone
	 * @return
	 */
	@RequestMapping(value = "/backend/index", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView BackendDefault2(HttpServletRequest request, HttpServletResponse respone) {
		ModelAndView mv = new ModelAndView("/backend/index");
		return mv;
		
	}
	
	/**
	 * 관리자 페이지3
	 * @param request
	 * @param respone
	 * @return
	 */
	@RequestMapping(value = "/backend", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView BackendDefault3(HttpServletRequest request, HttpServletResponse respone) {
		ModelAndView mv = new ModelAndView("/backend/index");
		return mv;
		
	}
	
}
