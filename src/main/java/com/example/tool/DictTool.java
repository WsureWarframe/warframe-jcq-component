package com.example.tool;

import com.example.Cache.DictManagerImpl;
import com.example.Cache.RivenManagerImpl;
import com.example.Cache.RmManagerImpl;

import java.util.*;

public class DictTool {
    public static String searchZhFrom(String En)
    {
        DictManagerImpl cm= new DictManagerImpl();
        return cm.isContains(En)?cm.getCacheDataByKey(En).toString():En;
    }

    public static String[] queryAllFromCache(String orgin)
    {
        String res[] =new String[2] ;

        DictManagerImpl cm = new DictManagerImpl();
        Set<String> allKeys = cm.getAllKeys();
        ArrayList<String>  matchedKeys = new ArrayList<>();
        for (String k:allKeys)
        {
            if(orgin.indexOf(k)>-1)
            {
                matchedKeys.add(k);
            }
        }
        if(matchedKeys.size()>0)
        {
            int index=0;
            for (int i=0;i<matchedKeys.size();i++)
            {
                if(matchedKeys.get(index).length()<matchedKeys.get(i).length())
                    index=i;
            }

//                    orgin.replace(matchedKeys.get(index),cm.getCacheDataByKey(matchedKeys.get(index)).toString());
            res[0] = matchedKeys.get(index);
            res[1] = cm.getCacheDataByKey(matchedKeys.get(index)).toString();
            return res;
        }
        res[0] = res[1] = orgin;
        return res;
    }

    public static String searchZhFromAll(String orgin)
    {
        String res="";

        String [] temp =queryAllFromCache(orgin);
        do
        {
            int p=orgin.indexOf(temp[0])+temp[0].length();
            String tempRes = orgin.substring(0,p);
            res += tempRes.replace(temp[0],temp[1]);
            orgin = orgin.substring(p,orgin.length());

            temp =queryAllFromCache(orgin);
        }while (!temp[0].equals(temp[1]));

        return res;
    }

    public static Map<String,String> searchRivenFromImg(List<String> words)
    {
        RmManagerImpl rmManagerImpl = new RmManagerImpl();

        Map<String,String> map = new HashMap<>();
        for(String key:rmManagerImpl.getAllKeys())
        {
            for(int i=0;i<words.size();i++)
            {
                String keyTemp = key.replaceAll("\\s*","").toLowerCase();
                String wordTemp = words.get(i).replaceAll("\\s*","").toLowerCase();
                if(wordTemp.contains(keyTemp))
                {
                    map.put(key,wordTemp.replace(keyTemp,"")+(i<words.size()-1?words.get(i+1).replaceAll("\\s*","").toLowerCase():""));
                }
            }
        }
        return map;
    }

}