package com.example;

import com.baidu.aip.ocr.AipOcr;
import com.example.Cache.CacheListener;
import com.example.Cache.CacheManagerImpl;
import com.example.Cache.DictManagerImpl;
import com.example.core.CreateMsg;
import com.example.core.KeyWords;
import com.example.dao.RenameMapper;
import com.example.entity.Rename;
import com.example.entity.RivenMarketItem;
import com.example.tool.*;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.eclipse.jetty.util.StringUtil;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.*;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.example.core.WikiSearch.createNewWikiMsg;
import static com.example.tool.RivenMarketTool.elementsToRivenModel;
import static com.example.tool.RivenMarketTool.rivenMarketUrlCreate;

public class Test {
    final public static String USER_AGENT="Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US) AppleWebKit/534.16 (KHTML, like Gecko) Chrome/10.0.648.133 Safari/534.16";
    public static void main(String[] args) throws Exception {


//        String BLANK = "  ";
//        Process process = Runtime.getRuntime().exec(
//                "C:/phantomjs-2.1.1-windows/bin/phantomjs.exe" + BLANK //你的phantomjs.exe路径
//                        + "C:/phantomjs-2.1.1-windows/screenshot.js" + BLANK //就是上文中那段javascript脚本的存放路径
//                        + "https://warframe.huijiwiki.com/wiki/Tenno" + BLANK //你的目标url地址
//                        + "D:/baidu.png");//你的图片输出路径
//
//        InputStream inputStream = process.getInputStream();
//        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//        String tmp = "";
//        while ((tmp = reader.readLine()) != null) {
//            if (reader != null) {
//                reader.close();
//                break;
//            }
//            if (process != null) {
//                process.destroy();
//                process = null;
//            }
//            System.out.println("渲染成功...");
//        }


//        Document doc = Jsoup.parse(new HttpTool().httpsRequest(rivenMarketUrlCreate(5,true,"Lenz",9999,1),"GET",null));
//        Elements rivenElements = doc.select(".riven");
//        StringBuffer result = new StringBuffer();
//        for(Element e:rivenElements)
//        {
//            if("riven".equals(e.className()))
//            {
//                result.append(elementsToRivenModel(e).fullInfoString(Demo.rmProperty)+"\n");
//            }
//        }
//
//        System.out.println(result.toString());

//        for(String s:new OcrApi().getBasicGeneral(getSharperPicture("C:\\Users\\ws\\Pictures\\1.png")))
//            if(!s.equals("10"))
//                System.out.println(s);
        System.out.println(createNewWikiMsg("qj","暗黑"));
        System.out.println(createNewWikiMsg("ff14","暗黑"));
        //时间工具测试
        /*
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String str1 = "2018-10-1";
        Calendar bef = Calendar.getInstance();
        bef.setTime(sdf.parse(str1));
        System.out.println(getTimePeriodsCount(bef,true));
        System.out.println("\n\n"+JsonTool.ObjToJson(getTimePeriods(bef,false)));
        */

//        testDoOCR_File();

    }


    public static byte[] getSharperPicture(String path) throws IOException {
        //BufferedImage originalPic
        BufferedImage originalPic = ImageIO.read(new File(path));

        int imageWidth = originalPic.getWidth();
        int imageHeight = originalPic.getHeight();

        BufferedImage newPic = new BufferedImage(imageWidth, imageHeight,
                BufferedImage.TYPE_3BYTE_BGR);
        float[] data =
                { -1.0f, -1.0f, -1.0f, -1.0f, 10.0f, -1.0f, -1.0f, -1.0f, -1.0f };

        Kernel kernel = new Kernel(3, 3, data);
        ConvolveOp co = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        co.filter(originalPic, newPic);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        boolean flag = ImageIO.write(newPic, "gif", out);
        byte[] b = out.toByteArray();
        return b;
    }



    public static void testDoOCR_File() throws Exception {
        File imageFile = new File("C:\\Users\\ws\\Pictures\\22.png");
        //set language
        ITesseract instance = new Tesseract();
        instance.setDatapath("E:\\Program Files (x86)\\Tesseract-OCR\\tessdata");
        instance.setLanguage("chi_sim+eng");
        String result = instance.doOCR(imageFile);
        System.out.println(result);
    }

    private static int getTimePeriodsCount(Calendar cal,boolean isWeekly)
    {
        if(isWeekly)
        {
            int weekOffset=0;
            int head = cal.get(Calendar.DAY_OF_WEEK) - 1;
            if(head == 0) head = 7;
            Calendar tailCal = Calendar.getInstance();
            tailCal.setTime(new Date());
            int tail = tailCal.get(Calendar.DAY_OF_WEEK) - 1;
            if(tail == 0) tail = 7;
            int dayOffset = (int) ((System.currentTimeMillis()-cal.getTime().getTime())/(1000*60*60*24))+1;
            weekOffset = (int)Math.ceil(dayOffset / 7.00);
            if(head>tail)
            {
                weekOffset += 1;
            }
            return weekOffset;
        } else {
            Calendar now = Calendar.getInstance();
            now.setTime(new java.util.Date());
            int result = now.get(Calendar.MONTH) - cal.get(Calendar.MONTH)+1;
            int month = (now.get(Calendar.YEAR) - cal.get(Calendar.YEAR)) * 12;
            return month + result;
        }
    }
    /*
     * 将时间转换为时间戳
     */
    private static Map<String,String> getTimePeriods(Calendar cal,boolean isWeekly) {
        Map<String,String> map = new HashMap<String, String>();
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
        Calendar startCal = (Calendar) cal.clone();
        Calendar endCal   = (Calendar) cal.clone();
        if(isWeekly) {
            int day = startCal.get(Calendar.DAY_OF_WEEK)-1;
            if(day==0)
            {
                startCal.add(Calendar.DATE,-6);
            } else {
                startCal.set(Calendar.DAY_OF_WEEK,2);
                endCal.add(Calendar.DATE,7-day);
            }
            map.put("startTime",sdf.format(startCal.getTime()));
            map.put("endTime",sdf.format(endCal.getTime()));
//            sdf.format()
        } else {
            startCal.set(Calendar.DAY_OF_MONTH,1);
            endCal.set(Calendar.DATE, 1);
            endCal.roll(Calendar.DATE, -1);
            map.put("startTime",sdf.format(startCal.getTime()));
            map.put("endTime",sdf.format(endCal.getTime()));
        }
        return map;
    }
}

