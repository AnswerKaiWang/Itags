package com.lytips.ITags.utils;

import java.util.List;

public class ITagsUtils {
	
	
	
	public static Boolean isNotEmptyList(@SuppressWarnings("rawtypes") List list) {
		if(null != list && list.size() > 0)
			return true;
		return false;
	} 
}
