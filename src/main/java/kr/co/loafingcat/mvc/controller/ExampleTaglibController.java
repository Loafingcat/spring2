package kr.co.loafingcat.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.loafingcat.mvc.domain.MenuType;
import kr.co.loafingcat.mvc.parameter.BoardSearchParameter;

@Controller
@RequestMapping("/example/taglib")
public class ExampleTaglibController {
	
	@RequestMapping("/search")
	public void search(BoardSearchParameter parameter, Model model) {
		model.addAttribute("boardTypes", MenuType.values());
		model.addAttribute("parameter", parameter);
	}
}
