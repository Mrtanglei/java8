package com.lei.tang.java8.thread.communication;

import lombok.extern.slf4j.Slf4j;

/**
 * @author tanglei
 * @date 2019/7/29
 *
 * volatile共享内存终止线程
 */
@Slf4j
public class Volatile {

    private static volatile boolean flag = true;

    public static void main(String[] args) {
        new Thread(()->{
            while (flag){
                log.debug("Thread runing");
            }
            log.debug("Thread run end");
        }).start();

        log.debug("main Thread runing");

        flag = false;
    }
}
