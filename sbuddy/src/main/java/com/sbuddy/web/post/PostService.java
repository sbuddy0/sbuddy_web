package com.sbuddy.web.post;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sbuddy.web.file.S3Service;
import com.sbuddy.web.util.ResponseUtil;
import com.sbuddy.web.vo.ResponseCode;

@Service
@Transactional
public class PostService {
	
	@Autowired
	private PostMapper postMapper;
	
	@Autowired
	private S3Service s3;
	
	/**
	 * 게시글 작성
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> writePost (Map<String, Object> param, MultipartFile mFile) throws Exception {
		
		// 게시글 insert
		if(postMapper.writePost(param) <= 0) {
			return ResponseUtil.error(ResponseCode.FAIL);
		}
		param.put("idx_post", param.get("idx"));
		
		// 키워드 insert
		String keywordStr = (String) param.get("keyword");
		String[] keywords = keywordStr.split(",");
		
		for(String keyword : keywords) {
			param.put("idx_keyword", keyword);

			if(postMapper.writePostKeyword(param) <= 0) {
				return ResponseUtil.error(ResponseCode.FAIL);
			}
		}
		
		// 파일 업로드
		String filePath = "post/" + param.get("idx_post") + "/";
		String fileName = mFile.getOriginalFilename();
		String uploadPath = s3.uploadFile(mFile, filePath + fileName);
		
		param.put("file_name", fileName);
		param.put("file_size", mFile.getSize());
		param.put("file_path", uploadPath);
		
		if(postMapper.writePostFile(param) <= 0) {
			s3.deleteFile(filePath + fileName);
			return ResponseUtil.error(ResponseCode.FAIL);
		}
		
		return ResponseUtil.success();
	}
	
	
	/**
	 * 게시글 상세 내용
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getDetail (Map<String, Object> param) throws Exception {
		
		// 글
		Map<String, Object> post = postMapper.getPostDetail(param);
		
		Map<String, Object> data = new HashMap<>();
		data.put("data", mappingPost(post));
		
		return ResponseUtil.success(data);
	}
	
	
	/**
	 * 내 게시글
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getMyPostList (Map<String, Object> param) throws Exception {
		
		// 글
		List<Map<String, Object>> list = postMapper.getMyPostList(param);
		
		Map<String, Object> data = new HashMap<>();
		data.put("list", mappingPost(list));
		
		return ResponseUtil.success(data);
	}
	
	
	/**
	 * 글 수정
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> updatePost (Map<String, Object> param, MultipartFile mFile) throws Exception {

		// 본인 게시글인지 확인
		if(postMapper.checkMyPost(param) <= 0) {
			return ResponseUtil.error(ResponseCode.FAIL);
		}
		
		Map<String, Object> post = postMapper.getPostDetail(param);
		List<Map<String, Object>> files = postMapper.getPostFile(post);

		String filePath = "post/" + param.get("idx_post") + "/";
		
		if(mFile != null) {
			System.out.println(mFile);
			// 기존 파일 삭제
			for(Map<String, Object> file : files) {
				s3.deleteFile(filePath + file.get("file_name"));
				postMapper.deletePostFile(file);
			}
			
			// 파일 업로드
			String fileName = mFile.getOriginalFilename();
			String uploadPath = s3.uploadFile(mFile, filePath + fileName);
			
			param.put("file_name", fileName);
			param.put("file_size", mFile.getSize());
			param.put("file_path", uploadPath);
			
			if(postMapper.writePostFile(param) <= 0) {
				return ResponseUtil.error(ResponseCode.FAIL);
			}
		}
		
		// 게시글 기본 정보 수정
		if(postMapper.updatePost(param) <= 0) {
			return ResponseUtil.error(ResponseCode.FAIL);
		}
		
		// 키워드 삭제 후 재등록
		postMapper.deletePostKeyword(param);
		
		// 키워드 insert
		String keywordStr = (String) param.get("keyword");
		String[] keywords = keywordStr.split(",");
		
		for(String keyword : keywords) {
			param.put("idx_keyword", keyword);

			if(postMapper.writePostKeyword(param) <= 0) {
				return ResponseUtil.error(ResponseCode.FAIL);
			}
		}
		
		return ResponseUtil.success();
	}

	
	/**
	 * 글 삭제
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> deletePost (Map<String, Object> param) throws Exception {

		// 본인 게시글인지 확인
		if(postMapper.checkMyPost(param) <= 0) {
			return ResponseUtil.error(ResponseCode.FAIL);
		}
		
		// 버킷 파일 삭제
		List<Map<String, Object>> list = postMapper.getPostFile(param);
		for(Map<String, Object> file : list) {
			String fileName = String.valueOf(file.get("file_name"));

			s3.deleteFile("post/" + param.get("idx_post") + "/" +  fileName);
		}

		// 키워드
		if(postMapper.deletePostKeyword(param) <=0) {
			return ResponseUtil.error(ResponseCode.FAIL);
		}
		
		// 파일
		if(postMapper.deletePostFile(param) <=0) {
			return ResponseUtil.error(ResponseCode.FAIL);
		}
		
		// 게시글
		if(postMapper.deletePost(param) <=0) {
			return ResponseUtil.error(ResponseCode.FAIL);
		}
		
		return ResponseUtil.success();
	}
	

	/**
	 * 북마크 리스트
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getBookmarkList(Map<String, Object> param) throws Exception {
				
		List<Map<String, Object>> list = postMapper.getBookmarkList(param);
		
		Map<String, Object> data = new HashMap<>();
		data.put("list", mappingPost(list));
		
		return ResponseUtil.success(data);
	}
	
	
	/**
	 * 텍스트 검색
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> searchText(Map<String, Object> param) throws Exception {
		System.out.println(param);
		// 글
		List<Map<String, Object>> list = postMapper.searchText(param);
		
		// 개수
		int cnt = postMapper.searchTextCnt(param);
		
		Map<String, Object> data = new HashMap<>();
		data.put("list", mappingPost(list));
		data.put("total", cnt);
		
		return ResponseUtil.success(data);
	}
	
	
	/**
	 * 키워드 검색
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> searchKeyword(Map<String, Object> param) throws Exception {
		
		// 글
		List<Map<String, Object>> list = postMapper.searchKeyword(param);
		
		// 개수
		int cnt = postMapper.searchKeywordCnt(param);
		
		Map<String, Object> data = new HashMap<>();
		data.put("list", mappingPost(list));
		data.put("total", cnt);
			
		return ResponseUtil.success(data);
	}
	
	
	/**
	 * 게시글과 키워드, 파일 매핑 (리스트)
	 * @param list
	 * @return
	 * @throws Exception
	 */
	private List<Map<String, Object>>  mappingPost(List<Map<String, Object>> list) throws Exception {
		
		Map<String, Object> postParam = new HashMap<>();
		for(Map<String, Object> post : list) {
			postParam.put("idx_post", post.get("idx_post"));

			// 키워드 매핑
			List<Map<String, Object>> keywords = postMapper.getPostKeyword(postParam);
			post.put("keyword", keywords);
			
			// 파일 매핑
			List<Map<String, Object>> files = postMapper.getPostFile(postParam);
			post.put("file", files);
		}
		
		return list;
	}
	
	/**
	 * 게시글과 키워드, 파일 매핑
	 * @param post
	 * @return
	 * @throws Exception
	 */
	private Map<String, Object>  mappingPost(Map<String, Object> post) throws Exception {
		
		// 키워드 매핑
		List<Map<String, Object>> keywords = postMapper.getPostKeyword(post);
		post.put("keyword", keywords);
		
		// 파일 매핑
		List<Map<String, Object>> files = postMapper.getPostFile(post);
		post.put("file", files);
		
		return post;

	}
		
		
	// ####### 게시물 메인 #######
	/**
	 * 게시물 메인 목록
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getList(Map<String, Object> param) throws Exception {
		Map<String, Object> data = new HashMap<>();
		List<Map<String, Object>> postList = postMapper.getList(param);
		int cnt = postMapper.getListCnt(param);
		
		data.put("total", cnt);
		data.put("list", postList);
		// TODO 게시물별 키워드 매핑
		
		return ResponseUtil.success(data);
	}
	
	/**
	 * 이번주 인기 모집 글 목록
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getPopularList(Map<String, Object> param) throws Exception {
		Map<String, Object> data = new HashMap<>();
		
		List<Map<String, Object>> list = postMapper.getPopularList();
		data.put("list", list);
		
		return ResponseUtil.success(data);
	}
	
	/**
	 * 게시글 좋아요
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> postLikes(Map<String, Object> param) throws Exception {
 		// 게시글이 존재하는지, 좋아요 중복인지 검사
 		if(postMapper.existPost(param) == 0) {
 			return ResponseUtil.error(ResponseCode.NOT_EXISTS_POST);
 		} else if(postMapper.countPostLikes(param) == 1) { 
 			return ResponseUtil.error(ResponseCode.ALREADY_POST_LIKE);
 		} else {
 			postMapper.postLikes(param);
 			postMapper.updateLikesTotal(param);
 		}
 		
		return ResponseUtil.success();
	}
	
	/**
	 * 게시글 좋아요 삭제
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> cancelLikes(Map<String, Object> param) throws Exception {
 		// 게시글이 존재하는지, 좋아요 누른 게시글인지 검사
 		if(postMapper.existPost(param) == 0) {
 			return ResponseUtil.error(ResponseCode.NOT_EXISTS_POST);
 		} else if(postMapper.countPostLikes(param) == 0) { 
 			return ResponseUtil.error(ResponseCode.NOT_EXISTS_POST_LIKE);
 		} else {
 			postMapper.deleteLikes(param);
 			postMapper.updateLikesTotal(param);
 		}
		
		return ResponseUtil.success();
	}
}
