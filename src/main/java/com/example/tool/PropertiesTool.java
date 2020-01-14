package com.example.tool;

import com.example.entity.CustomSettings;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

import static com.example.Demo.CQ;

public class PropertiesTool {

    public static String[][] defaultSettings = {
            {"help", "help"},
            {"tip", "Nymph的功能如下：\n警报\t入侵\n突袭\t裂缝\n奸商\t地球\n平原\t达尔沃\n大黄\tWM查询"},
            {"head", "指挥官阁下，Nymph为你侦测到以下信息："},
            {"robotName", "Nymph"},
            {"masterQQ", "844157922"},
            {"alertsTail", "\n\n发送“警报”查看详情"},
            {"invasionsTail", "\n\n发送“入侵”查看详情"},
            {"wm","WM查询"},
            {"wiki","wiki"},
            {"notice","启动完成"},
            {"wfa","wfa"},
            {"noticeFlag","true"},
            {"sendGroup","发送全部群"},
            {"draw","抽奖"},
            {"persecute","一带一路"}
    };

    public static String[][] defaultKeys= {
            {"alerts","警报"},
            {"invasions","入侵"},
            {"sortie","突击"},
            {"fissures","裂缝"},
            {"earthCycle","地球"},
            {"cetusCycle","平原"},
            {"voidTrader","奸商"},
            {"simaris","大黄"},
            {"dailyDeals","达尔沃"},
            {"events","活动"},
            {"persistentEnemies","小小黑"}
    };

    public static Object readProperties(String path, String fileName, CustomSettings settings) throws IOException {
        Properties props = new Properties();

        InputStream inStream = new FileInputStream(new File(path+fileName));
        props.load(inStream);
        inStream.close();
        Set keys = props.keySet();
        for (Iterator<String> it = keys.iterator(); it.hasNext();){
            String k = it.next();
            CQ.logInfo(k , props.getProperty(k));
        }
        return null;
    }

    public static ArrayList<Map<String,String>> initRewards(String rewardsPath)
    {
        ArrayList<Map<String,String>> res = (ArrayList<Map<String, String>>) JsonTool.jsonToMap(fileReader(rewardsPath));
        return res;
    }

    public static Map initSettingMap(String settingPath)
    {
        CQ.logInfo("settingPath",settingPath);
        Map settings= (Map) JsonTool.jsonToMap(fileReader(settingPath));
        for (String[] defaultSetting : defaultSettings) {
            if (settings.get(defaultSetting[0]) == null)
                settings.put(defaultSetting[0], defaultSetting[1]);
        }
        return settings;
    }

    public static Map initKeysMap(String keysPath)
    {
        Map keys= (Map) JsonTool.jsonToMap(fileReader(keysPath));
        for (String[] defaultKey : defaultKeys) {
            if (keys.get(defaultKey[0]) == null)
                keys.put(defaultKey[0], defaultKey[1]);
        }
        return keys;

    }

    public static Map initDictMap()
    {
        String [] types = {"Dict","Sale","Alert","Invasion","Riven"};
        Map<String,List> dict = new HashMap<>();
        for(String s:types)
        {
            dict.put(s,(List) JsonTool.jsonToMap(new HttpTool().getWfaTable(s.toLowerCase())));
        }
        return dict;
    }

    public static String fileReader(String path)
    {
        BufferedReader in = null;
        StringBuffer sb = new StringBuffer();
        try {
            in = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
            String str = null;
            while ((str = in.readLine()) != null) {
                sb.append(str);
            }
        } catch (Exception ex) {

        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
            }
        }
        String result = sb.toString();
        return result;
    }


}
