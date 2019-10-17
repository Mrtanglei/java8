package com.lei.tang.java8.demo;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.lei.tang.java8.domain.Orange;
import com.lei.tang.java8.domain.People;
import lombok.extern.slf4j.Slf4j;

/**
 * @author tanglei
 * @date 2019/8/20
 * <p>
 * 流操作
 */
@Slf4j
public class StreamDemo {

    private static void test() {
        List<Orange> list = Lists.newArrayList(new Orange(1.2, "红"), new Orange(2.3, "黄"), new Orange(3.2, "绿"),
                new Orange(0.2, "青"), new Orange(0.5, "蓝"));
        List<String> collect =
                list.stream().filter(orange -> orange.getWeight() > 1).sorted((orange1, orange2) -> orange2.getWeight() > orange1.getWeight() ? 0 : -1).map(Orange::getColor).limit(2).collect(Collectors.toList());
        log.debug(collect.toString());
    }

    /**
     * filter筛选
     */
    private static void filter() {
        List<Integer> list = Lists.newArrayList(1, 3, 10, 2, 8, 4, -10, -5);
        //筛选出大于等于4并是偶数的元素后迭代输出
        list.stream().filter(i -> i >= 4 && i % 2 == 0).forEach(System.out::println);
        //输出结果：10 8 4
    }

    /**
     * distinct去重
     */
    private static void distinct() {
        List<Integer> list = Lists.newArrayList(1, 3, 3, 2, -5, 4, -10, -5);
        //筛选出所有奇数去重后输出
        list.stream().filter(i -> i % 2 != 0).distinct().forEach(System.out::println);
        //输出结果：1 3 -5
    }

    /**
     * limit(n)截取前n位
     */
    private static void limit() {
        log.debug("有序集合");
        List<Integer> list = Lists.newArrayList(1, 3, 3, 2, -5, 4, -10, -5);
        list.stream().filter(i -> i > 0).limit(2).forEach(System.out::println);
        //输出结果：1 3
        log.debug("无序集合");
        Set<Integer> set = Sets.newHashSet(1, 3, 2, -5, 4, -10, -5);
        set.stream().filter(i -> i > 0).limit(2).forEach(System.out::println);
        //输出结果：1 2
    }

    /**
     * skip(n)去除前n位
     */
    private static void skip() {
        log.debug("有序集合");
        List<Integer> list = Lists.newArrayList(1, 3, 3, 2, -5, 4, -10, -5);
        list.stream().filter(i -> i > 0).skip(2).forEach(System.out::println);
        //输出结果：3 2 4
        log.debug("无序集合");
        Set<Integer> set = Sets.newHashSet(1, 3, 2, -5, 4, -10, -5);
        set.stream().filter(i -> i > 0).skip(2).forEach(System.out::println);
        //输出结果：3 4
    }

    private static void map() {
        List<People> list = Lists.newArrayList(new People(19, "张三"), new People(23, "李四"), new People(15, "王二"),
                new People(30, "麻子"));
        List<String> names =
                list.stream().filter(people -> people.getAge() >= 18).map(People::getName).collect(Collectors.toList());
        log.debug("成年人有：{}", names.toString());
    }

    private static void flatMap() {
        List<People> list = Lists.newArrayList(new People(19, "张三"), new People(23, "李四"), new People(15, "王二"),
                new People(30, "麻子"), new People(19, "张三"), new People(23, "李四"), new People(15, "王二"), new People(30
                        , "麻子"));
        //此处需要注意：distinct去重方法需在flatMap方法后才会起作用
        List<String> names =
                list.stream().filter(people -> people.getAge() >= 18).map(people -> people.getName().split("")).flatMap(Arrays::stream).distinct().collect(Collectors.toList());
        log.debug("去重后的字符：{}", names.toString());
    }

    private static void matchAndFind() {
        List<People> list = Lists.newArrayList(new People(19, "张三"), new People(23, "李四"), new People(15, "王二"),
                new People(30, "麻子"));
        // 如果网吧只要有一个未成年则提醒警察大叔
        if (list.stream().anyMatch(people -> people.getAge() < 18)) {
            log.debug("此网吧有未成年！");
        }
        // 如果网吧不是所有人全部年满18岁则提醒警察大叔
        if (!list.stream().allMatch(people -> people.getAge() >= 18)) {
            log.debug("此网吧有未成年！");
        }
        // 如果网吧所有人没有一个少于18岁告诉警察叔叔网吧合格，否则提醒
        if (list.stream().noneMatch(people -> people.getAge() < 18)) {
            log.debug("此网吧合格！");
        } else {
            log.debug("此网吧有未成年！");
        }
        // 如果存在未18岁的则从未满18岁的人群中随意抽出一位进行登记
        list.stream().filter(people -> people.getAge() < 18).findAny().ifPresent(people -> log.debug(people.toString()));
        // 如果存在年满18岁的则对第一位进行登记
        list.stream().filter(people -> people.getAge() > 18).findFirst().ifPresent(people -> log.debug(people.toString()));
    }

    /**
     * 规约操作
     */
    private static void count() {
        List<Integer> numbers = Lists.newArrayList(4, 2, 40, 23);
        List<People> peoples = Lists.newArrayList(new People(19, "张三"), new People(23, "李四"), new People(15, "王二"),
                new People(30, "麻子"));
        // 求和
        Integer sum = numbers.stream().reduce(0, (a, b) -> a + b);
        log.debug("sum 操作得到：[{}]", sum);
        // 由于没有给定初始值，并且不能保证流中一定有元素，所以返回Optional<Integer>
        Optional<Integer> reduce = numbers.stream().reduce((a, b) -> a + b);
        log.debug("sum 操作得到：[{}]", reduce.get());
        sum = numbers.stream().reduce(0, Integer::sum);
        log.debug("sum 操作得到：[{}]", sum);
        // 获取peoples集合中所有人年龄的和
        sum = peoples.stream().map(people -> people.getAge()).reduce(0, Integer::sum);
        log.debug("年龄总和：[{}]", sum);
        log.debug("===================");
        //最大值
        Optional<Integer> max = numbers.stream().reduce((a, b) -> a > b ? a : b);
        log.debug("最大值：[{}]",max.get());
        max = numbers.stream().reduce(Integer :: max);
        log.debug("最大值：[{}]",max.get());
        // 获取peoples集合中最大年龄
        max = peoples.stream().map(people -> people.getAge()).reduce(Integer :: max);
        log.debug("最年龄：[{}]",max.get());
        log.debug("===================");
        //最小值
        Optional<Integer> min = numbers.stream().reduce((a, b) -> a < b ? a : b);
        log.debug("最大值：[{}]",min.get());
        min = numbers.stream().reduce(Integer :: min);
        log.debug("最大值：[{}]",min.get());
        // 获取peoples集合中最大年龄
        min = peoples.stream().map(people -> people.getAge()).reduce(Integer :: min);
        log.debug("最年龄：[{}]",min.get());
    }

    public static void main(String[] args) {
        try {
            try {
                int i = 1/0;
            }catch (Exception e){
                log.error("1号catch", e);
                int i = 1/0;
            }
        }catch (Exception e){
            log.error("2号catch", e);
        }
    }
}