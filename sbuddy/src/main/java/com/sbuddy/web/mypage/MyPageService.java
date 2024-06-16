package com.sbuddy.web.mypage;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.sbuddy.web.util.ResponseUtil;
import com.sbuddy.web.vo.ResponseCode;

@Service
@SuppressWarnings("unchecked")
public class MyPageService {
	
	@Autowired
	private MypageMapper mypageMapper;
	
	private AmazonS3 S3Cli;
	private AmazonS3Client s3client;
	
	@Value("${cloud.aws.s3.bucket}")
	private String BUCKET;

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
		String filePath = uploadFile(mFile);
		param.put("profile", filePath);
				
		if(mypageMapper.modifyInfo(param) > 0) {
			return ResponseUtil.success();
		} else {
			return ResponseUtil.error(ResponseCode.FAIL);
		}		
	}
	
	/**
	 * 파일 S3 업로드
	 * @param mFile
	 * @return
	 * @throws Exception
	 */
	public String uploadFile(MultipartFile mFile) throws Exception {
		String fileName = mFile.getOriginalFilename();
		String S3FilePath = "member" + File.separator +fileName;

		InputStream inputStream;
		inputStream = mFile.getInputStream();
		
		try {
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentLength(mFile.getSize());
			metadata.setContentType(mFile.getContentType());
			
			s3client.putObject(BUCKET, fileName, inputStream, metadata);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			inputStream.close();
		}
		
        return s3client.getUrl(BUCKET, fileName).toString();
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
