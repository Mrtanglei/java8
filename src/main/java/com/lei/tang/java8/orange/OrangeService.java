package com.lei.tang.java8.orange;

import java.util.ArrayList;
import java.util.List;

import com.lei.tang.java8.domain.Orange;
import lombok.extern.slf4j.Slf4j;

/**
 * @author tanglei
 * @date 2019/5/30
 */
@Slf4j
public class OrangeService {

    public List<Orange> filterOranges(List<Orange> source, OrangePredicate p, Object obj) {
        List<Orange> result = new ArrayList<>();
        for (Orange orange : source) {
            if (p.fileter(orange, obj)) {
                result.add(orange);
            }
        }
        return result;
    }


    public List<Orange> filterOranges(List<Orange> source) {
        return filterOranges(source, new OrangePredicate() {
            @Override
            public boolean fileter(Orange orange, Object obj) {
                return orange.getWeight() > (Double) obj;
            }
        }, 100);
    }

    public List<Orange> filterOrangesFromLambda(List<Orange> source) {
        List<Orange> oranges = filterOranges(source, (Orange orange, Object obj) -> (Double) obj < orange.getWeight()
                , 100);
        return oranges;
    }

    public List<Orange> filter(List<Orange> oranges) {
        //使用Lambda表达式
        return FilterUtils.filter(oranges, (orange) -> orange.getWeight() > 100 && "green".equals(orange.getColor()));
        //未使用
        //        return FilterUtils.filter(oranges, new FilterUtils.Predicate<Orange>() {
        //            @Override
        //            public boolean filter(Orange orange) {
        //                return orange.getWeight() > 100 && "green".equals(orange.getColor());
        //            }
        //        });
    }
}
