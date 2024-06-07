package com.sbuddy.web.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
	
	@GetMapping(value = {"/", "/api"})
	public String main() {
		
		return "/api/api";
	}
}
