package com.sbuddy.web.mypage;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sbuddy.web.file.S3Service;
import com.sbuddy.web.util.ResponseUtil;
import com.sbuddy.web.util.SHAUtil;
import com.sbuddy.web.vo.ResponseCode;

@Service
@Transactional
public class MyPageService {
	
	@Autowired
	private MypageMapper mypageMapper;
	
	@Autowired
	private S3Service s3;
	
	/**
	 * 마이페이지 내 정보 가져오기
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getDetail(Map<String, Object> param) throws Exception {

		Map<String, Object> data = mypageMapper.getDetail(param);
		
		List<Map<String, Object>> keywords = mypageMapper.getMemberKeyword(param);
		data.put("keyword", keywords);
		
		return ResponseUtil.success(data);
	}
	
	/**
	 * 내 정보 수정
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> modifyInfo(Map<String, Object> param,  MultipartFile mFile) throws Exception {
		
		// 프로필 이미지 변경할 경우
		String filePath = "member/" + param.get("idx_member") + "/";

		if(mFile != null) {
			// 기존 파일 삭제
			Map<String, Object> data = mypageMapper.getDetail(param);
			String preFile = filePath + (String) data.get("file_name");
			s3.deleteFile(preFile);
			
			// 파일
			String fileName = mFile.getOriginalFilename();
			String uploadPath = s3.uploadFile(mFile, filePath + fileName);
			param.put("file_name", fileName);
			param.put("profile", uploadPath);
		}
				
		if(mypageMapper.modifyInfo(param) <= 0) {
			return ResponseUtil.error(ResponseCode.FAIL);
		}		
		return ResponseUtil.success();
	}
	
	/**
	 * 키워드 수정
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> modifyKeyword(Map<String, Object> param) throws Exception {
		
		// 등록된 키워드 일괄 삭제
		mypageMapper.deleteKeyword(param);
		
		// 체크한 키워드 하나씩 등록
		String keywordStr = (String) param.get("keyword");
		String[] keywords = keywordStr.split(",");
		
		for(String keyword : keywords) {
			param.put("idx_keyword", keyword);

			if(mypageMapper.modifyKeyword(param) <= 0) {
				return ResponseUtil.error(ResponseCode.FAIL);
			}
		}
		
		return ResponseUtil.success();
	}
	
	/**
	 * 비밀번호 변경
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> modifyPassword(Map<String, Object> param) throws Exception {
		
		// 기존 비밀번호 비교
		Map<String, Object> info = mypageMapper.getDetail(param);
		String prePassword = String.valueOf(info.get("password"));
		String encPrePassword = SHAUtil.encrypt(String.valueOf(param.get("password")));
		
		if(!prePassword.equals(encPrePassword)) {
			return ResponseUtil.error(ResponseCode.FAIL);
		}
		
		// 새 비밀번호로 변경
		String newPassword = String.valueOf(param.get("new_password"));
		String encPassword = SHAUtil.encrypt(newPassword);
		param.put("enc_password", encPassword);
		                                                                                                                    
		mypageMapper.modifyPassword(param);
		
		return ResponseUtil.success();
	}
}
