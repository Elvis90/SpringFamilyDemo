package com.elvis.demo.service;

import com.elvis.demo.mapper.GoodsMapper;
import com.elvis.demo.mapper.GoodsOrderMapper;
import com.elvis.demo.model.Goods;
import com.elvis.demo.model.GoodsOrder;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Elvis
 * @create 2019-05-21 9:29
 */
@Service
@Slf4j
public class GoodsService {

    private final static String lockName="lock-GoodsService";
    @Autowired
    public GoodsMapper gm;
    @Autowired
    public GoodsOrderMapper gom;
    @Autowired
    public Redisson redisson;
    //查询商品库存
    public String findById(Long id){

        Goods goods = gm.selectByPrimaryKey(id);
        Long Goodsnum = gom.getCountByGoodsId(id);
        return "商品:"+goods.getGoodsName()+"总计："+goods.getCount()+"件；目前已售出:"+Goodsnum+"件";
    }

    //查询所有商品信息
    //@Cacheable(cacheNames="allGoods")
    public List<Goods> getAll(){
        return  gm.selectAll();
    }
    //下单
    public void seckill(Long id) throws RuntimeException{
        //加锁
        RLock rLock = redisson.getLock(lockName);
        rLock.lock();
        try {

            Goods goods = gm.selectByPrimaryKey(id);
            if(goods!=null) {
                if (goods.getCount() < 1) {
                    throw new RuntimeException("商品已经卖光啦");
                }
                goods.setCount(goods.getCount() - 1);
                gm.updateByPrimaryKey(goods);
                //新增订单表
                GoodsOrder go = new GoodsOrder();
                go.setGoodsId(goods.getId());
                go.setGoodsName(goods.getGoodsName());
                go.setCreateTime(new Date());
                gom.insert(go);
            }else {
                throw new RuntimeException("商品ID不存在");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
          rLock.unlock();
        }


    }
}
