package com.sbuddy.web.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class S3Service {
	
	@Value("${cloud.aws.credentials.access-key}")
	private String ACCESS_KEY;
	
	@Value("${cloud.aws.credentials.secret-key}")
	private String SECRET_KEY;
	
	@Value("${cloud.aws.region.static}")
	private String REGION;
	
//	@Value("${file.path.upload.s3}")
//	private String FILE_PATH_UPLOAD_S3;
	
	
	@Bean
	public AmazonS3Client amazonS3Client() {
		BasicAWSCredentials awsCredentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
		return (AmazonS3Client) AmazonS3ClientBuilder.standard()
				.withRegion(REGION)
				.withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
				.build();
   }
}
