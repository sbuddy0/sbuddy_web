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
	
	// 게시글 상세
	public Map<String, Object> getPostDetail(Map<String, Object> param);
	
	// 게시글 키워드
	public List<Map<String, Object>> getPostKeyword(Map<String, Object> param);

	// 게시글 파일
	public List<Map<String, Object>> getPostFile(Map<String, Object> param);
	
	// 글 수정
	public int updatePost(Map<String, Object> param);
	
	// 글 삭제
	public int deletePost(Map<String, Object> param);
	
	// 글 키워드 삭제
	public int deletePostKeyword(Map<String, Object> param);
	
	// 글 파일 삭제
	public int deletePostFile(Map<String, Object> param);
	
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
	
	// 본인 게시글인지 확인
	public int checkMyPost(Map<String, Object> param);

	
	// ####### 게시물 메인 #######
	// 게시글 목록
	public List<Map<String, Object>> getList(Map<String, Object> param);
	public int getListCnt(Map<String, Object> param);
	
	// 게시글 키워드 목록
	public List<Map<String, Object>> getKeywordList(Map<String, Object> param);
	
	// 이번주 인기 모집글 목록
	public List<Map<String, Object>> getPopularList();
	
	// 게시글 좋아요
	public int postLikes(Map<String, Object> param);
	
	// 게시글 좋아요 삭제
	public int deleteLikes(Map<String, Object> param);
	
	// 존재하는 게시글인지 검사
	public int existPost(Map<String, Object> param);
	// 좋아요 누른 게시글인지 검사
	public int countPostLikes(Map<String, Object> param);
	// 게시글 좋아요 수 갱신
	public int updateLikesTotal(Map<String, Object> param);
}
