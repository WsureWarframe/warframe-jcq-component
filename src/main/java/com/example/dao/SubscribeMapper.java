package com.example.dao;

import com.example.entity.SubscribeKey;

import java.util.ArrayList;

public interface SubscribeMapper {
    int deleteByPrimaryKey(SubscribeKey key);

    int insert(SubscribeKey record);

    int insertSelective(SubscribeKey record);

    ArrayList<Long> getSubQQs(String name);

    ArrayList<String> getMySub(Long qq);

    int selectByKeys(SubscribeKey record);
}