package com.lytips.ITags.business;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lytips.ITags.dto.MsgDto;
import com.lytips.ITags.entity.ExtraMsg;
import com.lytips.ITags.entity.Msg;
import com.lytips.ITags.entity.MsgCondition;
import com.lytips.ITags.entity.MsgIndex;
import com.lytips.ITags.entity.MsgRelation;
import com.lytips.ITags.entity.UserCondition;
import com.lytips.ITags.entity.UserInfo;
import com.lytips.ITags.model.MessageModel;
import com.lytips.ITags.query.ExtraMsgQuery;
import com.lytips.ITags.query.MsgQuery;
import com.lytips.ITags.repository.MsgRepo;
import com.lytips.ITags.repository.UserRepo;

@Service
public class MsgBiz {
	
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private MsgRepo msgRepo;
	
	/**
	 * @param msg
	 * @return
	 * 前台传过来的content包括正文和图片链接，通过正则把图片标签挑选出来拼接后放入imgs中
	 * 方便后续查看消息时图片布局
	 * user_relation 0 关注 1粉丝
	 */
	public MessageModel addMsg(MsgDto msg) {
		List<Integer> ids = userRepo.queryFollowIds(msg.getUserId());
		String content = msg.getContent();
		String regex = "<img\\ssrc=\"http://itags-pic.\\S+\\s\\S+\\s\\S+/>";
		Pattern pattern = Pattern.compile(regex);
		Matcher m = pattern.matcher(content); 
		if(msg.getImgs() == null) {
			String imgs = "";
			while(m.find()){  
				imgs += m.group() + ",";
			}  
			if(imgs != null && imgs != "") {
				content = content.replaceAll(regex, "");
			}
			msg.setImgs(imgs);
		}
		msg.setContent(content);
		msgRepo.insertMsg(msg);
		MsgIndex msgIndex = new MsgIndex(msg.getUserId(), msg.getUserId(), msg.getId(), msg.getCreateTime());
		msgRepo.insertMsgIndex(msgIndex);
		if(msg.getVisibility().equals("1")) {
			if(ids != null && ids.size() > 0) {
				for(Integer temp: ids) {
					msgIndex.setUserId(temp);
					msgRepo.insertMsgIndex(msgIndex);
				}
			}
		}
		UserCondition userCondition = new UserCondition(msg.getUserId(), null, null, true);
		userRepo.updateUserCondition(userCondition);
		return new MessageModel();
	}
	
	
	//feed拉消息
	public PageInfo<MsgIndex> queryMsgByParams(MsgQuery msgQuery) {
		PageHelper.startPage(msgQuery.getPage(), msgQuery.getRows());
		List<MsgIndex> list = msgRepo.queryMsgIndexsByUserId(msgQuery);
		PageInfo<MsgIndex> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
	
	public PageInfo<Msg> personMsgList(MsgQuery msgQuery) {
		PageHelper.startPage(msgQuery.getPage(), msgQuery.getRows());
		List<Msg> list = msgRepo.queryPersonMsg(msgQuery);
		PageInfo<Msg> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
	
	
	
	//添加补充信息
	public MessageModel addExtraMsg(MsgRelation msgRelation, String content) {
		MessageModel messageModel = new MessageModel();
		Integer type = msgRelation.getType();
		MsgCondition condition = new MsgCondition();
		condition.setMsgId(msgRelation.getMsgId());
		if(type == 2 || type == 3 || type == 5) {
			switch (type) {
			case 2:
				condition.setUpTransferCount(true);
				msgRepo.updateMsgCondition(condition);  //更新对应消息收藏 点赞等数量
				break;
			case 3:
				condition.setUpCommentCount(true);
				msgRepo.updateMsgCondition(condition);  //更新对应消息收藏 点赞等数量
				break;
			}
			
			ExtraMsg extraMsg = new ExtraMsg(msgRelation.getMsgId(), content);
			msgRepo.insertExtraMsg(extraMsg);  //添加补充信息
			msgRelation.setReferenceMsgId(extraMsg.getId());
			msgRepo.insertMsgRelation(msgRelation);  //添加补充信息索引
			messageModel.setResult(extraMsg);
		} else {
			switch (type) {
			case 1:
				condition.setUpStoreCount(true);
				break;
			case 4:
				condition.setUpGoodCount(true);
				break;
			}
			msgRepo.updateMsgCondition(condition);
			msgRepo.insertMsgRelation(msgRelation);
			messageModel.setResult(msgRelation);
		}
		return messageModel;
	}
	
	//查询消息评论
	public PageInfo<MsgRelation> queryExtraMsg(ExtraMsgQuery query) {
		List<MsgRelation> list = msgRepo.queryMsgRelationByMsgId(query.getMsgId(), 3);
		if(null != list && list.size() > 0) {
			for(int i = 0; i < list.size(); i++) {
				List<MsgRelation> replyMsg = msgRepo.queryMsgRelationByMsgId(list.get(i).getReferenceMsgId(), 5);
				list.get(i).setReplyMsg(replyMsg);
			}
		}
		PageHelper.startPage(query.getPage(), query.getRows());
		PageInfo<MsgRelation> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}


	public MessageModel updateMsgState(MsgRelation msgRelation) {
		MsgCondition msgCondition = new MsgCondition();
		msgCondition.setMsgId(msgRelation.getMsgId());
		if(msgRelation.getType() == 4) {
			msgCondition.setUpGoodCount(false);
		}
		if(msgRelation.getType() == 1) {
			msgCondition.setUpStoreCount(false);
		}
		msgRepo.updateMsgCondition(msgCondition);
		msgRepo.setMsgUnabled(msgRelation.getId());
		return new MessageModel();
	}


	public MessageModel saveTransferMsg(MsgRelation msgRelation, String content) {
		Msg msg = msgRepo.queryById(msgRelation.getMsgId());
		UserInfo userInfo = userRepo.queryFullUserInfo(msgRelation.getUserId());
		MsgDto msg2 = new MsgDto();
		String str = "<p>" + content +  " // <a href='/ITags/person/" + userInfo.getUserId() + "'>@" 
				+ userInfo.getUserName() + "</a>：" + msg.getContent().trim().substring(3);
		msg2.setContent(str);
		if(msg.getImgs() != null && msg.getImgs() != "") {
			msg2.setImgs(msg.getImgs());
		}
		msg2.setUserId(msgRelation.getReferenceUserId());
		MsgCondition msgCondition = new MsgCondition();
		msgCondition.setMsgId(msgRelation.getMsgId());
		msgCondition.setUpTransferCount(true);
		addMsg(msg2);
		msgRepo.updateMsgCondition(msgCondition);
		msgRepo.insertMsgRelation(msgRelation);
		return new MessageModel();
	}


	public MessageModel hideDynamic(Integer id) {
		msgRepo.hideDynamic(id);
		return new MessageModel();
	}
	
	

}
