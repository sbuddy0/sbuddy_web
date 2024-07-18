package com.sbuddy.web.keyword;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface KeywordMapper {
	
	// 키워드 전체 조회
	public List<Map<String, Object>> getKeywordList(Map<String, Object> param);
	
}
