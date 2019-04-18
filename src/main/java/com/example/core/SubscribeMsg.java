package com.example.core;

import com.example.Cache.SubManageImpl;
import com.example.dao.GoodsMapper;
import com.example.dao.SubscribeMapper;
import com.example.entity.Goods;
import com.example.entity.SubscribeKey;
import org.apache.ibatis.session.SqlSession;

import java.util.*;

public class SubscribeMsg {
    private SubManageImpl   subManageImpl;
    private SqlSession sqlSession;
    private GoodsMapper goodsMapper;
    private SubscribeMapper subscribeMapper;
    public SubscribeMsg()
    {
        subManageImpl = new SubManageImpl();
    }

    public SubscribeMsg(SqlSession sqlSession)
    {
        subManageImpl = new SubManageImpl();
        this.sqlSession=sqlSession;
        this.goodsMapper=sqlSession.getMapper(GoodsMapper.class);
        this.subscribeMapper=sqlSession.getMapper(SubscribeMapper.class);
    }

    public Set<String> getSearchList(String key)
    {
        Set<String> keys=subManageImpl.getAllKeys();
        Set<String> result = new LinkedHashSet<>();
        for(String s:keys)
        {
            if(s.toUpperCase().trim().contains(key.toUpperCase().trim()))
                result.add(s);
        }
        return result;
    }

    public String createSubKey(String key)
    {
        StringBuffer sb=new StringBuffer();
        Set<String> result = getSearchList(key);
        switch (result.size())
        {
            case 0: sb.append(key+"未找到，请尝试精简关键词");break;
            case 1: sb=null;break;
            case 2:
            case 3:
            case 4:
            case 5: sb.append("你可能想找的是：");
                    for(String t:result)
                    {
                        sb.append("\n"+t);
                    }
                    sb.append("\n请输入准确的关键词");
            break;
            default:sb.append(key+"相关的物品超过5个，请再详细点");break;
        }
        return sb==null?null:sb.toString();
    }

    public String doSubscribe(String key,long qq,boolean type)
    {
        String gString = (String) getSearchList(key).toArray()[0];

        /**
         * 先判断是否有物品 ，没有则先插入到Goods
         */
//        SqlSessionTool st = new SqlSessionTool();
        List<Goods> gs = goodsMapper.selectByPrimaryName(gString);
//        st.close();
        //sqlSession.commit();
        if(gs.size()==0)
        {
            Goods goods = new Goods();
            goods.setEn((String) ((Map)subManageImpl.getCacheDataByKey(gString)).get("en"));
//            sqlSession.commit();
            goods.setName(gString);

//            st = new SqlSessionTool();
            int a =goodsMapper.insertSelective(goods);
//            st.close();
//            sqlSession.commit();
        }
//        st = new SqlSessionTool();
        Integer goodId = goodsMapper.selectByPrimaryName(gString).get(0).getId();
//        st.close();
        //sqlSession.commit();
        SubscribeKey subscribeKey = new SubscribeKey();
        subscribeKey.setQq(qq);
        subscribeKey.setId(goodId);
        /**
         * 现在执行订阅或退订
         */
        if(type)
        {
//            st = new SqlSessionTool();
            int num = subscribeMapper.selectByKeys(subscribeKey);
//            st.close();
            if(!(num>0))
            {
//                st = new SqlSessionTool();
                subscribeMapper.insert(subscribeKey);
//                st.close();
            }
        } else {
//            st = new SqlSessionTool();
            subscribeMapper.deleteByPrimaryKey(subscribeKey);
//            st.close();
        }


        return gString;
    }

    public String getUserSubs(Long fromQQ)
    {
//        SqlSessionTool st = new SqlSessionTool();
        ArrayList<String> goodNames = subscribeMapper.getMySub(fromQQ);
//        st.close();
        //sqlSession.commit();
        if(goodNames.size()==0)
            return "订阅列表为空";
        String reStr = "你订阅了";
        for(String s:goodNames)
        {
            reStr += "\n"+s;
        }
        return reStr;
    }

}
