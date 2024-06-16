package com.sbuddy.web.member;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
	public Map<String, Object> joinMember(Map<String, Object> param);
}
