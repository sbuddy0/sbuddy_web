package com.sbuddy.web.post;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostMapper {

	// 게시글 작성
	public int writePost(Map<String, Object> param);
	
	// 게시글 키워드 작성
	public int writePostKeyword(Map<String, Object> param);
	
	// 게시글 파일 작성
	public int writePostFile(Map<String, Object> param);
}
