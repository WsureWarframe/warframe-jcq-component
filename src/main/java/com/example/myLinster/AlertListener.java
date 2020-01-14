package com.example.myLinster;

import com.example.Cache.CacheManagerImpl;
import com.example.dao.GoodsMapper;
import com.example.dao.SubscribeMapper;
import com.example.entity.Goods;
import com.example.entity.Mission;
import com.example.tool.*;
import org.meowy.cqp.jcq.entity.Group;
import org.apache.ibatis.session.SqlSession;

import java.text.ParseException;
import java.util.*;

import static com.example.tool.TimeTool.dateToStamp;
import static com.example.Demo.CQ;

public class AlertListener extends Thread{
    private CacheManagerImpl cacheManagerImpl;
    private List<Group> groups;
    private ArrayList<Map<String,String>> rewards;
    private Map customSettings;

    volatile boolean flag = true;
    public AlertListener(CacheManagerImpl cacheManagerImpl,ArrayList<Map<String,String>> rewards,Map customSettings,List<Group> groups) {
        this.cacheManagerImpl = cacheManagerImpl;

        this.rewards = rewards;
        this.customSettings = customSettings;
        this.groups = groups;
        CQ.logInfo("this.groups",this.groups.toString());
/*
        Group a = new Group();
        a.setId(413992443L);
        a.setName("mammam");

        Group b = new Group();
        b.setId(1342323412L);
        b.setName("axcvsd");

        groups.add(a);
        groups.add(b);
*/
        CQ.logInfo("AlertLinster构造方法","完成");
    }
//
    public void startListen() {
        CQ.logInfo("AlertLinster 监听器","开始");
//        List<Group> newGroups = clearGroupList();
        this.start();
        CQ.logInfo("AlertLinster 监听器","完成");
    }

    public void run() {
        while (!isInterrupted()) {
            try {
                deal("alerts",groups);
                deal("invasions",groups);
                Thread.sleep(1000*60*2);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            } catch (Exception e) {
                e.printStackTrace();
                CQ.logInfo("监听器出现异常","监听器异常");
                CQ.sendPrivateMsg(844157922L,"alert监听器出现异常");
                break;
            }
        }
    }

    public void deal(String type,List<Group> newgroups) throws Exception {
        ArrayList<Mission> alertsMissions = getMissionFromObject(JsonTool.jsonToMap(new HttpTool().getHttpsContent(type)),type);
        SqlSession sqlSession = DBTools.getSession();
        GoodsMapper goodsMapper = sqlSession.getMapper(GoodsMapper.class);
        SubscribeMapper subscribeMapper = sqlSession.getMapper(SubscribeMapper.class);
        ArrayList<Goods> needGoods = goodsMapper.selectAllGoods();
        Set<String> goodNames = new LinkedHashSet<>();
        for(Mission m:alertsMissions)
        {
            if(!cacheManagerImpl.isContains(m.getId()))
            {
                cacheManagerImpl.putCache(m.getId(),m.getItemString(),m.getTimeOut());
                for(Map<String,String> map: rewards) {
                    if(m.getItemString().indexOf(map.get("item"))>-1)
                    {
                        CQ.logInfo(m.getId(),type+":"+map.get("msg"));
                        for (Group g:newgroups)
                        {
                            CQ.sendGroupMsg(g.getId(),map.get("msg")+customSettings.get(type+"Tail"));
                        }
                    }
                }
                for(Goods g:needGoods)
                {
                    if(m.getItemString().contains(g.getName()))
                    {
                        goodNames.add(g.getName());
                    }
                }
            }
        }
        for(String n:goodNames)
        {
            ArrayList<Long> qq = subscribeMapper.getSubQQs(n);
            if(qq.size()>0)
                new BatchSendTool(qq,n,type.equals("alerts")?"警报":"入侵").start();
        }
    }


    public  ArrayList<Mission> getMissionFromObject(Object obj,String type) throws ParseException {
        if(obj==null)
            return new ArrayList();
        ArrayList<Mission> missions=new ArrayList<>();

        switch (type)
        {
            case "alerts":
                ArrayList<Map> alerts = (ArrayList<Map>) obj;
                for(int i=0;i<alerts.size();i++)
                {
                    Map thisAlert=alerts.get(i);
                    if(!(boolean)thisAlert.get("active"))
                    {
                        Mission m =new Mission();
                        m.setId(thisAlert.get("id").toString());
                        m.setItemString(DictTool.searchZhFromAll(((Map<String,Map>)thisAlert.get("mission")).get("reward").get("asString").toString()));
                        m.setTimeOut(dateToStamp(thisAlert.get("expiry").toString())-System.currentTimeMillis()+(60*3000L));
                        missions.add(m);
                    }

                }
                break;
            case "invasions":
                ArrayList<Map> invasions = (ArrayList<Map>) obj;
                for (int i=0;i<invasions.size();i++)
                {
                    Map thisInvasions = invasions.get(i);
                    if(!(boolean)thisInvasions.get("completed"))
                    {
                        String attackerReward=((Map)thisInvasions.get("attackerReward")).get("asString").toString();
                        String defenderReward=((Map)thisInvasions.get("defenderReward")).get("asString").toString();

                        Mission m =new Mission();
                        m.setId(thisInvasions.get("id").toString());
                        m.setItemString(DictTool.searchZhFromAll(attackerReward)+"\tvs "+DictTool.searchZhFromAll(defenderReward));
                        m.setTimeOut(0L);

                        missions.add(m);
                    }
                    else {
                        if(cacheManagerImpl.isContains(thisInvasions.get("id").toString()))
                        {
                            cacheManagerImpl.clearByKey(thisInvasions.get("id").toString());
                        }
                    }
                }
                break;
        }

        return missions;
    }

    public List<Group> clearGroupList()
    {
        ArrayList groupIds = (ArrayList) customSettings.get("whitelist");

        List<Group> newGroups = new ArrayList<>();

        for (Object o:groupIds)
        {
            Group g=new Group();
            g.setId(Long.parseLong(o.toString()));
            newGroups.add(g);
        }

//        boolean flag=false;
//
//        for(int ii=0;ii<groups.size();ii++)
//        {
//
//            for(int i=0;i<groupIds.size();i++)
//            {
//                long id = Long.parseLong(groupIds.get(i).toString());
//
//                if (groups.get(ii).getId() == id)
//                    flag = true;
//            }
//            if(!flag)
//            {
//                newGroups.add(groups.get(ii));
//            }
//            flag=false;
//        }
        return newGroups;
    }

    public void exitLinsten()
    {
        this.interrupt();
    }
}