package kr.co.mtshop.frontend.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import kr.co.mtshop.common.CommonUtil;
import kr.co.mtshop.common.FileUtiles;
import kr.co.mtshop.common.LocalValue;
import kr.co.mtshop.frontend.dao.BoardFrontendDAO;


/**
 * Handles requests for the application home page.
 */
@Controller
public class BoardFrontendController {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardFrontendController.class);
	

	/**
	 * 게시판 리스트 페이지
	 * @param request
	 * @param respone
	 * @return
	 */
	@RequestMapping(value = "/board_default", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView BoardDefault(HttpServletRequest request, HttpServletResponse respone) {
		ModelAndView mv = new ModelAndView("/frontend/Board/board_default");
		return mv;
		
	}
	
	
	/**
	 * 자유 게시판 메인 리스트
	 * @param request
	 * @param respone
	 * @return
	 */
	@RequestMapping(value = "/board_main_list", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView BoardMainList(HttpServletRequest request, HttpServletResponse respone) {
		ModelAndView mv = new ModelAndView("/frontend/Board/board_main_list");
		return mv;
		
	}

	
	/**
	 * 게시판 정보 보기
	 * @param request
	 * @param respone
	 * @return
	 */
	@RequestMapping(value = "/board_view", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView BoardViewDefault(HttpServletRequest request, HttpServletResponse respone) {
		ModelAndView mv = new ModelAndView("/frontend/Board/board_view");
		return mv;
		
	}

	
	/**
	 * 게시글 등록하기
	 * @param request
	 * @param respone
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/board_write", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView BoardWriteDefault(HttpServletRequest request, HttpServletResponse respone) throws Exception {
		ModelAndView mv = new ModelAndView("/frontend/Board/board_write");
		return mv;
		
	}

	/**
	 * 게시글 작성 완료
	 * @param request
	 * @param respone
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/board_write_ok", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView BoardWriteOk(HttpServletRequest request, HttpServletResponse respone) throws Exception {
		ModelAndView mv = new ModelAndView("redirect:board_default");
		
		LocalValue lv = new LocalValue();
		CommonUtil CU = new CommonUtil();
		FileUtiles FU = new FileUtiles();
		
		String board_title =  request.getParameter("board_title");
		
		System.out.println("board_title : "+board_title);
		
		BoardFrontendDAO boardDAO = new BoardFrontendDAO();

		int board_idx = boardDAO.BoardMaxId()+1;
		int member_idx = Integer.parseInt(request.getParameter("member_idx"));
		
		board_title = board_title.replaceAll("'", "''");
		int ref = board_idx;
		int subref = 0;
		int depth = 0;
		int visit = 0;
		String photo = "";
		
		//파일 받기
		/**************************************************************/
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
		MultipartFile photo_temp = (MultipartFile)multipartRequest.getFile("photo");
		
		ServletContext context = request.getSession().getServletContext();
		String uploadPath = context.getRealPath("/")+lv.FILEUPLOAD_ROOT_PATH;
		String strFolder = "board";
		
		if(!photo_temp.getOriginalFilename().isEmpty()) {
			photo = FU.setSingleFileUpload(photo_temp, uploadPath, strFolder);
		}else {
			photo = "";
		}
		/**************************************************************/

		String board_contents = request.getParameter("board_contents");
//		board_contents = board_contents.replace("\r\n", "<br>");
//		board_contents = board_contents.replaceAll(System.getProperty("line.separator"), "<br>");
//		board_contents = board_contents.replaceAll("(\r\n|\r|\n|\n\r)", "<br>");
		
		HashMap<String, String> params = new HashMap<>();
		params.put("board_idx", String.valueOf(board_idx));
		params.put("member_idx", String.valueOf(member_idx));
		params.put("board_title", board_title);
		params.put("ref", String.valueOf(ref));
		params.put("subref", String.valueOf(subref));
		params.put("depth", String.valueOf(depth));
		params.put("visit", String.valueOf(visit));
		params.put("board_contents", board_contents);
		params.put("photo", photo);
		
		System.out.println(params);
		
		boardDAO.BoardInsert(params);
		
		return mv;
		
	}


	/**
	 * 게시글 수정하기
	 * @param request
	 * @param respone
	 * @return
	 */
	@RequestMapping(value = "/board_modify", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView BoardModifyDefault(HttpServletRequest request, HttpServletResponse respone) {
		ModelAndView mv = new ModelAndView("/frontend/Board/board_modify");
		return mv;
		
	}

	/**
	 * 게시글 수정 완료
	 * @param request
	 * @param respone
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/board_modify_ok", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView BoardModifyOk(HttpServletRequest request, HttpServletResponse respone) throws Exception {
		ModelAndView mv = new ModelAndView("redirect:board_view");
		
		LocalValue lv = new LocalValue();
		CommonUtil CU = new CommonUtil();
		FileUtiles FU = new FileUtiles();
		
		String board_title =  request.getParameter("board_title");
		
		BoardFrontendDAO boardDAO = new BoardFrontendDAO();

		int current_page = Integer.parseInt(request.getParameter("current_page"));
		int board_idx = Integer.parseInt(request.getParameter("board_idx"));
		int member_idx = Integer.parseInt(request.getParameter("member_idx"));
		
		board_title = board_title.replaceAll("'", "''");
		String photo = "";
		
		LinkedHashMap board_info = new LinkedHashMap();
		board_info = boardDAO.BoardInfo(board_idx);
		
		
		//파일 받기
		/**************************************************************/
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
		MultipartFile photo_temp = (MultipartFile)multipartRequest.getFile("photo");
		
		ServletContext context = request.getSession().getServletContext();
		String uploadPath = context.getRealPath("/")+lv.FILEUPLOAD_ROOT_PATH;
		String strFolder = "board";
		
		if(!photo_temp.getOriginalFilename().isEmpty()) {
			
			//포토 이미지 삭제
			FU.delete(uploadPath+"/"+ (String)board_info.get("photo") );
			//새로운 이미지를 저장
			photo = FU.setSingleFileUpload(photo_temp, uploadPath, strFolder);
			
		}else {
			photo = (String)board_info.get("photo");
		}
		/**************************************************************/

		String board_contents = request.getParameter("board_contents");
//		board_contents = board_contents.replace("\r\n", "<br>");
//		board_contents = board_contents.replaceAll(System.getProperty("line.separator"), "<br>");
//		board_contents = board_contents.replaceAll("(\r\n|\r|\n|\n\r)", "<br>");
		
		HashMap<String, String> params = new HashMap<>();
		params.put("board_idx", String.valueOf(board_idx));
		params.put("board_title", board_title);
		params.put("board_contents", board_contents);
		params.put("photo", photo);
		
		boardDAO.BoardModify(params);
		
		mv.addObject("current_page", current_page);
		mv.addObject("board_idx", board_idx);
		return mv;
		
	}

	
	/**
	 * 게시글 삭제하기
	 * @param request
	 * @param respone
	 * @return
	 */
	@RequestMapping(value = "/board_delete_ok", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView BoardDeleteOk(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:board_default");
		
		// html을 만들기 위해
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();

		// 세션을 만들기 위해
		HttpSession session = request.getSession();

		int current_page = Integer.parseInt(request.getParameter("current_page"));
		int board_idx = Integer.parseInt(request.getParameter("board_idx"));
		
		LocalValue lv = new LocalValue();
		FileUtiles FU = new FileUtiles();
		BoardFrontendDAO boardDAO = new BoardFrontendDAO();

		LinkedHashMap board_info = new LinkedHashMap();
		board_info = boardDAO.BoardInfo(board_idx);
		
		int member_idx = (Integer)board_info.get("member_idx");
		int member_idx_temp = (Integer)session.getAttribute("member_idx");
		
		if(member_idx!=member_idx_temp) {
			System.out.println("본인이 작성한 게시글이 아닙니다.");
			out.println("<script>");
			out.println("alert('본인이 작성한 글만 삭제 가능합니다.');");
			out.println("location.href='/board_default.do?current_page"+current_page+"'");
			out.println("</script>");
		}
		
		//파일 받기
		/**************************************************************/
		ServletContext context = request.getSession().getServletContext();
		String uploadPath = context.getRealPath("/")+lv.FILEUPLOAD_ROOT_PATH;

		//포토 이미지 삭제
		FU.delete(uploadPath+"/"+ (String)board_info.get("photo") );
		boardDAO.BoardDelete(board_idx);
		
		/**************************************************************/
		
		
		mv.addObject("current_page", current_page);
		return mv;
		
	}
	
	/**
	 * 답글 달기
	 * @param request
	 * @param respone
	 * @return
	 */
	@RequestMapping(value = "/board_reply", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView BoardReplyDefault(HttpServletRequest request, HttpServletResponse respone) {
		ModelAndView mv = new ModelAndView("/frontend/Board/board_reply");
		return mv;
		
	}
	
	/**
	 * 답글 작성 완료
	 * @param request
	 * @param respone
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/board_reply_ok", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView BoardReplyOk(HttpServletRequest request, HttpServletResponse respone) throws Exception {
		ModelAndView mv = new ModelAndView("redirect:board_default");

		BoardFrontendDAO boardDAO = new BoardFrontendDAO();

		// 변수 받아서 처리하기
		int pre_board_idx = Integer.parseInt(request.getParameter("board_idx"));
		int board_idx = boardDAO.BoardMaxId() + 1;
		int member_idx = Integer.parseInt(request.getParameter("member_idx"));

		int ref = Integer.parseInt(request.getParameter("ref"));
		int subref = Integer.parseInt(request.getParameter("subref"));
		int depth = Integer.parseInt(request.getParameter("depth"));

		int visit = 0;

		String board_title = request.getParameter("board_title");
		board_title = board_title.replaceAll("'", "''");

		String board_contents = request.getParameter("board_contents");
//	      board_contents = board_contents.replace("\r\n", "<br>");
//	      board_contents = board_contents.replaceAll(System.getProperty("line.separator"), "<br>");
//	      board_contents = board_contents.replaceAll("(\r\n|\r|\n|\n\r)", "<br>");

		String photo = "";
		
		boardDAO.BoardDepthUpdate(ref, depth);
		
		subref += 1;
		depth += 1 ;
		
		HashMap<String, String> params = new HashMap<>();
		params.put("board_idx", String.valueOf(board_idx));
		params.put("member_idx", String.valueOf(member_idx));
		params.put("ref", String.valueOf(ref));
		params.put("subref", String.valueOf(subref));
		params.put("depth", String.valueOf(depth));
		params.put("visit", String.valueOf(visit));
		params.put("board_title", board_title);
		params.put("board_contents", board_contents);
		params.put("photo", photo);

		
		boardDAO.BoardInsert(params);

		return mv;

	}

}
