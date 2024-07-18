package com.sbuddy.web.keyword;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/keyword")
public class KeywordController {

	@Autowired
	private KeywordService keywordService;
	
	/**
	 * 키워드 전체 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/list")
	public Map<String, Object> getKeywordList(@RequestBody Map<String, Object> param) throws Exception {
		
		return keywordService.getKeywordList(param);
	}
}
