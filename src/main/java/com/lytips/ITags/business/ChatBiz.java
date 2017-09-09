package com.lytips.ITags.business;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lytips.ITags.entity.Chat;
import com.lytips.ITags.entity.UserInfo;
import com.lytips.ITags.model.MessageModel;
import com.lytips.ITags.query.ChatQuery;
import com.lytips.ITags.repository.ChatRepo;
import com.lytips.ITags.repository.UserRepo;

@Service
public class ChatBiz {
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private ChatRepo chatRepo;
	
	public List<UserInfo> queryLinkManByParams(Integer userId, String userName) {
		List<UserInfo> list = null;
		if(StringUtils.isNoneEmpty(userName)) {
			list = userRepo.queryByUserName(userName);
		} else {
			list = chatRepo.queryLinkMan(userId);
		}
		return list;
	}
	
	public PageInfo<Chat> queryChatByParams(ChatQuery chatQuery) {
		PageHelper.startPage(chatQuery.getPage(), chatQuery.getRows());
		List<Chat> list = chatRepo.queryByParams(chatQuery);
		PageInfo<Chat> pageInfo = new PageInfo<>(list);
		chatRepo.setIsRead(chatQuery.getUserId(), chatQuery.getPersonUserId());
		return pageInfo;
	}
	
	public List<Chat> queryChatByParams2(ChatQuery chatQuery) {
		return chatRepo.queryByParams2(chatQuery);
	}

	public MessageModel addChatMsg(Chat chat) {
		chatRepo.insertChat(chat);
		return new MessageModel();
	}
}
