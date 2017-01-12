package com.ducetech.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/** 
 * @ClassName: WebSocketConfig  
 * @author chensf
 * @date 2016年5月13日 上午10:43:20 
 * @Description: 
 */
@Configuration
@EnableWebMvc
@EnableWebSocket
public class WebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(websocketEndPoint(),"/websocket").addInterceptors(new HandshakeInterceptor());
	}

	@Bean  
    public WebsocketEndPoint websocketEndPoint() {  
        return new WebsocketEndPoint();  
    } 
}
