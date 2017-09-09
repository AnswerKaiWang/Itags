package com.lytips.ITags.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import com.aliyun.oss.OSSClient;

public class OssUtils {
	final static String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
	final static String accessKeyId = "LTAIPcixGGi1j5b6";
	final static String accessKeySecret = "qA8Ts4mZwbUTY4DXTNq40lptzowPzm";
	
	public static String upLoadInputStream(String key, InputStream is) {
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		ossClient.putObject("itags-pic", key, is);
		Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
		    // 生成URL
		URL url = ossClient.generatePresignedUrl("itags-pic", key, expiration);
		ossClient.shutdown();
		return url.toString();
	}
	
	public static String upLoadByte(String key, byte[] b) {
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		ossClient.putObject("itags-pic", key, new ByteArrayInputStream(b));
		Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
		    // 生成URL
		URL url = ossClient.generatePresignedUrl("itags-pic", key, expiration);
		ossClient.shutdown();
		return url.toString();
	}
	
	
}
