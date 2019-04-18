package com.example.Cache;

public class EntityCache {
    /**
     * 保存的数据
     */
    private  Object datas;

    /**
     * 设置数据失效时间,为0表示永不失效
     */
    private  long timeOut;

    /**
     * 最后刷新时间
     */
    private  long lastRefeshTime;

    public EntityCache(Object datas, long timeOut, long lastRefeshTime) {
        this.datas = datas;
        this.timeOut = timeOut;
        this.lastRefeshTime = lastRefeshTime;
    }
    public Object getDatas() {
        return datas;
    }
    public void setDatas(Object datas) {
        this.datas = datas;
    }
    public long getTimeOut() {
        return timeOut;
    }
    public void setTimeOut(long timeOut) {
        this.timeOut = timeOut;
    }
    public long getLastRefeshTime() {
        return lastRefeshTime;
    }
    public void setLastRefeshTime(long lastRefeshTime) {
        this.lastRefeshTime = lastRefeshTime;
    }


}