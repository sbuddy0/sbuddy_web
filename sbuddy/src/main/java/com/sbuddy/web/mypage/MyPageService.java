package com.sbuddy.web.mypage;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbuddy.web.util.ResponseUtil;

@Service
public class MyPageService {
	
	@Autowired
	private MypageMapper mypageMapper;

	/**
	 * 마이페이지 내 정보 가져오기
	 * @param param
	 * @return
	 */
	public Map<String, Object> getDetail (Map<String, Object> param) {

		Map<String, Object> data = mypageMapper.getDetail(param);
		
		return ResponseUtil.success(data);
	}
	
	/**
	 * 내 정보 수정
	 * @param param
	 * @return
	 */
	public Map<String, Object> modifyInfo (Map<String, Object> param) {
		
		
		return ResponseUtil.success();
	}
}
