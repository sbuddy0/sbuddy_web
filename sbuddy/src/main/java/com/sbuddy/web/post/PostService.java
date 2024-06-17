package com.sbuddy.web.post;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbuddy.web.util.ResponseUtil;
import com.sbuddy.web.vo.ResponseCode;

@Service
@SuppressWarnings("unchecked")
public class PostService {
	
	@Autowired
	private PostMapper postMapper;
	
	/**
	 * 게시글 작성
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> writePost (Map<String, Object> param) throws Exception {

		// 게시글 insert
		if(postMapper.writePost(param) <= 0) {
			return ResponseUtil.error(ResponseCode.FAIL);
		}
		param.put("idx_post", param.get("idx"));
		
		// 키워드 insert
		List<String> keywords = (List<String>) param.get("keyword");
		
		for(String keyword : keywords) {
			param.put("idx_keyword", keyword);

			if(postMapper.writePostKeyword(param) <= 0) {
				return ResponseUtil.error(ResponseCode.FAIL);
			}
		}
		
		/**
		 * TODO
		 * 파일 업로드
		 */
		
		return ResponseUtil.success();
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
		
		// 키워드
		Map<String, Object> kparam = new HashMap<>();
		for(Map<String, Object> post : list) {
			kparam.put("idx_post", post.get("idx_post"));

			List<Map<String, Object>> keywords = postMapper.getPostKeyword(kparam);
			post.put("keyword", keywords);
		}
		
		/**
		 * TODO
		 * 파일 매핑
		 */
		
		Map<String, Object> data = new HashMap<>();
		data.put("list", list);
		
		return ResponseUtil.success(data);
	}
	
	
	/**
	 * 글 삭제
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> deletePost (Map<String, Object> param) throws Exception {
		
		if(postMapper.deletePost(param) <=0) {
			return ResponseUtil.error(ResponseCode.FAIL);
		}
		
		return ResponseUtil.success();
	}
}
