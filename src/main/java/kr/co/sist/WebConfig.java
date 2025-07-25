package kr.co.sist;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration 
//설정파일이니까 @Configuration 이걸 입력
public class WebConfig implements WebMvcConfigurer {
	
	@Value("${upload.saveDir}")
	private String uploadDir;

	/**
	 * ResourceHandlerRegistry 클래스로 정적 리소스(파일, 이미지)를<br>
	 * jar 파일 외부에 저장하고,<br>
	 * jar 파일 내부로 경로를 전달하는 일을 한다.<br>
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/images/**")//URL의 경로, /images/ 하위는 다 실행됨
		.addResourceLocations("file:"+uploadDir+"/");//HDD 경로
	}//addResourceHandlers
	
	
	
}//class
