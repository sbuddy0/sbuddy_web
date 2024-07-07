package com.sbuddy.web.filter;

import java.security.Key;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

 @Component
 @Order(1)
public class JWTFilter {
	 
	 @Value("${jwt.secret.key}")
	private String SECRET_KEY;
	
	public boolean apiRequestVerify(HttpServletRequest request) throws Exception {
		
		String jwtToken = request.getHeader("jwt-token");
		
		Claims claims;

		try {
			claims = Jwts.parserBuilder()
					.setSigningKey(createKey(SECRET_KEY))
					.build()
					.parseClaimsJws(jwtToken)
					.getBody();
		} catch(Exception e) {
			throw new Exception();
		}
		// TODO 토큰 검사
		
		return true;
	}
	
	private Key createKey(String key) throws Exception {
    	return Keys.hmacShaKeyFor(key.getBytes("UTF-8"));
    }
}
