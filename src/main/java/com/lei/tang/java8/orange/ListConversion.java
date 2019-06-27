package com.lei.tang.java8.orange;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.lei.tang.java8.domain.Orange;

/**
 * @author tanglei
 * @date 2019/6/27
 */
public class ListConversion {

    public static void main(String[] args) {
        List<Orange> list = Lists.newArrayList(new Orange(10.00, "red"), new Orange(100.00, "red"), new Orange(20.00,
                "green"));
        listConversionToSet(Lists.newArrayList(1,2,1,2));
    }

    public static void listConversionToMap(List<Orange> list) {
        //颜色作为key，重量作为value,如果key重复，取较重的值（注意：key重复不做处理会报-->java.lang.IllegalStateException: Duplicate key）
        Map<String, Double> colorForWeight = list.stream().collect(Collectors.toMap(Orange::getColor,
                Orange::getWeight, (oldValue, newValue) -> oldValue > newValue ? oldValue : newValue));

        //颜色作为key,Orange为value
        Map<String, Orange> colorForOrange = list.stream().collect(Collectors.toMap(Orange::getColor,
                (orange) -> orange, (oldValue, newValue) -> oldValue.getWeight() > newValue.getWeight() ? oldValue :
                        newValue));

        //已颜色作为key进行分组
        Map<String, List<Orange>> colorForOranges = list.stream().collect(Collectors.groupingBy(Orange::getColor));
    }

    public static void listConversionToSet(List<Integer> list) {
        Set<Integer> set = list.stream().collect(Collectors.toSet());
    }
}
