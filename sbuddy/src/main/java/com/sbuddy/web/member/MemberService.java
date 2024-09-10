package com.sbuddy.web.member;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbuddy.web.mail.MailData;
import com.sbuddy.web.mail.MailService;
import com.sbuddy.web.mail.template.FindPwTemplate;
import com.sbuddy.web.util.CommonUtil;
import com.sbuddy.web.util.ResponseUtil;
import com.sbuddy.web.util.SHAUtil;
import com.sbuddy.web.vo.ResponseCode;

@Service
public class MemberService {

	@Autowired
	private MemberMapper memberMapper;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private MailService mailService;
	
	/**
	 * 비밀번호 찾기
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> findPassword(Map<String, Object> param) throws Exception {
				
	 	Map<String, Object> data = memberMapper.getMemberInfo(param);
	 	
	 	if(data == null) {
	 		return ResponseUtil.error(ResponseCode.FAIL);
	 	}
	 	
	 	param.putAll(data);
	 	
	 	// 임시 비밀번호 생성 및 암호화
	 	String newPw = commonUtil.makeRandTempPasswd(8);
	 	String encPw = SHAUtil.encrypt(newPw);
	 	param.put("new_password", encPw);
	 	
	 	// 비밀번호 변경
	 	memberMapper.changePassword(param);
		
	 	// 메일 템플릿 설정
 		String email = (String) param.get("email");
	 	FindPwTemplate template = new FindPwTemplate();
	 	template.setName((String) param.get("username"));
	 	template.setId((String) param.get("id"));
	 	template.setNew_password(newPw);
 		
 		// 메일 발송
 		MailData mailData = new MailData(email, template);
 		mailService.sendMail(mailData);
	 	
		return ResponseUtil.success();
	}
	
	/**
	 * 회원 키워드 등록 (첫 로그인 시)
	 * @param param
	 * @return
	 * @throws ParseException 
	 * @throws JsonProcessingException 
	 * @throws JsonMappingException 
	 */
	public Map<String, Object> insertKeyword(Map<String, Object> param) throws JsonMappingException, JsonProcessingException {
		if(memberMapper.getMemberKeywordCount(param) >= 1) {
			return ResponseUtil.error(ResponseCode.ALREADY_CHOICE_KEYWORD);
		}
		
		ObjectMapper mapper = new ObjectMapper();
        List<Integer> keywordList = mapper.readValue(param.get("keyword_list").toString(), new TypeReference<List<Integer>>() {});
		
        for(Integer keywordIdx : keywordList) {
        	param.put("idx_keyword", keywordIdx);
        	memberMapper.insertMemberKeyword(param);
        }
        
		return ResponseUtil.success();
	}
}
