package com.example.core;

import com.sobte.cqp.jcq.entity.Group;

import java.util.List;
import java.util.Random;

import static com.sobte.cqp.jcq.event.JcqApp.CQ;

public class GroupMsg {
    List<Group> groups ;

    public GroupMsg()
    {
        groups = CQ.getGroupList();

    }

    public void sendGroupMsg(String msg)
    {
//        while (groups.size()==0)
//        {
//            try {
//                Thread.sleep(100L);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            groups = CQ.getGroupList();
//        }

        new Runnable() {
            /**
             * When an object implementing interface <code>Runnable</code> is used
             * to create a thread, starting the thread causes the object's
             * <code>run</code> method to be called in that separately executing
             * thread.
             * <p>
             * The general contract of the method <code>run</code> is that it may
             * take any action whatsoever.
             *
             * @see Thread#run()
             */
            @Override
            public void run() {
                for(Group g:groups)
                {
                    Random random = new Random();
                    try {
                        Thread.sleep(random.nextInt(3000)+3000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    CQ.sendGroupMsg(g.getId(),msg);
                }
            }
        }.run();

    }

}
