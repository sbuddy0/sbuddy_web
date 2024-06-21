package com.sbuddy.web.util;

import java.util.Map;
import java.util.Random;

public class CommonUtil {
	
	public static void checkIsNull(Map<String, Object> param, String key) throws Exception {
		if(!param.containsKey(key)) {
			throw new RuntimeException();
		}
	}
	
	public static String createRandomCode(int length) {
		Random random = new Random();
		StringBuffer randomCode = new StringBuffer();
		
		for(int i=0; i<length; i++) {
			if(random.nextBoolean()) {
				randomCode.append((char)((int)(random.nextInt(10)) + 48));
			} else {
				randomCode.append((char)((int)(random.nextInt(26)) + 65));
			}
		}
		
		return randomCode.toString();
	}
}
