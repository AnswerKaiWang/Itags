package com.lytips.ITags.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lytips.base.BaseController;

@Controller
@RequestMapping("dynamics")
public class DynamicsController extends BaseController {
	
	@RequestMapping("index")
	public String index() {
		return "dynamics";
	}
}
