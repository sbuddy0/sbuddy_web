package com.sbuddy.web.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbuddy.web.filter.JWTService;
import com.sbuddy.web.mail.MailData;
import com.sbuddy.web.mail.MailService;
import com.sbuddy.web.mail.template.JoinAuthTemplate;
import com.sbuddy.web.member.MemberMapper;
import com.sbuddy.web.util.CommonUtil;
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
	
	@Autowired
	private JWTService jwtService;
	
	/**
	 * 로그인
	 * @param param
	 * @return
	 * @throws Exception 
	 */
	public Map<String, Object> login(Map<String, Object> param) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		
		String password = String.valueOf(param.get("password"));
		String hashPassword = SHAUtil.encrypt(password);
		
		param.put("hashPassword", hashPassword);
		Map<String, Object> loginParam = memberMapper.loginMember(param);
		
		if(!CommonUtil.checkIsNull(loginParam, "idx_member")) {
			return ResponseUtil.error(ResponseCode.NOT_EXISTS_MEMBER);
		}
		
		String jwtToken = jwtService.loginToken(loginParam);
		String idx_member = jwtService.getIdxMember(jwtToken);
		
		result.put("token", jwtToken);
		result.put("idx_member", idx_member);
		
		if(loginParam.get("tmp_password_yn").equals("Y")) {
			result.put("tmp_password", true);
		}
		
		return ResponseUtil.success(result);
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
			return ResponseUtil.error(ResponseCode.EMAIL_DUIPLICATE);
		}
		//2. 인증된 이메일인지 확인
		if(authMapper.isEmailAuth(param) != 1) {
			return ResponseUtil.error(ResponseCode.NOT_AUTH_EMAIL);
		}
		
		param.put("id", param.get("email"));
		
		//3. password 암호화
		String password = String.valueOf(param.get("password"));
		String hashPassword = SHAUtil.encrypt(password);
		
		param.put("hashPassword", hashPassword);
		memberMapper.joinMember(param);

		Map<String, Object> result = new HashMap<>();
		result.put("idx_member", param.get("idx"));
		
		String jwtToken = jwtService.loginToken(result);
		result.put("token", jwtToken);
		
		return ResponseUtil.success(result);
	}
	
	/**
	 * 회원가입 시 이메일 인증번호 발송
	 * @param param
	 * @return
	 * @throws Exception 
	 */
	public Map<String, Object> joinEmailSend(Map<String, Object> param) throws Exception {
		
		// ToDo 이메일 형식 검사
		String authNum = CommonUtil.createRandomCode(8);
		String email = String.valueOf(param.get("email"));
		
		// 메일 템플릿 설정
		JoinAuthTemplate template = new JoinAuthTemplate();
		template.setAuthNum(authNum);
		
		// 메일 발송
		MailData mailData = new MailData(email, template);
		mailService.sendMail(mailData);
		
		int result = authMapper.countEmailAuth(param);
		
		param.put("auth_code", authNum);
		
		if(result == 1) {
			authMapper.regenEmailAuth(param);
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
		
		int result = authMapper.authEmail(param);
		
		if(result != 1) { // 인증번호 오류
			return ResponseUtil.error(ResponseCode.INCORRECT_AUTH_NUMBER);
		} else {
			authMapper.completeEmailAuth(param);
		}
		
		return ResponseUtil.success();
	}
}
