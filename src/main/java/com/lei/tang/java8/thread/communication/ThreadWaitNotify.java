package com.lei.tang.java8.thread.communication;

import java.util.List;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

/**
 * @author tanglei
 * @date 2019/7/24
 * <p>
 * 等待机制通信
 */
@Slf4j
public class ThreadWaitNotify {

    private static final List<Integer> list = Lists.newArrayList(2, 15, 5, 4, 8, 17, 23, 24, 43, 50, 57, 31);

    private static int index = 0;

    public static void main(String[] args) {
        new Thread(new OneThread()).start();
        new Thread(new TwoThread()).start();
        log.debug("main treand 执行完毕");
    }

    public static class OneThread implements Runnable {
        @Override
        public void run() {
            while (ThreadWaitNotify.index < list.size()) {
                synchronized (ThreadWaitNotify.class) {
                    log.info("OneThread抢到锁，index为[{}]", index);
                    //满足条件执行逻辑
                    if (index % 2 == 0) {
                        log.info("OneThread取出数据[{}]", list.get(index));
                        index++;
                        //将等待队列中的线程移动到同步队列中，线程状态也会更新为 BLOCKED
                        ThreadWaitNotify.class.notify();
                    } else {
                        try {
                            //释放锁，进入 WAITING 状态，该线程也会被移动到等待队列中
                            log.info("OneThread释放锁");
                            ThreadWaitNotify.class.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public static class TwoThread implements Runnable {
        @Override
        public void run() {
            while (ThreadWaitNotify.index < list.size()) {
                synchronized (ThreadWaitNotify.class) {
                    log.info("TwoThread抢到锁，index为[{}]", index);
                    //满足条件执行逻辑
                    if (index % 2 != 0) {
                        log.info("TwoThread取出数据[{}]", list.get(index));
                        index++;
                        ThreadWaitNotify.class.notify();
                    } else {//释放锁，进入 WAITING 状态，该线程也会被移动到等待队列中
                        try {
                            log.info("TwoThread释放锁");
                            ThreadWaitNotify.class.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
