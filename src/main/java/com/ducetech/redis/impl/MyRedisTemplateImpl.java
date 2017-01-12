package com.ducetech.redis.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.ducetech.constant.Constant;
import com.ducetech.redis.MyRedisTemplate;

/** 
 * @ClassName: RedisTemplateImpl  
 * @author chensf
 * @date 2016年5月10日 下午3:40:24 
 * @Description: Redis相关操作
 */
@Service
public class MyRedisTemplateImpl implements MyRedisTemplate {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Override
	public void setItem(String key, Object value){
		redisTemplate.opsForValue().set(key, value);
	}

	@Override
	public Object getItem(String key) {
		return redisTemplate.opsForValue().get(key);
	}

	@Override
	public void sendMessage(String userId , String noticeType){
		redisTemplate.convertAndSend(Constant.SYS_CHANNEL_MAIN, userId+":"+noticeType);
	}
	
	@Override
	public void expire(String key) {
		redisTemplate.expire(key, 30, TimeUnit.MINUTES);
	}

	@Override
	public void deleteItem(String key) {
		redisTemplate.delete(key);
	}
}
