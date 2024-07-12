package com.sbuddy.web.filter;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class JWTService {
	
	@Value("${jwt.secret.key}")
	private String SECRET_KEY;
		
	private String createToken(Map<String, Object> param) throws Exception {
		Date now = new Date();
		
		Map<String, Object> claims = new HashMap<>();
		
		claims.putAll(param);
		claims.put("iat", now);
		
		return Jwts.builder()
				.setHeaderParam(Header.TYPE, Header.JWT_TYPE)
				.setHeaderParam("alg", "HS256")
				.setClaims(claims)
				.signWith(createKey(SECRET_KEY))
				.compact();
	}
	
	 private Key createKey(String key) throws Exception {
    	return Keys.hmacShaKeyFor(key.getBytes("UTF-8"));
    }
	
    private String getJwt(){
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getHeader("X-ACCESS-TOKEN");
    }
    
    public String loginToken(Map<String, Object> param) throws Exception {
    	return createToken(param);
    }
	
	public String getIdxMember(String jwt) throws Exception {
		// 헤더에서 JWT 추출
		String accessToken = jwt;
		if(accessToken == null || accessToken.length() == 0) {
			throw new Exception();
		}
		
		// JWT 파싱
		Claims claims;
		try {
			claims = Jwts.parserBuilder()
					.setSigningKey(createKey(SECRET_KEY))
					.build()
					.parseClaimsJws(accessToken)
					.getBody();
		} catch(Exception e) {
			throw new Exception();
		}
		return String.valueOf(claims.get("idx_member"));
	}
}
