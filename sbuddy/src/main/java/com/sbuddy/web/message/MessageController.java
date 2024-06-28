package com.sbuddy.web.message;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/message")
public class MessageController {

	@Autowired
	private MessageService messageService;
	
	/**
	 * 쪽지 리스트
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/list")
	public Map<String, Object> getMessageList(@RequestBody Map<String, Object> param) throws Exception {
		
		return messageService.getMessageList(param);
	}
	
	/**
	 * 쪽지 상세내용
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/detail")
	public Map<String, Object> getMessageDetail(@RequestBody Map<String, Object> param) throws Exception {
		
		return messageService.getMessageDetail(param);
	}
}
