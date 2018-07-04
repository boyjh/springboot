package com.xwbing.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;

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
        if (list == null) {
            list = Collections.EMPTY_LIST;
        }
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
        return result;
    }

    /**
     * eCharts柱状图/折线图
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static Map<String, Object> eChartsBarOrLine(String startDate, String endDate, List<JSONObject> list) {
        Map<String, Object> result = new HashMap<>();
        //统计时间
        List<String> days = listDate(startDate, endDate);
        result.put("xAxis", days);
        //统计数据
        JSONArray aa = new JSONArray();
        JSONArray bb = new JSONArray();
        if (list == null) {
            list = Collections.EMPTY_LIST;
        }
        Map<String, List<JSONObject>> collect = list.stream().collect(Collectors.groupingBy(obj -> obj.getString("day")));
        days.forEach(day -> {
            List<JSONObject> sample = collect.get(day);
            if (sample != null) {
                int sumA = sample.stream().filter(obj -> "aa".equals(obj.getString("item"))).mapToInt(obj -> obj.getInteger("value")).sum();
                int sumB = sample.stream().filter(obj -> "bb".equals(obj.getString("item"))).mapToInt(obj -> obj.getInteger("value")).sum();
                aa.add(sumA);
                bb.add(sumB);
            } else {
                aa.add(0);
                bb.add(0);
            }
        });
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
     * g2柱状图
     *
     * @param startDate
     * @param endDate
     * @param list
     * @return
     */
    public static JSONArray g2Bar(String startDate, String endDate, List<JSONObject> list) {
        JSONArray result = new JSONArray();
        List<String> days = listDate(startDate, endDate);
        if (list == null) {
            list = Collections.EMPTY_LIST;
        }
        Map<String, List<JSONObject>> collect = list.stream().collect(Collectors.groupingBy(obj -> obj.getString("item")));
        JSONObject obj;
        for (String name : ITEM.split(",")) {
            obj = new JSONObject();
            obj.put("name", name);
            List<JSONObject> sample = collect.get(name);
            if (sample != null) {
                Iterator<JSONObject> it;
                for (String day : days) {
                    int sum = 0;
                    it = sample.iterator();
                    JSONObject next;
                    while (it.hasNext()) {
                        next = it.next();
                        String date = next.getString("day");
                        int value = next.getInteger("value");
                        if (day.equals(date)) {
                            sum += value;
                            it.remove();
                        }
                    }
                    obj.put(day, sum);
                }
            } else {
                for (String day : days) {
                    obj.put(day, 0);
                }
            }
            result.add(obj);
        }
        return result;
    }

    /**
     * g2折线图
     *
     * @param startDate
     * @param endDate
     * @param list
     * @return
     */
    public static JSONArray g2Line(String startDate, String endDate, List<JSONObject> list) {
        JSONArray result = new JSONArray();
        List<String> days = listDate(startDate, endDate);
        if (list == null) {
            list = Collections.EMPTY_LIST;
        }
        Map<String, List<JSONObject>> collect = list.stream().collect(Collectors.groupingBy(obj -> obj.getString("day")));
        JSONObject obj;
        for (String day : days) {
            obj = new JSONObject();
            obj.put("day", day);
            List<JSONObject> sample = collect.get(day);
            for (String item : ITEM.split(",")) {
                if (sample != null) {
                    int sum = sample.stream().filter(it -> item.equals(it.getString("item"))).mapToInt(it -> it.getInteger("value")).sum();
                    obj.put(item, sum);
                } else {
                    obj.put(item, 0);
                }
            }
            result.add(obj);
        }
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
//        List<JSONObject> list = null;
        log.info(JSON.toJSONString(pie(list), SerializerFeature.PrettyFormat));
        log.info(JSON.toJSONString(eChartsBarOrLine("2018-01-01", "2018-01-02", list), SerializerFeature.PrettyFormat));
        log.info(JSON.toJSONString(g2Bar("2018-01-01", "2018-01-02", list), SerializerFeature.PrettyFormat));
        log.info(JSON.toJSONString(g2Line("2018-01-01", "2018-01-02", list), SerializerFeature.PrettyFormat));
    }
}
