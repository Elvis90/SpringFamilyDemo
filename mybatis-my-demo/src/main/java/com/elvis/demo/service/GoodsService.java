package com.elvis.demo.service;

import com.elvis.demo.component.RedisLock;
import com.elvis.demo.mapper.GoodsMapper;
import com.elvis.demo.mapper.GoodsOrderMapper;
import com.elvis.demo.model.Goods;
import com.elvis.demo.model.GoodsOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Elvis
 * @create 2019-05-21 9:29
 */
@Service
public class GoodsService {

    @Autowired
    public GoodsMapper gm;
    @Autowired
    public GoodsOrderMapper gom;
    @Autowired
    public RedisLock lock;
    public static final Long expire=10*1000l;
    //查询商品库存
    public String findById(Long id){

        Goods goods = gm.selectByPrimaryKey(id);
        Long Goodsnum = gom.getCountByGoodsId(id);
        return "商品:"+goods.getGoodsName()+"总计："+goods.getCount()+"件；目前已售出:"+Goodsnum+"件";
    }

    //下单
    public boolean seckill(Long id) throws RuntimeException{
        //加锁
       long time =  System.currentTimeMillis() + expire;
        boolean islock = this.lock.lock("goodsSeckill" + id, time + "");
        if(!islock){
            //加锁失败返回
            throw new RuntimeException("活动太火爆啦请稍后再试");
        }
        Goods goods = gm.selectByPrimaryKey(id);
        if(goods!=null){
            if(goods.getCount()<1){
                throw new RuntimeException("商品已经卖光啦");
            }
            goods.setCount(goods.getCount()-1);
            gm.updateByPrimaryKey(goods);
            //新增订单表
            GoodsOrder go = new GoodsOrder();
            go.setGoodsId(goods.getId());
            go.setGoodsName(goods.getGoodsName());
            go.setCreateTime(new Date());
            gom.insert(go);

            //解锁
            this.lock.unlock("goodsSeckill"+id,time+"");
            return true;
        }

        return false;
    }
}
