package com.sbuddy.web.sy;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SyMapper {
	public Map<String, Object> getMember();
}
