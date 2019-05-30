package com.lei.tang.java8.scheduled;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author tanglei
 * @date 2019/5/10
 */
@Slf4j
@Component
public class ScheduledDemo {

    private List<Integer> index = Arrays.asList(2000, 8000, 1000, 1000, 2000);

    private AtomicInteger atomicInteger = new AtomicInteger(0);

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public ScheduledDemo() {
        log.info("加载ScheduledDemo");
    }

    //    /**
    //     * 此方式为在执行完成一次后间隔1秒后再执行下一次
    //     * @throws InterruptedException
    //     */
    //        @Scheduled(fixedDelay = 1 * 1000)
    //        public void fixedDelay() throws InterruptedException {
    //            int i = atomicInteger.get();
    //            if (i < 3) {
    //                log.info("开始执行时间 {}", simpleDateFormat.format(new Date(System.currentTimeMillis())));
    //                log.info("第{}此执行，执行时间为{}ms", i, index.get(i));
    //                Thread.sleep(index.get(i));
    //                atomicInteger.getAndIncrement();
    //            }
    //        }


    //    /**
    //     * 此处设定的是0,5,15,25...秒时执行
    //     *
    //     * @throws InterruptedException
    //     */
    //    @Scheduled(cron = "0/5 * * * * ?")
    //    public void cron() throws InterruptedException {
    //        int i = atomicInteger.get();
    //        if (i < 3) {
    //            log.info("开始执行时间 {}", System.currentTimeMillis());
    //            log.info("第{}此执行，执行时间为{}ms", i, index.get(i));
    //            Thread.sleep(index.get(i));
    //            atomicInteger.getAndIncrement();
    //        }
    //    }

    @Scheduled(fixedRate = 5 * 1000)
    public void fixedRate() throws InterruptedException {
        int i = atomicInteger.get();
        if (i < 5) {
            log.info("开始执行时间 {}", simpleDateFormat.format(new Date(System.currentTimeMillis())));
            log.info("第{}此执行，执行时间为{}ms", i, index.get(i));
            Thread.sleep(index.get(i));
            atomicInteger.getAndIncrement();
        }
    }
}
