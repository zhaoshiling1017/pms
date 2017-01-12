package com.ducetech.util;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MyCache {
	
	private static Map<String, List<String>> roleIdsCache = new ConcurrentHashMap<>();

	public Map<String, List<String>> getRoleIdsCache() {
		return roleIdsCache;
	}

	public void setRoleIdsCache(Map<String, List<String>> roleIdsCache) {
		MyCache.roleIdsCache = roleIdsCache;
	}
}