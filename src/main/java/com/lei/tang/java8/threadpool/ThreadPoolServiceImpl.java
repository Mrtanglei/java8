package com.lei.tang.java8.threadpool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author tanglei
 * @date 2019/7/18
 */
@Slf4j
@Service("threadPoolService")
public class ThreadPoolServiceImpl implements ThreadPoolService {

    @Override
    public boolean testExcuteThread(int threadNumber) {
        log.info("start testExcuteThread");
        for (int i = 0; i < threadNumber; i++) {
            ThreadPoolManager.getThreadPool().execute(new ConsumerQueueThread());
        }
        log.info("end testExcuteThread");
        return true;
    }
}
