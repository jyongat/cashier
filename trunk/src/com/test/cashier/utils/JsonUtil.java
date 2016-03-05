package com.test.cashier.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonUtil {
	
	public static Object[] parseJsonArray(String strJson) {
		JSONArray jsonArray = JSONArray.fromObject(strJson);
		
		return jsonArray.toArray();
	}
	
	public static JSONObject parseJsonObject(String strJson) {
		return null;
	}
}
