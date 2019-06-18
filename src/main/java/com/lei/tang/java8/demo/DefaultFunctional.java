package com.lei.tang.java8.demo;

import java.util.List;
import java.util.function.*;

import com.google.common.collect.Lists;
import com.lei.tang.java8.domain.Orange;

/**
 * @author tanglei
 * @date 2019/6/18
 * <p>
 * 默认的函数式接口调用
 */
public class DefaultFunctional {

    private void predicate() {
        Predicate<String> predicate = (String s) -> "aaa".equals(s);
    }

    public static void consumer(List<Orange> oranges, Consumer<Orange> consumer) {
        oranges.forEach(orange -> {
            consumer.accept(orange);
        });
    }

    public static void main(String[] args) {
        consumer(Lists.newArrayList(new Orange(), new Orange()), (Orange orange) -> orange.setColor("red"));
        // sdemo中的Lambda表达式实现的是接收字符串s返回s的长度
        System.out.println(function(Lists.newArrayList("a", "aa", "aaa"), (String s) -> s.length()).toString());

        System.out.println(supplier(() -> "supplier functionaal"));

        System.out.println(biFunction("a", "b", (String t, String s) -> t + s));

        System.out.println(biPredicate("a", "b", (String s1, String s2) -> s1.equals(s2)));
        Orange orange1 = new Orange();
        orange1.setWeight(10);
        Orange orange2 = new Orange();
        orange2.setWeight(10);
        biConsumer(orange1, orange2,
                (Orange orange11, Orange orange22) -> orange11.setWeight(orange11.getWeight()+orange22.getWeight()));
        System.out.println(orange1.getWeight());
    }

    /**
     * 根据传入的Lambda表达式的要求转入传入的ts
     *
     * @param ts
     * @param function
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> List<R> function(List<T> ts, Function<T, R> function) {
        List<R> list = Lists.newArrayList();
        ts.forEach(s -> {
            list.add(function.apply(s));
        });
        return list;
    }

    public static <T> T supplier(Supplier<T> supplier) {
        return supplier.get();
    }

    public static <T, U, R> R biFunction(T t, U u, BiFunction<T, U, R> biFunction) {
        return biFunction.apply(t, u);
    }

    public static <T, U> boolean biPredicate(T t, U u, BiPredicate<T, U> biPredicate) {
        return biPredicate.test(t, u);
    }

    public static <T, U> void biConsumer(T t, U u, BiConsumer<T, U> biConsumer) {
        biConsumer.accept(t, u);
    }
}
