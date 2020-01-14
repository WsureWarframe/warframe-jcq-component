package com.example.core;

import com.example.tool.HttpTool;
import com.example.tool.JsonTool;

import java.util.*;

import static com.example.Demo.CQ;

public class WfaSearch {

    public String createWfaMsg(String name)
    {

        try {
            List<Map> items = (List<Map>) JsonTool.jsonToMap(new HttpTool().getWfaContent(name));
            List<Map> show = new LinkedList<>();
            String reMsg ="从WFA查询到以下紫卡信息(截取价格最低前5条):\n";


            for(Map map:items)
            {
                if(((int)map.get("User_Status"))==2&&((int)map.get("Item_Status"))==0&&((int)map.get("IsSell"))==0&&!(map.get("Item_Name").toString()).contains("已出"))
                {
                    show.add(map);
                }
            }
//            show = bubbleSort(show,"Item_Price");
            Collections.sort(show, new Comparator<Map>() {
                @Override
                public int compare(Map o1, Map o2) {
                    Double d1 = (Double) o1.get("Item_Price");
                    Double d2 = (Double) o2.get("Item_Price");
                    return d1.compareTo(d2);
                }
            });
            if(show.size()<=0)
                return reMsg+="没有出售信息！";
            for(int i=0;i<(show.size()>=5?5:show.size());i++)
            {
                reMsg+="\n"+(i+1)+"."+show.get(i).get("Item_Name")+"("+show.get(i).get("Item_Price")+ "p)[id:"+show.get(i).get("User_Name") +"] "+show.get(i).get("LockingNum")+"锁\n";
                reMsg+= "  |"+show.get(i).get("Item_ResetNum")+"洗|"+show.get(i).get("Item_Level")+"级 ["+show.get(i).get("Item_Perporty").toString().replace("|",":")+"]\n";
            }
            return reMsg;
        } catch (Exception e) {
            e.printStackTrace();
            String reMsg ="你查询了一个错误的物品名称";
            return reMsg;
        }

    }

    public static List<Map> bubbleSort(List<Map> show,String key)
    {
        int size = show.size();
        for(int i = 0 ; i < size-1; i ++)
        {
            for(int j = 0 ;j < size-1-i ; j++)
            {
                if(objCompareTo(show.get(j).get(key),show.get(j+1).get(key))  >0 )  //交换两数位置
                {
                    show.add(j, show.get(j+1));
                    show.add(j+1+1, show.get(j+1));
                    show.remove(j+1);
                    show.remove(j+1+1);
                }
            }
        }
        return show;
    }

    public static int objCompareTo(Object a,Object b)
    {
        if(a instanceof Integer)
            a = new Double(((Integer) a).intValue());
        if(b instanceof Integer)
            b = new Double(((Integer) b).intValue());
        if(a instanceof Double && b instanceof Double)
        {
            return ((Double) a).compareTo((Double) b);
        } else if(a instanceof Integer && b instanceof Integer){
            return ((Integer) a).compareTo((Integer) b);
        } else if(a instanceof Long && b instanceof Long){
            return ((Long) a).compareTo((Long) b);
        }
        return 0;
    }
}
