package com.example.dao;

import com.example.entity.Goods;

import java.util.ArrayList;

public interface GoodsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Goods record);

    int insertSelective(Goods record);

    Goods selectByPrimaryKey(Integer id);

    ArrayList<Goods> selectByPrimaryName(String name);

    int updateByPrimaryKeySelective(Goods record);

    int updateByPrimaryKey(Goods record);

    ArrayList<Goods> selectAllGoods();
}