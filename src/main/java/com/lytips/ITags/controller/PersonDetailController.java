package com.lytips.ITags.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lytips.ITags.vo.PersonVo;
import com.lytips.base.BaseController;

@Controller
public class PersonDetailController extends BaseController {
	
	
	@RequestMapping("{personUserId}")
	public String turnToPersonZone(@PathVariable(value = "personUserId") Integer personUserId) {
		return "redirect:person/" + personUserId;
	}
	
	@RequestMapping("person/{personUserId}/{type}")
	public String personIndex(Model model, @PathVariable(value = "personUserId")
	Integer personUserId, HttpServletRequest request, @PathVariable(value = "type")
	String type) {
		PersonVo personVo = new PersonVo();
		personVo.setPersonUserId(personUserId);
		personVo.setType(type);
		request.getSession().setAttribute("personUserId", personUserId);
		model.addAttribute("change", "personZone.ftl");
		model.addAttribute("personVo", personVo);
		request.getSession().setAttribute("location", "personZone");
		return "index";
	}
	
	
	
	@RequestMapping("person/{personUserId}")
	public String personIndex(Model model, @PathVariable(value = "personUserId")
	Integer personUserId, HttpServletRequest request) {
		PersonVo personVo = new PersonVo();
		personVo.setPersonUserId(personUserId);
		request.getSession().setAttribute("personUserId", personUserId);
		model.addAttribute("change", "personZone.ftl");
		model.addAttribute("personVo", personVo);
		request.getSession().setAttribute("location", "personZone");
		return "index";
	}
	
	@RequestMapping("personDetail")
	public String personDetail(Model model, HttpServletRequest request, PersonVo personVo) {
		model.addAttribute("change", "personZone.ftl");
		model.addAttribute("personVo", personVo);
		request.getSession().setAttribute("location", "personZone");
		return "index";
	}
	
	@RequestMapping("findPerson")
	public String queryPerson(PersonVo personVo, String key, Model model) {
		model.addAttribute("change", "person.ftl");
		model.addAttribute("key", key);
		return "index";
	}
	
}
