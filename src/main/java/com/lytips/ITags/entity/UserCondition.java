package com.lytips.ITags.entity;

public class UserCondition {
	private Integer userId;
	private Boolean upFollowCount;
	private Boolean upFollowedCount;
	private Boolean upDynamicCount;
	
	
	
	public UserCondition(Integer userId, Boolean upFollowCount, Boolean upFollowedCount, Boolean upDynamicCount) {
		this.userId = userId;
		this.upFollowCount = upFollowCount;
		this.upFollowedCount = upFollowedCount;
		this.upDynamicCount = upDynamicCount;
	}
	public UserCondition() {
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Boolean getUpFollowCount() {
		return upFollowCount;
	}
	public void setUpFollowCount(Boolean upFollowCount) {
		this.upFollowCount = upFollowCount;
	}
	public Boolean getUpFollowedCount() {
		return upFollowedCount;
	}
	public void setUpFollowedCount(Boolean upFollowedCount) {
		this.upFollowedCount = upFollowedCount;
	}
	public Boolean getUpDynamicCount() {
		return upDynamicCount;
	}
	public void setUpDynamicCount(Boolean upDynamicCount) {
		this.upDynamicCount = upDynamicCount;
	}
	
	
}
