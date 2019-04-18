package com.example.tool;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AliasTool {
    public static Map<String, Map<String,String>> alias = new ConcurrentHashMap<>();


    public void init(Map<String,Map> rmProperty)
    {
        for(String s:rmProperty.keySet())
        {
            String first = rmProperty.get(s).get("Prefix").toString();
            Map<String,String> map =new HashMap<>();
            map.put(s,rmProperty.get(s).get("Name").toString());
//            Double type = (Double) ;
            for(String s1:rmProperty.keySet())
            {
//                type =type==null?(Double) rmProperty.get(s1).get("MeleeOnly"):type;
                if(!s1.equals(s)&&isPass((Boolean) rmProperty.get(s).get("MeleeOnly"),(Boolean)rmProperty.get(s1).get("MeleeOnly")))
                {
                    String second =  rmProperty.get(s1).get("Suffix").toString();
                    map.put(s1,rmProperty.get(s1).get("Name").toString());
                    alias.put(first+second,map);
//                    System.out.println(first+second);
                    for(String s2:rmProperty.keySet())
                    {
                        if(!s2.equals(s)&&!s2.equals(s1)&&isPass((Boolean) rmProperty.get(s).get("MeleeOnly"),(Boolean)rmProperty.get(s1).get("MeleeOnly"),(Boolean)rmProperty.get(s2).get("MeleeOnly")))
                        {
                            String third = rmProperty.get(s2).get("Prefix").toString();
                            map.put(s2,rmProperty.get(s2).get("Name").toString());
                            alias.put(third+"-"+first.toLowerCase()+second,map);
//                            System.out.println(third+"-"+first.toLowerCase()+second);
                        }
                    }
                }
            }
        }
    }

    public static boolean isPass(Boolean t1,Boolean t2,Boolean t3)
    {
        String type=""+getType(t1)+getType(t2)+getType(t3);
        return !(type.contains("+")&&type.contains("-"));
    }
    public static boolean isPass(Boolean t1,Boolean t2)
    {
        String type=""+getType(t1)+getType(t2);
        return !(type.contains("+")&&type.contains("-"));
    }
    public static char getType(Boolean t)
    {
        if(t==null)
            return '-';
        else if(t==false)
            return ' ';
        else
            return '+';
    }
}
