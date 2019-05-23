package com.elvis.demo.mapper;

import com.elvis.demo.model.GoodsOrder;
import org.apache.ibatis.annotations.Param;

public interface GoodsOrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GoodsOrder record);

    int insertSelective(GoodsOrder record);

    GoodsOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GoodsOrder record);

    int updateByPrimaryKey(GoodsOrder record);

    Long getCountByGoodsId(@Param(value="goodsId")Long goodsId);
}