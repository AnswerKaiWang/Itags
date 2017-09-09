package com.lytips.ITags.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

public class SmsUtils {
	//初始化ascClient需要的几个参数
	final static String product = "Dysmsapi";
	final static String domain = "dysmsapi.aliyuncs.com";
	//替换成你的AK
	final static String accessKeyId = "LTAIPcixGGi1j5b6";
	final static String accessKeySecret = "qA8Ts4mZwbUTY4DXTNq40lptzowPzm";

	
	public static Integer sendVerifyCode(String phone, String type) throws ServerException, ClientException {
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
				accessKeySecret);
		DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
		IAcsClient acsClient = new DefaultAcsClient(profile);
		SendSmsRequest request = new SendSmsRequest();
		request.setMethod(MethodType.POST);
		request.setPhoneNumbers(phone);
		 request.setSignName("ITags互动空间");
		 request.setTemplateCode(type);
		 int verifyCode = (int)((Math.random() * 9 + 1) * 100000);
		 request.setTemplateParam("{\"number\":\"" + verifyCode + "\"}");
		 request.setOutId("yourOutId");
		SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
		if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
			return verifyCode;
		}
		return null;
	}
}
