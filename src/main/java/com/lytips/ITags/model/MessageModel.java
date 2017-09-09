package com.lytips.ITags.model;

import com.lytips.ITags.constant.ItagsConstant;

/**
 * 返回消息模型bean
 * @author kiTe
 * 2017年7月3日
 * 
 */
public class MessageModel {
	
	private Integer resultCode = ItagsConstant.OPTIONS_SUCCESS_CODE; //默认为成功状态
	
	private String msg = ItagsConstant.OPTIONS_SUCCESS_MSG;  //默认为成功状态
	
	private Object result; //返回到前台bean
	
	
	

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public Integer getResultCode() {
		return resultCode;
	}

	public void setResultCode(Integer resultCode) {
		this.resultCode = resultCode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
	
	

}
