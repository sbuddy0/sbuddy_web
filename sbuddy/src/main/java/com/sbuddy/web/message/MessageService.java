package com.sbuddy.web.message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sbuddy.web.util.ResponseUtil;

@Service
@Transactional
public class MessageService {
	
	@Autowired
	private MessageMapper messageMapper;
	
	/**
	 * 쪽지 리스트
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getMessageList(Map<String, Object> param) throws Exception {
		
		List<Map<String, Object>> list = messageMapper.getMessageList(param);
		
		Map<String, Object> data= new HashMap<>();
		data.put("list", list);
		
		return ResponseUtil.success(data);
	}
	
	
	/**
	 * 쪽지 상세내용
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getMessageDetail(Map<String, Object> param) throws Exception {
		
		Map<String, Object> data = messageMapper.getMessageDetail(param);
		
		return ResponseUtil.success(data);
	}
}
