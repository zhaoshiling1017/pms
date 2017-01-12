package com.ducetech.websocket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/** 
 * @ClassName: WSSessionLocalCache  
 * @author chensf
 * @date 2016年5月12日 下午4:51:46 
 * @Description: 缓存WSSession
 */
public class WSSessionLocalCache {

	private static Map<String, UserSocketVo> wsSessionCache = new HashMap<>();

	public static boolean exists(String sessionId) {
		if (!wsSessionCache.containsKey(sessionId)) {
			return false;
		} else {
			return true;
		}
	}

	public static void put(String sessionId, UserSocketVo UserSocketVo) {
		wsSessionCache.put(sessionId, UserSocketVo);
	}

	public static UserSocketVo get(String sessionId) {
		return wsSessionCache.get(sessionId);
	}
	
	public static UserSocketVo getUserSocketVoByUserId(String userId) {
		for (Entry<String, UserSocketVo> entry : wsSessionCache.entrySet()) {
			if(userId.equals(entry.getValue().getUserId())){
				return get(entry.getKey());
			}
		}
		return null;
	}

	public static void remove(String sessionId) {
		wsSessionCache.remove(sessionId);
	}

	public static List<UserSocketVo> getAllSessions() {
		return new ArrayList<>(wsSessionCache.values());
	}
}
