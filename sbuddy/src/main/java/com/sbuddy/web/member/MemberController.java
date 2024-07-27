package com.sbuddy.web.member;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/member")
public class MemberController {

	@Autowired
	private MemberService memberService;
	
	/**
	 * 비밀번호 찾기
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/find/password")
	public Map<String, Object> finePassword(@RequestBody Map<String, Object> param) throws Exception {
		
		return memberService.findPassword(param);
	}
	
	/**
	 * 회원 키워드 등록 (첫 로그인 시)
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/keyword/insert")
	public Map<String, Object> insertKeyword(@RequestBody Map<String, Object> param) throws Exception {
		
		return memberService.insertKeyword(param);
	}
	
}
