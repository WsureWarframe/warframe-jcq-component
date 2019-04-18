package com.example.dao;

import com.example.entity.Rivendaily;

public interface RivendailyMapper {
    int deleteByPrimaryKey(String id);

    int insert(Rivendaily record);

    int insertSelective(Rivendaily record);

    Rivendaily selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Rivendaily record);

    int updateByPrimaryKey(Rivendaily record);
}