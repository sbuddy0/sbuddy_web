package com.sbuddy.web.util;

import java.util.HashMap;
import java.util.Map;

import com.sbuddy.web.vo.ResponseCode;

public class ResponseUtil {
	public static Map<String, Object>success (Map<String, Object> data) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", ResponseCode.OK.getCode());
		result.put("message", ResponseCode.OK.getMessage());
		result.put("data", data);
		
		return result;
	}
}
