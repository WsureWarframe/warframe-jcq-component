package com.example.tool;

import java.util.ArrayList;

public class AlertTool {

    public static String ROBOTNAME ="Nymph";
    public static String help= ROBOTNAME +"的功能如下：\n警报\n入侵\n突袭\n裂缝\n奸商\n地球\n赏金\n平原";

    public static String Obj2Info(Object obj,int type)
    {
        ArrayList<Object> arr=(ArrayList<Object>) obj;
        if(arr.size()<=0)
            return ROBOTNAME +" 目前没有拦截到此类信息";
        String msgStr= ROBOTNAME +" 为您侦测到以下信息：\n\n";
        for(int i=0;i<arr.size();i++)
        {

            if(type<4)
            {
                msgStr += (i+1)+".";
                ArrayList<Object> info=(ArrayList<Object>) arr.get(i);
                switch (type)
                {
                    case 0:msgStr += ((String) info.get(2)+(String) info.get(1) +"("+(String)info.get(3)+")\n");
                        msgStr+= ("奖励："+(String) info.get(9)+"+星币:"+(String) info.get(11)+"\n");
                        msgStr+=((String) info.get(10)).equals("")? "": ("模式："+(String) info.get(10)+"\n");
                        msgStr+= ("时间："+TimeTool.etaTime(Long.valueOf(String.valueOf(info.get(8))).longValue())+"\n");
                        break;
                    case 1:msgStr += ((String) info.get(2)+info.get(1) +"("+info.get(10)+info.get(11)+"%)\n");
                        msgStr+= ("阵营：\t"+ info.get(3)+" vs " +info.get(6)+"\n");
                        msgStr+= ("奖励：\t"+ info.get(5)+" vs " +info.get(8)+"\n");
//                        msgStr+= ("时间："+setDateInfo(Long.valueOf(String.valueOf(info.get(9))).longValue())+"\n");
                        break;
                    case 2:msgStr += ((String) info.get(2)+info.get(1) +"("+info.get(5)+"-"+info.get(6)+")\n");
                        msgStr+= ("任务：\t"+ info.get(3)+" ( " +info.get(4)+" )\n");
                        msgStr+= ("时间：\t"+ TimeTool.etaTime(Long.valueOf(String.valueOf(info.get(7))).longValue())+"\n");
                        break;
                    case 3:msgStr += ("["+info.get(5)+"] "+ info.get(2)+info.get(1) +"("+info.get(4)+")\n");
                        msgStr+= ("任务：\t"+ info.get(3)+" \n");
                        msgStr+= ("时间：\t"+ TimeTool.etaTime(Long.valueOf(String.valueOf(info.get(6))).longValue())+"\n");
                        break;
                }
            }
            else
            {

            }
        }
        switch (type)
        {
            case 4:
                long nowTime = System.currentTimeMillis() / 1000;
                msgStr += ("奸商状态："+arr.get(3)+"\n");
                msgStr += (Long.valueOf(String.valueOf(arr.get(4))).longValue()>nowTime
                        ? ("奸商"+TimeTool.etaTime(Long.valueOf(String.valueOf(arr.get(4))).longValue())+"回归\n")
                        : ("奸商"+TimeTool.etaTime(Long.valueOf(String.valueOf(arr.get(5))).longValue())+"离开\n")
                );
                break;
            case 5:
            case 6:
            case 7:
                msgStr += (arr.get(0)+":"+arr.get(1)+"\n");
                msgStr+= ("时间：\t"+ TimeTool.etaTime(Long.valueOf(String.valueOf(arr.get(2))).longValue())+"\n");
                break;
        }
        return msgStr;
    }





}
