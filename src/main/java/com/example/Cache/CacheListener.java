package com.example.Cache;

import static com.example.Demo.CQ;

public class CacheListener extends Thread{
    private CacheManagerImpl cacheManagerImpl;
    public CacheListener(CacheManagerImpl cacheManagerImpl) {
        this.cacheManagerImpl = cacheManagerImpl;
    }
    volatile boolean flag = true;

    public void run() {
        while (!isInterrupted()) {
            for(String key : cacheManagerImpl.getAllKeys()) {
                if (cacheManagerImpl.isTimeOut(key)) {
                    cacheManagerImpl.clearByKey(key);
                    CQ.logInfo("缓存被清除",key);
//                            System.out.println("缓存被清除"+key);
                }
            }
            try {
                Thread.sleep(100L);
            } catch(InterruptedException e){
                e.printStackTrace();
                break;//捕获到异常之后，执行break跳出循环。
            }
        }
    }


    public void startListen() {
        this.start();

    }
    public void exitLinsten()
    {
        this.interrupt();
    }
}