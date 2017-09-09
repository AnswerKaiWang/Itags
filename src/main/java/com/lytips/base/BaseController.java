package com.lytips.base;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

public class BaseController {
	
	/**
	 * 再指定方法执行前 先执行preMethod  前提：页面控制器继承BaseController
	 * @param request
	 * @param model
	 */
	@ModelAttribute
	public void preMethod(HttpServletRequest request,Model model){
		model.addAttribute("ctx", request.getContextPath());//获取上下文路径并放入request域中
	}

}
