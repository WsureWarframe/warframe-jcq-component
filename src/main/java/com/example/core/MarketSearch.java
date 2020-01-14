package com.example.core;

import com.example.Cache.SaleManagerImpl;
import com.example.entity.Sale;
import com.example.tool.HttpTool;
import com.example.tool.JsonTool;

import java.util.*;

import static com.example.core.WfaSearch.bubbleSort;
import static com.example.core.WfaSearch.objCompareTo;
import static com.example.Demo.CQ;

public class MarketSearch {

    public static String getWMSearchKey(String msg,String key)
    {
        int index= msg.indexOf(key+" ");
        if(index>-1)
            return (msg.substring(index+key.length()+1)).trim();
        return null;
    }

    public static String getFirstSearchKey(String msg,String key)
    {
        int index= msg.indexOf(key+" ");
        if(index==0)
            return (msg.substring(index+key.length()+1)).trim();
        return null;
    }

    public static String getAtKey(String msg,String key)
    {
        int index= msg.indexOf(key);
        if(index>-1)
            return (msg.substring(index+key.length())).trim();
        return null;
    }
    public static String dealString(String str)
    {
        str= str.replaceAll("\\s*", "").toUpperCase();
        if(str.indexOf("总图")>-1)
            str=str.replace("总图","蓝图");
        if(getContentWordCount(str)==1&&str.contains("P"))
            str=str.replace("P","PRIME");
        return str;
    }
    public static String searchSaleList(String str)
    {
        str = dealString(str);
        List<Sale> sales = searchFromCash(str);
        if(sales.size()==0)
        {
            return "未找到你查询的物品，可能是关键词过长或者你的输入有误，建议精简关键词";
        }
        else if(sales.size()==1)
        {
            String res = "你查询的物品是:"+sales.get(0).getZh()+"\n";
            res+=MarketSearch.createMsg(JsonTool.jsonToMap(new HttpTool().getWMContent(sales.get(0).getSearch(),"orders")),JsonTool.jsonToMap(new HttpTool().getWMContent(sales.get(0).getSearch(),"statistics")));
            return res;
        }else if(sales.size()>10)
        {
            return "你输入的关键词相关物品超过10钟，建议调整输入";
        }else {
            for(Sale s:sales)
            {
                if(s.getZh().equals(str)||s.getZh().contains("一套"))
                {
                    String res = "你查询的物品是:"+s.getZh()+"\n";
                    res+=MarketSearch.createMsg(JsonTool.jsonToMap(new HttpTool().getWMContent(s.getSearch(),"orders")),JsonTool.jsonToMap(new HttpTool().getWMContent(s.getSearch(),"statistics")));
                    return res;
                }
            }
            String res="你可能想搜索以下内容：\n";
            for(Sale s:sales)
            {
                res += (s.getZh() +"\n");
            }
            res+="\n请搜索以上内容以便于准确获取信息";
            return res;
        }

    }

    public static List<Sale> searchFromCash(String str)
    {
        List<Sale> sales =new ArrayList<>();

        SaleManagerImpl saleManager = new SaleManagerImpl();
        Set<String> set = saleManager.getAllKeys();
        for (String s:set)
        {
            if(s.replaceAll("\\s*", "").indexOf(str)>-1)
            {
                sales.add((Sale) saleManager.getCacheDataByKey(s));
            }
        }
        return sales;
    }


    public static int getContentWordCount(String content){
        content = content.replaceAll("\\s", "");//可以替换大部分空白字符，不限于空格
        int hzCount = 0;//汉字数量
        int szCount = 0;//数字数量
        int zmCount = 0;//字母数量
        int fhCount = 0;//标点符号数量
        for(int i = 0;i < content.length();i++){
            char c = content.charAt(i);
            if(c >= '0' && c <= '9'){
                szCount++;
            }else if((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')){
                zmCount++;
            }else if(Character.toString(c).matches("[\\u4E00-\\u9FA5]+")){
                hzCount++;
            }else{
                fhCount++;
            }
        }
        return zmCount;
    }


    public static String createMsg(Object orders,Object statistics)
    {
        String reMsg="";
        try {
            ArrayList<Map> ordersMaps=(ArrayList<Map>) (((Map)((Map)orders).get("payload")).get("orders"));

            ArrayList<Map> statisticsMaps=(ArrayList<Map>) ((Map)((Map)((Map)statistics).get("payload")).get("statistics_live")).get("48hours");

            List<Map> top5seller = new ArrayList<>();
            Map prices = new HashMap();

            for (Map map:ordersMaps)
            {
                if(map.get("order_type").toString().equals("sell")&&!((Map)map.get("user")).get("status").toString().equals("offline"))
                {
                    top5seller.add(map);
                }
            }
            Collections.sort(top5seller, new Comparator<Map>() {
                @Override
                public int compare(Map o1, Map o2) {
                    return objCompareTo(o1.get("platinum"),o2.get("platinum"));
                }
            });
//                    bubbleSort(top5seller,"platinum");
            prices=statisticsMaps.get(statisticsMaps.size()-1);

            reMsg =  "估计价格区间："+prices.get("min_price")+" - "+prices.get("max_price")+"p\n";
            reMsg += (      "均价：" +prices.get("avg_price")+"p\n\n");
            reMsg += (      "捕获到top5卖家信息(>^ω^<)\n");

            for (int i=0;i<((top5seller.size()<5)?top5seller.size():5);i++)
            {
                Map m = top5seller.get(i);
                Map user = (Map) m.get("user");
                reMsg += ( user.get("ingame_name")+"(状态:"+user.get("status")+") : "+m.get("platinum")+"p(数量:"+m.get("quantity")+")\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
            reMsg ="信息被敌人截获，请重试";
        }


        return reMsg;
    }

}
