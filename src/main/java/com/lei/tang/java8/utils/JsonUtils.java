package com.lei.tang.java8.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.lei.tang.java8.domain.People;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

/**
 * @author tanglei
 * @date 2019/12/11
 */
@Slf4j
public final class JsonUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    //设置转换时的配置，具体视情况配置，如果通过此处配置，使用全局。也可以通过在bean的属性上添加注解配置对应的bean
    static {
        //序列化的时候序列对象，bean的属性设置
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //反序列化的时候如果多了其它属性，不抛出异常
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //如果是空对象的时候，不抛异常
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        //取消时间的转换格式，默认是时间戳，同时需设置时间格式
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
    }

    public static String toJsonString(Object obj) throws JsonProcessingException {
        if (obj != null) {
            return mapper.writeValueAsString(obj);
        }
        return null;
    }

    public static <T> T toBean(String json, Class<T> clazz) throws IOException {
        if (StringUtils.hasText(json) && clazz != null) {
            return (T) mapper.readValue(json, clazz);
        }
        return null;
    }

    public static void main(String[] args) throws IOException {
        People people = new People(18, null);
        People people2 = new People(20, "李四");
        String toJsonString = JsonUtils.toJsonString(people);
        //输出：Bean to json {"age":18}
        log.debug("Bean to json {}", toJsonString);
        people = JsonUtils.toBean(toJsonString, People.class);
        //输出：json to Bean People(age=18, name=null)
        log.debug("json to Bean {}", people);
        toJsonString = JsonUtils.toJsonString(Lists.newArrayList(people, people2));
        //输出：List to json [{"age":18},{"age":20,"name":"李四"}]
        log.debug("List to json {}", toJsonString);
        List<People> list = JsonUtils.toBean(toJsonString, List.class);
        //输出：json to List [{age=18}, {age=20, name=李四}]
        log.debug("json to List {}", list);
        toJsonString = JsonUtils.toJsonString(Sets.newHashSet(people, people2));
        //输出：Set to json [{"age":20,"name":"李四"},{"age":18}]
        log.debug("Set to json {}", toJsonString);
        Set<People> set = JsonUtils.toBean(toJsonString, Set.class);
        //输出：json to Set [{age=20, name=李四}, {age=18}]
        log.debug("json to Set {}", set);
        Map<Integer, People> map = Maps.newHashMap();
        map.put(18, people);
        map.put(20, people2);
        toJsonString = JsonUtils.toJsonString(map);
        //输出：Map to json {"18":{"age":18},"20":{"age":20,"name":"李四"}}
        log.debug("Map to json {}", toJsonString);
        map = JsonUtils.toBean(toJsonString, Map.class);
        //输出：json to Map {18={age=18}, 20={age=20, name=李四}}
        log.debug("json to Map {}", map);
    }
}
