package com.lei.tang.java8.demo;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.lei.tang.java8.domain.Orange;
import lombok.extern.slf4j.Slf4j;

/**
 * @author tanglei
 * @date 2019/8/20
 * <p>
 * 流操作
 */
@Slf4j
public class StreamDemo {

    public static void main(String[] args) {
        List<Orange> list = Lists.newArrayList(new Orange(1.2, "红"), new Orange(2.3, "黄"), new Orange(3.2, "绿"),
                new Orange(0.2, "青"), new Orange(0.5, "蓝"));
        List<String> collect =
                list.stream().filter(orange -> orange.getWeight() > 1).sorted((orange1, orange2) -> orange2.getWeight() > orange1.getWeight() ? 0 : -1).map(Orange::getColor).limit(2).collect(Collectors.toList());
        log.debug(collect.toString());
    }
}