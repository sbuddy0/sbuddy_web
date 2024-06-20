package com.sbuddy.web.post;

import java.util.List;
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
	
	// 내 게시글
	public List<Map<String, Object>> getMyPostList(Map<String, Object> param);
	
	// 게시글 키워드
	public List<Map<String, Object>> getPostKeyword(Map<String, Object> param);

	// 게시글 파일
	public List<Map<String, Object>> getPostFile(Map<String, Object> param);
	
	// 글 삭제
	public int deletePost(Map<String, Object> param);
	
	// 북마크 리스트
	public List<Map<String, Object>> getBookmarkList(Map<String, Object> param);
	
	// 텍스트 검색
	public List<Map<String, Object>> searchText(Map<String, Object> param);
	
	// 텍스트 검색 개수
	public int searchTextCnt(Map<String, Object> param);
	
	// 키워드 검색
	public List<Map<String, Object>> searchKeyword(Map<String, Object> param);

	// 키워드 검색 개수
	public int searchKeywordCnt(Map<String, Object> param);
}
