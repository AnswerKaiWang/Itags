package com.lytips.ITags.vo;

public class UserVo {
	private Integer userId;
	private String userName;
	private String head;
	private Integer noticeCount;
	private String personBac;
	
	
	public String getPersonBac() {
		return personBac;
	}
	public void setPersonBac(String personBac) {
		this.personBac = personBac;
	}
	public Integer getNoticeCount() {
		return noticeCount;
	}
	public void setNoticeCount(Integer noticeCount) {
		this.noticeCount = noticeCount;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	
	
}
