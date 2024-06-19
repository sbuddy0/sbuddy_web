package com.sbuddy.web.file;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;

@Configuration 
public class S3Service {
	
	@Value("${cloud.aws.credentials.access-key}")
	private String ACCESS_KEY;
	
	@Value("${cloud.aws.credentials.secret-key}")
	private String SECRET_KEY;
	
	@Value("${cloud.aws.region.static}")
	private String REGION;
	
	@Value("${cloud.aws.s3.bucket}")
	private String BUCKET;

//	@Value("${file.path.upload.s3}")
//	private String FILE_PATH_UPLOAD_S3;
    
	private AmazonS3 s3Client;
    
	@Bean
	public void setS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);

        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(REGION)
                .build();
    }

	
	/**
	 * 파일 업로드
	 * @param mFile
	 * @return
	 * @throws Exception
	 */
	public String uploadFile(MultipartFile mFile) throws Exception {
		InputStream inputStream;
		inputStream = mFile.getInputStream();

		String fileName = mFile.getOriginalFilename();

		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(mFile.getSize());
		metadata.setContentType(mFile.getContentType());
		try {
			s3Client.putObject(BUCKET, fileName, inputStream, metadata);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			inputStream.close();
		}
		
		return s3Client.getUrl(BUCKET, fileName).toString();
	}

}
