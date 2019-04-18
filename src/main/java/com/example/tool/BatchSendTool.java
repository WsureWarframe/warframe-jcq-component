package com.example.tool;

import java.util.ArrayList;

import static com.sobte.cqp.jcq.event.JcqApp.CQ;

public class BatchSendTool extends Thread{
    private ArrayList<Long> qqs;
    private String name;
    private String type;

    public BatchSendTool(ArrayList<Long> qqs,String name,String type)
    {
        this.qqs = qqs;
        this.name = name;
        this.type = type;
    }

    @Override
    public void run() {
        super.run();
        for (Long qq:qqs)
        {
            try {
                Thread.sleep(800+Math.round(Math.random()*4000));
                CQ.sendPrivateMsg(qq,"你订阅的"+name+"已在'"+type+"'中出现");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
