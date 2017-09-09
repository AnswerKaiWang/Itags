package com.lytips.ITags.utils;

import com.lytips.base.exception.ParamsException;

public class AssertUtil {
	
	public static void isTrue(Boolean flag,String msg){
		// 判断操作是否成功
		if(flag){
			throw new ParamsException(msg);
		}
	}

}
