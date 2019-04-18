package com.example.dao;

import com.example.entity.Rivenhave;

public interface RivenhaveMapper {
    int deleteByPrimaryKey(String id);

    int insert(Rivenhave record);

    int insertSelective(Rivenhave record);

    Rivenhave selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Rivenhave record);

    int updateByPrimaryKey(Rivenhave record);
}