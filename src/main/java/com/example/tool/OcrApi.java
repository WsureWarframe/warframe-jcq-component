package com.example.tool;

import com.baidu.aip.ocr.AipOcr;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

public class OcrApi {
    private static final String APP_ID      = "14844959";
    private static final String API_KEY     = "UdpVmt6lWpamMgRxaByWySQC";
    private static final String SECRET_KEY  = "FXTVdwEfaiNlOBPwbCFFGGB2NVbwDZxm";

    private static AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

    private static HashMap<String, String> options = new HashMap<>();
    static {
        options.put("language_type", "CHN_ENG");
        options.put("detect_direction", "true");
        options.put("detect_language", "true");
        options.put("probability", "true");
    }

    public List<String> getBasicGeneral(byte[] path)
    {
        JSONObject jo = client.basicGeneral(path,options);
        if(jo.has("words_result"))
            return jsonToList((JSONArray) jo.get("words_result"));
        else
            return null;
    }

    public List<String> getBasicAccurateGeneral(byte[] path)
    {
        JSONObject jo = client.basicAccurateGeneral(path,options);
        if(jo.has("words_result"))
            return jsonToList((JSONArray) jo.get("words_result"));
        else
            return null;
    }

    public static List<String> jsonToList(JSONArray words)
    {
        List<String> resList = new ArrayList<>();
        Iterator<Object> it = words.iterator();
        while(it.hasNext())
        {
            resList.add(((JSONObject)(it.next())).getString("words"));
        }
        return resList;
    }

}
