package com.lytips.ITags.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lytips.ITags.repository.MsgRepo;
import com.lytips.ITags.vo.UserVo;
import com.lytips.base.BaseController;

@Controller
public class NoticeController extends BaseController {
	@Autowired
	private MsgRepo msgRepo;
	
	@RequestMapping("notice")
	public String noticeIndex(Model model, Integer page, HttpServletRequest request) {
		if(page == null) {
			page = 1;
		}
		Integer userId = (Integer) request.getSession().getAttribute("userId");
		UserVo userVo = (UserVo) request.getSession().getAttribute("userInfo");
		userVo.setNoticeCount(0);
		request.getSession().setAttribute("userInfo", userVo);
		msgRepo.updateIsRead(userId);
		model.addAttribute("page", page);
		model.addAttribute("change", "notice.ftl");
		request.getSession().setAttribute("location", "notice");
		return "index";
	}
}
