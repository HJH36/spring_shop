package kr.co.mtshop.backend.controller;

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

import kr.co.mtshop.backend.dao.MemberBackendDAO;
import kr.co.mtshop.common.CommonUtil;


/**
 * Handles requests for the application home page.
 */
@Controller
public class MemberBackendController {
   
   private static final Logger logger = LoggerFactory.getLogger(MemberBackendController.class);
   
   /**
    * 첫번째 메인 홈
    * @param request
    * @param response
    * @return
    */
   @RequestMapping(value = "/member_backend_default", method = {RequestMethod.GET, RequestMethod.POST})
   public ModelAndView MemberBackendDefault(HttpServletRequest request, HttpServletResponse response) {
      ModelAndView mv = new ModelAndView("/backend/MemberManager/member_backend_default");
      return mv;
      
   }

   
   /**
    * 회원가입 페이지 이동
    * @param request
    * @param response
    * @return
    */
   @RequestMapping(value = "/member_backend_write_default", method = {RequestMethod.GET, RequestMethod.POST})
   public ModelAndView MemberBackendWriteDefault(HttpServletRequest request, HttpServletResponse response) {
      ModelAndView mv = new ModelAndView("/backend/MemberManager/member_backend_write_default");
      return mv;
      
   }
   
   

   
   /**
    * 회원 가입 완료
    * @param request
    * @param response
    * @return
    * @throws Exception 
    */
   @RequestMapping(value = "/member_backend_write_ok", method = {RequestMethod.GET, RequestMethod.POST})
   public ModelAndView MemberBackendWriteOk(HttpServletRequest request, HttpServletResponse response) throws Exception {
      ModelAndView mv = new ModelAndView("redirect:member_backend_default");
      
//      request.setCharacterEncoding("utf-8");
      
      CommonUtil commonUtil = new CommonUtil();
      
      //변수 받아서 처리하기
      String member_id = request.getParameter("member_id");
      String member_pwd = request.getParameter("member_pwd");
      member_pwd = commonUtil.getEncrypt(member_pwd);
      
      String member_name = request.getParameter("member_name");
      String member_kind = request.getParameter("member_kind");
      String member_phone = request.getParameter("member_phone");
      String member_email = request.getParameter("member_email");
      String zipcode = request.getParameter("zipcode");
      String member_jaddress = request.getParameter("member_jaddress");
      String member_raddress = request.getParameter("member_raddress");
      String member_address = request.getParameter("member_address");
      
      HashMap<String, String>params = new HashMap();
      params.put("member_id", member_id);
      params.put("member_pwd", member_pwd);
      params.put("member_name", member_name);
      params.put("member_kind", member_kind);
      params.put("member_phone", member_phone);
      params.put("member_email", member_email);
      params.put("zipcode", zipcode);
      params.put("member_jaddress", member_jaddress);
      params.put("member_raddress", member_raddress);
      params.put("member_address", member_address);
      
      System.out.println(params);
      
      MemberBackendDAO MD = new MemberBackendDAO();
      try {
         MD.MemberInsert(params);
         
      } catch (Exception e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      
      return mv;
      
   }

   
   /**
    * 회원 정보 보기
    * @param request
    * @param response
    * @return
    */
   @RequestMapping(value = "/member_backend_view_default", method = {RequestMethod.GET, RequestMethod.POST})
   public ModelAndView MemberBackendViewDefault(HttpServletRequest request, HttpServletResponse response) {
      ModelAndView mv = new ModelAndView("/backend/MemberManager/member_backend_view_default");
      return mv;
      
   }
   
   
   /**
    * 회원 정보 수정 페이지
    * @param request
    * @param response
    * @return
    */
   @RequestMapping(value = "/member_backend_modify_default", method = {RequestMethod.GET, RequestMethod.POST})
   public ModelAndView MemberBackendModifyDefault(HttpServletRequest request, HttpServletResponse response) {
      ModelAndView mv = new ModelAndView("/backend/MemberManager/member_backend_modify_default");
      return mv;
      
   }

   /**
    * 회원 정보 수정 완료
    * @param request
    * @param response
    * @return
    * @throws Exception
    */
   @RequestMapping(value = "/member_backend_modify_ok", method = {RequestMethod.GET, RequestMethod.POST})
   public ModelAndView MemberBackendModifyOk(HttpServletRequest request, HttpServletResponse response) throws Exception {
      ModelAndView mv = new ModelAndView("redirect:member_backend_view_default");
      
      CommonUtil commonUtil = new CommonUtil();
      
      //변수 받아서 처리하기
      int current_page = Integer.parseInt( request.getParameter("current_page") );
      int member_idx = Integer.parseInt( request.getParameter("member_idx") );
      String member_id = request.getParameter("member_id");
      String member_pwd = request.getParameter("member_pwd");
      member_pwd = commonUtil.getEncrypt(member_pwd);
      
      String member_name = request.getParameter("member_name");
      String member_kind = request.getParameter("member_kind");
      String member_phone = request.getParameter("member_phone");
      String member_email = request.getParameter("member_email");
      String zipcode = request.getParameter("zipcode");
      String member_jaddress = request.getParameter("member_jaddress");
      String member_raddress = request.getParameter("member_raddress");
      String member_address = request.getParameter("member_address");
      
      String member_del_yn = null;
      if(request.getParameter("member_del_yn")!=null) {
         member_del_yn = "Y";
      }else {
         member_del_yn = "N";
      }
      
      HashMap<String, String> params = new HashMap();
      params.put("member_idx", String.valueOf(member_idx));
      params.put("member_id", member_id);
      params.put("member_pwd", member_pwd);
      params.put("member_name", member_name);
      params.put("member_kind", member_kind);
      params.put("member_phone", member_phone);
      params.put("member_email", member_email);
      params.put("zipcode", zipcode);
      params.put("member_jaddress", member_jaddress);
      params.put("member_raddress", member_raddress);
      params.put("member_address", member_address);
      
//      System.out.println(params);
      
      MemberBackendDAO MD = new MemberBackendDAO();
      MD.MemberModify(params);
      
      mv.addObject("current_page", current_page);
      mv.addObject("member_idx", member_idx);
      
      return mv;
      
   }
   
   /**
    * 회원 삭제 완료
    * @param request
    * @param response
    * @return
    * @throws Exception
    */
   @RequestMapping(value = "/member_backend_delete_ok", method = {RequestMethod.GET, RequestMethod.POST})
   public ModelAndView MemberBackendDeleteOk(HttpServletRequest request, HttpServletResponse response) throws Exception {
      ModelAndView mv = new ModelAndView("redirect:member_backend_default");
      
      CommonUtil commonUtil = new CommonUtil();
      
      //변수 받아서 처리하기
      int current_page = Integer.parseInt( request.getParameter("current_page") );
      int member_idx = Integer.parseInt( request.getParameter("member_idx") );
      
      
      MemberBackendDAO MD = new MemberBackendDAO();
      MD.MemberDelete(member_idx);
      
      mv.addObject("current_page", current_page);
      
      return mv;
      
   }
   
   /**
    * 비밀번호 수정 페이지
    * @param request
    * @param response
    * @return
    */
   @RequestMapping(value = "/member_backend_pwd_default", method = {RequestMethod.GET, RequestMethod.POST})
   public ModelAndView PwdBackendPwdDefault(HttpServletRequest request, HttpServletResponse response) {
      ModelAndView mv = new ModelAndView("/backend/MemberManager/member_backend_pwd_default");
      return mv;
      
   }
   
   /**
    * 비밀번호 수정 완료
    * @param request
    * @param response
    * @throws Exception
    */
   @RequestMapping(value = "/member_backend_pwd_ok", method = {RequestMethod.GET, RequestMethod.POST})
   public void PwdBackendModifyOk(HttpServletRequest request, HttpServletResponse response) throws Exception {
      
	   int current_page = Integer.parseInt(request.getParameter("current_page"));
	   
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
         
         MemberBackendDAO memberDAO = new MemberBackendDAO();
         
         int nflag = memberDAO.MemberPwdCheck(member_idx, member_pwd0);
         
         if(nflag==1) {
            
            memberDAO.PwdUpdate(member_idx, member_pwd1);
            
            out.println("<script>");
            out.println("alert('회원님의 비밀번호가 수정되었습니다. ');");
            out.println("location.href='/member_backend_view_default.do?member_idx="+member_idx+"&current_page"+current_page+"'");
            out.println("</script>");

         }else {
            
            out.println("<script>");
            out.println("alert('회원님의 이전 비밀번호가 틀립니다. ');");
            out.println("location.href='/member_backend_pwd_default.do?member_idx="+member_idx+"&current_page"+current_page+"'");
            out.println("</script>");

         }


      } catch (Exception e) {
         e.printStackTrace();
      }
      
   }
   
/**
 * 관리자 정보 수정   
 */
/***************************************************************************************************************/   
   /**
    * 관리자 정보 보기
    * @param request
    * @param response
    * @return
    */
   @RequestMapping(value = "/admin_backend_view_default", method = {RequestMethod.GET, RequestMethod.POST})
   public ModelAndView AdminBackendViewDefault(HttpServletRequest request, HttpServletResponse response) {
      ModelAndView mv = new ModelAndView("/backend/MemberManager/admin_backend_view_default");
      return mv;
      
   }
   
   
   /**
    * 관리자 정보 수정 페이지
    * @param request
    * @param response
    * @return
    */
   @RequestMapping(value = "/admin_backend_modify_default", method = {RequestMethod.GET, RequestMethod.POST})
   public ModelAndView AdminBackendModifyDefault(HttpServletRequest request, HttpServletResponse response) {
      ModelAndView mv = new ModelAndView("/backend/MemberManager/admin_backend_modify_default");
      return mv;
      
   }

   /**
    * 관리자 정보 수정 완료
    * @param request
    * @param response
    * @return
    * @throws Exception
    */
   @RequestMapping(value = "/admin_backend_modify_ok", method = {RequestMethod.GET, RequestMethod.POST})
   public ModelAndView AdminBackendModifyOk(HttpServletRequest request, HttpServletResponse response) throws Exception {
      ModelAndView mv = new ModelAndView("redirect:admin_backend_view_default");
      
      CommonUtil commonUtil = new CommonUtil();
      
      //변수 받아서 처리하기
      int current_page = Integer.parseInt( request.getParameter("current_page") );
      int member_idx = Integer.parseInt( request.getParameter("member_idx") );
      String member_id = request.getParameter("member_id");
      String member_pwd = request.getParameter("member_pwd");
      member_pwd = commonUtil.getEncrypt(member_pwd);
      
      String member_name = request.getParameter("member_name");
      String member_kind = request.getParameter("member_kind");
      String member_phone = request.getParameter("member_phone");
      String member_email = request.getParameter("member_email");
      String zipcode = request.getParameter("zipcode");
      String member_jaddress = request.getParameter("member_jaddress");
      String member_raddress = request.getParameter("member_raddress");
      String member_address = request.getParameter("member_address");
      
      String member_del_yn = null;
      if(request.getParameter("member_del_yn")!=null) {
         member_del_yn = "Y";
      }else {
         member_del_yn = "N";
      }
      
      HashMap<String, String> params = new HashMap();
      params.put("member_idx", String.valueOf(member_idx));
      params.put("member_id", member_id);
      params.put("member_pwd", member_pwd);
      params.put("member_name", member_name);
      params.put("member_kind", member_kind);
      params.put("member_phone", member_phone);
      params.put("member_email", member_email);
      params.put("zipcode", zipcode);
      params.put("member_jaddress", member_jaddress);
      params.put("member_raddress", member_raddress);
      params.put("member_address", member_address);
      
//      System.out.println(params);
      
      MemberBackendDAO MD = new MemberBackendDAO();
      MD.MemberModify(params);
      
      mv.addObject("current_page", current_page);
      mv.addObject("member_idx", member_idx);
      
      return mv;
      
   }
   
   
}