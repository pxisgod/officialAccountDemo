package com.px.oad.configuration;

import com.alibaba.fastjson.JSONObject;
import com.px.oad.vo.AccessTokenInfo;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * access_token服务器的zk配置
 */
@Configuration
public class ServerZkConfig {

    //当前使用的accessToken
    private  static AccessTokenInfo accessTokenInfo;

    private static ReentrantReadWriteLock accessTokenLock=new ReentrantReadWriteLock();

    public static AccessTokenInfo getAccessTokenInfo(){
        accessTokenLock.readLock().lock();
        if(accessTokenInfo==null)
            return null;
        AccessTokenInfo newAccessTokenInfo=new AccessTokenInfo();
        newAccessTokenInfo.setToken(accessTokenInfo.getToken());
        newAccessTokenInfo.setVersion(accessTokenInfo.getVersion());
        accessTokenLock.readLock().unlock();
        return newAccessTokenInfo;
    }

    public static void setAccessTokenInfo(AccessTokenInfo newAccessTokenInfo){
        accessTokenLock.writeLock().lock();
        accessTokenInfo=newAccessTokenInfo;
        accessTokenLock.writeLock().unlock();
    }

    //监听ACCESS_TOKEN的节点
    private static String ACCESS_TOKEN_NODE="/access_token";


    @Bean(name="serverZk",destroyMethod = "close")
    public CuratorFramework getZkClient() throws Exception {
        CuratorFramework client = CuratorFrameworkFactory
                .builder()
                .connectString("127.0.0.1:2181")
                .sessionTimeoutMs(5000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .build();
        //开启客户端
        client.start();
        //注册监听
        PathChildrenCache pathChildrenCache = new PathChildrenCache(client, "/", true);

        pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener() {

            @Override
            public void childEvent(CuratorFramework curatorFramework,
                                   PathChildrenCacheEvent event) throws Exception {

                byte[] data;
                String path;
                switch (event.getType()) {
                    case CHILD_ADDED:
                    case CHILD_UPDATED:
                        path = event.getData().getPath();
                        //添加ACCESS_TOKEN
                        if (ACCESS_TOKEN_NODE.equals(path)) {
                            data = event.getData().getData();
                            AccessTokenInfo curAccessTokenInfo = JSONObject.parseObject(new String(data, "UTF-8"), AccessTokenInfo.class);
                            setAccessTokenInfo(curAccessTokenInfo);
                        }
                        break;
                    case CHILD_REMOVED:
                        break;
                    case CONNECTION_RECONNECTED:
                        //zk重连
                        System.out.println("zk重连");
                        break;
                    case INITIALIZED:
                        //使用POST_INITIALIZED_EVENT启动,可以做一些初始化工作
                        break;
                    default:
                        break;
                }
            }
        });
        pathChildrenCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
        return client;
    }

}
