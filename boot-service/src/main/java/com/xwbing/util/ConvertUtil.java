package com.xwbing.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author: xiangwb
 * @date: 2018/8/11 21:40
 * @description: 实体转换
 */
@Slf4j
public class ConvertUtil {
    public static <F, T> T convert(F fromObj, Class<T> toClass) {
        if (null == fromObj) {
            return null;
        }
        try {
            T toObject = toClass.newInstance();
            copyProperties(fromObj, toObject);
            return toObject;
        } catch (Exception e) {
            return null;
        }
    }

    public static <F, T> List<T> convertList(List<F> fromList, Class<T> toClass) {
        if (fromList == null || fromList.isEmpty()) {
            return Collections.emptyList();
        }
        List<T> toList = new ArrayList<>();
        fromList.forEach(fromObj -> toList.add(convert(fromObj, toClass)));
        return toList;
    }

    /**
     * 对象转化
     *
     * @param source
     * @param target
     */
    public static void copyProperties(Object source, Object target) {
        try {
            BeanUtils.copyProperties(source, target);
        } catch (Exception e) {
            log.error("convert error!", e);
        }
    }
}
