package com.test.cashier.utils;

public class StringUtil {
	
	public static final String CHARACTER_CRLF = "\r\n";
	public static final String CHARACTER_DOT = ".";
	
	public static boolean isEmpty(String str) {
		if (null == str || "".equals(str)) {
			return true;
		}
		
		return false;
	}
	
	public static String round2(float value) {
		return String.format("%.2f", value);
	}
}
