package com.xwbing.util;

import com.alibaba.fastjson.JSONArray;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 项目名称: cloud-control-demonstration
 * 创建时间: 2017/10/18 8:55
 * 作者: xiangwb
 * 说明:
 */
public class PageUtil {
    public static Map<String, Object> page(JSONArray array, int currentPage, int pageSize) {
        Map<String, Object> pageMap = new HashMap<>();
        int size = array.size();
        pageMap.put("count", size);
        int start = (currentPage - 1) * pageSize;
        int end = pageSize * currentPage;
        if (start >= end || start >= size) {
            pageMap.put("data", Collections.emptyList());
        } else {
            if (start < size && end > size) {
                end = size;
            }
            pageMap.put("data", array.subList(start, end));
        }
        int totalPage = size % pageSize == 0 ? size / pageSize : size / pageSize + 1;
        pageMap.put("totalPage", totalPage);
        return pageMap;
    }
}
