package com.sbuddy.web.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
	
	@GetMapping(value = {"/", "/api"})
	public String main() {
		
		return "/api/api";
	}
	
	@GetMapping(value = {"/mypage"})
	public String mypage() {
		
		return "/api/mypage";
	}
	
	@GetMapping(value = {"/post"})
	public String post() {
		
		return "/api/post";
	}
	
	@GetMapping(value = {"/message"})
	public String message() {
		
		return "/api/message";
	}
}
