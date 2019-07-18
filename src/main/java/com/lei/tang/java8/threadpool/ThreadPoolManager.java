package com.lei.tang.java8.threadpool;

import javax.annotation.PreDestroy;
import java.util.concurrent.*;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author tanglei
 * @date 2019/7/18
 * <p>
 * 线程池管理器
 */
@Slf4j
@Component
public class ThreadPoolManager {

    private static volatile ThreadPool threadPool;

    public static ThreadPool getThreadPool() {
        if (threadPool == null) {
            initThreadPool();
        }
        return threadPool;
    }

    public static void initThreadPool() {
        if (threadPool == null) {
            synchronized (ThreadPoolManager.class) {
                if (threadPool == null) {
                    // 获取处理器数量
                    int cpuNum = Runtime.getRuntime().availableProcessors();
                    // 根据cpu数量,计算出合理的线程并发数
                    int threadNum = cpuNum * 2;
                    log.info("cpuNum [{}]，threadNum [{}]", cpuNum, threadNum);
                    threadPool = new ThreadPool(threadNum, threadNum, 0L);
                }
            }
        }
    }

    /**
     * PostConstruct修饰的方法会在服务器加载Servle的时候运行，并且只会被服务器执行一次。PostConstruct在构造函数之后执行,init()方法之前执行。方法在destroy()方法执行执行之后执行
     */
    @PreDestroy
    public void destroyThreadPool() {
        if (threadPool != null) {
            log.info("thread pool shutdown.");
            threadPool.shutdown();
        }
    }

    @Getter
    public static class ThreadPool {
        private ThreadPoolExecutor threadPoolExecutor;

        private ThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime) {
            //定义线程池名字
            ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("thread pool").build();
            //创建线程池
            /**
             *
             */
            threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime,
                    TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>(), namedThreadFactory,
                    new ThreadPoolExecutor.AbortPolicy());
        }

        /**
         * 提交一个任务到线程池中
         *
         * @param runnable
         */
        public void execute(Runnable runnable) {
            if (runnable == null) {
                return;
            }
            threadPoolExecutor.execute(runnable);
        }

        /**
         * 提交一个任务到线程池中，并返回执行结果
         *
         * @param futureTask
         */
        public <T> void submit(FutureTask<T> futureTask) {
            if (futureTask == null) {
                return;
            }
            threadPoolExecutor.submit(futureTask);
        }

        /**
         * 从线程队列中移除对象
         *
         * @param runnable
         */
        public void cancel(Runnable runnable) {
            if (threadPoolExecutor != null) {
                threadPoolExecutor.getQueue().remove(runnable);
            }
            threadPoolExecutor.shutdown();
        }

        public <T> void cancel(Callable<T> task) {
            if (threadPoolExecutor != null) {
                threadPoolExecutor.getQueue().remove(task);
            }
        }

        /**
         * 不再接受新任务了，但是队列里的任务得执行完毕
         */
        public void shutdown() {
            if (threadPoolExecutor != null) {
                threadPoolExecutor.shutdown();
            }
        }
    }
}
