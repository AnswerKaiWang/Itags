package com.lytips.ITags.query;

public class FollowQuery {
	private Integer userId;
	private Integer sex;
	private String type;
	private String ageStr;
	
	public String getAgeStr() {
		return ageStr;
	}
	public void setAgeStr(String ageStr) {
		this.ageStr = ageStr;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public FollowQuery(Integer userId, Integer sex, String type) {
		this.userId = userId;
		this.sex = sex;
		this.type = type;
	}
	public FollowQuery() {
	}
	public FollowQuery(Integer userId, String type) {
		this.userId = userId;
		this.type = type;
	}
	
}
