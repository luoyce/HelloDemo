//package com.example.provider.service;
//
//import com.netflix.hystrix.HystrixCommand;
//import com.netflix.hystrix.HystrixCommandGroupKey;
//
///**
// * @description:
// * @author: weijian.yan
// * @create: 2020-05-27 10:52
// **/
//public class UserExceptionCommand extends HystrixCommand<String> {
//    public UserExceptionCommand() {
//        super(HystrixCommandGroupKey.Factory.asKey("userGroup"));
//    }
//
//    @Override
//    protected String run() throws Exception {
//        return new String("正常啊");
//    }
//
//    @Override
//    protected String getFallback() {
//        return "服务降级，暂时不可用";
//    }
//}
