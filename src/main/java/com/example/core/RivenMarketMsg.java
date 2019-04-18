package com.example.core;

import com.example.tool.HttpTool;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Map;

import static com.example.tool.RivenMarketTool.elementsToRivenModel;
import static com.example.tool.RivenMarketTool.rivenMarketUrlCreate;

public class RivenMarketMsg {

    public static String createRivenMarketMsg(String weapon,Map reProperty) {
        if (weapon == null)
            return "你查询了一个错误的物品名称";
        String str = new HttpTool().httpsRequest(rivenMarketUrlCreate(5, true, weapon, 9999, 1), "GET", null,null);
        if(str.equals("[]"))
            return "与Riven.Market通信超时，请重试";
        Document doc = Jsoup.parse(str);
        Elements rivenElements = doc.select(".riven");
        StringBuffer result = new StringBuffer();
        result.append("从Riven.Market查询到以下紫卡信息(截取价格最低前5条):\n");
        for (Element e : rivenElements) {
            if ("riven".equals(e.className())) {
                result.append(elementsToRivenModel(e).fullInfoString(reProperty) + "\n");
            }
        }
        return result.toString();
    }
}
