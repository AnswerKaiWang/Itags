package com.lytips.ITags.entity;

public class ExtraMsg {
	private Integer id;
	private Integer msgId;
	private String content;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getMsgId() {
		return msgId;
	}
	public void setMsgId(Integer msgId) {
		this.msgId = msgId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public ExtraMsg(Integer userId, String content) {
		this.msgId = userId;
		this.content = content;
	}
	public ExtraMsg() {
	}
	
	
}
