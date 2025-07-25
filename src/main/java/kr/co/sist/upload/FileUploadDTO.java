package kr.co.sist.upload;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FileUploadDTO {
	private String uploader;
	private String[] targetAge;
	private String fileName;
}//class
