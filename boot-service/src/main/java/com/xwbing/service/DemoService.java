package com.xwbing.service;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DemoService {
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private String chacheTime = "";

    private static Double formate(Double v1) {
        BigDecimal bg = new BigDecimal(v1);
        return bg.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    private Double hoursBetween(String startDateTime, String endDateTime) {
        LocalDateTime sDateTime = LocalDateTime.parse(startDateTime, dateTimeFormatter);
        LocalDateTime eDateTime = LocalDateTime.parse(endDateTime, dateTimeFormatter);
        Duration duration = Duration.between(sDateTime, eDateTime);
        long m = duration.toMinutes();
        return formate((double) m / 60.0);
    }

    private List getGpsByUserName(String username, String stime, String etime) {
        return null;
    }

    private Map<String, Object> hand(List<JSONObject> gpsList, String endTime) {
        Map<String, Object> map = new HashMap<>();
        //存数据库
        JSONObject obj = gpsList.get(0);
        // TODO: 2017/11/22
        //获取下一个时间
        String time = (String) obj.get("time");
        String startTime = LocalDateTime.parse(time, dateTimeFormatter).plusHours(1).format(dateTimeFormatter);
        //跟截止时间比较
        if (hoursBetween(startTime, endTime) > 1) {
            //过滤
            gpsList = gpsList.stream().filter(jsonObject -> ((String) jsonObject.get("time")).compareTo(startTime) < 0 ? true : false).collect(Collectors.toList());
            map.put("gpsList", gpsList);
            map.put("startTime", startTime);
        }else {
            map.put("startTime",time);
            map.put("startTime",gpsList);
        }
        return map;
    }

    @Scheduled(cron = "0 0 */1 * * ?") // 每小时执行一次
    public void scheduler() {
        LocalDateTime now = LocalDateTime.now();
        String endTime = now.format(dateTimeFormatter);//截止时间
        String startTime;//开始时间
        List<JSONObject> gpsList = new ArrayList<>();//所有gps数据
        //获取巡更人员
        List<JSONObject> list = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(list)) {
            //判断开始时间
            if (StringUtils.isNotEmpty(chacheTime)) {
                startTime = chacheTime;
            } else {
                //从数据库去查
                //数据库无数据
                startTime = LocalDate.now().toString() + " 00:00:00";
            }
            while (hoursBetween(startTime, endTime) > 1) {
                if (CollectionUtils.isNotEmpty(gpsList)) {
                    Map<String, Object> hand = hand(gpsList,endTime);
                    startTime = (String) hand.get("startTime");
                    gpsList = (List<JSONObject>) hand.get("hand");
                } else {
                    for (JSONObject jsonObject : list) {
                        gpsList.addAll(getGpsByUserName((String) jsonObject.get("tel"), startTime, endTime));
                    }
                    if (CollectionUtils.isNotEmpty(gpsList)) {
                        //排序
                        //获取处理结果
                        Map<String, Object> hand = hand(gpsList,endTime);
                        startTime = (String) hand.get("startTime");
                        gpsList = (List<JSONObject>) hand.get("hand");
                    }
                }
            }
            chacheTime = startTime;
        }
    }
}
