package com.sbuddy.web.post;

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
	
}
