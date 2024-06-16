package com.sbuddy.web.member;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
	public int duplicateMember(Map<String, Object> param);
	
	public int joinMember(Map<String, Object> param);
}
