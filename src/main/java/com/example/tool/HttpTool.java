package com.example.tool;

import com.example.Cache.CacheManagerImpl;
import com.example.Demo;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import static com.example.tool.PropertiesTool.fileReader;
import static com.sobte.cqp.jcq.event.JcqApp.CQ;

public class HttpTool {
    static String urlStr = "https://api.warframestat.us/pc/";
    static String wmUrl  = "https://api.warframe.market/v1/items/";
    static String wikiUrl = "https://warframe.huijiwiki.com/api.php?action=opensearch&format=json&formatversion=2&namespace=0&limit=10&suggest=true&search=";
            //"http://wf.poedb.tw/data.php?t=all";
    static String wfaUrl = "https://api.richasy.cn/market/riven/getitems";
    static String wfaTable = "https://api.richasy.cn/wfa/lib/all/";
    public static String newWikiUrl = "https://warframe.huijiwiki.com/index.php?search=";
    static String wholeWikiUrl = "https://ff14.huijiwiki.com/index.php?title=%E7%89%B9%E6%AE%8A:%E5%85%A8%E5%B1%80%E6%90%9C%E7%B4%A2&key=";
//
//    测试方法
//
    /*
    public static void main(String[] args) throws Exception {
        //方法一
        //System.out.println((new Test()).getURLContent());

        System.out.println((new Test()).getURLContent(urlStr));
    }
    */
    public static String getURLContent(String strURL,String time) throws Exception {

        System.setProperty("http.proxyHost", "127.0.0.1");
        System.setProperty("https.proxyHost", "127.0.0.1");
        System.setProperty("http.proxyPort", "8888");
        System.setProperty("https.proxyPort", "8888");
        System.setProperty("http.agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US) AppleWebKit/534.16 (KHTML, like Gecko) Chrome/10.0.648.133 Safari/534.16");

        URL url = new URL(strURL);
        HttpURLConnection httpConn = (HttpURLConnection)url.openConnection();
        httpConn.setRequestMethod("GET");
        httpConn.setRequestProperty("Cookie", "scrask=1600" +
                "&Hm_lvt_cdd0b761d35a67a9dd5cb89375a4cb61="+time +
                "&Hm_lpvt_cdd0b761d35a67a9dd5cb89375a4cb61="+time +
                "&region=%E7%94%B5%E4%BF%A1%E4%B8%80%E5%8C%BA" +
                "&server=%E9%95%BF%E5%AE%89%E5%9F%8E" +
                "&serendipity=%E4%B8%8D%E9%99%90" +
                "&search=");
        httpConn.connect();
        BufferedReader reader = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
        String line;
        StringBuffer buffer = new StringBuffer();
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        reader.close();
        httpConn.disconnect();

        return buffer.toString();
    }

    /**
     * 程序中访问http数据接口
     */

//    public static String getURLContent(String urlStr) {
//        /** 网络的url地址 */
//        URL url = null;
//        /** http连接 */
//        HttpURLConnection httpConn = null;
//        /**//** 输入流 */
//        BufferedReader in = null;
//        StringBuffer sb = new StringBuffer();
//        try {
//            url = new URL(urlStr);
//            in = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
//            String str = null;
//            while ((str = in.readLine()) != null) {
//                sb.append(str);
//            }
//        } catch (Exception ex) {
//
//        } finally {
//            try {
//                if (in != null) {
//                    in.close();
//                }
//            } catch (IOException ex) {
//            }
//        }
//        String result = sb.toString();
////        System.out.println(result);
//        return result;
//    }

    public static String getContent(String urlString) {
        System.setProperty("http.proxyHost", "127.0.0.1");
        System.setProperty("https.proxyHost", "127.0.0.1");
        System.setProperty("http.proxyPort", "8888");
        System.setProperty("https.proxyPort", "8888");
        System.setProperty("http.agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US) AppleWebKit/534.16 (KHTML, like Gecko) Chrome/10.0.648.133 Safari/534.16");
        /** 网络的url地址 */
        URL url = null;
        /** http连接 */
        HttpURLConnection httpConn = null;
        /**//** 输入流 */
        BufferedReader in = null;
        StringBuffer sb = new StringBuffer();
        try {
            url = new URL(urlString);
            in = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
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
        System.out.println(result);
        return result;
    }



    /*
     * 处理https GET/POST请求
     * 请求地址、请求方法、参数
     * */
    public String httpsRequest(String requestUrl,String requestMethod,String outputStr,Map<String,String> hader){
        StringBuffer buffer=null;
        System.setProperty("http.proxyHost", "127.0.0.1");
        System.setProperty("https.proxyHost", "127.0.0.1");
        System.setProperty("http.proxyPort", "8888");
        System.setProperty("https.proxyPort", "8888");
        System.setProperty("http.agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US) AppleWebKit/534.16 (KHTML, like Gecko) Chrome/10.0.648.133 Safari/534.16");
        try{
            //创建SSLContext
            SSLContext sslContext=SSLContext.getInstance("SSL");
            TrustManager[] tm={new MyX509TrustManager()};
            //初始化
            sslContext.init(null, tm, new java.security.SecureRandom());
            //获取SSLSocketFactory对象
            SSLSocketFactory ssf=sslContext.getSocketFactory();

            URL url=new URL(requestUrl);
            HttpsURLConnection conn=(HttpsURLConnection)url.openConnection();
            conn.setConnectTimeout(6000);
            conn.setReadTimeout(8000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod(requestMethod);
            if(hader!=null)
            {
                for(String key:hader.keySet())
                {
                    conn.setRequestProperty(key,hader.get(key));
                }
            }
            //设置当前实例使用的SSLSoctetFactory
            conn.setSSLSocketFactory(ssf);
            conn.connect();
            //往服务器端写内容
            if(null!=outputStr){
                OutputStream os=conn.getOutputStream();
                os.write(outputStr.getBytes("utf-8"));
                os.close();
            }

            //读取服务器端返回的内容
            InputStream is=conn.getInputStream();
            InputStreamReader isr=new InputStreamReader(is,"utf-8");
            BufferedReader br=new BufferedReader(isr);
            buffer=new StringBuffer();
            String line=null;
            while((line=br.readLine())!=null){
                buffer.append(line);
            }


        }catch(Exception e){
            e.printStackTrace();
        }
        return buffer!=null?buffer.toString():"[]";
    }
    public String httpsPostRequest(String requestUrl,String requestMethod,String outputStr,String search){
        StringBuffer buffer=null;
        System.setProperty("http.proxyHost", "127.0.0.1");
        System.setProperty("https.proxyHost", "127.0.0.1");
        System.setProperty("http.proxyPort", "8888");
        System.setProperty("https.proxyPort", "8888");
        System.setProperty("http.agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US) AppleWebKit/534.16 (KHTML, like Gecko) Chrome/10.0.648.133 Safari/534.16");
        try{
            //创建SSLContext
            SSLContext sslContext=SSLContext.getInstance("SSL");
            TrustManager[] tm={new MyX509TrustManager()};
            //初始化
            sslContext.init(null, tm, new java.security.SecureRandom());
            //获取SSLSocketFactory对象
            SSLSocketFactory ssf=sslContext.getSocketFactory();

            URL url=new URL(requestUrl);
            HttpsURLConnection conn=(HttpsURLConnection)url.openConnection();
            conn.setConnectTimeout(6000);
            conn.setReadTimeout(8000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod(requestMethod);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("User-Agent","Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US) AppleWebKit/534.16 (KHTML, like Gecko) Chrome/10.0.648.133 Safari/534.16");
            //设置当前实例使用的SSLSoctetFactory
            conn.setSSLSocketFactory(ssf);
            conn.connect();
            //往服务器端写内容
            if(null!=outputStr){
                OutputStream os=conn.getOutputStream();
                os.write(outputStr.getBytes("utf-8"));
                os.close();
            }

            //读取服务器端返回的内容
            InputStream is=conn.getInputStream();
            InputStreamReader isr=new InputStreamReader(is,"utf-8");
            BufferedReader br=new BufferedReader(isr);
            buffer=new StringBuffer();
            String line=null;
            while((line=br.readLine())!=null){
                buffer.append(line);
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return buffer!=null?buffer.toString():"[]";
    }
    public String getHttpsContent(String type)
    {
        return httpsRequest(urlStr+type,"GET",null,null);
    }

    public String getWMContent(String str,String type)
    {
        return httpsRequest(wmUrl+str+"/"+type,"GET",null,null);
    }

    public String getWikiContent(String type,String search)
    {
//        System.out.println(httpsPostRequest(wikiUrl,"POST",null,URLEncoder.encode(search)));
        return httpsRequest(wikiUrl.replace("warframe",type)+URLEncoder.encode(search),"POST",null,null);
    }

    public String getNewWikiContent(String type,String search){
        return httpsRequest(newWikiUrl.replace("warframe",type)+URLEncoder.encode(search),"POST",null,null);
    }
    public String getWholeWikiContent(String search){
        return httpsRequest(wholeWikiUrl+URLEncoder.encode(search),"POST",null,null);
    }
    public String getWfaContent(String name)
    {
        try {
            return httpsPostRequest(wfaUrl,"POST","Platform=pc&WeaponName="+URLEncoder.encode(name,"UTF-8")+"&Category=",null);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            CQ.logInfo("wfa查询","解码失败："+name);
            return "404";
        }
    }

    public String getWfaTable(String type)
    {
        try {
            CacheManagerImpl cacheManagerImpl = new CacheManagerImpl();
            if(!cacheManagerImpl.isContains("wfa_token"))
            {
                Map<String,String> hader = new HashMap<>();
                hader.put("Authorization","Bearer "+getWfaAccessToken());
                cacheManagerImpl.putCache("wfa_token",hader,0L);
            }
            return httpsRequest(wfaTable+type, "GET", null, (Map<String, String>) cacheManagerImpl.getCacheDataByKey("wfa_token"));
        }catch (Exception e){

            return fileReader(CQ.getAppDirectory()+ type+".json");
        }
    }

    public String getWfaAccessToken()
    {
        Map<String,String> map = (Map<String, String>) JsonTool.jsonToMap(httpsRequest("https://api.richasy.cn/connect/token","POST","client_id=eadfa670ed114c7dbcaecb1a3a1f5fac&client_secret=2bdaaf0e90bd4e8784788d86eb8bca12&grant_type=client_credentials",null));
        return map.get("access_token");
    }

}
