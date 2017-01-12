package com.ducetech.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

import com.ducetech.constant.Constant;
import com.ducetech.websocket.UserSocketVo;
import com.ducetech.websocket.WSSessionLocalCache;
import com.ducetech.websocket.WebsocketEndPoint;

/** 
 * @ClassName: TopicMessageListener  
 * @author chensf
 * @date 2016年5月11日 下午12:21:46 
 * @Description: 系统redis主频道监听
 */
@Service
public class TopicMessageListener implements MessageListener {
	
	protected Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Autowired
	private WebsocketEndPoint websocketEndPoint;
	
	public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {  
        this.redisTemplate = redisTemplate;  
    } 
	
	@Override
	public void onMessage(Message message, byte[] pattern) {
		byte[] body = message.getBody();// 请使用valueSerializer
		// 请参考配置文件，本例中key，value的序列化方式均为string。
		// 其中key必须为stringSerializer。和redisTemplate.convertAndSend对应
		String itemValue = (String) redisTemplate.getValueSerializer().deserialize(body);
		String[] userAndMessage = itemValue.split(":");
		String userId = userAndMessage[0];
		TextMessage textMessage = new TextMessage(userAndMessage[1]);
		try {
			if(Constant.NOTICE_TYPE_ALL.equals(textMessage.getPayload())){
				websocketEndPoint.handleMessage(null, textMessage);
			}else{
				UserSocketVo socketVo = WSSessionLocalCache.getUserSocketVoByUserId(userId);
				if(socketVo != null){
					websocketEndPoint.handleMessage(socketVo.getWebSocketSession(), textMessage);
				}else {
					log.info("缓存中找不到用户ID为: {} 的WebSocketSession.", userId);
				}
			}
		} catch (Exception e) {
			log.error("ducetech.com exception: WebSocket  ", e);
			e.printStackTrace();
		}
	}
}
