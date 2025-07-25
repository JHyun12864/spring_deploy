package kr.co.sist.condition;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import kr.co.sist.img.ImgDTO;
import kr.co.sist.obj.ObjDTO;

@SessionAttributes("oDTO")
@Controller
public class ConditionController {
	
	@GetMapping("/condition/condition_view")
	public String condition(Model model) {
		
		model.addAttribute("name","이장훈");
		model.addAttribute("score",100);
		
		ObjDTO oDTO=new ObjDTO();
		oDTO.setName("주현석");
		oDTO.setAge(26);
		oDTO.setRole("ADMIN");//ADMIN, USER, GUEST
		
		model.addAttribute("oDTO",oDTO);
		
		List<String> list=new ArrayList<String>();
		list.add("이장훈");
		list.add("주현석");
		list.add("양준수");
		list.add("이호빈");
		list.add("강태일");
		
		model.addAttribute("listNames",list);
		
		
		List<ImgDTO> list2=new ArrayList<ImgDTO>();
		list2.add(new ImgDTO("default_img.jpg","기본 이미지"));
		list2.add(new ImgDTO("default_img2.png","기본 이미지2"));
		list2.add(new ImgDTO("dice_1.png","주사위 1"));
		list2.add(new ImgDTO("dice_2.png","주사위 2"));
		
		model.addAttribute("listImgs",list2);
		
		return "condition/condition_view";
	}//condition
	
	
	
}//class
