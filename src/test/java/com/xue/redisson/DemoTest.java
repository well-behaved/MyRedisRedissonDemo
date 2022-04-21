package com.xue.redisson;

import org.junit.jupiter.api.Test;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

/**
 * @author: xuexiong@souche.com
 * @date: 2022/4/19 17:02
 * @description:
 */
@SpringBootTest
public class DemoTest {
    @Autowired
    RedissonClient redissonClient;

    /**
     * 普通加锁尝试
     *
     * @return
     * @throws
     * @author xuexiong@souche.com
     * @date 2022/4/19 17:52
     */
    @Test
    public void lockCommon() throws InterruptedException {
        RLock xuexiongLock = redissonClient.getLock("xuexiong");
        xuexiongLock.lock();
        //可重入锁
        xuexiongLock.lock();

        // 3. 尝试加锁，最多等待3秒，上锁以后10秒自动解锁
        boolean res = xuexiongLock.tryLock(3, 10, TimeUnit.SECONDS);
        System.out.println("res:"+res);

        Thread.sleep(10000);
        xuexiongLock.unlock();
        xuexiongLock.unlock();

        xuexiongLock.unlock();
        Thread.sleep(10000000);
        System.out.println("hello");
    }

}