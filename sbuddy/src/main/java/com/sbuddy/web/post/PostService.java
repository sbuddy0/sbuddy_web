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
@SuppressWarnings("unchecked")
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
		List<String> keywords = (List<String>) param.get("keyword");
		
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
	 * 글 삭제
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> deletePost (Map<String, Object> param) throws Exception {
		
		if(postMapper.deletePost(param) <=0) {
			return ResponseUtil.error(ResponseCode.FAIL);
		}

		// 파일 삭제
		List<Map<String, Object>> list = postMapper.getPostFile(param);
		for(Map<String, Object> file : list) {
			String fileName = String.valueOf(file.get("file_name"));
			
			s3.deleteFile("post/" + param.get("idx_post") + "/" +  fileName);
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
	 * 게시글과 키워드, 파일 매핑
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
}
