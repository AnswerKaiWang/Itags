package com.lytips.ITags.entity;

import java.util.Date;

public class User {
	private Integer id;
	private String userName;
	private String userPwd;
	private Date createTime;
	private Date updateTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", userPwd=" + userPwd + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + "]";
	}
	public User(String userName, String userPwd) {
		this.userName = userName;
		this.userPwd = userPwd;
	}
	public User() {
	}
	
	
	
}
