package com.example.core;

import com.sobte.cqp.jcq.message.CQCode;

import com.sobte.cqp.jcq.entity.Member;
import java.util.List;
import java.util.Random;

import static com.sobte.cqp.jcq.event.JcqApp.CC;
import static com.sobte.cqp.jcq.event.JcqApp.CQ;

public class DrawCore {
    public void draw(String msg,long fromGroup,long fromQQ)
    {
        List<Long> longs =new CQCode().getAts(msg);
        Long pqq = null;
        for(Long qq:longs)
        {
            if(CQ.getGroupMemberInfo(fromGroup,qq).getAuthority()==1)
            {
                pqq=qq;
            }
        }
        String reMsg = "";
        if(pqq==null)
        {
            reMsg +="你的行动失败了，被@人中没有符合帮扶政策的群员，你将独享";
        }
        else {
            reMsg +="恭喜你成功带动了"+ CC.at(pqq)+"的经济发展，QWQ";
        }
        CQ.sendGroupMsg(fromGroup,CC.at(fromQQ)+reMsg);
        int t = new Random().nextInt(10*60);
        CQ.setGroupBan(fromGroup,fromQQ,t);
        if(pqq!=null)
            CQ.setGroupBan(fromGroup,pqq,t/2);
//        return reMsg;
    }
}
