package com.example.core;

import com.sobte.cqp.jcq.entity.Group;

import java.util.List;

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
        for(Group g:groups)
        {
            CQ.sendGroupMsg(g.getId(),msg);
        }
    }

}
