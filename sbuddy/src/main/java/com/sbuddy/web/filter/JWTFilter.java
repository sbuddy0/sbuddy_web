package com.sbuddy.web.filter;

import java.io.IOException;
import java.security.Key;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

 @Component
 @Order(1)
public class JWTFilter extends OncePerRequestFilter {
	 
	@Value("${jwt.secret.key}")
	private String SECRET_KEY;
	
	private final static String[] EXCLUDE_URI = {
			"/",
			"/message",
			"/login",
			"/assets/*",
			"/js/*",
			"/api",
	};
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		boolean isExclude = false;
		String requestURI = request.getRequestURI();
		
		for(String uri : EXCLUDE_URI) {
			int lastIndex = uri.lastIndexOf("*");
			if(lastIndex > -1) {
				String startUri = uri.substring(0, lastIndex);
				
				if(requestURI.startsWith(startUri)) {
					isExclude = true;
					break;
				}
			} else if(requestURI.equals(uri)) {
				isExclude = true;
				break;
			}
		}
		
		filterChain.doFilter(request, response);
		
		if(isExclude) {
			filterChain.doFilter(request, response);
			return;
		}
		
		System.out.println("request Url ==> " + requestURI);
		// TODO token 검증
	}
	
	
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
