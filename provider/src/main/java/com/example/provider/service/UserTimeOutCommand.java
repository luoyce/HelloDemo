//package com.example.provider.service;
//
//import com.netflix.hystrix.*;
//
//import static java.lang.System.currentTimeMillis;
//
///**
// * @description:
// * @author: weijian.yan
// * @create: 2020-05-27 10:50
// **/
//public class UserTimeOutCommand  extends HystrixCommand<String> {
//    String Key="";
//
//    public UserTimeOutCommand(String key) {
//        super(Setter
//                // 分组名称用于统计
//                .withGroupKey(HystrixCommandGroupKey.Factory.asKey("userGroup"))
//                // 用于监控、熔断、度量发布、缓存的Key值
//                .andCommandKey(HystrixCommandKey.Factory.asKey("userCommandKey"))
//                // 线程池命名，默认是HystrixCommandGroupKey的名称。
//                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("userThreadPool"))
//                // command 熔断相关参数配置
//                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
//                        // 隔离方式：线程池和信号量。默认使用线程池
//                        //.withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.THREAD)
//                        // 超时时间500毫秒
//                        //.withExecutionTimeoutEnabled(true)
//                        //.withExecutionTimeoutInMilliseconds(1500)
//                        // 信号量隔离的模式下，最大的请求数。和线程池大小的意义一样
//                        // .withExecutionIsolationSemaphoreMaxConcurrentRequests(2)
//                        // 熔断时间（熔断开启后，各5秒后进入半开启状态，试探是否恢复正常）
//                        // .withCircuitBreakerSleepWindowInMilliseconds(5000)
//                )
//                // 设置线程池参数
//                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
//                        // 线程池大小
//                        .withCoreSize(1)));
//
//        Key = key;
//    }
//
//    @Override
//    protected String run() throws Exception {
//        System.out.println("===开始处理：" + Key +" "+Thread.currentThread().getId());
//        Long val = currentTimeMillis();
//        long mol = val%10;
//
//        if(mol==0 ||mol==1){
//            System.out.println("===超时了："+Key);
//            Thread.sleep(6000);
//        }else  if (mol==2 ||mol==3){
//            System.out.println("===异常了："+Key);
//            throw new Exception("模拟错误");
//        }else {
//            Thread.sleep(60);
//        }
//
//
//        System.out.println("===结束处理："+Key);
//        return new String("正常啊");
//    }
//
//    @Override
//    protected String getFallback() {
//        // 执行超时、出错或者开启熔断之后，使用这个方法返回
//        // 这种策略称为服务降级
//        // 这里可以是一个固定返回值，查询缓存等
//        System.out.println("XXXXXXXX降级处理："+Key);
//        return "服务降级，暂时不可用:" + Key;
//    }
//}
