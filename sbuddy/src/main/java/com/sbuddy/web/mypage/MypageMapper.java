package com.sbuddy.web.mypage;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MypageMapper {

	public Map<String, Object> getDetail(Map<String, Object> param);
}
