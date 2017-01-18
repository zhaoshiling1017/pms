package com.ducetech.util;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.ducetech.framework.model.User;
/**
 * 
* @ClassName: CachePool 
* @Description: 角色缓存
* @author yett 
* @date 2016年10月25日 上午9:13:59 
*
 */
public class CachePool {
	private static CachePool cachePool;
	private Map<String, List<User>> cacheItems;
	private CachePool() {
		cacheItems =new ConcurrentHashMap<String, List<User>>();
	}
	/**
	 * 获取唯一实例
	 * @return instance
	 */
	public static CachePool getInstance() {
		if (cachePool == null) {
			synchronized (CachePool.class) {
				if (cachePool ==null) {
					cachePool =new CachePool();
				}
			}
		}
		return cachePool;
	}

	/**
	 * 获取所有cache信息
	 * @return cacheItems
	 */
	public Map<String, List<User>> getCacheItems() {
		return this.cacheItems;
	}

	/**
	 * 清空cache
	 */
	public void clearAllItems() {
		cacheItems.clear();
	}

	/**
	 * 获取指定cache信息
	 * @return cacheItem
	 */
	public List<User> getCacheItem(String key) {
		if (cacheItems.containsKey(key)) {
			return cacheItems.get(key);
		}
		return null;
	}

	/**
	 * 存放cache信息
	 */
	public void putCacheItem(String key,List<User> value) {
		if (!cacheItems.containsKey(key)) {
			cacheItems.put(key, value);
		}
	}

	/**
	 * 删除一个cache
	 */
	public void removeCacheItem(String key) {
		if (cacheItems.containsKey(key)) {
			cacheItems.remove(key);
		}
	}

	/**
	 * 获取cache长度
	 * @return size
	 */
	public int getSize() {
		return cacheItems.size();
	}

}
