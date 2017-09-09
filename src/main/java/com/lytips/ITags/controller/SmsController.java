package com.lytips.ITags.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.lytips.ITags.constant.ItagsConstant;
import com.lytips.ITags.model.MessageModel;
import com.lytips.ITags.utils.SmsUtils;
import com.lytips.base.BaseController;

@Controller
@RequestMapping("sms")
public class SmsController extends BaseController {
	
	@RequestMapping("getBindVerifyCode")
	@ResponseBody
	public MessageModel getVerifyCode(String phone, HttpServletRequest req) throws ServerException, ClientException {
		Integer verifyCode = SmsUtils.sendVerifyCode(phone, ItagsConstant.SMS_BIND);
		if(null != verifyCode) {
			req.getSession().setAttribute(ItagsConstant.BIND_VERIFY_CODE, verifyCode);
			req.getSession().setMaxInactiveInterval(300000); 
		}
		return new MessageModel();
	}
	
	@RequestMapping("getCancelBindVerifyCode")
	@ResponseBody
	public MessageModel getCancelBindVerifyCode(String phone, HttpServletRequest req) throws ServerException, ClientException {
		Integer verifyCode = SmsUtils.sendVerifyCode(phone, ItagsConstant.SMS_CANCEL_BIND);
		if(null != verifyCode) {
			req.getSession().setAttribute(ItagsConstant.CANCEL_BIND_VERIFY_CODE, verifyCode);
			req.getSession().setMaxInactiveInterval(300000); 
		}
		return new MessageModel();
	}
	
	@RequestMapping("getModifyPwdVerifyCode")
	@ResponseBody
	public MessageModel getModifyPwdVerifyCode(String phone, HttpServletRequest req) throws ServerException, ClientException {
		Integer verifyCode = SmsUtils.sendVerifyCode(phone, ItagsConstant.SMS_MODIFY_PASSWORD);
		if(null != verifyCode) {
			req.getSession().setAttribute(ItagsConstant.MODIFY_PWD_VERIFY_CODE, verifyCode);
			req.getSession().setMaxInactiveInterval(300000); 
		}
		return new MessageModel();
	}
	
}
