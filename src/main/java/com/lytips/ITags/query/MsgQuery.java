package com.lytips.ITags.query;

import com.lytips.base.BaseQuery;

public class MsgQuery extends BaseQuery{
	private Integer id;
	private Integer userId;
	private String type;
	private Integer personUserId;
	
	
	
	public MsgQuery() {
	}
	public MsgQuery(Integer id, Integer userId, String type, Integer personUserId) {
		this.id = id;
		this.userId = userId;
		this.type = type;
		this.personUserId = personUserId;
	}
	public Integer getPersonUserId() {
		return personUserId;
	}
	public void setPersonUserId(Integer personUserId) {
		this.personUserId = personUserId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	
	
	
}
