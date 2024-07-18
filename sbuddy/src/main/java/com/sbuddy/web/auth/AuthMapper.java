package com.sbuddy.web.auth;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthMapper {
	// 이메일 인증번호 생성
	public int insertEmailAuth(Map<String, Object> param);
	
	// 이메일 인증 테이블 중복 확인
	public int countEmailAuth(Map<String, Object> param);
	
	// 이메일 인증
	public int authEmail(Map<String, Object> param);
	
	// 이메일 인증 완료
	public int completeEmailAuth(Map<String, Object> param);
	
	// 이메일 인증번호 재생성
	public int regenEmailAuth(Map<String, Object> param);
	
	// 인증 완료된 이메일인지 확인
	public int isEmailAuth(Map<String, Object> param);
	
	// 첫 로그인 시 키워드 등록
	public int insertKeyword(Map<String, Object> param);
	
}
