package com.sbuddy.web.util;

import java.util.HashMap;
import java.util.Map;

import com.sbuddy.web.vo.ResponseCode;

public class ResponseUtil {
	/**
	 * 통신 성공 시 응답(데이터 x)
	 * @return response map
	 */
	public static Map<String, Object> success() {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", ResponseCode.OK);
		result.put("message", ResponseCode.OK);
		
		return result;
	}
	
	/**
	 * 통신 성공 시 응답(데이터 o)
	 * @return response map
	 */
	public static Map<String, Object> success(Map<String, Object> data) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", ResponseCode.OK);
		result.put("message", ResponseCode.OK);
		result.put("data", data);
		
		return result;
	}
	
	/**
	 * 통신 에러 시 응답(데이터 x)
	 * @return response map
	 */
	public static Map<String, Object> error(ResponseCode responseCode) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", responseCode);
		result.put("message", responseCode);
		
		return result;
	}
	
	/**
	 * 통신 에러 시 응답(데이터 o)
	 * @return response map
	 */
	public static Map<String, Object> error(ResponseCode responseCode, Map<String, Object> data) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", responseCode);
		result.put("message", responseCode);
		result.put("data", data);
		
		return result;
	}
}
