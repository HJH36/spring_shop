package kr.co.mtshop.frontend.controller;

import java.io.PrintWriter;
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
import kr.co.mtshop.common.KakaoApi;
import kr.co.mtshop.frontend.dao.MemberFrontendDAO;


/**
 * Handles requests for the application home page.
 */
@Controller
public class MemberFrontendController {
	
	private static final Logger logger = LoggerFactory.getLogger(MemberFrontendController.class);
	
	/**
	 * 첫번째 메인 홈
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/member_list", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView MemberDefault(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("/frontend/Member/member_list");
		return mv;
		
	}

	
	/**
	 * 회원가입 페이지 이동
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/member_write_default", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView MemberWriteDefault(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("/frontend/Member/member_write_default");
		return mv;
		
	}
	
	

	
	/**
	 * 회원 가입 완료
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/member_write_ok", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView MemberWriteOk(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("redirect:member_confirm_default");
		
//		request.setCharacterEncoding("utf-8");
		
		CommonUtil commonUtil = new CommonUtil();
		
		//변수 받아서 처리하기
		String member_id = request.getParameter("member_id");
		String member_pwd = request.getParameter("member_pwd");
		member_pwd = commonUtil.getEncrypt(member_pwd);
		
		String member_kind = request.getParameter("member_kind");
		String member_name = request.getParameter("member_name");
		String member_email = request.getParameter("member_email");
		String member_phone = request.getParameter("member_phone");
		String zipcode = request.getParameter("zipcode");
		String member_raddress = request.getParameter("member_raddress");
		String member_jaddress = request.getParameter("member_jaddress");
		String member_address = request.getParameter("member_address");

		HashMap<String, String>params = new HashMap();
		params.put("member_id", member_id);
		params.put("member_pwd", member_pwd);
		params.put("member_kind", member_kind);
		params.put("member_name", member_name);
		params.put("member_email", member_email);
		params.put("member_phone", member_phone);
		params.put("zipcode", zipcode);
		params.put("member_jaddress", member_jaddress);
		params.put("member_raddress", member_raddress);
		params.put("member_address", member_address);

		System.out.println(params);
		
		MemberFrontendDAO MD = new MemberFrontendDAO();
		MD.MemberInsert(params);
		
		return mv;
		
	}
	/**
	 * 회원가입 완료 확인 페이지
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/member_confirm_default", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView MemberConfirmDefault(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("/frontend/Member/member_confirm_default");
		return mv;
		
	}
	
	
	/**
	 * 회원 정보 보기
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/member_view_default", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView MemberViewDefault(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("/frontend/Member/member_view_default");
		return mv;
		
	}
	
	
	/**
	 * 회원 정보 수정 페이지
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/member_modify_default", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView MemberModifyDefault(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("/frontend/Member/member_modify_default");
		return mv;
		
	}

	
	@RequestMapping(value = "/member_modify_ok", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView MemberModifyOk(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("redirect:member_view_default");
		
		CommonUtil commonUtil = new CommonUtil();
		
		//변수 받아서 처리하기
		int current_page = Integer.parseInt( request.getParameter("current_page") );
		int member_idx = Integer.parseInt( request.getParameter("member_idx") );
		String member_id = request.getParameter("member_id");
		String member_pwd = request.getParameter("member_pwd");
		String member_kind = request.getParameter("member_kind");
		member_pwd = commonUtil.getEncrypt(member_pwd);
		
		String member_name = request.getParameter("member_name");
		String member_email = request.getParameter("member_email");
		String member_phone = request.getParameter("member_phone");
		String zipcode = request.getParameter("zipcode");
		String member_raddress = request.getParameter("member_raddress");
		String member_jaddress = request.getParameter("member_jaddress");
		String member_address = request.getParameter("member_address");
		
		//위도 경도 만들기
		/***********************************************************************/
		String latitude = "";
		String longitude = "";
		
		KakaoApi kakaoApi = new KakaoApi();
		
		double[] coords = {(double)0, (double)0};
		if(!member_raddress.equals("") && member_raddress!=null && !member_raddress.isEmpty()) {
			coords = kakaoApi.AddrTocoord(member_raddress);
		}else {
			if(!member_jaddress.equals("") && member_jaddress!=null && !member_jaddress.isEmpty()) {
				coords = kakaoApi.AddrTocoord(member_jaddress);
			}
		}
		
		latitude = Double.toString(coords[0]);
		longitude = Double.toString(coords[1]);
		
		System.out.println("latitude : "+latitude+", longitude : "+longitude);
		/***********************************************************************/
		
		HashMap<String, String> params = new HashMap();
		params.put("member_idx", String.valueOf(member_idx));
		params.put("member_id", member_id);
		params.put("member_pwd", member_pwd);
		params.put("member_name", member_name);
		params.put("member_phone", member_phone);
		params.put("member_email", member_email);
		params.put("zipcode", zipcode);
		params.put("member_raddress", member_raddress);
		params.put("member_jaddress", member_jaddress);
		params.put("member_address", member_address);
		params.put("member_kind", member_kind);
		
		MemberFrontendDAO MD = new MemberFrontendDAO();
		MD.MemberModify(params);
		
		mv.addObject("current_page", current_page);
		mv.addObject("member_idx", member_idx);
		
		return mv;
		
	}
	
	
	/**
	 * 비밀번호 수정 페이지
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/pwd_modify_default", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView PwdModifyDefault(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("/frontend/Member/pwd_modify_default");
		return mv;
		
	}
	
	
	@RequestMapping(value = "/pwd_modify_ok", method = {RequestMethod.GET, RequestMethod.POST})
	public void PwdModifyOk(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		//html을 만들기 위해
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		//세션을 만들기 위해
		HttpSession session = request.getSession();
		
		int member_idx = Integer.parseInt(request.getParameter("member_idx"));
		String member_pwd0 = request.getParameter("member_pwd0");
		String member_pwd1 = request.getParameter("member_pwd1");
		
		CommonUtil commonUtil = new CommonUtil();
		member_pwd1 = commonUtil.getEncrypt(member_pwd1);
		
		try {
			
			MemberFrontendDAO memberDAO = new MemberFrontendDAO();
			
			int nflag = memberDAO.MemberPwdCheck(member_idx, member_pwd0);
			
			if(nflag==1) {
				
				memberDAO.PwdUpdate(member_idx, member_pwd1);
				
				out.println("<script>");
				out.println("alert('회원님의 비밀번호가 수정되었습니다. ');");
				out.println("location.href='/member_view_default.do'");
				out.println("</script>");

			}else {
				
				out.println("<script>");
				out.println("alert('회원님의 이전 비밀번호가 틀립니다. ');");
				out.println("location.href='/pwd_modify_default.do'");
				out.println("</script>");

			}


		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 회원 주문 내역 페이지
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/member_order_default", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView MemberOrderDefault(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("/frontend/Member/member_order_default");
		return mv;
		
	}
	
}
