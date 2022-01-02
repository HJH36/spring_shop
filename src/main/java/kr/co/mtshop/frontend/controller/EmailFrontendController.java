package kr.co.mtshop.frontend.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import kr.co.mtshop.common.CommonMail;
import kr.co.mtshop.common.CommonUtil;
import kr.co.mtshop.common.FileUtiles;
import kr.co.mtshop.common.LocalValue;
import kr.co.mtshop.frontend.dao.BoardFrontendDAO;
import kr.co.mtshop.frontend.dao.EmailFrontendDAO;


/**
 * Handles requests for the application home page.
 */
@Controller
public class EmailFrontendController {
	
	private static final Logger logger = LoggerFactory.getLogger(EmailFrontendController.class);
	

	/**
	 * 이메일 작성하기 페이지
	 * @param request
	 * @param respone
	 * @return
	 */
	@RequestMapping(value = "/mailer_default", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView MailerDefault(HttpServletRequest request, HttpServletResponse respone) {
		ModelAndView mv = new ModelAndView("/frontend/mailer/mailer_default");
		return mv;
		
	}
	/**
	 * 메일 보내기 완료
	 * @param request
	 * @param respone
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/mailer_confirm_ok", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView MailerConfirmOk(HttpServletRequest request, HttpServletResponse respone) throws Exception {
		ModelAndView mv = new ModelAndView("/frontend/index");
		
		CommonUtil CU = new CommonUtil();
		LocalValue LV = new LocalValue();
		CommonMail CM = new CommonMail();
		EmailFrontendDAO ED = new EmailFrontendDAO();
		
		String from_email = request.getParameter("from_email");
		String to_email = request.getParameter("to_email");
		String from_name = request.getParameter("from_name");
		String email_contents = request.getParameter("from_contents");

		/** 메일 보내기 알고리즘 */
		/*********************************************************************/
		HashMap<String, String> params = new HashMap();
		params.put("from_email", from_email);
		params.put("from_name", from_name);
		params.put("to_email", to_email);
		params.put("subject", from_name+" 고객 상담 메일");
		params.put("email_contents", email_contents);
		
		/* 메일 보내기 */
		CM.SendMail(params);
		
		/* DB에 데이터 저장 */
		try {
			ED.EmailInsert(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/*********************************************************************/
		
		
		return mv;
		
	}
	

}
