package com.sbuddy.web.member;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
	public Map<String, Object> loginMember(Map<String, Object> param);
	
	public int duplicateMember(Map<String, Object> param);
	
	public int joinMember(Map<String, Object> param);
	
	// 아이디 유무 확인
	public Map<String, Object> getMemberInfo(Map<String, Object> param);
	
	// 임시 비민번호 변경
	public int changePassword(Map<String, Object> param);
	
	// 첫 로그인 시 키워드가 등록되어있는지 검사
	public int getMemberKeywordCount(Map<String, Object> param);
	// 첫 로그인 시 회원 키워드 등록
	public int insertMemberKeyword(Map<String, Object> param);
}
