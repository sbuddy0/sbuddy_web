package com.sbuddy.web.mypage;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sbuddy.web.file.S3Service;
import com.sbuddy.web.util.ResponseUtil;
import com.sbuddy.web.vo.ResponseCode;

@Service
@SuppressWarnings("unchecked")
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
	public Map<String, Object> getDetail (Map<String, Object> param) throws Exception {

		Map<String, Object> data = mypageMapper.getDetail(param);
		
		return ResponseUtil.success(data);
	}
	
	/**
	 * 내 정보 수정
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> modifyInfo (Map<String, Object> param,  MultipartFile mFile) throws Exception {
		
		// 파일
		String filePath = "member/" + param.get("idx_member") + "/";
		String fileName = mFile.getOriginalFilename();
		String uploadPath = s3.uploadFile(mFile, filePath + fileName);
		param.put("profile", uploadPath);
				
		if(mypageMapper.modifyInfo(param) > 0) {
			return ResponseUtil.success();
		} else {
			s3.deleteFile(filePath + fileName);
			return ResponseUtil.error(ResponseCode.FAIL);
		}		
	}
	
	
	/**
	 * 키워드 수정
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> modifyKeyword (Map<String, Object> param) throws Exception {
		
		// 등록된 키워드 일괄 삭제
		mypageMapper.deleteKeyword(param);
		
		// 체크한 키워드 하나씩 등록
		List<String> keywords = (List<String>) param.get("keyword");
		
		for(String keyword : keywords) {
			param.put("idx_keyword", keyword);

			if(mypageMapper.modifyKeyword(param) <= 0) {
				return ResponseUtil.error(ResponseCode.FAIL);
			}
		}
		
		return ResponseUtil.success();
	}
}
