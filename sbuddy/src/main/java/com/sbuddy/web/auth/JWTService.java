package com.sbuddy.web.auth;

import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;

import org.springframework.stereotype.Service;

@Service
public class JWTService {
	
	private String createToken(Map<String, Object> param) throws Exception {
		Map<String, Object> claims = new HashMap<>();
		claims.putAll(param);
		return Jwts.builder()
				.setHeaderParam(Header.TYPE, Header.JWT_TYPE)
				.setHeaderParam("alg", "HS256")
				.compact();
		
		
	}
}
