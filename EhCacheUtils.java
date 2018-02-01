/**

 */
package com.example.demo.util;

import org.springframework.beans.factory.annotation.Autowired;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * Cache工具类
 * @author lcw
 * @version 2017-12-22
 */
public class EhCacheUtils {
	@Autowired
	private  CacheManager cacheManager;
	//private static CacheManager cacheManager = ((CacheManager)SpringContextHolder.getBean("cacheManager"));
	//设备ID列表
	public static final String CACHE_DEVICE_MAP = "deviceMap";
	private static final String SYS_CACHE = "sysCache";
	public static final String HOLIDAY_CACHE = "holidayCache";
	/**
	 * 获取SYS_CACHE缓存
	 * @param key
	 * @return
	 */
	public  Object get(String key) {
		return get(SYS_CACHE, key);
	}
	
	/**
	 * 写入SYS_CACHE缓存
	 * @param key
	 * @return
	 */
	public  void put(String key, Object value) {
		put(SYS_CACHE, key, value);
	}
	
	/**
	 * 从SYS_CACHE缓存中移除
	 * @param key
	 * @return
	 */
	public  void remove(String key) {
		remove(SYS_CACHE, key);
	}
	
	/**
	 * 获取缓存
	 * @param cacheName
	 * @param key
	 * @return
	 */
	public  Object get(String cacheName, String key) {
		Element element = getCache(cacheName).get(key);
		return element==null?null:element.getObjectValue();
	}

	/**
	 * 写入缓存
	 * @param cacheName
	 * @param key
	 * @param value
	 */
	public  void put(String cacheName, String key, Object value) {
		Element element = new Element(key, value);
		getCache(cacheName).put(element);
	}

	/**
	 * 从缓存中移除
	 * @param cacheName
	 * @param key
	 */
	public  void remove(String cacheName, String key) {
		getCache(cacheName).remove(key);
	}
	
	/**
	 * 获得一个Cache，没有则创建一个。
	 * @param cacheName
	 * @return
	 */
	private  Cache getCache(String cacheName){
		Cache cache = cacheManager.getCache(cacheName);
		if (cache == null){
			cacheManager.addCache(cacheName);
			cache = cacheManager.getCache(cacheName);
			cache.getCacheConfiguration().setEternal(true);
		}
		return cache;
	}

	public  CacheManager getCacheManager() {
		return cacheManager;
	}
	
}
