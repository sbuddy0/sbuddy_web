package com.sbuddy.web.util;

import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class CommonUtil {
	
	/**
	 * 필수 파라미터 검사 
	 * @param param
	 * @param key
	 * @throws Exception
	 */
	public static void checkIsNullException(Map<String, Object> param, String key) throws Exception {		
		if(!param.containsKey(key) || param.get(key).equals("") || param.get(key).equals(null)) {
			throw new RuntimeException();
		}
	}
	
	/**
	 * 파라미터 안에 키 값이 존재하는지 검사
	 * @param param
	 * @param key
	 * @return
	 */
	public static boolean checkIsNull(Map<String, Object> param, String key) {
		if(param == null || key == null || param.get(key).equals("") || param.get(key).equals(null)) {
			return false;
		}
		return true;
	}
		
	/**
	 * 숫자, 영대문자 조합의 랜덤 코드 생성
	 * @param length
	 * @return
	 */
	public static String createRandomCode(int length) {
		Random random = new Random();
		StringBuffer randomCode = new StringBuffer();
		
		for(int i=0; i<length; i++) {
			if(random.nextBoolean()) {
				randomCode.append((char)((int)(random.nextInt(10)) + 48));
			} else {
				randomCode.append((char)((int)(random.nextInt(26)) + 65));
			}
		}
		
		return randomCode.toString();
	}
	
	/**
	 * 영어 대/소문자, 숫자, 특수문자 3가지 조합 n자리 랜덤 임시 비밀번호 생성
	 * @param len
	 * @return
	 * @throws Exception
	 */
	public String makeRandTempPasswd(int len) throws Exception {
		if(len > 0) {
			StringBuffer randStr = new StringBuffer();
			Random random = new Random();
			char[] symbolChar = {'@', '$', '!', '%', '*', '?', '#'};

			for (int i = 0; i < len; i++) {
				int type = random.nextInt(4);
				double mathRandom = Math.random();

				switch(type) {
				case 0:
					randStr.append((int)(mathRandom * 9 + 1));
					break;
				case 1:
					randStr.append((char)(mathRandom * 26 + 'a'));
					break;
				case 2:
					randStr.append((char)(mathRandom * 26 + 'A'));
					break;
				default: 
					randStr.append(symbolChar[random.nextInt(symbolChar.length)]);
					break;
				}
			}
			
			if(!validatePw(randStr.toString())) {
				return makeRandTempPasswd(len);
			}

			return randStr.toString();
		} else {
			return "";
		}
	}
	
	/**
	 * 비밀번호 유효성 체크
	 * 특수문자 1자리, 영어 2자리, 숫자 N 총 10자리 이상
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public boolean validatePw(String passwd) throws Exception {
		int cnt = 0;
		
		for (int i = 0; i < passwd.length(); i++) {
			if((64 < passwd.charAt(i) && passwd.charAt(i) < 91) || (96 < passwd.charAt(i) && passwd.charAt(i) < 123)) {
				cnt++;
			}
		}
		
		if(cnt < 2) {
			return false;
		}
		
		Pattern pattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$");
		Matcher matcher = pattern.matcher(passwd);
		
		if (matcher.matches()) {
			return true;
		} else {
			return false;
		}
	}
}
