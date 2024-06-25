package com.sbuddy.web.member;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbuddy.web.mail.MailService;
import com.sbuddy.web.util.CommonUtil;
import com.sbuddy.web.util.ResponseUtil;
import com.sbuddy.web.util.SHAUtil;
import com.sbuddy.web.vo.ResponseCode;

@Service
public class MemberService {

	@Autowired
	private MemberMapper memberMapper;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private MailService mailService;
	
	/**
	 * 비밀번호 찾기
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> findPassword(Map<String, Object> param) throws Exception {
				
		/**
		 * 1. 아이디 유무 확인
		 * 2. 아이디가 있을 경우 랜덤 문자열 임시 비밀번호 발급, 비밀번호 암호화
		 * 3. DB 비밀번호를 임시 비밀번호로 변경
		 * 4. 메일로 비밀번호 발송
		 * 5. 로그인 시 임시 비밀번호로 로그인
		 */
		
	 	Map<String, Object> data = memberMapper.getMemberInfo(param);
	 	
	 	if(data == null) {
	 		return ResponseUtil.error(ResponseCode.FAIL);
	 	}
	 	
	 	param.putAll(data);
	 	
	 	// 임시 비밀번호 생성 및 암호화
	 	String newPw = commonUtil.makeRandTempPasswd(10);
	 	String encPw = SHAUtil.encrypt(newPw);
	 	param.put("new_password", encPw);
	 	
	 	// 비밀번호 변경
	 	memberMapper.changePassword(param);
		
	 	// 이메일 발송
	 	mailService.sendFindPwMail((String) param.get("email"));
	 	
		return ResponseUtil.success();
	}
}
