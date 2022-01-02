package kr.co.mtshop.backend.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import kr.co.mtshop.backend.dao.FaqBackendDAO;
import kr.co.mtshop.common.CommonUtil;
import kr.co.mtshop.common.FileUtiles;
import kr.co.mtshop.common.LocalValue;


/**
 * Handles requests for the application home page.
 */
@Controller
public class FaqBackendController {
	
	private static final Logger logger = LoggerFactory.getLogger(FaqBackendController.class);
	

	/**
	 * 질문과 답변 리스트 페이지
	 * @param request
	 * @param respone
	 * @return
	 */
	@RequestMapping(value = "/faq_backend_default", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView FaqBackendDefault(HttpServletRequest request, HttpServletResponse respone) {
		ModelAndView mv = new ModelAndView("/backend/faq/faq_backend_default");
		return mv;
		
	}
	
	/**
	 * 질문과 답변 정보 보기
	 * @param request
	 * @param respone
	 * @return
	 */
	@RequestMapping(value = "/faq_backend_view_default", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView FaqBackendViewDefault(HttpServletRequest request, HttpServletResponse respone) {
		ModelAndView mv = new ModelAndView("/backend/faq/faq_backend_view_default");
		return mv;
		
	}

	
	/**
	 * 질문과 답변 등록하기
	 * @param request
	 * @param respone
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/faq_backend_write_default", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView FaqBackendWriteDefault(HttpServletRequest request, HttpServletResponse respone) throws Exception {
		ModelAndView mv = new ModelAndView("/backend/faq/faq_backend_write_default");
		return mv;
		
	}

	/**
	 * 질문과 답변 작성 완료
	 * @param request
	 * @param respone
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/faq_backend_write_ok", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView FaqBackendWriteOk(HttpServletRequest request, HttpServletResponse respone) throws Exception {
		ModelAndView mv = new ModelAndView("redirect:faq_backend_default");
		
		LocalValue lv = new LocalValue();
		CommonUtil CU = new CommonUtil();
		FileUtiles FU = new FileUtiles();
		
		FaqBackendDAO FaqDAO = new FaqBackendDAO();	
		
		String faq_title =  request.getParameter("faq_title");
		faq_title = faq_title.replaceAll("'", "''");
		
		String faq_contents = request.getParameter("faq_contents");
//		faq_contents = faq_contents.replace("\r\n", "<br>");
//		faq_contents = faq_contents.replaceAll(System.getProperty("line.separator"), "<br>");
//		faq_contents = faq_contents.replaceAll("(\r\n|\r|\n|\n\r)", "<br>");
		
		FaqDAO.FaqInsert(faq_title, faq_contents);
		
		return mv;
		
	}


	/**
	 * 질문과 답변 수정하기
	 * @param request
	 * @param respone
	 * @return
	 */
	@RequestMapping(value = "/faq_backend_modify_default", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView FaqBackendModifyDefault(HttpServletRequest request, HttpServletResponse respone) {
		ModelAndView mv = new ModelAndView("/backend/faq/faq_backend_modify_default");
		return mv;
		
	}

	/**
	 * 질문과 답변 수정 완료
	 * @param request
	 * @param respone
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/faq_backend_modify_ok", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView FaqBackendModifyOk(HttpServletRequest request, HttpServletResponse respone) throws Exception {
		ModelAndView mv = new ModelAndView("redirect:faq_backend_view_default");
		
		LocalValue lv = new LocalValue();
		CommonUtil CU = new CommonUtil();
		FileUtiles FU = new FileUtiles();
		
		FaqBackendDAO FaqDAO = new FaqBackendDAO();
		
		int current_page = Integer.parseInt(request.getParameter("current_page"));
		int faq_idx = Integer.parseInt(request.getParameter("faq_idx"));
		
		String faq_title =  request.getParameter("faq_title");
		faq_title = faq_title.replaceAll("'", "''");
		
		String faq_contents = request.getParameter("faq_contents");
//		faq_contents = faq_contents.replace("\r\n", "<br>");
//		faq_contents = faq_contents.replaceAll(System.getProperty("line.separator"), "<br>");
//		faq_contents = faq_contents.replaceAll("(\r\n|\r|\n|\n\r)", "<br>");
		
		FaqDAO.FaqModify(faq_title, faq_contents, faq_idx);
		
		mv.addObject("current_page", current_page);
		mv.addObject("faq_idx", faq_idx);
		
		return mv;
		
	}

	
	/**
	 * 질문과 답변 삭제하기
	 * @param request
	 * @param respone
	 * @return
	 */
	@RequestMapping(value = "/faq_backend_delete_ok", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView FaqBackendDeleteOk(HttpServletRequest request, HttpServletResponse respone) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:faq_backend_default");
		
		int current_page = Integer.parseInt(request.getParameter("current_page"));
		int faq_idx = Integer.parseInt(request.getParameter("faq_idx"));
		
		LocalValue lv = new LocalValue();
		FileUtiles FU = new FileUtiles();
		FaqBackendDAO FaqDAO = new FaqBackendDAO();

		FaqDAO.FaqDelete(faq_idx);
		
		mv.addObject("current_page", current_page);
		return mv;
		
	}

}
