package com.xwbing.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 项目名称: boot-module-demo
 * 创建时间: 2018/7/3 下午8:57
 * 作者: xiangwb
 * 说明: 统计分析
 */
@Slf4j
public class CountDemo {
    public static final String ITEM = "aa,bb";

    /**
     * 饼图
     *
     * @return
     */
    public static JSONArray pie(List<JSONObject> list) {
        JSONArray result = new JSONArray();
        if (CollectionUtils.isNotEmpty(list)) {
            Map<String, List<JSONObject>> collect = list.stream().collect(Collectors.groupingBy(obj -> obj.getString("item")));
            JSONObject obj;
            for (String item : ITEM.split(",")) {
                obj = new JSONObject();
                obj.put("name", item);
                List<JSONObject> sample = collect.get(item);
                if (sample != null) {
                    int sum = sample.stream().mapToInt(value -> value.getInteger("value")).sum();
                    obj.put("value", sum);
                } else {
                    obj.put("value", 0);
                }
                result.add(obj);
            }
        } else {
            JSONObject obj;
            for (String item : ITEM.split(",")) {
                obj = new JSONObject();
                obj.put("name", item);
                obj.put("value", 0);
                result.add(obj);
            }
        }
        return result;
    }

    /**
     * 柱状图/折线图
     *
     * @param startDate
     * @param endDate
     * @return
     * @throws Exception
     */
    public static Map<String, Object> barOrLine(String startDate, String endDate, List<JSONObject> list) {
        Map<String, Object> result = new HashMap<>();
        //统计时间
        List<String> days = listDate(startDate, endDate);
        result.put("xAxis", days);
        //统计数据
        JSONArray aa = new JSONArray();
        JSONArray bb = new JSONArray();
        if (CollectionUtils.isNotEmpty(list)) {
            Map<String, List<JSONObject>> collect = list.stream().collect(Collectors.groupingBy(obj -> obj.getString("day")));
            days.forEach(day -> {
                List<JSONObject> sample = collect.get(day);
                if (CollectionUtils.isNotEmpty(sample)) {
                    int sumA = sample.stream().filter(obj -> "aa".equals(obj.getString("item"))).mapToInt(obj -> obj.getInteger("value")).sum();
                    int sumB = sample.stream().filter(obj -> "bb".equals(obj.getString("item"))).mapToInt(obj -> obj.getInteger("value")).sum();
                    aa.add(sumA);
                    bb.add(sumB);
                } else {
                    aa.add(0);
                    bb.add(0);
                }
            });

        } else {
            days.forEach(day -> {
                aa.add(0);
                bb.add(0);
            });
        }
        JSONArray series = new JSONArray();
        JSONObject obj = new JSONObject();
        obj.put("name", "aa");
        obj.put("data", aa);
        series.add(obj);
        obj = new JSONObject();
        obj.put("name", "bb");
        obj.put("data", bb);
        series.add(obj);
        result.put("series", series);
        return result;
    }

    /**
     * 获取数据列表
     *
     * @param startDate
     * @param endDate
     * @return
     */
    private static List<JSONObject> listByDate(String startDate, String endDate) {
        List<JSONObject> result = new ArrayList<>();
        List<String> list = listDate(startDate, endDate);
        JSONObject obj;
        int i = 0;
        for (String object : list) {
            obj = new JSONObject();
            obj.put("day", object);
            obj.put("value", ++i);
            obj.put("item", ITEM.split(",")[new Random().nextInt(2)]);
            result.add(obj);
        }
        return result;
    }

    /**
     * 获取日期列表
     *
     * @param startDate
     * @param endDate
     * @return
     */
    private static List<String> listDate(String startDate, String endDate) {
        List<String> days = new ArrayList<>();
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        long len = ChronoUnit.DAYS.between(start, end);
        for (long i = 0; i <= len; i++) {
            days.add(start.plusDays(i).toString());
        }
        return days;
    }

    public static void main(String[] args) {
        List<JSONObject> list = listByDate("2018-01-01", "2018-01-02");
//        List<JSONObject> list = new ArrayList<>();
        log.info(JSON.toJSONString(pie(list), SerializerFeature.PrettyFormat));
        log.info(JSON.toJSONString(barOrLine("2018-01-01", "2018-01-02", list), SerializerFeature.PrettyFormat));
    }
}
