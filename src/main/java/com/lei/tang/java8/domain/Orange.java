package com.lei.tang.java8.domain;

import lombok.*;

/**
 * @author tanglei
 * @date 2019/5/8
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Orange {

    @NonNull
    private Double weight;

    private String color;
}
