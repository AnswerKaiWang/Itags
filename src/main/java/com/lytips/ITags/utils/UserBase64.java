package com.lytips.ITags.utils;

import org.apache.commons.lang3.StringUtils;
import com.sun.org.apache.xml.internal.security.utils.Base64;

@SuppressWarnings("restriction")
public class UserBase64 {


	public static String decode(String encodedUserID)  {
		if (StringUtils.isBlank(encodedUserID)) {
			return null;
		}
		try {
			String reversedString = new StringBuffer(encodedUserID).reverse().toString();
			String base64String = reversedString.replaceAll("#", "=");
			int userIDPos = base64String.indexOf("==") + 6;
			String realBase64UserID = base64String.substring(userIDPos);
			return new String(Base64.decode(realBase64UserID.getBytes()));
		} catch (Exception e) {
			return null;
		}
	}

	public static String encode(String userID){
		String base64UserIDEncoded = Base64.encode((userID + "").getBytes());
		String currentStringBase64Encoded = Base64.encode((System.currentTimeMillis() + "").getBytes());
		String keyString = currentStringBase64Encoded  
				+ currentStringBase64Encoded.substring(4, 8) + base64UserIDEncoded;
		byte[] codeBytes = keyString.getBytes();
		byte[] ordedBytes = new byte[codeBytes.length];
		for(int i=0; i<codeBytes.length; i++){
			ordedBytes[i] = codeBytes[codeBytes.length-i-1];
		}
		return new String(ordedBytes).replaceAll("=", "#");
	}
}