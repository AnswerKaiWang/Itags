package com.lytips.ITags.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.lytips.ITags.business.MsgBiz;
import com.lytips.ITags.dto.MsgDto;
import com.lytips.ITags.entity.MsgRelation;
import com.lytips.ITags.model.MessageModel;
import com.lytips.ITags.query.ExtraMsgQuery;
import com.lytips.ITags.repository.MsgRepo;
import com.lytips.base.BaseController;

@RequestMapping("msg")
@Controller
public class MsgController extends BaseController {
	@Autowired
	private MsgBiz msgBiz;
	@Autowired
	private MsgRepo msgRepo;
	
	@ResponseBody
	@RequestMapping("addMsg")
	public MessageModel addMsg(MsgDto msg) {
		return msgBiz.addMsg(msg);
	}
	
	@ResponseBody
	@RequestMapping("addComment")
	public MessageModel addExtraMsg(MsgRelation msgRelation, String content) {
		return msgBiz.addExtraMsg(msgRelation, content);
	}
	
	@ResponseBody
	@RequestMapping("queryExtraMsg")
	public PageInfo<MsgRelation> queryExtraMsg(ExtraMsgQuery extraMsgQuery, Model model) {
		return msgBiz.queryExtraMsg(extraMsgQuery);
	}
	
	@ResponseBody
	@RequestMapping("updateMsgState")
	public MessageModel updateMsgState(MsgRelation msgRelation) {
		return msgBiz.updateMsgState(msgRelation);
	}
	
	@ResponseBody
	@RequestMapping("transformMsg")
	public MessageModel transformMsg(MsgRelation msgRelation, String content) {
		return msgBiz.saveTransferMsg(msgRelation, content);
	}
	
	@RequestMapping("queryNoticeCount")
	@ResponseBody
	public Integer queryNoticeCount(Integer userId) {
		return msgRepo.queryNoticeCount(userId);
	}
	
	@ResponseBody
	@RequestMapping("hideDynamic")
	public MessageModel hideDynamic(Integer id) {
		return msgBiz.hideDynamic(id);
	}
	
}
