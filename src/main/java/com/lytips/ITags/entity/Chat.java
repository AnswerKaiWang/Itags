package com.lytips.ITags.entity;

import java.util.Date;

public class Chat {
	private Integer id;
	private Integer userId;
	private Integer personUserId;
	private String content;
	private Date createTime = new Date();
	private Integer type = 0;
	
	
	
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
	public Integer getPersonUserId() {
		return personUserId;
	}
	public void setPersonUserId(Integer personUserId) {
		this.personUserId = personUserId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
