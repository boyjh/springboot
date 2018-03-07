package com.xwbing.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者: xiangwb
 * 说明: 列表分页工具类
 */
public class PageUtil {
    public static Map<String, Object> page(List list, int currentPage, int pageSize) {
        Map<String, Object> pageMap = new HashMap<>();
        int size = list.size();
        pageMap.put("count", size);
        int start = (currentPage - 1) * pageSize;
        if (start >= size) {
            pageMap.put("data", Collections.emptyList());
        }
        int end = pageSize * currentPage > size ? size : pageSize * currentPage;
        pageMap.put("data", list.subList(start, end));
        int totalPage = size % pageSize == 0 ? size / pageSize : size / pageSize + 1;
        pageMap.put("totalPage", totalPage);
        return pageMap;
    }
}
