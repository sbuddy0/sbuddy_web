package com.sbuddy.web.auth;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbuddy.web.member.MemberMapper;
import com.sbuddy.web.util.ResponseUtil;

@Service
public class AuthService {
	
	@Autowired
	private AuthMapper authMapper;
	
	@Autowired
	private MemberMapper memberMapper;
	
	
	public Map<String, Object> login(Map<String, Object> param) {
		
		
		return ResponseUtil.success();
	}
}
