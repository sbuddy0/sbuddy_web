package com.sbuddy.web.post;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/post")
public class PostController {

	@Autowired
	private PostService postService;
	
	/**
	 * 게시글 작성
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/write")
	public Map<String, Object> writePost (@RequestPart Map<String, Object> param, @RequestPart MultipartFile file) throws Exception {
		
		return postService.writePost(param, file);
	}
	
	/**
	 * 내 게시글
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/my/list")
	public Map<String, Object> getMyPostList(@RequestBody Map<String, Object> param) throws Exception {
		
		return postService.getMyPostList(param);
	}
	
	/**
	 * 글 삭제
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/delete")
	public Map<String, Object> deletePost(@RequestBody Map<String, Object> param) throws Exception {
		
		return postService.deletePost(param);
	}
	
	/**
	 *  북마크 리스트
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/bookmark/list")
	public Map<String, Object> getBookmarkList(@RequestBody Map<String, Object> param) throws Exception {
		
		return postService.getBookmarkList(param);
	}
	
}
