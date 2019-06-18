package com.elvis.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.elvis.demo.model.Goods;
import com.elvis.demo.service.GoodsService;
import com.elvis.demo.service.RedisTemplateService;
import com.elvis.demo.task.MsghandleThread;
import com.elvis.demo.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author Elvis
 * @create 2019-05-21 9:42
 * 商品秒杀案例
 * redis作为消息队列的例子
 */
@RestController
@RequestMapping("goods")
@Slf4j
public class GoodsController {
    @Autowired
    GoodsService gs;
    //String类型redis有默认处理的类
//    @Autowired
//    RedisTemplate<String,String> rt;
    @Autowired
    RedisTemplateService rts;

//    @GetMapping("sendMsg")
//    @ResponseBody
//    public Object sendMsg(@RequestParam(value = "msg",required = false) String msg, @RequestBody(required = false) JSONObject json ,HttpServletRequest request){
//        rt.convertAndSend("msg",msg);
//        return "发送成功";
//    }

    @GetMapping("sendMsg")
    @ResponseBody
    public Object sendMsg(@RequestBody(required = false) JSONObject msg , HttpServletRequest request){

        rts.sendmsg(msg);
        return "发送成功";
    }

    @GetMapping("sendMsgbyqueue")
    @ResponseBody
    public Object sendMsgbyqueue(@RequestBody(required = false) JSONObject msg , HttpServletRequest request){
        for (int i = 0; i < 10; i++) {
            msg.put("flag",i);
            rts.sendmsgByquene(RedisTemplateService.QUENE_KEY,msg);
        }
        return "发送成功";
    }

    @GetMapping("start")
    @ResponseBody
    public Object start(HttpServletRequest request){
        new Thread(new MsghandleThread(rts)).start();
        return "消费线程启动成功";
    }

    @GetMapping("query/{id}")
    @ResponseBody
    public Object query(@PathVariable("id") Long Id, HttpServletRequest request){
        HttpSession session = request.getSession();
        log.info("sessionId:{}",session.getId());
        String msg = gs.findById(Id);
        return new ResponseEntity<R>(R.data(msg), HttpStatus.OK);
    }

    @GetMapping("getall")
    @ResponseBody
    public Object getall(HttpServletRequest request){
        List<Goods> data = gs.getAll();
        return new ResponseEntity<R>(R.data(data), HttpStatus.OK);
    }

    @PostMapping("seckill/{id}")
    @ResponseBody
    public Object seckill(@PathVariable("id") Long Id){
        boolean res=false;
        try{
            res = gs.seckill(Id);
        }catch (Exception e){
            return new ResponseEntity<R>(R.error(500,e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(res){
            return new ResponseEntity<R>(R.ok("抢购成功"),HttpStatus.OK);
        }else{
            return new ResponseEntity<R>(R.error(500,"活动太火爆了 请稍后再试吧"),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
