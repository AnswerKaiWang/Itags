package com.lytips.ITags.entity;

import java.util.Date;
import java.util.List;

public class MsgRelation {
	private Integer id;
	private Integer userId;  //发布动态者
	private Integer msgId;	//动态ID
	private Integer referenceUserId;	//	引用者
	private Integer referenceMsgId;  //引用消息id
	private Integer type; // 1收藏 2转发 3评论 4点赞 5消息回复
	private Integer state = 1;
	private Date createTime = new Date();
	private Date updateTime = new Date();
	private ExtraMsg extraMsg;
	private UserInfo user;
	private UserInfo referenceUser;
	private Integer isRead = 0;
	
	
	
	
	public Integer getIsRead() {
		return isRead;
	}
	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}
	public UserInfo getUser() {
		return user;
	}
	public void setUser(UserInfo user) {
		this.user = user;
	}
	public UserInfo getReferenceUser() {
		return referenceUser;
	}
	public void setReferenceUser(UserInfo referenceUser) {
		this.referenceUser = referenceUser;
	}
	private List<MsgRelation> replyMsg;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getMsgId() {
		return msgId;
	}
	public void setMsgId(Integer msgId) {
		this.msgId = msgId;
	}
	public Integer getReferenceUserId() {
		return referenceUserId;
	}
	public void setReferenceUserId(Integer referenceUserId) {
		this.referenceUserId = referenceUserId;
	}
	public Integer getReferenceMsgId() {
		return referenceMsgId;
	}
	public void setReferenceMsgId(Integer referenceMsgId) {
		this.referenceMsgId = referenceMsgId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public ExtraMsg getExtraMsg() {
		return extraMsg;
	}
	public void setExtraMsg(ExtraMsg extraMsg) {
		this.extraMsg = extraMsg;
	}
	public List<MsgRelation> getReplyMsg() {
		return replyMsg;
	}
	public void setReplyMsg(List<MsgRelation> replyMsg) {
		this.replyMsg = replyMsg;
	}
	
	
	
	
}
