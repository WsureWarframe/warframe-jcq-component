package com.example.core;

import com.example.Cache.CacheManagerImpl;
import com.example.tool.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.tool.TimeTool.stmpEtaTime;

public class CreateMsg {


    public static String getMsg(Map customSettings,String type)
    {
        CacheManagerImpl cacheManager = new CacheManagerImpl();
        if(!cacheManager.isContains(type))
        {
            cacheManager.putCache(type,new HttpTool().getHttpsContent(type),2*60*1000L);
        }

        return mapToMsg(customSettings,JsonTool.jsonToMap(cacheManager.getCacheDataByKey(type).toString()),type);
    }



    public static String mapToMsg(Map settings,Object msg,String type)
    {
        String resMsg=settings.get("head").toString()+"\n\n";
        try {
            switch (type)
            {
                case "alerts":ArrayList<Map> alerts = (ArrayList<Map>) msg;
                    for(int i=0;i<alerts.size();i++)
                    {
                        Map thisAlert=alerts.get(i);
                        resMsg += ((i+1) +"." +SearchTool.nodeSearch(((Map)thisAlert.get("mission")).get("node").toString())+" "+DictTool.searchZhFrom(((Map)thisAlert.get("mission")).get("faction").toString())+"\n" );
                        resMsg += ( "类型："+DictTool.searchZhFrom(((Map)thisAlert.get("mission")).get("type").toString())+"\n");
                        resMsg += ( "奖励："+DictTool.searchZhFromAll(((Map<String,Map>)thisAlert.get("mission")).get("reward").get("asString").toString())  +"\n");
                        resMsg += ( "时间："+stmpEtaTime(thisAlert.get("expiry").toString())+"\n");
                    }
                    break;
                case "sortie":Map sortie=(Map) msg;
                    ArrayList<Map> variants=(ArrayList<Map>) sortie.get("variants");
                    for(int i=0;i<variants.size();i++)
                    {
                        Map thisVariants=variants.get(i);
                        resMsg += ((i+1) +"." +SearchTool.nodeSearch(thisVariants.get("node").toString())+"\n" );
                        resMsg += ( "任务："+DictTool.searchZhFrom(thisVariants.get("missionType").toString())+"("+DictTool.searchZhFromAll(thisVariants.get("modifier").toString())+")"+"\n" );
                    }
                    resMsg += ( "\n时间："+stmpEtaTime(sortie.get("expiry").toString())+"\n");
                    break;
                case "invasions":ArrayList<Map> invasions = (ArrayList<Map>) msg;
                    for (int i=0,j=0;i<invasions.size();i++)
                    {
                        Map thisInvasions = invasions.get(i);
                        if(!(boolean)thisInvasions.get("completed"))
                        {
                            resMsg += ((j+1) +"." +SearchTool.nodeSearch(thisInvasions.get("node").toString())+"\n" );
                            resMsg += ("\t攻\t\t受\n");
                            resMsg += (thisInvasions.get("attackingFaction")+"\t"+thisInvasions.get("defendingFaction")+"\n");
                            String attackerReward=((Map)thisInvasions.get("attackerReward")).get("asString").toString();
                            String defenderReward=((Map)thisInvasions.get("defenderReward")).get("asString").toString();
                            resMsg += ( (attackerReward.equals("")?"没有奖励":DictTool.searchZhFromAll(attackerReward))+"\tvs "+(defenderReward.equals("")?"没有奖励":DictTool.searchZhFromAll(defenderReward))+"\n");
                            int needRuns=(int)thisInvasions.get("requiredRuns")-(Math.abs((int)thisInvasions.get("count")));
                            resMsg += ("进度剩余：" + String.format("%.2f", Double.parseDouble(thisInvasions.get("completion").toString()))+"% ("+(needRuns>0?"还需"+needRuns:"超出"+(0-needRuns))+"助攻喵~)\n\n");
                            j++;
                        }
                    }
                    break;
                case "fissures":ArrayList<Map> fissures= (ArrayList<Map>) msg;
                    for (Map map:fissures)
                    {
                        if(!(boolean)map.get("expired"))
                        {
                            resMsg += ("[T"+map.get("tierNum")+"] "+SearchTool.nodeSearch(map.get("node").toString())+"\n");
                            resMsg += ("任务："+DictTool.searchZhFrom(map.get("missionType").toString())+"("+map.get("enemy")+")\n" );
                            resMsg += ( "时间："+stmpEtaTime(map.get("expiry").toString())+"\n\n");
                        }
                    }
                    break;
                case "voidTrader":Map voidTrader = (Map) msg;
                    ArrayList<Map> inventory = (ArrayList<Map>) voidTrader.get("inventory");
                    resMsg +="奸商状态：";
                    if((boolean)voidTrader.get("active"))
                    {
                        resMsg += "到达\n";
                        resMsg += "\t奸商货物\n";
                        int index=1;
                        for (Map good:inventory)
                        {
                            resMsg += (index+"."+DictTool.searchZhFrom(good.get("item").toString())+"("+good.get("ducats")+"Du+"+good.get("credits")+"Cr)"+"\n" );
                            index++;
                        }

                        resMsg += "\n奸商"+stmpEtaTime(voidTrader.get("expiry").toString())+"离开";
                    }
                    else {
                        resMsg += "离开\n";
                        resMsg += "\n奸商"+stmpEtaTime(voidTrader.get("activation").toString())+"到达";
                    }
                    break;
                case "earthCycle":Map earthCycle= (Map) msg;
                    resMsg += "地球状态：";
                    if((boolean)earthCycle.get("isDay")){
                        resMsg += "白天";
                    } else {
                        resMsg += "夜晚";
                    }
                    resMsg += "\n时间："+stmpEtaTime(earthCycle.get("expiry").toString());
                    break;
                case "cetusCycle":Map cetusCycle= (Map) msg;
                    resMsg += "平原状态：";
                    if((boolean)cetusCycle.get("isDay")){
                        resMsg += "白天";
                    } else {
                        resMsg += "夜晚";
                    }
                    resMsg += "\n时间："+stmpEtaTime(cetusCycle.get("expiry").toString());
                    break;
                case "simaris":Map simaris= (Map) msg;
                    String target=DictTool.searchZhFrom(simaris.get("target").toString());
                    resMsg += "\"请问Simaris今天要来点兔子吗？\"\n";
                    resMsg += "\"不，猎手，我需要"+target+"!\"\n\n";
                    resMsg += "关于"+target+"，请移步:https://warframe.huijiwiki.com/wiki/"+simaris.get("target").toString().replace(" ","_");
                    break;
                case "dailyDeals":ArrayList<Map> dailyDeals = (ArrayList<Map>) msg;
                    resMsg += "\"听说你也是个广东♂人，所以我们可能是老♂乡，便宜卖你啦！\"\n";
                    for (Map good:dailyDeals)
                    {
                        resMsg += ("商品："+DictTool.searchZhFrom(good.get("item").toString())+"\n");
                        resMsg += ("价格："+good.get("salePrice")+"p (原价:"+good.get("originalPrice")+"p)\n");
                        resMsg += ("库存："+((int)good.get("total")-(int)good.get("sold"))+"\n");
                        resMsg += ("时间："+stmpEtaTime(good.get("expiry").toString()) +"\n");
                    }

                    break;
                case "persistentEnemies":
                    List<Map> persistentEnemies = (List<Map>) msg;
                    if(persistentEnemies.size()>0)
                    {
                        resMsg += "当前有以下小小黑:\n";
                        for (Map<String,Object> m:persistentEnemies)
                        {
                            resMsg += ("\n类型:"+DictTool.searchZhFrom(m.get("agentType").toString()) +"(生命:"+(String.format("%.2f", Double.parseDouble(m.get("healthPercent").toString())*100))+"%)\n");
                            resMsg += ("时间:"+stmpEtaTime(m.get("lastDiscoveredTime").toString())+"\n");
                            resMsg += ("位置:"+SearchTool.nodeSearch(m.get("lastDiscoveredAt").toString())+"\n");
                            resMsg += ("状态:");
                            if((boolean)m.get("isDiscovered"))
                            {
                                resMsg += "可以追捕喵(>^ω^<)\n";
                            }
                            else
                                resMsg += "正在逃跑，无法追捕喵(>^ω^<)\n";
                        }
                    }
                    else
                    {
                        resMsg += "当前没有小小黑哦\n";
                    }


                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();

            resMsg ="信号被S佬干扰,请1分钟后再试";
        }


        return resMsg;
    }


}
