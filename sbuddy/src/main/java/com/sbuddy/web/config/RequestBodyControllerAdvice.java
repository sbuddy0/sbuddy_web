package com.sbuddy.web.config;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbuddy.web.filter.JWTService;

@RestControllerAdvice
public class RequestBodyControllerAdvice implements RequestBodyAdvice {

    private final ObjectMapper objectMapper;
    
    @Autowired
    private JWTService jwtService;

    public RequestBodyControllerAdvice(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType,
                            Class<? extends HttpMessageConverter<?>> converterType) {
        return MappingJackson2HttpMessageConverter.class.isAssignableFrom(converterType);
    }

    @SuppressWarnings("unchecked")
	@Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
                                           Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
    	
    	// JSON 문자열을 Map으로 변환
    	byte[] bodyBytes = StreamUtils.copyToByteArray(inputMessage.getBody());
        Map<String, Object> param = objectMapper.readValue(bodyBytes, Map.class);
        
        // 요청 본문 수정
        String token = inputMessage.getHeaders().get("token").get(0);
        String idxLogin = jwtService.getIdxMember(token);
        if(idxLogin != null) {
        	param.put("idx_login", idxLogin);
        	System.out.println("idx_login ---> " + idxLogin);
        }
        
        // 수정된 Map을 다시 JSON 문자열로 변환
        String modifiedBodyString = objectMapper.writeValueAsString(param);
        byte[] modifiedBodyBytes = modifiedBodyString.getBytes("UTF-8");
        
        return new CustomHttpInputMessage(inputMessage, modifiedBodyBytes);
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
                                Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter,
                                  Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    // 새로운 body 값을 가진 inputMessage를 반환하기 위한 CustomHttpInputMessage
    private static class CustomHttpInputMessage implements HttpInputMessage {

        private final HttpInputMessage original;
        private final byte[] body;

        public CustomHttpInputMessage(HttpInputMessage original, byte[] body) {
            this.original = original;
            this.body = body;
        }

        @Override
        public HttpHeaders getHeaders() {
            return original.getHeaders();
        }

        @Override
        public InputStream getBody() throws IOException {
            return new ByteArrayInputStream(body);
        }
    }
}
