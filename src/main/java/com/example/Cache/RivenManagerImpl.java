package com.example.Cache;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class RivenManagerImpl implements ICacheManager {
    private static Map<String, EntityCache> rivenCache = new ConcurrentHashMap<String, EntityCache>();

    /**
     * 存入缓存
     * @param key
     * @param cache
     */
    public void putCache(String key, EntityCache cache) {
        rivenCache.put(key, cache);
    }

    /**
     * 存入缓存
     * @param key
     * @param cache
     */
    public void putCache(String key, Object datas, long timeOut) {
        timeOut = timeOut > 0L ? timeOut : 0L;
        putCache(key, new EntityCache(datas, timeOut, System.currentTimeMillis()));
    }

    /**
     * 获取对应缓存
     * @param key
     * @return
     */
    public EntityCache getCacheByKey(String key) {
        if (this.isContains(key)) {
            return rivenCache.get(key);
        }
        return null;
    }

    /**
     * 获取对应缓存
     * @param key
     * @return
     */
    public Object getCacheDataByKey(String key) {
        if (this.isContains(key)) {
            return rivenCache.get(key).getDatas();
        }
        return null;
    }

    /**
     * 获取所有缓存
     * @return
     */
    public Map<String, EntityCache> getCacheAll() {
        return rivenCache;
    }

    /**
     * 判断是否在缓存中
     * @param key
     * @return
     */
    public boolean isContains(String key) {
        return rivenCache.containsKey(key);
    }

    /**
     * 清除所有缓存
     */
    public void clearAll() {
        rivenCache.clear();
    }

    /**
     * 清除对应缓存
     * @param key
     */
    public void clearByKey(String key) {
        if (this.isContains(key)) {
            rivenCache.remove(key);
        }
    }

    /**
     * 缓存是否超时失效
     * @param key
     * @return
     */
    public boolean isTimeOut(String key) {
        if (!rivenCache.containsKey(key)) {
            return true;
        }
        EntityCache cache = rivenCache.get(key);
        long timeOut = cache.getTimeOut();
        long lastRefreshTime = cache.getLastRefeshTime();
        if (timeOut != 0L && System.currentTimeMillis() - lastRefreshTime >= timeOut) {
            return true;
        }
        return false;
    }

    /**
     * 获取所有key
     * @return
     */
    public Set<String> getAllKeys() {
        return rivenCache.keySet();
    }
}