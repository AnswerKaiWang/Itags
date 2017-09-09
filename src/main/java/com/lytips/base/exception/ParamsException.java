package com.lytips.base.exception;

public class ParamsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4633548724576997976L;
	
	private String errMsg="操作失败!";
	
	private Integer errCode=300;
	
	
	
	

	public ParamsException(String errMsg, Integer errCode) {
		super(errMsg);
		this.errCode = errCode;
	}





	public ParamsException(String errMsg) {
		super(errMsg);
	}





	public String getErrMsg() {
		return errMsg;
	}





	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}





	public Integer getErrCode() {
		return errCode;
	}





	public void setErrCode(Integer errCode) {
		this.errCode = errCode;
	}
	
	
	
	

}
