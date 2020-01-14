package com.example.myLinster;

import static com.example.Demo.CQ;

public class TestLinster {
    public TestLinster() {

    }

    public void startListen() {
        new Thread(){
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(3000);
                        CQ.logInfo(this.getClass().getName(),"aaa");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

    }
}
