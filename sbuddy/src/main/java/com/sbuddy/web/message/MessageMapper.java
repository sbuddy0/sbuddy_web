package com.sbuddy.web.message;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MessageMapper {

	// 쪽지 리스트
	public List<Map<String, Object>> getMessageList(Map<String, Object> param);
	
	// 쪽지 상세내용
	public Map<String, Object> getMessageDetail(Map<String, Object> param);
}
