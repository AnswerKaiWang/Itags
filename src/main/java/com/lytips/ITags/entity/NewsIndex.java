package com.lytips.ITags.entity;

public class NewsIndex {
	private Integer id;
	private String title;
	private Integer type;
	private String createTime;
	private Integer state;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public NewsIndex(String title, Integer type, String createTime, Integer state) {
		super();
		this.title = title;
		this.type = type;
		this.createTime = createTime;
		this.state = state;
	}
	public NewsIndex() {
	}
	
	
}
