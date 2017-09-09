package com.lytips.ITags.dto;

import com.lytips.ITags.entity.Msg;

public class MsgDto extends Msg {
	private String userName;
	private String head;
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
