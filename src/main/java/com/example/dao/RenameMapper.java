package com.example.dao;

import com.example.entity.Rename;

public interface RenameMapper {
    int deleteByPrimaryKey(Long qq);

    int insert(Rename record);

    int insertSelective(Rename record);

    Rename selectByPrimaryKey(Long qq);

    int updateByPrimaryKeySelective(Rename record);

    int updateByPrimaryKey(Rename record);
}