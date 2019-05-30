package com.lei.tang.java8.domain;

/**
 * @author tanglei
 * @date 2019/5/29
 */
public class SingleTon {

    private static SingleTon singleTon = new SingleTon();

    public static int count1;

    public static int count2 = 0;

    private SingleTon() {
        count1++;
        count2++;
    }

    public static SingleTon getInstance() {
        return singleTon;
    }
}
