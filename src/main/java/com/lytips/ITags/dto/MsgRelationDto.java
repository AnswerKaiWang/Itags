package com.lytips.ITags.dto;

import java.util.List;

import com.lytips.ITags.entity.MsgRelation;

public class MsgRelationDto extends MsgRelation {
	private String content;
	private List<MsgRelationDto> list;
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List<MsgRelationDto> getList() {
		return list;
	}
	public void setList(List<MsgRelationDto> list) {
		this.list = list;
	}
	
	
}
