package com.sbuddy.web.filter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

 @Component
 @Order(1)
public class JWTFilter {
	
	public boolean apiRequestVerify(HttpServletRequest request) {
		
		String jwtToken = request.getHeader("jwt-token");
		
		// TODO 토큰 검사
		
		return true;
	}
}
