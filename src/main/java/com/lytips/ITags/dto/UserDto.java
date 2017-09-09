package com.lytips.ITags.dto;

import com.lytips.ITags.entity.User;

public class UserDto extends User {
	private String confirmPassword;
	private String checkMark;

	public String getCheckMark() {
		return checkMark;
	}
	public void setCheckMark(String checkMark) {
		this.checkMark = checkMark;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	@Override
	public String toString() {
		return "UserDto [confirmPassword=" + confirmPassword + ", checkMark=" + checkMark + "]";
	}
	
	
	
}
