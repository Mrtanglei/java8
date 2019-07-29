package com.lei.tang.java8.thread.communication;

import java.util.concurrent.CountDownLatch;

import com.lei.tang.java8.thread.threadpool.ThreadPoolManager;
import lombok.extern.slf4j.Slf4j;

/**
 * @author tanglei
 * @date 2019/7/29
 */
@Slf4j
public class CountDownLatchDemo {

    private static volatile int index = 0;

    public static void main(String[] args) throws InterruptedException {
        //创建倒计数器，指定计数数量为3
        CountDownLatch countDownLatch = new CountDownLatch(3);
        ThreadPoolManager.ThreadPool threadPool = ThreadPoolManager.getThreadPool();
        for (int i = 1; i < 4; i++) {
            int s=++index;
            threadPool.execute(() -> {
                log.info(s + " runing " + System.currentTimeMillis());
                try {
                    Thread.sleep(1000);
                    log.info(s + " run end " + System.currentTimeMillis());
                } catch (InterruptedException e) {
                    log.error(e.getMessage(), e);
                } finally {
                    //通知CountDownLatch有一个线程已经准备完毕，倒计数器可以减一了
                    countDownLatch.countDown();
                }
            });
        }
        //要求主线程需等所有线程准备好后，并行执行完毕后才能继续执行
        countDownLatch.await();
        //关闭线程池
        threadPool.shutdown();
        log.debug("main Thread end");
    }
}
