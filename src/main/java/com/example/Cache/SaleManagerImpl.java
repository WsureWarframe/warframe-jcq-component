package com.example.Cache;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class SaleManagerImpl implements ICacheManager {
    private static Map<String, EntityCache> saleCash = new ConcurrentHashMap<String, EntityCache>();

    /**
     * 存入缓存
     * @param key
     * @param cache
     */
    public void putCache(String key, EntityCache cache) {
        saleCash.put(key, cache);
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
            return saleCash.get(key);
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
            return saleCash.get(key).getDatas();
        }
        return null;
    }

    /**
     * 获取所有缓存
     * @return
     */
    public Map<String, EntityCache> getCacheAll() {
        return saleCash;
    }

    /**
     * 判断是否在缓存中
     * @param key
     * @return
     */
    public boolean isContains(String key) {
        return saleCash.containsKey(key);
    }

    /**
     * 清除所有缓存
     */
    public void clearAll() {
        saleCash.clear();
    }

    /**
     * 清除对应缓存
     * @param key
     */
    public void clearByKey(String key) {
        if (this.isContains(key)) {
            saleCash.remove(key);
        }
    }

    /**
     * 缓存是否超时失效
     * @param key
     * @return
     */
    public boolean isTimeOut(String key) {
        if (!saleCash.containsKey(key)) {
            return true;
        }
        EntityCache cache = saleCash.get(key);
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
        return saleCash.keySet();
    }
}