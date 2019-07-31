package com.lei.tang.java8.thread.communication;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import lombok.extern.slf4j.Slf4j;

/**
 * @author tanglei
 * @date 2019/7/31
 */
@Slf4j
public class CyclicBarrierDemo {

    /**
     * CyclicBarrier(int parties, Runnable barrierAction)构造函数，最后一个线程执行await方法后执行Runnable任务
     *
     * @param parties
     * @param barrierAction
     */
    private static void cyclicBarrier(int parties, Runnable barrierAction) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(parties, barrierAction);
        cyclicBarrier(cyclicBarrier);
        log.debug("main thread end");
    }

    /**
     * CyclicBarrier(int parties)构造
     *
     * @param parties 指定线程数量
     */
    private static void cyclicBarrier(int parties) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(parties);
        cyclicBarrier(cyclicBarrier);
        log.debug("main 1 thread end");
        while (true) {
            //cyclicBarrier未执行未执行await方法的线程数量，如上次未执行完毕执行reset方法则会抛出BrokenBarrierException异常
            if (cyclicBarrier.getNumberWaiting() == 0) {
                //重置CyclicBarrier到新建状态
                cyclicBarrier.reset();
                cyclicBarrier(cyclicBarrier);
                break;
            }
        }
        log.debug("main thread end");
    }

    private static void cyclicBarrier(CyclicBarrier cyclicBarrier) {
        new Thread(() -> {
            log.info(Thread.currentThread().getName() + " start " + System.currentTimeMillis());
            try {
                cyclicBarrier.await();
                log.info(Thread.currentThread().getName() + " end " + System.currentTimeMillis());
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }, "threadA").start();

        new Thread(() -> {
            log.info(Thread.currentThread().getName() + " start " + System.currentTimeMillis());
            try {
                cyclicBarrier.await();
                log.info(Thread.currentThread().getName() + " end " + System.currentTimeMillis());
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }, "threadB").start();

        new Thread(() -> {
            log.info(Thread.currentThread().getName() + " start " + System.currentTimeMillis());
            try {
                Thread.sleep(2000);
                cyclicBarrier.await();
                log.info(Thread.currentThread().getName() + " end " + System.currentTimeMillis());
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }, "threadC").start();
    }
}
