package com.lytips.ITags.query;

public class StateQuery {
	private Integer userId;
	private Integer msgId;
	private Integer type;
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
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public StateQuery(Integer userId, Integer msgId, Integer type) {
		this.userId = userId;
		this.msgId = msgId;
		this.type = type;
	}
	public StateQuery() {
	}
	
	
}
