package com.ducetech.websocket;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.ducetech.framework.model.User;

/** 
 * @ClassName: WebsocketEndPoint  
 * @author chensf
 * @date 2016年5月12日 下午4:49:53 
 * @Description: Websocket处理类
 */
public class WebsocketEndPoint extends TextWebSocketHandler {

	private static final Logger logger = LoggerFactory.getLogger(WebsocketEndPoint.class);
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		super.handleTextMessage(session, message);
		TextMessage returnMessage = new TextMessage(message.getPayload());
		if(session==null){
			List<UserSocketVo> socketVos = WSSessionLocalCache.getAllSessions();
			for (UserSocketVo userSocketVo : socketVos) {
				userSocketVo.getWebSocketSession().sendMessage(returnMessage);
			}
		}else{
			synchronized (session) {
				session.sendMessage(returnMessage);
			}
		}
	}

	/**
	 * @Description :　建立连接后
	 * @param session
	 * @throws Exception
	 */
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		if (!WSSessionLocalCache.exists(session.getId())) {
			UserSocketVo userSocketVo = new UserSocketVo();
			userSocketVo.setWebSocketSession(session);
			User user = (User)session.getAttributes().get("DT_LOGIN_USER");
			userSocketVo.setUserId(user.getUserId());
			WSSessionLocalCache.put(session.getId(), userSocketVo);
			logger.info("socket成功建立连接...    建立者是:{} , ID:{} .", user.getName(), user.getUserId());
		}
		super.afterConnectionEstablished(session);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		User user = (User)session.getAttributes().get("DT_LOGIN_USER");
		if (WSSessionLocalCache.exists(session.getId())) {
			WSSessionLocalCache.remove(session.getId());
		}
		logger.info("socket成功关闭连接...    关闭者是:{} , ID:{} .", user.getName(), user.getUserId());
		super.afterConnectionClosed(session, status);
	}
}
