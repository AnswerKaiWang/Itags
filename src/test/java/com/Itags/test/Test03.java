package com.Itags.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Test03 {
	public List<String> checkErr(String key) {
		FileReader fileReader = null;
		List<String> list = null;
		try {
			list = new ArrayList<>();
			fileReader = new FileReader(new File("demo.txt"));
			@SuppressWarnings("resource")
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String str = "";
			while((str = bufferedReader.readLine()) != null) {
				try {
					query(str, key);
				} catch (Exception e) {
					list.add(str);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public void query(String sourStr, String tarStr) {
		Integer len = tarStr.length();
		String tempStr = sourStr.substring(0, len);
		if(tarStr.equals(tempStr))
			throw new RuntimeException();
		sourStr = sourStr.substring(1);
		if(sourStr.length() > len)
			query(sourStr, tarStr);
	}
	
	public static void main(String[] args) {
		Test03 test03 = new Test03();
		List<String> list = test03.checkErr("DEBUG");
		if(null != list && list.size() > 0) {
			for(String ex:list) {
				System.out.println(ex);
			}
		}
	}
	
	
	
}
