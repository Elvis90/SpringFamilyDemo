package com.elvis.demo.mapper;

import com.elvis.demo.model.Log;
import org.springframework.stereotype.Repository;

@Repository
public interface LogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Log record);

    int insertSelective(Log record);

    Log selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Log record);

    int updateByPrimaryKey(Log record);
}