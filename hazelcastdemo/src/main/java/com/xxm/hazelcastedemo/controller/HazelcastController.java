package com.xxm.hazelcastedemo.controller;

import com.hazelcast.core.HazelcastInstance;
import com.xxm.hazelcastedemo.service.MusicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class HazelcastController {

    private Logger logger = LoggerFactory.getLogger(HazelcastController.class);

    @Autowired
    private MusicService musicService;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private HazelcastInstance hazelcastInstance;

    //参考 https://blog.csdn.net/zhongzunfa/article/details/80551753
    @RequestMapping("/hezelcast")
    @ResponseBody
    public void getHazelcast() {

        logger.info("Spring Boot Hazelcast Caching Example Configuration");
        logger.info("Using cache manager: " + cacheManager.getClass().getName());

        // 清空缓存
        musicService.clearCache();

        // 调用方法
        play("trombone");
        play("guitar");

        play("trombone");
        play("guitar");

        play("bass");
        play("trombone");
        play("guitar");
    }

    private void play(String instrument) {
        logger.info("Calling: " + MusicService.class.getSimpleName() + ".play(\"" + instrument + "\");");
        musicService.play(instrument);
    }

    //参考   https://www.cnblogs.com/cxyxiaobao/p/12394360.html
    @PostMapping(value = "/write-data")
    public String writeDataToHazelcast(@RequestParam String key, @RequestParam String value) {
        Map<String, String> hazelcastMap = hazelcastInstance.getMap("my-map");
        hazelcastMap.put(key, value);
        logger.info("write "+key);
        return "Data is stored.";
    }

    @GetMapping(value = "/read-data")
    public String readDataFromHazelcast(@RequestParam String key) {
        Map<String, String> hazelcastMap = hazelcastInstance.getMap("my-map");
        return hazelcastMap.get(key);
    }

    @GetMapping(value = "/read-all-data")
    public Map<String, String> readAllDataFromHazelcast() {
        Map<String, String> hazelcastMap = hazelcastInstance.getMap("my-map");
        return hazelcastInstance.getMap("my-map");
    }
}