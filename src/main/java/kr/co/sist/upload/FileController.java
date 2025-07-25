package kr.co.sist.upload;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FileController {
//	private String saveDir="C:/Users/user/git/spring_mvc5/spring_mvc3/src/main/java/static/upload";

	@Value("${upload.saveDir}")
	private String saveDir;
	


	@PostMapping("/upload_process")
	public String uploadProcess(
			@RequestParam("upfile") MultipartFile mf,
			FileUploadDTO fuDTO, Model model
			)throws Exception {

		
		//이미지만 업로드 (설정은 10MByte까지 But, 업로드 가능 5MByte)
		if(mf.getContentType().startsWith("image")){
			int maxSize=1024*1024*5;//5MB
			
			if(mf.getSize() > maxSize) {
				throw new Exception("업로드 파일의 크기는 최대 5MByte까지만 가능합니다.");//강제 exception 발생
			}//end if
			
			System.out.println("saveDir-----"+saveDir);
			
			//중복 파일명에 처리 (동일 파일명이 존재한다면, 파일명_숫자.확장자를 붙여 새로 생성)
			//1.업로드된 파일명 받기
			String originalFilename=mf.getOriginalFilename();
			//디렉토리가 존재하지 않으면 디렉토리를 생성
			File dir=new File(saveDir);
			if(!dir.exists()) {
				dir.mkdirs();
			}//end if
			
			//2.파일명으로 파일을 생성
			File uploadFile=new File(saveDir+File.separator+originalFilename);
			
			//3.확장자를 기준으로 파일명을 나눈다.
			String fileName="";
			String fileExt="";
			
			
			int fileSeperator=originalFilename.lastIndexOf(".");
			if(fileSeperator == -1) {
				//확장자 없을 때.
				fileSeperator = originalFilename.length();
			}//end if
			
			
			fileName=originalFilename.substring(0, fileSeperator);
			if(originalFilename.contains(".")) {
				//확장자 있을 때
				fileExt=originalFilename.substring(originalFilename.lastIndexOf(".")+1);
			}//end if
			
			
			//4.같은 이름의 파일이 존재하면 파일명과 . 사이에 _1(카운트)을 붙여준다.
			int cnt=1;
			StringBuilder newFileName=new StringBuilder();
			while(uploadFile.exists()) {//같은 이름의 파일이 존재하면
				newFileName.append(fileName).append("_").append(cnt).append(".").append(fileExt);
				uploadFile=new File(saveDir+File.separator+newFileName.toString());
				cnt++;
				
				newFileName.delete(0, newFileName.length());
			}//end while
			
			
			
			//File saveFile=new File(saveDir + File.separator + mf.getOriginalFilename());
			
			//5.업로드 수행
			mf.transferTo(uploadFile);//중복 파일을 처리하지 않는다. (덮어쓴다.)
			
			fuDTO.setFileName(uploadFile.getName());
			model.addAttribute("uploadFlag",true);
			model.addAttribute("fileSize", mf.getSize());
			model.addAttribute("fileData",fuDTO);
			
		}//end if
		
		System.out.println("fuDTO-------------"+fuDTO);
		
		return "upload_result";
	}//uploadProcess
	
	
	
	
	
	@ExceptionHandler(Exception.class)
	//spring.servlet.multipart.max-file-size=10MB 설정된 파일크기보다 업로드된 파일의 
	//크기가 크다면 해당 예외는 처리할 수 없다.
	public ModelAndView uploadErr(Exception e) {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/err/upload_err.html");
		
		e.printStackTrace();
		return mav;
	}//uploadErr
	
	
}//class
