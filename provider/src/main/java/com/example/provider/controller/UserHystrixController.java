//package com.example.provider.controller;
//
//import com.example.provider.service.UserExceptionCommand;
//import com.example.provider.service.UserTimeOutCommand;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * @description:
// * @author: weijian.yan
// * @create: 2020-05-27 09:17
// **/
//@RestController
//@RequestMapping("/userHystrix")
//public class UserHystrixController {
////    @RequestMapping("/command/timeout")
////    public String commandTimeout(String key) {
////        System.out.println("开始处理：" + key +" "+Thread.currentThread().getId());
////        UserTimeOutCommand cmd = new UserTimeOutCommand(key);
////        String res = cmd.execute();
////        return  res;
////    }
////
////    @RequestMapping("/command/exception")
////    public String commandException() {
////        return new UserExceptionCommand().execute();
////    }
//}
