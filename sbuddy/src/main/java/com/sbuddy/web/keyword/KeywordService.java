package com.sbuddy.web.keyword;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbuddy.web.util.ResponseUtil;

@Service
public class KeywordService {

	@Autowired
	private KeywordMapper keywordMapper;
	
	/**
	 * 키워드 전체 조회
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getKeywordList(Map<String, Object> param) throws Exception {
				
	 	List<Map<String, Object>> list = keywordMapper.getKeywordList(param);
		
	 	Map<String, Object> data = new HashMap<>();
	 	data.put("list", list);
	 	
		return ResponseUtil.success(data);
	}
}
