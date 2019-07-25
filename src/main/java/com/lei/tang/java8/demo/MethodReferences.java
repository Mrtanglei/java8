package com.lei.tang.java8.demo;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import com.lei.tang.java8.domain.Orange;

/**
 * @author tanglei
 * @date 2019/6/19
 * <p>
 * 方法引用
 */
public class MethodReferences {

    public static void main(String[] args) {
        //静态方法引用
        Function<String, Integer> stringIntegerFunction = (String s) -> Integer.parseInt(s);
        stringIntegerFunction = Integer::parseInt;

        //指向任意类型实例方法的方法引用
        stringIntegerFunction = (String s) -> s.length();
        stringIntegerFunction = String::length;

        //指向现有对象的实例方法的方法引用
        Orange orange = new Orange();
        orange.setWeight(10.34);
        Supplier<Double> supplier = () -> orange.getWeight();
        supplier = orange::getWeight;

        //构造函数引用
        //无参构造函数
        Supplier<Orange> supplierConstructor = () -> new Orange();
        Orange orangeConstructor = supplierConstructor.get();

        supplierConstructor = Orange::new;
        orangeConstructor = supplierConstructor.get();

        //一个参数
        Function<Double, Orange> functionConstructor = Orange::new;
        orangeConstructor = functionConstructor.apply(10.00);

        functionConstructor = (weight) -> new Orange(weight);
        orangeConstructor = functionConstructor.apply(10.00);

        //2个参数
        BiFunction<Double, String, Orange> biFunctionConstructor = Orange::new;
        orangeConstructor = biFunctionConstructor.apply(10.00, "red");

        biFunctionConstructor = (weight, color) -> new Orange(weight, color);
        orangeConstructor = biFunctionConstructor.apply(10.00, "red");

        //超过二个参数的可自定义函数接口:BiiFunction
    }
}
