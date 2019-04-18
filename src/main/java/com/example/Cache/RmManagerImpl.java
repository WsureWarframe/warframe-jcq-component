package com.example.Cache;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class RmManagerImpl implements ICacheManager {
    private static Map<String, EntityCache> rmCash = new ConcurrentHashMap<String, EntityCache>();

    /**
     * 存入缓存
     * @param key
     * @param cache
     */
    public void putCache(String key, EntityCache cache) {
        rmCash.put(key, cache);
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
            return rmCash.get(key);
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
            return rmCash.get(key).getDatas();
        }
        return null;
    }

    /**
     * 获取所有缓存
     * @return
     */
    public Map<String, EntityCache> getCacheAll() {
        return rmCash;
    }

    /**
     * 判断是否在缓存中
     * @param key
     * @return
     */
    public boolean isContains(String key) {
        return rmCash.containsKey(key);
    }

    /**
     * 清除所有缓存
     */
    public void clearAll() {
        rmCash.clear();
    }

    /**
     * 清除对应缓存
     * @param key
     */
    public void clearByKey(String key) {
        if (this.isContains(key)) {
            rmCash.remove(key);
        }
    }

    /**
     * 缓存是否超时失效
     * @param key
     * @return
     */
    public boolean isTimeOut(String key) {
        if (!rmCash.containsKey(key)) {
            return true;
        }
        EntityCache cache = rmCash.get(key);
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
        return rmCash.keySet();
    }

    public String getEnByZh(String weapon)
    {
        Set<String> weapons = this.getAllKeys();
        List<String> result  = new ArrayList<>();
        for(String s:weapons)
        {
            if(s.contains(weapon))
                result.add(s);
        }
        if(result.size()==0)
            return null;
        else if(result.size()==1)
            return rmCash.get(result.get(0)).getDatas().toString();
        else
        {
            return null;
        }
    }
}