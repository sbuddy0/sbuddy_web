package com.sbuddy.web.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
public class MailService {
	private final JavaMailSender javaMailSender;
	
	public String sendMail(String receiver) throws Exception{
		String authNum = createAuthCode();
		
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
	
		try {
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(receiver); // 메일 수신자
            mimeMessageHelper.setSubject("테스트 입니다."); // 메일 제목
            mimeMessageHelper.setText("<h1> 인증번호 : " + authNum + "</h1>", true); // 메일 본문 내용, HTML 여부
            javaMailSender.send(mimeMessage);
            
            return authNum;
		} catch (MessagingException e) {
			throw new RuntimeException (e);
		}
	}
	
	
	
	private static String createAuthCode() {
		return "test";
	}

}