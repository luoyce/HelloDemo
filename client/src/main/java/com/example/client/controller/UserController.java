package com.example.client.controller;

import com.example.client.service.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * @description:
 * @author: weijian.yan
 * @create: 2020-05-23 16:59
 **/
@RestController
public class UserController {
    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    public User user;

    @GetMapping(value = "/router2")
    public String router2(){
        String res = user.getUser("1");

        return  res;
    }

    @GetMapping(value = "/router")
    public String router(){
        ServiceInstance si=this.loadBalancerClient.choose("PROVIDER");
        RestTemplate rt=new RestTemplate();

        String url = si.getUri()+"/getUser?id=1";
        ResponseEntity<String> res = rt.exchange(url, HttpMethod.GET,null,String.class);

        return  si.getUri() +"："+res.getBody();
    }
}