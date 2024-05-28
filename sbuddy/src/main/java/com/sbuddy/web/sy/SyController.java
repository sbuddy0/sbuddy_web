package com.sbuddy.web.sy;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sy")
public class SyController {
	@Autowired
	private SyService syService;
	
	@PostMapping("/test")
	public Map<String, Object> test(@RequestBody Map<String, Object> param) throws Exception {
		
		return syService.test(param);
	}
}
