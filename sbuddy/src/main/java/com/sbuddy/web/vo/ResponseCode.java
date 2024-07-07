package com.sbuddy.web.vo;

import org.springframework.http.HttpStatus;

import lombok.Getter;

/**
 * 응답 코드
 */

@Getter
public enum ResponseCode {
	// Success
	OK("200", HttpStatus.OK, "OK"),
	
	// fail
	FAIL("400", HttpStatus.BAD_REQUEST, "FAIL"),
	
	NOT_EXISTS_MEMBER("1001", HttpStatus.OK, "NOT_EXISTS_MEMBER"),
	
	SEND_FAIL_MESSAGE("5001", HttpStatus.OK, "SEND_FAIL_MESSAGE"),
	;
	
	private final String code;
	private final HttpStatus httpStatus;
	private final String message;
	
	ResponseCode(String code, HttpStatus httpStatus, String message) {
		// TODO Auto-generated constructor stub
		this.code = code;
		this.httpStatus = httpStatus;
		this.message = message;
	}
}
