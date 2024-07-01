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
	public Map<String, Object> writePost (@RequestPart Map<String, Object> param, @RequestPart(required = false) MultipartFile file) throws Exception {
		
		return postService.writePost(param, file);
	}
	
	/**
	 * 게시글 상세 내용
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/detail")
	public Map<String, Object> getDetail(@RequestBody Map<String, Object> param) throws Exception {
		
		return postService.getDetail(param);
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
	 * 글 수정
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/update")
	public Map<String, Object> updatePost(@RequestPart Map<String, Object> param, @RequestPart(required = false) MultipartFile file) throws Exception {
		System.out.println(file);
		return postService.updatePost(param, file);
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
	
	/**
	 *  텍스트 검색
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/search/text")
	public Map<String, Object> searchText(@RequestBody Map<String, Object> param) throws Exception {
		
		return postService.searchText(param);
	}
	
	/**
	 *  키워드 검색
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/search/keyword")
	public Map<String, Object> searchKeyword(@RequestBody Map<String, Object> param) throws Exception {
		
		return postService.searchKeyword(param);
	}
	
	
	 // ####### 게시물 메인 #######
	@PostMapping("/list")
	public Map<String, Object> getList(@RequestBody Map<String, Object> param) throws Exception {
		
		return postService.getList(param);
	}
	
	/**
	 * 이번주 인기 모집 글 목록
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/popular/list")
	public Map<String, Object> getPopularList(@RequestBody Map<String, Object> param) throws Exception {
		
		return postService.getPopularList(param);
	}
}
