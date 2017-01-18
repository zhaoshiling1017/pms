package com.ducetech.redis;


import java.util.List;
import java.util.Map;
import java.util.Set;

public interface MyRedisTemplate {

	public void setItem(String key, Object value);
	
	public Object getItem(String key);

	public void delDic(String dicName);

	public void clearByDicName(String dicName);

	public void loadByDicName(String dicName);

	public void delDicKey(String dicName,String key);

	public Set<String> getKeys(String dicName);

	public String getValueByKey(String dicName,String key);

	public Map<Object, Object> getAllByDicName(String dicName);

	public boolean hexists(String dicName,String key);

	public void setValueByKey(String dicName,String key,String value);

	public long rpush(String dicName, String value);

	public long lpush(String dicName, String value);

	public String rpop(String dicName);

	public String lpop(String dicName);

	public long llen(String dicName);

	public void hmset(String dicName,Map<String,String> elements);

	public List<Object> hmget(String dicName,Object[] keys);

	public void sendMessage(String userId, String noticeType);

	public void publish(String channel, String message);

	public void expire(String key);

}
