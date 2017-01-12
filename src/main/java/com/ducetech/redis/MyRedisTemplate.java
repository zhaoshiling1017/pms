package com.ducetech.redis;



/** 
 * @ClassName: RedisTemplate  
 * @author chensf
 * @date 2016年5月10日 下午3:39:04 
 * @Description: redis操作接口
 */
public interface MyRedisTemplate {

	
	public  void setItem(String key, Object value);
	
	public  Object getItem(String key);

	void deleteItem(String key);
	
	/** 
	* @Title: sendMessage  
	* @param userId
	* @param messageText
	* @return void
	* @Description: 使用系统主频道发送消息
	*/
	void sendMessage(String userId, String noticeType);
	
	/** 
	* @Title: expire  
	* @param key
	* @return void
	* @Description: 设置某值的过期时间
	*/
	void expire(String key);
}
