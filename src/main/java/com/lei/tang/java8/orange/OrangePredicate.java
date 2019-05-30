package com.lei.tang.java8.orange;

import com.lei.tang.java8.domain.Orange;

/**
 * @author tanglei
 * @date 2019/5/30
 */
public interface OrangePredicate {

    boolean fileter(Orange orange, Object obj);
}
