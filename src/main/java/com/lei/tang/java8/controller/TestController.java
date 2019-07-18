package com.lei.tang.java8.controller;

import com.lei.tang.java8.threadpool.ThreadPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tanglei
 * @date 2019/5/10
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private ThreadPoolService threadPoolService;

    @GetMapping("/testExcuteThread/{threadNumber}")
    public boolean testExcuteThread(@PathVariable int threadNumber) {
        return threadPoolService.testExcuteThread(threadNumber);
    }
}
