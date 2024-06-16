package com.sbuddy.web.auth;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbuddy.web.member.MemberMapper;
import com.sbuddy.web.util.ResponseUtil;
import com.sbuddy.web.vo.ResponseCode;

@Service
public class AuthService {
	
	@Autowired
	private AuthMapper authMapper;
	
	@Autowired
	private MemberMapper memberMapper;
	
	/**
	 * 로그인
	 * @param param
	 * @return
	 */
	public Map<String, Object> login(Map<String, Object> param) {
		
		
		return ResponseUtil.success(param);
	}
	
	/**
	 * 회원가입
	 * @param param
	 * @return
	 */
	public Map<String, Object> join(Map<String, Object> param) {
		
		
		return ResponseUtil.success();
	}
	
	/**
	 * 회원가입 시 이메일 인증번호 발송
	 * @param param
	 * @return
	 */
	public Map<String, Object> joinEmailSend(Map<String, Object> param) {
		
		return ResponseUtil.success();
	}
	
	/**
	 * 회원가입 시 이메일 인증 확인
	 * @param param
	 * @return
	 */
	public Map<String, Object> joinEmailAuth(Map<String, Object> param) {
		
		int result = authMapper.countEmailAuth(param);
		if(result != 1) { // 인증번호 오류
			ResponseUtil.error(ResponseCode.FAIL);
		}
		
		return ResponseUtil.success();
	}
}
