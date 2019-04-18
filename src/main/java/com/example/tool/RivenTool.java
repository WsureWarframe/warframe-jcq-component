package com.example.tool;

import com.example.entity.Riven;

import java.util.LinkedHashMap;

public class RivenTool {

    public static String yourRivenString(LinkedHashMap<String,Double> yourRiven)
    {
        String reStr ="";
        for (String key : yourRiven.keySet()) {
            reStr += "\n"+(yourRiven.get(key)>0.0?"+":"")+yourRiven.get(key) +(key.contains("连击持续时间")?"秒":"%")+key;
        }
        return reStr;
    }

    public static String zhRivenType(String en)
    {
        switch (en)
        {
            case "Shotgun": return "霰弹枪 裂罅";
            case "Rifle" : return "步枪 裂罅";
            case "Pistol": return "手枪 裂罅";
            case "Melee" : return "近战 裂罅";
            default: return null;
        }
    }

    public static String getVeiledRivenMsg(LinkedHashMap<Riven,LinkedHashMap<String,Double>> rivenAndPropertys)
    {
        String reStr = "";
        for(Riven r:rivenAndPropertys.keySet())
        {
            if(rivenAndPropertys.get(r)==null)
                reStr += "\n"+zhRivenType(r.getType()) ;
        }
        return reStr;
    }
}
