package com.example.client.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @description:
 * @author: weijian.yan
 * @create: 2020-05-24 21:58
 **/
@FeignClient(value = "PROVIDER")
public interface User {
    @RequestMapping(value = "/getUser",method = RequestMethod.GET)
    String getUser(@RequestParam(value = "id") String id);
}
