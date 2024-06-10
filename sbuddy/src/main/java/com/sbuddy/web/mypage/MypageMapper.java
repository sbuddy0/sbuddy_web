package com.sbuddy.web.mypage;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MypageMapper {

	// 내 정보 가져오기
	public Map<String, Object> getDetail(Map<String, Object> param);
	
	// 내 정보 수정
	public int  modifyInfo(Map<String, Object> param);
	
	// 등록된 키워드 일괄 삭제
	public int  deleteKeyword(Map<String, Object> param);
	
	// 키워드 수정
	public int  modifyKeyword(Map<String, Object> param);
}
