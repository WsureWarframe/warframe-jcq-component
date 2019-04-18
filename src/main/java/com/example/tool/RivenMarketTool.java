package com.example.tool;

import com.example.entity.RivenMarketItem;
import org.eclipse.jetty.util.StringUtil;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RivenMarketTool {


    public static RivenMarketItem elementsToRivenModel(Element e)
    {
        RivenMarketItem riven = new RivenMarketItem();
        riven.setId(e.attr("id"));
        riven.setWeapon(e.attr("data-weapon"));
        riven.setWtype(e.attr("data-wtype"));
        riven.setName(e.attr("data-name"));
        riven.setPrice(Integer.parseInt(e.attr("data-price")));
        String age = e.attr("data-age");
        if(age.contains("new")) {
            riven.setAge(age.replace("new","新上架"));
        } else if(age.contains("day")) {
            riven.setAge(age.replace("day","天"));
        } else if(age.contains("week")) {
            riven.setAge(age.replace("week","周"));
        } else if(age.contains("month")) {
            riven.setAge(age.replace("month","月"));
        }
        riven.setRank(Integer.parseInt(e.attr("data-rank")));
        riven.setMr(Integer.parseInt(e.attr("data-mr")));
        riven.setRerolls(Integer.parseInt(e.attr("data-rerolls")));
        riven.setPolarity(e.attr("data-polarity"));
        riven.setStat1(e.attr("data-stat1"));
        riven.setStat1val(Double.parseDouble(e.attr("data-stat1val")));
        riven.setStat2(e.attr("data-stat2"));
        riven.setStat2val(Double.parseDouble(e.attr("data-stat2val")));
        if(StringUtil.isNotBlank(e.attr("data-stat3")))
        {
            riven.setStat3(e.attr("data-stat3"));
            riven.setStat3val(Double.parseDouble(e.attr("data-stat3val")));
        }
        if(StringUtil.isNotBlank(e.attr("data-stat4")))
        {
            riven.setStat4(e.attr("data-stat4"));
            riven.setStat4val(Double.parseDouble(e.attr("data-stat4val")));
        }
        if(!(e.select(".ingame")).isEmpty())
        {
            riven.setStatus("ingame");
        } else if(!(e.select(".offline")).isEmpty())
        {
            riven.setStatus("offline");
        } else {
            riven.setStatus("online");
        }
        riven.setSeller(e.select(".seller").text());
        return riven;
    }

    public static String rivenMarketUrlCreate(int limit,boolean onlinefirst,String weapon,int price,int page)
    {

        return "https://riven.market/_modules/riven/showrivens.php?baseurl=Lw==" +
                "&platform=PC" +
                "&limit=" +limit+
                "&recency=-1" +
                "&veiled=false" +
                "&onlinefirst=" +onlinefirst+
                "&polarity=all" +
                "&rank=all" +
                "&mastery=16" +
                "&weapon=" +weapon+
                "&stats=Any" +
                "&neg=all" +
                "&price=" +price+
                "&rerolls=-1" +
                "&sort=price" +
                "&direction=ASC" +
                "&page=" +page+
                "&time="+System.currentTimeMillis();
    }

    public static String nameToProperty(String name, Map<String,Map> rmProperty)
    {
        String res = name+" :";
        name = name.replaceAll("\\s*", "").toLowerCase();
        for(String s:rmProperty.keySet())
        {
            String prefix = rmProperty.get(s).get("Prefix").toString().toLowerCase();
            String suffix = rmProperty.get(s).get("Suffix").toString().toLowerCase();
            if(name.indexOf(prefix)==0||name.indexOf(prefix)==name.indexOf('-')+1||(name.length()==name.indexOf(suffix)+suffix.length()))
                res += " | "+rmProperty.get(s).get("Name").toString();
        }
        return res;
    }

    public static List<String> aliasToList(String name, Map<String,Map> rmProperty)
    {
        List<String> res =new ArrayList<>();
        name = name.replaceAll("\\s*", "").toLowerCase();
        for(String s:rmProperty.keySet())
        {
            String prefix = rmProperty.get(s).get("Prefix").toString().toLowerCase();
            String suffix = rmProperty.get(s).get("Suffix").toString().toLowerCase();
            if(name.indexOf(prefix)==0||name.indexOf(prefix)==name.indexOf('-')+1||(name.length()==name.lastIndexOf(suffix)+suffix.length()))
                res.add(s);
        }
        return res;
    }
}
