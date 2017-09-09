package com.lytips.ITags.vo;

public class FollowVo {
	private Integer menCount;
	private Integer womenCount;
	public Integer getMenCount() {
		return menCount;
	}
	public void setMenCount(Integer menCount) {
		this.menCount = menCount;
	}
	public Integer getWomenCount() {
		return womenCount;
	}
	public void setWomenCount(Integer womenCount) {
		this.womenCount = womenCount;
	}
	public FollowVo(Integer menCount, Integer womenCount) {
		this.menCount = menCount;
		this.womenCount = womenCount;
	}
	public FollowVo() {
	}
	
	
}
