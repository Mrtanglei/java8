package com.lei.tang.java8.threadpool;

import lombok.extern.slf4j.Slf4j;

/**
 * @author tanglei
 * @date 2019/7/18
 */
@Slf4j
public class ConsumerQueueThread implements Runnable {

    private static volatile int i = 0;

    @Override
    public void run() {
        log.info("start thread [{}] time [{}]",i++,System.currentTimeMillis());
        log.info("queue size [{}]",ThreadPoolManager.getThreadPool().getThreadPoolExecutor().getQueue().size());
    }
}
