package com.lytips.ITags.entity;

public class Remark {
	private Integer id;
	private Integer userId;
	private Integer personUserId;
	private String remark;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
