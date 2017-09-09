package com.Itags.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author kiTe
 * 2017年6月29日
 * 
 */
public class Test01 {
	
	Logger logger = LoggerFactory.getLogger(Test01.class);
	/**
	 * 查询字符出现次数
	 * @param key
	 */
	public void checkDoc(char key) {
		InputStream inputStream;
		Integer count = 0;
		try {
			inputStream = new FileInputStream(new File("demo.txt"));
			byte[] car = new byte[1024];
			Integer len = 0;
			while((len = inputStream.read(car)) != -1) {
				String str = new String(car, 0, len);
				char[] chars = str.toCharArray();
				if(null != chars && chars.length > 0) {
					for(int i = 0; i < chars.length; i++) {
						if(key == chars[i])
							count++;
					}
				}
			}
		} catch (Exception e) {}
		logger.info("{}出现次数：{}", key, count);
	}
	
	
	/**
	 * 查询文档中单词出现次数
	 * @param str
	 */
	public void queryStr(String str) {
		InputStream inputStream;
		Integer count = 0;
		try {
			inputStream = new FileInputStream(new File("demo.txt"));
			byte[] car = new byte[1024];
			Integer len = 0;
			while((len = inputStream.read(car)) != -1) {
				String string = new String(car, 0, len);
				String[] strs = string.split(" ");
				if(null != strs && strs.length > 0) {
					for(String ex:strs) {
						if(ex.equals(str))
							count++;
					}
				}
			}
		} catch (Exception e) {}
		logger.info("{} 出现次数：{}", str, count);
	}
	
	public void queryStr2(String str) {
		InputStream inputStream;
		Integer count = 0;
		try {
			inputStream = new FileInputStream(new File("demo.txt"));
			byte[] car = new byte[1024];
			Integer len = 0;
			while((len = inputStream.read(car)) != -1) {
				String string = new String(car, 0, len);
				count += query(string, str);
			}
		} catch (Exception e) {}
		logger.info("{} 出现次数：{}", str, count);
	}
	
	
	/**
	 * 针对无法根据特定符号分隔时，采用递归查询
	 * @param sourStr
	 * @param tarStr
	 * @return
	 */
	public Integer query(String sourStr, String tarStr) {
		Integer count = 0;
		Integer len = tarStr.length();
		String tempStr = sourStr.substring(0, len);
		if(tarStr.equals(tempStr))
			count = 1;
		sourStr = sourStr.substring(1);
		if(sourStr.length() > len)
			count += query(sourStr, tarStr);
		return count;
	}
	
	
	public static void main(String[] args) {
		Test01 test01 = new Test01();
		test01.queryStr("DEBUG");
	}
	
	
	
	
}
