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
	
	/*
	@PostMapping("/Keyword/insert")
	public Map<String, Object>
	*/
}
