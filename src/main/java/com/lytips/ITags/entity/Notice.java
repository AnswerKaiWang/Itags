package com.lytips.ITags.entity;

import java.util.Date;

public class Notice {
	private Integer id;
	private Integer userId;
	private Integer msgRelationId;
	private Integer type;
	private Date createTime = new Date();
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
	public Integer getMsgRelationId() {
		return msgRelationId;
	}
	public void setMsgRelationId(Integer msgRelationId) {
		this.msgRelationId = msgRelationId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
}
