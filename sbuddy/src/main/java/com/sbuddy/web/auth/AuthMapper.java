package com.sbuddy.web.auth;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthMapper {
	public int insertEmailAuth(Map<String, Object> param);
	public int countEmailAuth(Map<String, Object> param);
	public int updateEmailAuth(Map<String, Object> param);
	
}
