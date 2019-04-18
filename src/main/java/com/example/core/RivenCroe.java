package com.example.core;

import com.example.Cache.DictManagerImpl;
import com.example.Cache.RivenManagerImpl;
import com.example.entity.Riven;
import com.example.entity.RivenProperty;
import com.example.tool.JsonTool;

import java.util.*;

import static com.sobte.cqp.jcq.event.JcqApp.CQ;


public class RivenCroe {

    RivenManagerImpl rivenManagerImpl = new RivenManagerImpl();
    ArrayList<Map> rivenList = (ArrayList<Map>) rivenManagerImpl.getCacheDataByKey("rivenList");

    static String [] otherProperty = {"切割伤害","穿刺伤害","冲击伤害","火焰伤害","冰冻伤害","毒素伤害","电击伤害","对Grineer伤害","对Corpus伤害","对Infested伤害"};

    public Riven getRivenItem(String name)
    {
        for(Map map:rivenList)
        {
            Riven r = (Riven) JsonTool.jsonToAnyType(JsonTool.ObjToJson(map),Riven.class);
            if(name.equals(r.getName()))
                return r;
        }
        return null;
    }

    public List<Map> getBasic(String type)
    {
        return (List<Map>) ((Map) rivenManagerImpl.getCacheDataByKey("property")).get(type);
    }
    public Map RivenInit(String name,String type,int perNum,boolean isMinus,int level)
    {

        if (name!=null && type!=null && perNum != 0) {
            Riven riven = getRivenItem(name);
            Map result=new HashMap();
            result.put("riven",riven);
            String query = type.substring(0, 1) + perNum;

            if (isMinus) {
                query += 'M';
            }
            List<Map> basic = getBasic(query);
            List arr = new ArrayList();

            for(Map map:basic)
            {
                RivenProperty rp=(RivenProperty) JsonTool.jsonToAnyType(JsonTool.ObjToJson(map),RivenProperty.class);
                RivenProperty newRp = new RivenProperty();
                newRp.setProperty(rp.getProperty());
                newRp.setMin(Double.parseDouble(String.format("%.2f",(rp.getMin() * riven.getRatio() * (level +1)))));
                newRp.setMax(Double.parseDouble(String.format("%.2f",(rp.getMax() * riven.getRatio() * (level +1)))));
                if(rp.getMinusMax()!=null||rp.getMinusMin()!=null)
                {
                    newRp.setMinusMin(Double.parseDouble(String.format("%.2f",(rp.getMinusMin() * riven.getRatio() * (level +1)))));
                    newRp.setMinusMax(Double.parseDouble(String.format("%.2f",(rp.getMinusMax() * riven.getRatio() * (level +1)))));
                }
                arr.add(newRp);
            }
            result.put("property",arr);
//            basic.forEach(p => {
//
//                    let t = {
//                    perporty: p.Property,
//                    min: Math.round(p.Min * riven.Ratio * (level + 1), 2),
//                    max: Math.round(p.Max * riven.Ratio * (level + 1), 2),
//                };
//            t.valueShow =  t.min + '% ~ ' + t.max + '%';
//            if (t.perporty.indexOf('连击持续时间') != -1) {
//                t.valueShow =  t.min + '秒 ~ ' + t.max + '秒';
//            }
//            if (p.MinusMin || p.MinusMax) {
//                t.minusMin = Math.round(p.MinusMin * riven.Ratio * (level + 1), 2);
//                t.minusMax = Math.round(p.MinusMax * riven.Ratio * (level + 1), 2);
//                t.minusShow =  t.minusMin + '% ~ ' + t.minusMax + '%';
//                if (t.perporty.indexOf('连击持续时间') != -1) {
//                    t.minusShow = t.minusMin + '秒 ~ ' + t.minusMax + '秒';
//                }
//            }
//            arr.push(t);
//            });
//            result.list=arr;
            return result;
        }else{
            return null;
        }
    }

    public String getRivenResult(String name,int perNum,boolean isMinus,int level)
    {

        if(getRivenItem(name)==null)
            return "武器名称有误";
        String type = getRivenItem(name).getType();
        Map result = RivenInit(name,type,perNum,isMinus,level);
        String resMsg ="";

        if(result==null)
        {
            return "武器名称有误";
        }
        else
        {
            resMsg +=( "为你找到"+name+perNum+"+"+(isMinus?"1-":""));
            resMsg +=propertyToString(result,isMinus);
        }
        return resMsg;
    }

    public String propertyToString(Map result,boolean isMinus)
    {
        String resMsg="";
        ArrayList<RivenProperty> rps= (ArrayList<RivenProperty>) result.get("property");
        for(RivenProperty rp:rps)
        {
            if(rp.getProperty().contains("连击持续时间"))
            {
                resMsg += ("\n"+rp.getProperty()+":"+rp.getMin()+"秒 ~ "+rp.getMax()+"秒");
                if(isMinus)
                    resMsg += ("("+rp.getMinusMin()+"秒 ~ "+rp.getMinusMax()+"秒)");
            }
            else {
                resMsg += ("\n"+rp.getProperty()+":"+rp.getMin()+"% ~ "+rp.getMax()+"%");
                if(isMinus)
                    resMsg += ("("+rp.getMinusMin()+"% ~ "+rp.getMinusMax()+"%)");
            }

        }
        return resMsg;
    }

    public LinkedHashMap<String,Double> getRendomResult(Riven riven)
    {
        LinkedHashMap<String,Double> yourRiven = new LinkedHashMap<String, Double>();
        Random random = new Random();
        int perNum = random.nextInt(2)+2;
        boolean isMinus = random.nextBoolean();
        Map result = RivenInit(riven.getName(),riven.getType(),perNum,isMinus,8);
//        yourRiven.put("卡面",new Double(perNum+(isMinus?0.1:0.0)));

        ArrayList<RivenProperty> rps= (ArrayList<RivenProperty>) result.get("property");
        for(int i = 0;i<perNum;i++)
        {
            RivenProperty rivenProperty = rps.get(random.nextInt(rps.size()));
            if(rivenProperty.getProperty().contains("物理伤害"))
            {
                yourRiven = setOtherProperty(yourRiven,rivenProperty,false,"物理伤害");
            }
            else if(rivenProperty.getProperty().contains("元素伤害"))
            {
                yourRiven = setOtherProperty(yourRiven,rivenProperty,false,"元素伤害");
            }
            else if(rivenProperty.getProperty().contains("对派系伤害"))
            {
                yourRiven = setOtherProperty(yourRiven,rivenProperty,false,"对派系伤害");
            }
            else
            {
                while(yourRiven.get(rivenProperty.getProperty())!=null||rivenProperty.getProperty().equals("物理伤害")||rivenProperty.getProperty().equals("元素伤害"))
                {
                    rivenProperty = rps.get(random.nextInt(rps.size()));
                }
                yourRiven.put(rivenProperty.getProperty(),Double.parseDouble(String.format("%.2f",getRandom(rivenProperty.getMin(),rivenProperty.getMax()))));
            }
        }
        if(isMinus)
        {
            RivenProperty rivenProperty = rps.get(random.nextInt(rps.size()));
            if(rivenProperty.getProperty().contains("物理伤害"))
            {
                yourRiven = setOtherProperty(yourRiven,rivenProperty,true,"物理伤害");
            }
            else if(rivenProperty.getProperty().contains("元素伤害"))
            {
                yourRiven = setOtherProperty(yourRiven,rivenProperty,true,"元素伤害");
            }
            else if(rivenProperty.getProperty().contains("对派系伤害"))
            {
                yourRiven = setOtherProperty(yourRiven,rivenProperty,true,"对派系伤害");
            }
            else
            {
                while(yourRiven.get(rivenProperty.getProperty())!=null||rivenProperty.getProperty().equals("物理伤害")||rivenProperty.getProperty().equals("元素伤害"))
                {
                    rivenProperty = rps.get(random.nextInt(rps.size()));
                }
                yourRiven.put(rivenProperty.getProperty(),Double.parseDouble(String.format("%.2f",getRandom(rivenProperty.getMinusMin(),rivenProperty.getMinusMax()))));
            }
        }
        return yourRiven;
    }

    public LinkedHashMap<String,Double> setOtherProperty(LinkedHashMap<String,Double> yourRiven,RivenProperty rivenProperty,boolean isMinus,String type)
    {
        Random random = new Random();
        String pname = getOtherPropertyName(type);

        while (yourRiven.get(pname)!=null)
        {
            pname = getOtherPropertyName(type);
        }
        if(isMinus)
        {
            yourRiven.put(pname,Double.parseDouble(String.format("%.2f",getRandom(rivenProperty.getMinusMin(),rivenProperty.getMinusMax()))));
        }
        else {
            yourRiven.put(pname,Double.parseDouble(String.format("%.2f",getRandom(rivenProperty.getMin(),rivenProperty.getMax()))));
        }

        return yourRiven;
    }

    public static String getOtherPropertyName(String type)
    {
        Random random = new Random();
        String pname ="";
        switch (type)
        {
            case "物理伤害" : pname = otherProperty[random.nextInt(3)]; break;
            case "元素伤害" : pname = otherProperty[random.nextInt(4)+3]; break;
            case "对派系伤害" : pname = otherProperty[random.nextInt(3)+7]; break;
            default: pname = type ;break;
        }
        return pname;
    }

    public double getRandom(double min,double max)
    {
        return min +(Math.random() * (max-min+1));
    }

    public Riven getRandomRiven()
    {
        Random random = new Random();
        Riven riven= (Riven) JsonTool.jsonToAnyType(JsonTool.ObjToJson(rivenList.get(random.nextInt(rivenList.size()))),Riven.class);
        return riven;
    }
}
