package com.px.oad.configuration;

import com.alibaba.fastjson.JSONObject;
import com.px.oad.service.AccessTokenService;
import com.px.oad.service.PushMsgService;
import com.px.oad.vo.AccessTokenInfo;
import com.px.oad.vo.VpnInfo;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.concurrent.locks.ReentrantReadWriteLock;



/**
 * 客户端的zk配置
 */
@Configuration
public class ClientZkConfig {

    @Value("${clientZk}")
    private String clientZk;

    //获取accessToken的服务
    @Autowired
    private AccessTokenService accessTokenService;

    //推送消息的服务
    @Autowired
    private PushMsgService pushMsgService;

    //当前使用的accessToken
    private static AccessTokenInfo accessTokenInfo;

    private static ReentrantReadWriteLock accessTokenLock = new ReentrantReadWriteLock();

    public static AccessTokenInfo getAccessTokenInfo() {
        accessTokenLock.readLock().lock();
        if (accessTokenInfo == null)
            return null;
        AccessTokenInfo newAccessTokenInfo = new AccessTokenInfo();
        newAccessTokenInfo.setToken(accessTokenInfo.getToken());
        newAccessTokenInfo.setVersion(accessTokenInfo.getVersion());
        accessTokenLock.readLock().unlock();
        return newAccessTokenInfo;
    }

    public static void setAccessTokenInfo(AccessTokenInfo newAccessTokenInfo) {
        accessTokenLock.writeLock().lock();
        accessTokenInfo = newAccessTokenInfo;
        accessTokenLock.writeLock().unlock();
    }

    //当前使用的vpn信息
    private static VpnInfo vpnInfo;

    private static ReentrantReadWriteLock vpnLock = new ReentrantReadWriteLock();

    public static VpnInfo getVpnInfo() {
        vpnLock.readLock().lock();
        if (vpnInfo == null)
            return null;
        VpnInfo newVpnInfo = new VpnInfo();
        newVpnInfo.setServerIp(vpnInfo.getServerIp());
        newVpnInfo.setServerPort(vpnInfo.getServerPort());
        newVpnInfo.setPassword(vpnInfo.getPassword());
        newVpnInfo.setMethod(vpnInfo.getMethod());
        newVpnInfo.setProtocol(vpnInfo.getProtocol());
        newVpnInfo.setObfs(vpnInfo.getObfs());
        vpnLock.readLock().unlock();
        return newVpnInfo;
    }

    public static void setVpnInfo(VpnInfo newVpnInfo) {
        vpnLock.writeLock().lock();
        vpnInfo = newVpnInfo;
        vpnLock.writeLock().unlock();
    }


    //监听ACCESS_TOKEN的节点
    public static String ACCESS_TOKEN_NODE = "/access_token";

    //监听VPN信息的节点
    public static String VPN_INFO_NODE = "/vpn_info";


    @Bean(name="clientZk",destroyMethod = "close")
    public CuratorFramework getZkClient() throws Exception {
        CuratorFramework client = CuratorFrameworkFactory
                .builder()
                .connectString(clientZk)
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
                        } else {
                            if (VPN_INFO_NODE.equals(path)) {
                                data = event.getData().getData();
                                VpnInfo vpnInfo = JSONObject.parseObject(new String(data, "UTF-8"), VpnInfo.class);
                                setVpnInfo(vpnInfo);
                            }
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
