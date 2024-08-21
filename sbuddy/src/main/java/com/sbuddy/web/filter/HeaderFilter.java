package com.sbuddy.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sbuddy.web.config.CustomHttpServletRequest;

@Component
@Order(1)
public class HeaderFilter extends OncePerRequestFilter {

	@Autowired
	private JWTService jwtService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		CustomHttpServletRequest customRequest = new CustomHttpServletRequest(request);
		
		String token = request.getHeader("token");
		
		if(token != null && token != "") {
			String idxLogin = jwtService.getIdxMember(token);
			if(idxLogin != null) {
				customRequest.setParameter("idx_login", idxLogin);
				request = (HttpServletRequest) customRequest;
			}
		}

		filterChain.doFilter(request, response);
	}
}
