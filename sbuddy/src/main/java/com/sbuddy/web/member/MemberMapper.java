package com.sbuddy.web.member;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
	public int loginMember(Map<String, Object> param);
	
	public int duplicateMember(Map<String, Object> param);
	
	public int joinMember(Map<String, Object> param);
	
	// 아이디 유무 확인
	public Map<String, Object> getMemberInfo(Map<String, Object> param);
	
	// 임시 비민번호 변경
	public int changePassword(Map<String, Object> param);
}
