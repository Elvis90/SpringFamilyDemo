package com.elvis.demo.mapper;

import com.elvis.demo.model.Goods;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface GoodsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Goods record);

    int insertSelective(Goods record);

    Goods selectByPrimaryKey(Long id);

    List<Goods> selectAll();

    int updateByPrimaryKeySelective(Goods record);

    int updateByPrimaryKey(Goods record);
}