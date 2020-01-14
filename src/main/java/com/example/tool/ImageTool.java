package com.example.tool;

import java.io.*;
import java.util.List;
import java.util.Map;

import static com.example.Demo.rmProperty;
import static com.example.Demo.CQ;

public class ImageTool {
    public String ImageCreate(String phantomjsPath,String screenshotPath,String url,String filePath) throws IOException {
        String BLANK = "  ";
//        Process process = Runtime.getRuntime().exec(
//                "C:/phantomjs-2.1.1-windows/bin/phantomjs.exe" + BLANK //你的phantomjs.exe路径
//                        + "C:/phantomjs-2.1.1-windows/screenshot.js" + BLANK //就是上文中那段javascript脚本的存放路径
//                        + "https://warframe.huijiwiki.com/wiki/Tenno" + BLANK //你的目标url地址
//                        + "D:/baidu.png");//你的图片输出路径

        String reStr= CQ.getAppDirectory()+"wiki\\"+filePath;
        reStr= reStr.replace("\\","/");
        Process process = Runtime.getRuntime().exec(
                phantomjsPath + BLANK //你的phantomjs.exe路径
                        + screenshotPath + BLANK //就是上文中那段javascript脚本的存放路径
                        + url + BLANK //你的目标url地址
                        + reStr);//你的图片输出路径
        InputStream inputStream = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String tmp = "";
        while ((tmp = reader.readLine()) != null) {
            if (reader != null) {
                reader.close();
                break;
            }
            if (process != null) {
                process.destroy();
                process = null;
            }
            System.out.println("渲染成功...");
        }
        return reStr;
    }

    public static String ocrRiven(byte[] image)
    {
        String res = "已识别到:";
        Map<String,String> rivenMap=DictTool.searchRivenFromImg(new OcrApi().getBasicGeneral(image));
        if(rivenMap.size()==0)
            return "什么都没识别到";
        res+=JsonTool.ObjToJson(rivenMap);
        for(String key:rivenMap.keySet())
        {
            List<String> proList = RivenMarketTool.aliasToList(rivenMap.get(key),rmProperty);
            if(proList.size()>=2&&proList.size()<=3)
            {
                res+= key+" "+RivenMarketTool.nameToProperty(rivenMap.get(key),rmProperty)+"|";
            }
            else {
                res+= "\n"+key+" (未识别到属性";
            }
        }
        return res;
    }

    public static byte[] image2Bytes(String imgSrc) throws Exception
    {
        FileInputStream fin = new FileInputStream(new File(imgSrc));
        //可能溢出,简单起见就不考虑太多,如果太大就要另外想办法，比如一次传入固定长度byte[]
        byte[] bytes  = new byte[fin.available()];
        //将文件内容写入字节数组，提供测试的case
        fin.read(bytes);
        fin.close();
        return bytes;
    }


}
