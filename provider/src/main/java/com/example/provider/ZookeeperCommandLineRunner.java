package com.example.provider;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.List;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
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
    //    PERSISTENT：持久化
//    PERSISTENT_SEQUENTIAL：持久化并且带序列号
//    EPHEMERAL：临时
//    EPHEMERAL_SEQUENTIAL：临时并且带序列号
    @Autowired
    Environment environment;
    private static final String PATH_ROOT = "/root";
    private static final String CONNECT_TOSTRING = "127.0.0.1:2181";

    private static NodeCache nodeCache=null;
    private static PathChildrenCache childrenCache=null;
    private static LeaderLatch leaderLatch=null;

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

            String leaderNode = PATH_ROOT+"/leader";
            if(curatorFramework.checkExists().forPath(leaderNode) == null){
                curatorFramework.create().creatingParentContainersIfNeeded().
                        withMode(CreateMode.PERSISTENT).
                        forPath(leaderNode, environment.getProperty("local.server.port").getBytes());

                System.out.println("创建node");
            }

            String leaderNodeTest = PATH_ROOT+"/leader1/Test"+environment.getProperty("local.server.port");
            if(curatorFramework.checkExists().forPath(leaderNodeTest) == null){
                curatorFramework.create().creatingParentContainersIfNeeded().
                        withMode(CreateMode.EPHEMERAL_SEQUENTIAL).
                        forPath(leaderNodeTest, environment.getProperty("local.server.port").getBytes());

                System.out.println("创建:"+leaderNodeTest);
            }

            //使用顺序特征原理
            leaderLatch = new LeaderLatch(curatorFramework, leaderNode, environment.getProperty("local.server.port"));
            leaderLatch.addListener(new LeaderLatchListener() {
                @Override
                public void isLeader() {
                    System.out.println("Currently run as leader:"+environment.getProperty("local.server.port"));
                }

                @Override
                public void notLeader() {
                    System.out.println("Currently run as slave:"+environment.getProperty("local.server.port"));
                }
            });
            leaderLatch.start();



            //创建节点
            //curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(PATH_ROOT+"/server", "456".getBytes());
            String provider = PATH_ROOT+"/server/provider"+environment.getProperty("local.server.port").getBytes();
            curatorFramework.create().creatingParentContainersIfNeeded().
                    withMode(CreateMode.EPHEMERAL).
                    forPath(provider, environment.getProperty("local.server.port").getBytes());
            //curatorFramework.setData().forPath(PATH_ROOT+"/server/provider", environment.getProperty("local.server.port").getBytes());
            //获取所有子节点
            //List<String> strings = curatorFramework.getChildren().forPath(PATH_ROOT);

            /*设置节点事件监听*/
//            nodeCache = new NodeCache(curatorFramework, PATH_ROOT);
//            nodeCache.start();
//            nodeCache.getListenable().addListener(new NodeCacheListener() {
//                @Override
//                public void nodeChanged() throws Exception {
//                    byte[] result = nodeCache.getCurrentData().getData();
//                    System.out.println("======="+PATH_ROOT + "=" + new String(result));
//                }
//            });

            /*设置子节点事件监听*/
//            childrenCache = new PathChildrenCache(curatorFramework, PATH_ROOT, true);
//            childrenCache.start();
//            childrenCache.getListenable().addListener(new PathChildrenCacheListener() {
//                @Override
//                public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
//                    PathChildrenCacheEvent.Type type = pathChildrenCacheEvent.getType();
//                    switch (type) {
//                        case CHILD_ADDED:
//                            System.out.println("=======CHILD_ADDED:"+pathChildrenCacheEvent.getData().getPath()+"="+new String(pathChildrenCacheEvent.getData().getData()));
//                        case CHILD_UPDATED:
//                            System.out.println("=======CHILD_UPDATED:"+pathChildrenCacheEvent.getData().getPath()+"="+new String(pathChildrenCacheEvent.getData().getData()));
//                        case CHILD_REMOVED:
//                            System.out.println("=======CHILD_REMOVED:"+pathChildrenCacheEvent.getData().getPath()+"="+new String(pathChildrenCacheEvent.getData().getData()));
//                        default:
//                            break;
//                    }
//                }
//            });

            int xxx =1;
            System.out.println("---------------------启动完成-----------------------");
        }catch (Exception ex){
            System.out.println("---------------------启动异常-----------------------");
            System.out.println(ex);
        }

    }
}
