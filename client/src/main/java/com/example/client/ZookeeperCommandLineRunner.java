package com.example.client;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description:
 * @author: weijian.yan
 * @create: 2020-06-10 15:46
 **/
@Component
public class ZookeeperCommandLineRunner implements CommandLineRunner {

    private static final String PATH_ROOT = "/root";
    private static final String CONNECT_TOSTRING = "127.0.0.1:2181";

    @Override
    public void run(String... var1){
        try {
            RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
            CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                    .connectString(CONNECT_TOSTRING)
                    .sessionTimeoutMs(5000)
                    .connectionTimeoutMs(5000)
                    .retryPolicy(retryPolicy)
                    .build();

            curatorFramework.start();


            curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(PATH_ROOT, "456".getBytes());
            curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(PATH_ROOT+"/test", "456".getBytes());
            List<String> strings = curatorFramework.getChildren().forPath(PATH_ROOT);
            int xxx =1;
        }catch (Exception ex){
            System.out.println(""+ex.getMessage());
        }

    }
}
