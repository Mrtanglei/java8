package com.lei.tang.java8.orange;

import com.lei.tang.java8.domain.Orange;
import org.springframework.util.StringUtils;

/**
 * @author tanglei
 * @date 2019/5/30
 */
public class OrangeColorPredicate implements OrangePredicate {

    @Override
    public boolean fileter(Orange orange, Object obj) {
        return obj != null && StringUtils.hasText(obj.toString()) ? obj.toString().equals(orange.getColor()) : false;
    }
}
