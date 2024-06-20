package com.sbuddy.web.auth;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbuddy.web.mail.MailService;
import com.sbuddy.web.member.MemberMapper;
import com.sbuddy.web.util.ResponseUtil;
import com.sbuddy.web.util.SHAUtil;
import com.sbuddy.web.vo.ResponseCode;

@Service
public class AuthService {
	
	@Autowired
	private AuthMapper authMapper;
	
	@Autowired
	private MemberMapper memberMapper;
		
	@Autowired
	private MailService mailService;
	
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
	 * @throws Exception 
	 */
	public Map<String, Object> join(Map<String, Object> param) throws Exception {
		
		//1. 이메일 중복확인
		if(memberMapper.duplicateMember(param) == 1) {
			return ResponseUtil.error(ResponseCode.FAIL);
		}
		param.put("id", param.get("email"));
		
		//2. password 암호화
		String password = String.valueOf(param.get("password"));
		String encryptPassword = SHAUtil.encrypt(password);
		
		param.put("encryptPassword", encryptPassword);
		memberMapper.joinMember(param);
		
		return ResponseUtil.success();
	}
	
	/**
	 * 회원가입 시 이메일 인증번호 발송
	 * @param param
	 * @return
	 * @throws Exception 
	 */
	public Map<String, Object> joinEmailSend(Map<String, Object> param) throws Exception {
		
		// ToDo 이메일 형식 검사
		String num = mailService.sendMail(String.valueOf(param.get("email")));
		
		param.put("auth_code", num);
		authMapper.insertEmailAuth(param);
		
		int result = authMapper.countEmailAuth(param);
		if(result == 1) {
			authMapper.updateEmailAuth(param);
		} else if(result == 0) {
			authMapper.insertEmailAuth(param);
		}
		
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
