package kr.co.mtshop.frontend.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import kr.co.mtshop.backend.dao.FaqBackendDAO;
import kr.co.mtshop.backend.dao.NoticeBackendDAO;
import kr.co.mtshop.common.CommonUtil;
import kr.co.mtshop.common.FileUtiles;
import kr.co.mtshop.common.LocalValue;


/**
 * Handles requests for the application home page.
 */
@Controller
public class NoticeFrontendController {
	
	private static final Logger logger = LoggerFactory.getLogger(NoticeFrontendController.class);
	

	/**
	 * 공지사항 리스트 페이지
	 * @param request
	 * @param respone
	 * @return
	 */
	@RequestMapping(value = "/notice_frontend_default", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView NoticeFrontendDefault(HttpServletRequest request, HttpServletResponse respone) {
		ModelAndView mv = new ModelAndView("/frontend/Notice/notice_frontend_default");
		return mv;
		
	}
	
	/**
	 * 공지사항 정보 보기
	 * @param request
	 * @param respone
	 * @return
	 */
	@RequestMapping(value = "/notice_frontend_view_default", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView NoticeFrontendViewDefault(HttpServletRequest request, HttpServletResponse respone) {
		ModelAndView mv = new ModelAndView("/frontend/Notice/notice_frontend_view_default");
		return mv;
		
	}

}
