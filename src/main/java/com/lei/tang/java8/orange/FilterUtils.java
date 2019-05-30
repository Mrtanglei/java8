package com.lei.tang.java8.orange;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tanglei
 * @date 2019/5/30
 */
public class FilterUtils {

    public interface Predicate<T> {
        boolean filter(T t);
    }

    public static <T> List<T> filter(List<T> list, Predicate<T> p) {
        List<T> result = new ArrayList<>();
        for (T t : list) {
            if (p.filter(t)) {
                result.add(t);
            }
        }
        return result;
    }
}
