package com.sbuddy.web.sy;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbuddy.web.util.ResponseUtil;

@Service
public class SyService {
	
	@Autowired
	private SyMapper syMapper;
	
	public Map<String, Object> test(Map<String, Object> param) {
		Map<String, Object> result = new HashMap<>();
		result = syMapper.getMember();
		//
		return ResponseUtil.success(result);
	}
	
	public Map<String, Object> testError(Map<String, Object> param) {
		
		return ResponseUtil.success(param);
	}
}
