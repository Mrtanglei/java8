package com.lei.tang.java8.thread.communication;

import lombok.extern.slf4j.Slf4j;

/**
 * @author tanglei
 * @date 2019/8/2
 */
@Slf4j
public class StopThread implements Runnable {

    public static void main(String[] args) throws InterruptedException {
        Thread thread =new Thread(new StopThread());
        thread.start();
        log.debug("main Thread");
        thread.interrupt();
        log.debug("调用interrupt后是否中断" + thread.isInterrupted());
        log.debug("调用interrupt后thread是否存活：" + thread.isAlive());
        Thread.sleep(1000);
        log.debug("Thread end后是否中断：" + thread.isInterrupted());
        log.debug("Thread end后是否存活：" + thread.isAlive());
    }

    @Override
    public void run() {
        for (int i = 1; i <= 10 && !Thread.currentThread().isInterrupted(); i++) {
            log.debug("i = "+i);
        }
        log.debug("Thread end");
    }
}
