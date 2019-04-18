package com.example.core;

import java.util.Map;

public class KeyWords {

//    public static String [][] keys= {
//            {"警报","alerts_raw"},        //0
//            {"入侵","invasion_mini"},     //1
//            {"突击","sortie"},            //2
//            {"裂缝","void_raw"},          //3
//            {"奸商","baro_status"},       //4
//            {"地球","earth"},             //5
//            {"赏金","bounties_status"},   //6
//            {"平原","poe"}                //7
//
//    };
//    public static String isKeyWords(String str)
//    {
//        for(int i=0;i<keys.length;i++)
//        {
//            if (keys[i][0].equals(str))
//                return keys[i][1];
//        }
//        return null;
//    }

    public static String getKey(Map map, String value){
        String key = null;
        //Map,HashMap并没有实现Iteratable接口.不能用于增强for循环.
        for (Object getKey : map.keySet()) {
            if (map.get(getKey).equals(value)) {
                key = getKey.toString();
            }
        }
        return key;
        //这个key肯定是最后一个满足该条件的key.
    }

}
