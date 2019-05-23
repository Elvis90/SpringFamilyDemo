package com.elvis.demo.controller;

import com.elvis.demo.service.GoodsService;
import com.elvis.demo.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Elvis
 * @create 2019-05-21 9:42
 */
@RestController
@RequestMapping("goods")
@Slf4j
public class GoodsController {
    @Autowired
    GoodsService gs;

    @RequestMapping(value = "/first", method = RequestMethod.GET)
    public Map<String, Object> firstResp (HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        request.getSession().setAttribute("request Url", request.getRequestURL());
        map.put("request Url", request.getRequestURL());
        return map;
    }

    @RequestMapping(value = "/sessions", method = RequestMethod.GET)
    public Object sessions (HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        map.put("sessionId", request.getSession().getId());
        map.put("message", request.getSession().getAttribute("map"));
        return map;
    }
    @GetMapping("query/{id}")
    @ResponseBody
    public Object query(@PathVariable("id") Long Id, HttpServletRequest request){
        HttpSession session = request.getSession();
        log.info("sessionId:{}",session.getId());
        String msg = gs.findById(Id);
        return new ResponseEntity<R>(R.data(msg), HttpStatus.OK);
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
