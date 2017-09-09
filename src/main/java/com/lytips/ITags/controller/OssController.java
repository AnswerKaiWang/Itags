package com.lytips.ITags.controller;

import java.io.File;
import java.io.FileNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectResult;
import com.lytips.ITags.model.MessageModel;

@Controller
@RequestMapping("oss")
public class OssController {
	
	@RequestMapping("send")
	@ResponseBody
	public MessageModel send() throws FileNotFoundException {
		// endpoint以杭州为例，其它region请按实际情况填写
		String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
		// 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建
		String accessKeyId = "LTAIPcixGGi1j5b6";
		String accessKeySecret = "qA8Ts4mZwbUTY4DXTNq40lptzowPzm";
		// 创建OSSClient实例
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		PutObjectResult putObject = ossClient.putObject("itags-pic", "test2", new File("002.jpg"));
		System.err.println(putObject.getClientCRC());
		ossClient.shutdown();
		return new MessageModel();
	}
}
