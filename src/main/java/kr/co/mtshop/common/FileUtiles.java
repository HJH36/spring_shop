/**
 * 프로젝트명 : 과외마스터 홈페이지 재구축 및 운영사업 공통 사용 method 담당하는 클래스 이다. 파일명 : CommonUtil.java 작성일 : 2005/11/09 작성자 : 소 속 : 엔원(주)
 * 수정내역 :
 */

package kr.co.mtshop.common;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;

import org.jcodec.api.FrameGrab;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.AWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class FileUtiles {
	/*
	 * 클래스 생성자
	 */
	public FileUtiles() {
	};

	@Autowired
	ServletContext context;
	

	/**
	 * 아파치 Common-io 이용하여 Single 파일 업로드
	 * 헤더가 없을때
	 * @param filename
	 * @param imagepath
	 * @return
	 * @throws Exception
	 */
	public String setSingleFileUpload(MultipartFile filename, String uploadPath, String strFoler) throws Exception{
		
		LocalValue lv = new LocalValue();

		String oldfilename = null;
		String newfilename = null;
		String filetype = null; /* 파일 확장자 */
		
		String fileHeader = "EM";
		
		String now = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String strYear = new SimpleDateFormat("yyyy").format(new Date());
		
		Calendar c = Calendar.getInstance();
		strYear = String.valueOf( (c.get(Calendar.YEAR)) );
		
		/* 저장 폴더 구하기 */
		String uploadFoler = strFoler+"/"+strYear;
		
		
		//최종 위치
		String final_path = uploadPath+"/"+uploadFoler;
		
		//현재 디렉토리가 없으면 새로 생성한다.
		if(!isExists(final_path)){
			createDirectory(final_path);
		}

		CommonsMultipartFile cmf =(CommonsMultipartFile) filename; 
		
		String path = final_path+"/"+cmf.getOriginalFilename();
		System.out.println(path);
		File file = new File(path);
		
		//파일의 새로운 이름 만들기
		int i=0;
		i=cmf.getOriginalFilename().lastIndexOf(".");
		String newpath = final_path+"/"+fileHeader+now+cmf.getOriginalFilename().substring(i, cmf.getOriginalFilename().length());
		
		
		//파일 저장하기
		cmf.transferTo(file);
		
		//파일 이름 변경하기
		File newfile = new File(newpath);
		file.renameTo(newfile);
		
		oldfilename = file.getName();
		
		/* 파일 확장자 구하기 */
		filetype = oldfilename.substring(oldfilename.lastIndexOf(".")+1,oldfilename.length());
		
		newfilename = newfile.getName();
		
		return uploadFoler+"/"+newfilename;
		
	}

	
	/**
	 * 아파치 Common-io 이용하여 Single 파일 업로드
	 * 헤더가 있을때
	 * @param filename
	 * @param imagepath
	 * @return
	 * @throws Exception
	 */
	public String setSingleFileUpload(MultipartFile filename, String uploadPath, String strFoler, String fileHeader) throws Exception{
		
		LocalValue lv = new LocalValue();

		String oldfilename = null;
		String newfilename = null;
		String filetype = null; /* 파일 확장자 */
		
		/* 파일 헤더 */
		if(fileHeader.equals("") && fileHeader==null) {
			fileHeader = "AD";
		}
		
		String now = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String strYear = new SimpleDateFormat("yyyy").format(new Date());
		
		Calendar c = Calendar.getInstance();
		strYear = String.valueOf( (c.get(Calendar.YEAR)) );

		String uploadFoler = strFoler+"/"+strYear;
		
		
		//최종 위치
		String final_path = uploadPath+"/"+uploadFoler;
		
		//현재 디렉토리가 없으면 새로 생성한다.
		if(!isExists(final_path)){
			createDirectory(final_path);
		}

		CommonsMultipartFile cmf =(CommonsMultipartFile) filename; 
		
		String path = final_path+"/"+cmf.getOriginalFilename();
		System.out.println(path);
		File file = new File(path);
		
		//파일의 새로운 이름 만들기
		int i=0;
		i=cmf.getOriginalFilename().lastIndexOf(".");
		String newpath = final_path+"/"+fileHeader+now+cmf.getOriginalFilename().substring(i, cmf.getOriginalFilename().length());
		
		
		//파일 저장하기
		cmf.transferTo(file);
		
		//파일 이름 변경하기
		File newfile = new File(newpath);
		file.renameTo(newfile);
		
		oldfilename = file.getName();
		
		/* 파일 확장자 구하기 */
		filetype = oldfilename.substring(oldfilename.lastIndexOf(".")+1,oldfilename.length());
		
		newfilename = newfile.getName();
		return uploadFoler+"/"+newfilename;
		
	}
	
	
	/**
	 * 아파치 Common-io 이용하여 Single 파일 업로드
	 * 헤더가 없을때/년도 없이 하나의 폴더에 통으로 저장
	 * @param filename
	 * @param uploadPath
	 * @param strFoler
	 * @return
	 * @throws Exception
	 */
	public String setSingleFileUpload2(MultipartFile filename, String uploadPath, String strFoler, String fileHeader) throws Exception{
		
		LocalValue lv = new LocalValue();

		String oldfilename = null;
		String newfilename = null;
		String filetype = null; /* 파일 확장자 */
		
		/* 파일 헤더 */
		if(fileHeader.equals("") && fileHeader==null) {
			fileHeader = "AD";
		}

		String now = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String strYear = new SimpleDateFormat("yyyy").format(new Date());
		
		Calendar c = Calendar.getInstance();
		strYear = String.valueOf( (c.get(Calendar.YEAR)) );

//		String uploadFoler = strFoler+"/"+strYear;
		String uploadFoler = strFoler;
		
		
		//최종 위치
		String final_path = uploadPath+"/"+uploadFoler;
		
		//현재 디렉토리가 없으면 새로 생성한다.
		if(!isExists(final_path)){
			createDirectory(final_path);
		}

		CommonsMultipartFile cmf =(CommonsMultipartFile) filename; 
		
		String path = final_path+"/"+cmf.getOriginalFilename();
		System.out.println(path);
		File file = new File(path);
		
		//파일의 새로운 이름 만들기
		int i=0;
		i=cmf.getOriginalFilename().lastIndexOf(".");
		String newpath = final_path+"/"+fileHeader+now+cmf.getOriginalFilename().substring(i, cmf.getOriginalFilename().length());
		
		
		//파일 저장하기
		cmf.transferTo(file);
		
		//파일 이름 변경하기
		File newfile = new File(newpath);
		file.renameTo(newfile);
		
		oldfilename = file.getName();
		
		/* 파일 확장자 구하기 */
		filetype = oldfilename.substring(oldfilename.lastIndexOf(".")+1,oldfilename.length());

		newfilename = newfile.getName();
		
		return uploadFoler+"/"+newfilename;
		
	}

	
	/**
	 * 동영상 올리때 사용하는 파일 업로드 함수
	 * 동영상의 썸네일을 만들어 줌
	 * @param filename
	 * @param uploadPath
	 * @param strFoler
	 * @param fileHeader
	 * @return
	 * @throws Exception
	 */
	public String[] setSingleVideoUpload(MultipartFile filename, String uploadPath, String strFoler, String fileHeader) throws Exception{
		
		LocalValue lv = new LocalValue();

		String oldfilename = null;
		String newfilename = null;
		String filetype = null; /* 파일 확장자 */
		
		/* 파일 헤더 */
		if(fileHeader.equals("") && fileHeader==null) {
			fileHeader = "AD";
		}
		
		String now = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String strYear = new SimpleDateFormat("yyyy").format(new Date());
		
		Calendar c = Calendar.getInstance();
		strYear = String.valueOf( (c.get(Calendar.YEAR)) );

		String uploadFoler = strFoler+"/"+strYear;
		
		
		//최종 위치
		String final_path = uploadPath+"/"+uploadFoler;
		
		//현재 디렉토리가 없으면 새로 생성한다.
		if(!isExists(final_path)){
			createDirectory(final_path);
		}

		CommonsMultipartFile cmf =(CommonsMultipartFile) filename; 
		
		String path = final_path+"/"+cmf.getOriginalFilename();
		System.out.println("1 : "+path);
		File file = new File(path);
		
		//파일의 새로운 이름 만들기
		int i=0;
		i=cmf.getOriginalFilename().lastIndexOf(".");
		String newpath = final_path+"/"+fileHeader+now+cmf.getOriginalFilename().substring(i, cmf.getOriginalFilename().length());
		
		
		//파일 저장하기
		cmf.transferTo(file);
		
		//파일 이름 변경하기
		File newfile = new File(newpath);
		file.renameTo(newfile);
		
		oldfilename = file.getName();
		
		/* 파일 확장자 구하기 */
		filetype = oldfilename.substring(oldfilename.lastIndexOf(".")+1,oldfilename.length());
		
		newfilename = newfile.getName();
		

		System.out.println("path : "+path);
		System.out.println("newpath : "+newpath);
		System.out.println("oldfilename : "+oldfilename);
		System.out.println("newfilename : "+newfilename);
		
		
		String[] strResult = new String[2];
		strResult[0] = uploadFoler+"/"+newfilename;
		
		
		String thumbnail_name="";
		
	    try {
	        double startSec = 0;
	        int frameCount = 10;
	        System.out.println("start : "+newfile.getAbsolutePath());

	        FrameGrab grab = FrameGrab.createFrameGrab(NIOUtils.readableChannel(file));
	        grab.seekToSecondPrecise(startSec);

	        for (int j = 0; i < frameCount; j++) {
	            Picture picture = grab.getNativeFrame();
	            System.out.println(picture.getWidth() + "x" + picture.getHeight() + " " + picture.getColor());

	            BufferedImage bufferedImage = AWTUtil.toBufferedImage(picture);
	            ImageIO.write(bufferedImage, "png", new File(newfile.getAbsolutePath() + "-frame" + j + ".png"));
	            thumbnail_name = newfile.getAbsolutePath() + "-frame" + j + ".png";
	            strResult[1] = thumbnail_name;
	        }
	    } catch (Exception e) {
	    	System.out.println(e.getMessage());
	    }
		
		
		
//		int frameNumber = 42;
//		Picture picture = FrameGrab.getFrameFromFile(newfile, frameNumber);
//		//for JDK (jcodec-javase)
//		BufferedImage bufferedImage = AWTUtil.toBufferedImage(picture);
//		ImageIO.write(bufferedImage, "png", new File("frame42.png"));		
		
//		Log.debug("extracting thumbnail from video");
//		int frameNumber = 0;
//		
//		Picture picture = FrameGrab.getFrameFromFile(newfile, frameNumber);
//		BufferedImage bufferedImage = AWTUtil.toBufferedImage(picture);
//		String thumbnail_name = final_path+"/"+fileHeader+now+".png";
//		ImageIO.write(bufferedImage, "png", new File(thumbnail_name));
//		
//		System.out.println("thumbnail_name : "+thumbnail_name);

//		FrameGrab grab;
//		grab = FrameGrab.createFrameGrab(NIOUtils.readableChannel(newfile));
//		grab.seekToSecondPrecise(0.5);
//		Picture picture = grab.getNativeFrame();
//		BufferedImage bufferedImage = AWTUtil.toBufferedImage(picture);
//		
//		String thumbnail_name = final_path+"/"+fileHeader+now+".png";
//		ImageIO.write(bufferedImage, "png", new File(thumbnail_name));
		
		
		return strResult;
		
	}


	public void generate(File file) {
	    try {
	        double startSec = 0;
	        int frameCount = 10;
	        System.out.println("start : "+file.getAbsolutePath());

	        FrameGrab grab = FrameGrab.createFrameGrab(NIOUtils.readableChannel(file));
	        grab.seekToSecondPrecise(startSec);

	        for (int i = 0; i < frameCount; i++) {
	            Picture picture = grab.getNativeFrame();
	            System.out.println(picture.getWidth() + "x" + picture.getHeight() + " " + picture.getColor());

	            BufferedImage bufferedImage = AWTUtil.toBufferedImage(picture);
	            ImageIO.write(bufferedImage, "png", new File(file.getAbsolutePath() + "-frame" + i + ".png"));
	        }
	    } catch (Exception e) {
//	        log.error(e.getMessage());
	    	System.out.println(e.getMessage());
	    }
	}	
	
	
	/**
	 * 썸네일을 추출하는 메소드
	 * @param source
	 * @throws Excption
	 */
	public String getImageFromFrame(File videoFile) {

		
		return null;

	}




	/**
	 * 경로 가져오기(문자열 처리)
	 * @param strFullPath
	 * @return
	 */
	public static String getPath(String strFullPath){
		if(strFullPath == null || strFullPath.length()<1){
			return strFullPath;
		}
		
		int nPosLast = strFullPath.lastIndexOf("/");
		
		if(nPosLast == -1&strFullPath.indexOf(".")==-1){
			return strFullPath;
		}
		
		return strFullPath.substring(0,  nPosLast);
		
	}
	
	
	/**
	 * 디렉토리 생성하기
	 * @param strDirectoryPath
	 * 			생성할 디렉토리명
	 * @return
	 * 			성공:true, 실패:false
	 */
	public static boolean createDirectory(String strDirectoryPath){
		
		boolean retValue = false;
		
		retValue = new File(strDirectoryPath).mkdirs();
		
		return retValue;
	}
	
	
	/**
	 * 디렉토리/파일 존재 여부를 확인한다.
	 * @param strFileName
	 * @return
	 */
	public static boolean isExists(String strFileName){
		
		File objFile = new File(strFileName);
		return objFile.exists();
		
	}
	
	
	/**
	 * 파일 또는 디렉토리 삭제하기
	 * @param strFileName
	 * @return
	 */
	public static boolean delete(String strFileName){
		File objDeleteFile = new File(strFileName);
		return objDeleteFile.delete();
	}
	
	
	/**
	 * 파일 또는 디렉토리 삭제하기
	 * @param strFileName
	 * 			삭제할 파일 또는 디렉토리
	 * @param bChild
	 * 			하위 디렉토리 및 파일 삭제 여부
	 * @return
	 */
	public static boolean delete(String strFileName, boolean bChild){
		
		File objFile = new File(strFileName);
		
		try{
			
			if(!objFile.exists()){
				return false;
			}
			
			if(objFile.delete()){
				return true;
			}else if(objFile.isDirectory()){
				
				if(!bChild){
					return false;
				}
				
				if( !strFileName.substring(strFileName.length()-1).equals("/") ){
					strFileName = strFileName+"/";
				}
				boolean bDelete = true;
				
				
				
				/***************************************************************
				* 하위 폴더는 존재하지 않는다는 가정하에 디렉토리를 삭제한다. 
				***************************************************************/
				String[] arsFileList = objFile.list();
				
				for(int i=0;i<arsFileList.length;i++){
					System.out.println(strFileName+arsFileList[i]);
					if(!delete(strFileName + arsFileList[i])){
						if(bDelete){
							bDelete = false;
						}
					}
				}
				
				if(bDelete){
					bDelete = objFile.delete();
				}
				
				return bDelete;
				
			}else{
				return false;
			}
			
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
	}
	
	
//	/**
//	 * 파일명 또는 디렉토리명 변경하기
//	 * @param strSrc
//	 * @param strDest
//	 * @return
//	 */
//	public static boolean rename(String strSrc, String strDest){
//		
//		boolean retValue = false;
//		
//		File objSrcFile = new File( toEncoding(strSrc));
//		File objDestFile = new File( toEncoding(strDest));
//		
//		try{
//			retValue = objSrcFile.renameTo(objDestFile);
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		
//		return retValue;
//		
//	}



	/**
	 * 상품 이미지업로드시 이미지 이외 파일 제외
	 * @param filename
	 * @param imagepath
	 * @return
	 * @throws Exception
	 */
	public String ImageFileUpload(List filename, String uploadPath) throws Exception{
		
		String oldfilename = null;
		String newfilename = null;
		String filetype=null;
		String now = new SimpleDateFormat("yyyyMMddHmsS").format(new Date());
		
		LocalValue lv = new LocalValue();
		
		
		CommonsMultipartFile cmf =(CommonsMultipartFile) filename.get(0); 
		
		String path = uploadPath+"/"+cmf.getOriginalFilename();
		File file = new File(path);
		oldfilename = file.getName();
		filetype = oldfilename.substring(oldfilename.lastIndexOf(".")+1,oldfilename.length()); 		

		int i=0;
		i=cmf.getOriginalFilename().lastIndexOf(".");
		String newpath = uploadPath+"/"+now+cmf.getOriginalFilename().substring(i, cmf.getOriginalFilename().length());
		
		//이미지 파일 확인하고 저장하기
		
		if(filetype.equals("jpg") || filetype.equals("gif") || filetype.equals("png") || filetype.equals("bmp")){
			cmf.transferTo(file);
			File newfile = new File(newpath);
			file.renameTo(newfile);
			newfilename = newfile.getName();
		}else{
			newfilename = "";
		}
		
		return newfilename;
		
	}
	
	
}
