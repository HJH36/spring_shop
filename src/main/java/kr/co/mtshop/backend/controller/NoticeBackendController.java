package kr.co.mtshop.backend.controller;

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
public class NoticeBackendController {
	
	private static final Logger logger = LoggerFactory.getLogger(NoticeBackendController.class);
	

	/**
	 * 공지사항 리스트 페이지
	 * @param request
	 * @param respone
	 * @return
	 */
	@RequestMapping(value = "/notice_backend_default", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView NoticeBackendDefault(HttpServletRequest request, HttpServletResponse respone) {
		ModelAndView mv = new ModelAndView("/backend/NoticeManager/notice_backend_default");
		return mv;
		
	}
	
	/**
	 * 공지사항 정보 보기
	 * @param request
	 * @param respone
	 * @return
	 */
	@RequestMapping(value = "/notice_backend_view_default", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView NoticeBackendViewDefault(HttpServletRequest request, HttpServletResponse respone) {
		ModelAndView mv = new ModelAndView("/backend/NoticeManager/notice_backend_view_default");
		return mv;
		
	}

	
	/**
	 * 공지사항 등록하기
	 * @param request
	 * @param respone
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/notice_backend_write_default", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView NoticeBackendWriteDefault(HttpServletRequest request, HttpServletResponse respone) throws Exception {
		ModelAndView mv = new ModelAndView("/backend/NoticeManager/notice_backend_write_default");
		return mv;
		
	}

	/**
	 * 공지사항 작성 완료
	 * @param request
	 * @param respone
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/notice_backend_write_ok", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView NoticeBackendWriteOk(HttpServletRequest request, HttpServletResponse respone) throws Exception {
		ModelAndView mv = new ModelAndView("redirect:notice_backend_default");
		
		LocalValue lv = new LocalValue();
		CommonUtil CU = new CommonUtil();
		FileUtiles FU = new FileUtiles();
		
		NoticeBackendDAO ND = new NoticeBackendDAO();	
		
		String notice_title =  request.getParameter("notice_title");
		notice_title = notice_title.replaceAll("'", "''");
		
		String title_check = "N";
		if(request.getParameter("title_check")!=null){
			title_check = "Y";
		}
		
		String notice_edt = request.getParameter("notice_edt");
		
		String notice_contents = request.getParameter("notice_contents");
		
		ND.NoticeInsert(notice_title, title_check, notice_edt, notice_contents);
		
		return mv;
		
	}


	/**
	 * 공지사항 수정하기
	 * @param request
	 * @param respone
	 * @return
	 */
	@RequestMapping(value = "/notice_backend_modify_default", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView NoticeBackendModifyDefault(HttpServletRequest request, HttpServletResponse respone) {
		ModelAndView mv = new ModelAndView("/backend/NoticeManager/notice_backend_modify_default");
		return mv;
		
	}

	/**
	 * 공지사항 수정 완료
	 * @param request
	 * @param respone
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/notice_backend_modify_ok", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView NoticeBackendModifyOk(HttpServletRequest request, HttpServletResponse respone) throws Exception {
		ModelAndView mv = new ModelAndView("redirect:notice_backend_view_default");
		
		LocalValue lv = new LocalValue();
		CommonUtil CU = new CommonUtil();
		FileUtiles FU = new FileUtiles();
		
		NoticeBackendDAO ND = new NoticeBackendDAO();
		
		int current_page = Integer.parseInt(request.getParameter("current_page"));
		int notice_idx = Integer.parseInt(request.getParameter("notice_idx"));
		
		String notice_title =  request.getParameter("notice_title");
		notice_title = notice_title.replaceAll("'", "''");
		
		String title_check = "N";
		if(request.getParameter("title_check")!=null){
			title_check = "Y";
		}
		
		String notice_edt = request.getParameter("notice_edt");
		
		String notice_contents = request.getParameter("notice_contents");
		
		ND.NoticeModify(notice_idx, notice_title, title_check, notice_edt, notice_contents);
		
		mv.addObject("current_page", current_page);
		mv.addObject("notice_idx", notice_idx);
		
		return mv;
		
	}

	
	/**
	 * 공지사항 삭제하기
	 * @param request
	 * @param respone
	 * @return
	 */
	@RequestMapping(value = "/notice_backend_delete_ok", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView NoticeBackendDeleteOk(HttpServletRequest request, HttpServletResponse respone) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:notice_backend_default");
		
		int current_page = Integer.parseInt(request.getParameter("current_page"));
		int notice_idx = Integer.parseInt(request.getParameter("notice_idx"));
		
		LocalValue lv = new LocalValue();
		FileUtiles FU = new FileUtiles();
		NoticeBackendDAO ND = new NoticeBackendDAO();

		ND.NoticeDelete(notice_idx);
		
		mv.addObject("current_page", current_page);
		return mv;
		
	}

}
