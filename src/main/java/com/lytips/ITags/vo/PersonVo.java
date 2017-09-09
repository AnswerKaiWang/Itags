package com.lytips.ITags.vo;

public class PersonVo {
	private Integer personUserId;
	private Integer dynamicPage = 1;
	private String type = "follow";
	private Integer followPage = 1;
	
	
	public Integer getDynamicPage() {
		return dynamicPage;
	}
	public void setDynamicPage(Integer dynamicPage) {
		this.dynamicPage = dynamicPage;
	}
	public Integer getFollowPage() {
		return followPage;
	}
	public void setFollowPage(Integer followPage) {
		this.followPage = followPage;
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
	
	
}
