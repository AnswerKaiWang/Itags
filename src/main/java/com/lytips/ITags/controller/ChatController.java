package com.lytips.ITags.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lytips.ITags.business.ChatBiz;
import com.lytips.ITags.entity.Chat;
import com.lytips.ITags.model.MessageModel;
import com.lytips.ITags.query.ChatQuery;
import com.lytips.ITags.repository.UserRepo;
import com.lytips.base.BaseController;

@Controller
public class ChatController extends BaseController {
	@Autowired
	private ChatBiz chatBiz;
	@Autowired
	private UserRepo userRepo;
	
	@RequestMapping("chat")
	public String chatIndex(ChatQuery chatQuery, Model model) {
		model.addAttribute("chatPageInfo", chatBiz.queryChatByParams(chatQuery));
		
		model.addAttribute("chatUser", userRepo.queryUserInfoById(chatQuery.getPersonUserId()));
		return "chat";
	}
	
	@RequestMapping("linkMan")
	public String linkManIndex(Model modal, Integer userId, String userName, HttpServletRequest request) {
		modal.addAttribute("linkMans", chatBiz.queryLinkManByParams(userId, userName));
		request.getSession().setAttribute("linkDate", new Date());
		return "linkMan";
	}
	
	@RequestMapping("chatMsg")
	@ResponseBody
	public List<Chat> chatMsg(ChatQuery chatQuery, Model model, HttpServletRequest request) throws InterruptedException {
		chatQuery.setLinkDate((Date) request.getSession().getAttribute("linkDate"));
		List<Chat> list = null;
		while(true) {
			list = chatBiz.queryChatByParams2(chatQuery);
			if(list != null && list.size() > 0) {
				request.getSession().setAttribute("linkDate", new Date());
				break; 
			} else {
				Thread.sleep(10000);
			}
		}
		return list;
		
	}
	
	@RequestMapping("addChatMsg")
	@ResponseBody
	public MessageModel addChatMsg(Chat chat){
		return chatBiz.addChatMsg(chat);
	}
	
	
	
}
