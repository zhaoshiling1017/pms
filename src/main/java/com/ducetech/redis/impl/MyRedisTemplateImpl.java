package com.ducetech.redis.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.ducetech.constant.Constant;
import com.ducetech.redis.MyRedisTemplate;

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
	public void delDic(String key) {
		redisTemplate.delete(key);
	}

	@Override
	public void clearByDicName(String dicName) {
		redisTemplate.delete("*" + dicName + "*");
	}

	@Override
	public void loadByDicName(String dicName) {

	}

	@Override
	public void delDicKey(String dicName, String key) {
		redisTemplate.opsForHash().delete(dicName, key);
	}

	@Override
	public Set<String> getKeys(String dicName) {
		return redisTemplate.keys(dicName);
	}

	@Override
	public String getValueByKey(String dicName, String key) {
		return redisTemplate.opsForHash().get(dicName, key).toString();
	}

	@Override
	public Map<Object, Object> getAllByDicName(String dicName) {
		return redisTemplate.opsForHash().entries(dicName);
	}

	@Override
	public boolean hexists(String dicName, String key) {
		return redisTemplate.opsForHash().hasKey(dicName, key);
	}

	@Override
	public void setValueByKey(String dicName, String key, String value) {
		redisTemplate.opsForHash().put(dicName, key, value);
	}

	@Override
	public long rpush(String dicName, String value) {
		return redisTemplate.opsForList().rightPush(dicName, value);
	}

	@Override
	public long lpush(String dicName, String value) {
		return redisTemplate.opsForList().leftPush(dicName, value);
	}

	@Override
	public String rpop(String dicName) {
		return redisTemplate.opsForList().rightPop(dicName).toString();
	}

	@Override
	public String lpop(String dicName) {
		return redisTemplate.opsForList().leftPop(dicName).toString();
	}

	@Override
	public long llen(String dicName) {
		return redisTemplate.opsForList().size(dicName);
	}

	@Override
	public void hmset(String dicName, Map<String, String> elements) {
		redisTemplate.opsForHash().putAll(dicName, elements);
	}

	@Override
	public List<Object> hmget(String dicName, Object[] keys) {
		return redisTemplate.opsForHash().multiGet(dicName, Arrays.asList(keys));
	}

	@Override
	public void publish(String channel, String message) {
		redisTemplate.convertAndSend(channel, message);
	}
}
