package com.lytips.ITags.query;

import java.util.Date;

import com.lytips.base.BaseQuery;

public class ChatQuery extends BaseQuery{
	private Integer userId;
	private Integer personUserId;
	private Date linkDate;
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
	public Date getLinkDate() {
		return linkDate;
	}
	public void setLinkDate(Date linkDate) {
		this.linkDate = linkDate;
	}
	
	
}
