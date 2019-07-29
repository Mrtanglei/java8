package com.lei.tang.java8.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * @author tanglei
 * @date 2019/7/26
 */
@Slf4j
public class ThreadStatus {

    private static volatile int index = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread threadA = new Thread(new ThreadA());
        log.info("threadA status [{}]",threadA.getState());
        Thread threadB = new Thread(new ThreadB());log.info("threadB status [{}]",threadB.getState());

        threadA.start();
        threadB.start();
        while (threadA.isAlive() || threadB.isAlive()){
            log.info("threadA status [{}]",threadA.getState());
            log.info("threadB status [{}]",threadB.getState());
        }
        log.info("threadA status [{}]",threadA.getState());
        log.info("threadB status [{}]",threadB.getState());
        log.debug("main treand 执行完毕");
    }

    public static class ThreadA implements Runnable{
        @Override
        public void run() {
            while (ThreadStatus.index < 3) {
                synchronized (ThreadStatus.class) {
                    log.info("ThreadA，获取锁");
                    //满足条件执行逻辑
                    if (index % 2 == 0) {
                        index++;
                        //将等待队列中的线程移动到同步队列中，线程状态也会更新为 BLOCKED
                        ThreadStatus.class.notify();
                    } else {
                        try {
                            //释放锁，进入 WAITING 状态，该线程也会被移动到等待队列中
                            log.info("ThreadA释放锁");
                            ThreadStatus.class.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public static class ThreadB implements Runnable{
        @Override
        public void run() {
            while (ThreadStatus.index < 3) {
                synchronized (ThreadStatus.class) {
                    log.info("ThreadB，获取锁");
                    //满足条件执行逻辑
                    if (index % 2 != 0) {
                        index++;
                        //将等待队列中的线程移动到同步队列中，线程状态也会更新为 BLOCKED
                        ThreadStatus.class.notify();
                    } else {
                        try {
                            //释放锁，进入 WAITING 状态，该线程也会被移动到等待队列中
                            log.info("ThreadB释放锁");
                            ThreadStatus.class.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
