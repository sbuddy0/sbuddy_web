package com.sbuddy.web.util;

import java.security.MessageDigest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class SHAUtil extends HttpServletRequestWrapper{
	private static final String SHA = "SHA-256";

	
	public SHAUtil(HttpServletRequest request) {
		super(request);
	}
	
	public static String encrypt(String passwd) throws Exception {
		try {
            MessageDigest messagedigest = null;
            messagedigest = MessageDigest.getInstance(SHA);
            messagedigest.update(passwd.getBytes());
            byte[] bytes = messagedigest.digest();
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < bytes.length; i++) {
                buffer.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            return buffer.toString();
        } catch (Exception e) {
            throw e;
        }
		
	}

}
