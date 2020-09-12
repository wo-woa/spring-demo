package com.xxm.hazelcastedemo.config;

import com.hazelcast.config.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class HazelCastConfiguration {
    @Value("${hazelcast.join-addrs:127.0.0.1}")
    private String[] joinAddrs;

    @Bean
    public Config hazelCastConfig() {
        //如果有集群管理中心，可以配置
        ManagementCenterConfig centerConfig = new ManagementCenterConfig();
        centerConfig.setUrl("http://127.0.0.1:8200/mancenter");
        centerConfig.setEnabled(true);

        NetworkConfig networkConfig=new NetworkConfig().setPort(5601);
        networkConfig.getJoin().getMulticastConfig().setEnabled(false);
        networkConfig.getJoin().getTcpIpConfig().setEnabled(true).setMembers(Arrays.asList(joinAddrs));
        return new Config().setInstanceName("ins").setNetworkConfig(networkConfig).addMapConfig(new MapConfig()
                .setName("instruments").setEvictionPolicy(EvictionPolicy.NONE).setMaxIdleSeconds(720*60));

//        return new Config()
//                .setInstanceName("hazelcast-instance")
//                .setManagementCenterConfig(centerConfig)
//                .addMapConfig(
//                        new MapConfig()
//                                .setName("instruments")
//                                .setMaxSizeConfig(new MaxSizeConfig(200, MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE))
//                                .setEvictionPolicy(EvictionPolicy.LRU)
//                                .setTimeToLiveSeconds(20000));



    }

//    @Bean
//    public HazelcastInstance hazelcastInstance(Config config) {
//        HazelcastInstance hzInstance = Hazelcast.newHazelcastInstance(config);
//        //分布式map监听
//        IMap<Object, Object> imap = hzInstance.getMap("my-map");
//        imap.addLocalEntryListener(new IMapListener());
//        //拦截器（没写内容）
//        imap.addInterceptor(new IMapInterceptor());
//        //发布/订阅模式
//        ITopic<String> topic = hzInstance.getTopic("topic");
//        topic.addMessageListener(new TopicListener());
//
//        return hzInstance;
//    }
}