package com.lytips.ITags.constant;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 常量类
 * @author kiTe
 * 2017年7月3日
 * 
 */
public class ItagsConstant {
	
	public static final Integer OPTIONS_SUCCESS_CODE = 1;   //操作成功状态码
	public static final String OPTIONS_SUCCESS_MSG = "操作成功！"; //操作成功信息
	public static final Integer OPTIONS_FAILURE_CODE = 0;  //操作失败状态码
	public static final String LOGIN_FIRST = "未登录";
	
	public static final String SMS_BIND = "SMS_80355001";
	public static final String SMS_MODIFY_PASSWORD = "SMS_80175073";
	public static final String SMS_REGISTER = "SMS_80230064";
	public static final String SMS_CANCEL_BIND = "SMS_80970033";
	
	public static final String BIND_VERIFY_CODE = "BIND_VERIFY_CODE";
	public static final String CANCEL_BIND_VERIFY_CODE = "CANCEL_BIND_VERIFY_CODE";
	public static final String MODIFY_PWD_VERIFY_CODE = "MODIFY_PWD_VERIFY_CODE";
	
	public static final long DAY_TIME = 24 * 60 * 60 * 1000;
	public static final long HOUR_TIME = 60 * 60 * 1000;
	public static final long MINUTE_TIME = 60 * 1000;
	
	public static final String NEWS_INDEX = new SimpleDateFormat("M-dd").format(new Date());
	
}
