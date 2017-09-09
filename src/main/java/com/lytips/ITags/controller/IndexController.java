package com.lytips.ITags.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.lytips.base.BaseController;

@Controller
public class IndexController extends BaseController {
	
	@RequestMapping("index")
	public String index(Model model, Integer page, HttpServletRequest request) {
		if(null == page) {
			page = 1;
		}
		model.addAttribute("page", page);
		model.addAttribute("change", "dynamics.ftl");
		request.getSession().setAttribute("location", "dynamic");
		return "index";
	}
	
	@RequestMapping("login")
	public String login() {
		return "login";
	}
	
	
	
}
