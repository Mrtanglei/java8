package com.lei.tang.java8.thread.communication;

import lombok.extern.slf4j.Slf4j;

/**
 * @author tanglei
 * @date 2019/7/25
 * <p>
 * Thread join方法使外线程等待到此线程接收继续执行
 */
@Slf4j
public class JoinThread {

    public static void join() throws InterruptedException {
        long startTime = System.currentTimeMillis();
        Thread threadA = new Thread(() -> {
            try {
                log.info("threadA start");
                Thread.sleep(4000);
                log.info("threadA end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread threadB = new Thread(() -> {
            try {
                threadA.join();
                log.info("threadB start");
                Thread.sleep(3000);
                log.info("threadB end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        threadA.start();
        threadB.start();


        threadB.join();
        log.info("run time [{}]", startTime - System.currentTimeMillis());
        log.info("main thread end");
    }
}
