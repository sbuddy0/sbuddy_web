package com.sbuddy.web.auth;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sbuddy.web.util.CommonUtil;

@RestController
@RequestMapping("/api/v1")
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
	/**
	 * 로그인
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/login")
	public Map<String, Object> login(@RequestBody Map<String, Object> param) throws Exception {
		// id, pw 전처리
		
		CommonUtil.checkIsNullException(param, "id");
		CommonUtil.checkIsNullException(param, "password");
		
		return authService.login(param);
	}
	
	/**
	 * 회원가입
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/join")
	public Map<String, Object> join(@RequestBody Map<String, Object> param) throws Exception {
		
		CommonUtil.checkIsNull(param, "username");
		CommonUtil.checkIsNull(param, "email");
		CommonUtil.checkIsNull(param, "password");
		
		return authService.join(param);
	}
	
	/**
	 * 회원가입 시 이메일 인증번호 발송
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/join/email/send")
	public Map<String, Object> joinEmailSend(@RequestBody Map<String, Object> param) throws Exception {
		
		CommonUtil.checkIsNull(param, "email");
		
		return authService.joinEmailSend(param);
	}
	
	/**
	 * 회원가입 시 이메일 인증 확인
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/join/email/auth")
	public Map<String, Object> joinEmailAuth(@RequestBody Map<String, Object> param) throws Exception {
		
		CommonUtil.checkIsNull(param, "email");
		CommonUtil.checkIsNull(param, "auth_code");
		
		return authService.joinEmailAuth(param);
	}
	
	// 회원가입 시 키워드 선택 (최소 3개 이상)
	@PostMapping("/join/keyword/insert")
	public Map<String, Object> joinKeywordInsert(@RequestBody Map<String, Object> param) {
		
		
		return authService.joinKeywordInsert(param);
	}
}
