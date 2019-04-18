package com.example.core;

import com.example.tool.HttpTool;
import com.example.tool.JsonTool;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import static com.example.tool.RivenMarketTool.elementsToRivenModel;
import static com.example.tool.RivenMarketTool.rivenMarketUrlCreate;

public class WikiSearch {
    public static String createWikiMsg(String type,String key)
    {
        List<Object> os;
        try {
           os = (List<Object>) JsonTool.jsonToMap(new HttpTool().getWikiContent(type,key));
        } catch (Exception e) {
            //e.printStackTrace();
            return "通信不畅，重新试试吧";
        }
        if(os==null||os.size()<3)
            return "通信不畅，重新试试吧";
        List<String> names = (List<String>) os.get(1);
        List<String> urls  = (List<String>) os.get(3);

        String reStr = "你可能要找的是:\n";
        if(names.size()==0)
        {
            return "什么也没搜到 喵(>^ω^<)";
        }else {
            for(String temp:names)
            {
                if(temp.equals(key))
                {
                    return "为你找到："+key+"\n"+urls.get(0);
                }
            }

            for(String s:names)
            {
                reStr += (s +"\n");
            }

            reStr ="为你找到："+key+"\n"+urls.get(0) +"\n\n"+reStr+ ("\n请搜索以上内容获取准确的信息 喵~");
            return reStr;

        }
    }

    public static String createNewWikiMsg(String type,String key) {
        String str ;
        if(type.equals("qj")){
            str = new HttpTool().getWholeWikiContent(key);
        }   else    {
            str = new HttpTool().getNewWikiContent(type,key);
        }

        if(str.equals("[]"))
            return "与灰机Wiki通信超时，请重试";
        Document doc = Jsoup.parse(str);
        Elements rivenElements = doc.select(".mw-search-result-heading");
        StringBuffer result = new StringBuffer();
        result.append("为你找到:");
        boolean first = true;
        String firstKey = "";
        int count = 0;
        if(rivenElements.size()==0)
        {
            return result.append(key+":"+ HttpTool.newWikiUrl.replace("warframe",type)+URLEncoder.encode(key)).toString();
        }
        for (Element e : rivenElements) {
            if(type.equals("qj"))
            {
                if (first) {
                    firstKey = e.select("a:nth-child(1)").text().replace("<em>","").replace("</em>","");
                    result.append(firstKey+":"+e.select("a:nth-child(1)").attr("href")+"\n("+e.select("a:nth-child(2)").text()+")"+ "\n\n你可能要找的是:\n");
                    first = false;
                } else {
                    String thisKey = e.select("a:nth-child(1)").text().replace("<em>","").replace("</em>","");
                    String thisHref = e.select("a:nth-child(1)").attr("href");
                    if(thisKey.equals(firstKey))
                    {
                        result.append(thisKey+":"+thisHref+"("+e.select("a:nth-child(2)").text()+")\n");
                    }   else    {
                        result.append(thisKey+"("+e.select("a:nth-child(2)").text()+")\n");
                    }
                }
            }   else    {

                if (first) {
                    result.append(e.select("a").attr("title")+":https://"+type+".huijiwiki.com"+e.select("a").attr("href")+ "\n\n你可能要找的是:\n");
                    first = false;
                } else {
                    result.append(e.select("a").attr("title")+"\n");
                }
            }

            count++;
            if(count>=10)
                break;
        }
        result.append("\n请搜索以上内容获取准确的信息 喵~");
        return result.toString();
    }
}
