package com.lei.tang.java8.functional;

/**
 * @author tanglei
 * @date 2019/6/26
 */
@FunctionalInterface
public interface BiiFunction<T, U, K, R> {

    R apply(T t, U u, K k);
}
