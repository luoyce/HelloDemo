package com.example.provider.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: weijian.yan
 * @create: 2020-05-23 16:59
 **/
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    Environment environment;

    /**
     * 假如这个客户端要提供一个getUser的方法
     * @return
     */
    @GetMapping(value = "/getUser")
    @ResponseBody
    public Map<String,Object> getUser(@RequestParam Integer id){
        Map<String,Object> data = new HashMap<>();
        data.put("id",id);
        data.put("userName","admin");
        data.put("from",environment.getProperty("local.server.port"));
        return data;
    }

    @GetMapping(value = "/getUser0")
    @ResponseBody
    public Map<String,Object> getUser0(){
        Map<String,Object> data = new HashMap<>();
        data.put("id",0);
        data.put("userName","admin");
        data.put("from",environment.getProperty("local.server.port"));
        return data;
    }
}
