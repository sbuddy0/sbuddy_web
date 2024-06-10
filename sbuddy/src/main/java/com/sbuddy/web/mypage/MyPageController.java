package com.sbuddy.web.mypage;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/mypage")
public class MyPageController {

	@Autowired
	private MyPageService myPageService;
	
	/**
	 * 마이페이지 내 정보 가져오기
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/detail")
	public Map<String, Object> getDetail (@RequestBody Map<String, Object> param) throws Exception {
		
		return myPageService.getDetail(param);
	}
	
	/**
	 * 내 정보 수정
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/modify")
	public Map<String, Object> modifyInfo (@RequestBody Map<String, Object> param) throws Exception {
		
		
		return myPageService.modifyInfo(param);
	}
}
