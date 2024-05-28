package com.sbuddy.web.sy;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.sbuddy.web.util.ResponseUtil;

@Service
public class SyService {
	
	public Map<String, Object> test(Map<String, Object> param) {
		Map<String, Object> result = new HashMap<>();
		//
		return ResponseUtil.success(param);
	}
}
