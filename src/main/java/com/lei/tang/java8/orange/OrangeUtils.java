package com.lei.tang.java8.orange;

import java.util.ArrayList;
import java.util.List;

import com.lei.tang.java8.domain.Orange;
import org.springframework.util.StringUtils;

/**
 * @author tanglei
 * @date 2019/5/30
 */
public class OrangeUtils {

    /**
     * 通过颜色筛选橘子工具类
     *
     * @param source 需要筛选的橘子
     * @param color  目标颜色
     * @return
     */
    public static List<Orange> filterOrangesbyColor(List<Orange> source, String color) {
        if (source != null && source.size() > 0 && StringUtils.hasText(color)) {
            List<Orange> result = new ArrayList<>();
            for (Orange orange : source) {
                if (color.equals(orange.getColor())) {
                    result.add(orange);
                }
            }
            return result;
        }
        return null;
    }

    /**
     * 通过重量筛选橘子
     *
     * @param source 需要筛选的橘子
     * @param weight 重量
     * @param bool   筛选符号
     * @return
     */
    public static List<Orange> filterOrangesByWeight(List<Orange> source, double weight, boolean bool) {
        List<Orange> result = new ArrayList<>();
        for (Orange orange : source) {
            if (bool && orange.getWeight() > weight) {
                result.add(orange);
            } else if (!bool && orange.getWeight() < weight) {
                result.add(orange);
            }
        }
        return result;
    }
}
