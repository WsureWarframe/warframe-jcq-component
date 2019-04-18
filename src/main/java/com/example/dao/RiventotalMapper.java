package com.example.dao;

import com.example.entity.Riventotal;

public interface RiventotalMapper {
    int deleteByPrimaryKey(Integer qq);

    int insert(Riventotal record);

    int insertSelective(Riventotal record);

    Riventotal selectByPrimaryKey(Integer qq);

    int updateByPrimaryKeySelective(Riventotal record);

    int updateByPrimaryKey(Riventotal record);
}