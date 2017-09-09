package com.lytips.ITags.entity;

import java.util.Date;

public class MsgIndex {
	private Integer id;
	private Integer userId;
	private Integer authorId;
	private Integer msgId;
	private Msg msg;
	private Date publishTime;
	private UserInfo author;
	private Integer state = 1;
	
	

	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public UserInfo getAuthor() {
		return author;
	}
	public void setAuthor(UserInfo author) {
		this.author = author;
	}
	public Msg getMsg() {
		return msg;
	}
	public void setMsg(Msg msg) {
		this.msg = msg;
	}
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
	public Integer getAuthorId() {
		return authorId;
	}
	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}
	public Integer getMsgId() {
		return msgId;
	}
	public void setMsgId(Integer msgId) {
		this.msgId = msgId;
	}
	public Date getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	
	public MsgIndex() {
	}
	public MsgIndex(Integer userId, Integer authorId, Integer msgId, Date publishTime) {
		this.userId = userId;
		this.authorId = authorId;
		this.msgId = msgId;
		this.publishTime = publishTime;
	}
	
	
	
}
