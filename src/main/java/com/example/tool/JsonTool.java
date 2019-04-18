package com.example.tool;


import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.Map;

public class JsonTool {
    public static Object jsonToMap(String json)
    {
        try {
            ObjectMapper objectMapper=new ObjectMapper();
            Object all=objectMapper.readValue(json,Object.class);
            return all;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public static String ObjToJson(Object obj)
    {
        String res="";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            res = objectMapper.writeValueAsString(obj);
        } catch (IOException e) {
            e.printStackTrace();
            res ="出现异常";
        }
        return res;
    }

    public static Object jsonToAnyType(String json,Class c)
    {
        try {
            ObjectMapper objectMapper=new ObjectMapper();
            Object all=objectMapper.readValue(json,c);
            return all;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }


}
