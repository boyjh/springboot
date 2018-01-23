package com.xwbing.demo;

import com.alibaba.fastjson.JSONObject;
import com.xwbing.domain.entity.sys.SysUser;
import com.xwbing.domain.entity.sys.SysUserRole;
import com.xwbing.service.sys.SysUserRoleService;
import com.xwbing.service.sys.SysUserService;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Date: 2017/6/15 17:09
 * Author: xiangwb
 */
public class LambdaDemo {
    @Resource
    private SysUserRoleService sysUserRoleService;
    @Resource
    private static ThreadPoolTaskExecutor taskExecutor;

    public static void main(String[] args) {
        //匿名内部类
        Thread t = new Thread(() -> System.out.println("hello,lambda"));
        t.start();
        List<String> list = Arrays.asList("a", "b", "c");
        list.sort(Comparator.naturalOrder());
        /**
         * stream api 高级版本的迭代器
         */
        List<Integer> lists = Arrays.asList(1, 2, 4, 2, 3, 5, 5, 6, 8, 9, 7, 10);
//        //遍历
//        Arrays.stream(arrays).forEach(System.out::println);
        list.forEach(System.out::println);
        lists.sort((o1, o2) -> o1 - o2);//升序排序，不需要收集
        System.out.println("sort:" + lists.stream().sorted((o1, o2) -> o2 - o1).collect(Collectors.toList()));//排序
        System.out.println("map:" + lists.stream().map(o1 -> o1 * 2).collect(Collectors.toList()));//转换
        System.out.println("distinct:" + lists.stream().distinct().collect(Collectors.toList()));//去重(去重逻辑依赖元素的equals方法)
        System.out.println("limit:" + lists.stream().limit(4).collect(Collectors.toList()));//截取
        System.out.println("skip:" + lists.stream().skip(4).collect(Collectors.toList()));//丢弃
        //匹配
        boolean b1 = lists.stream().anyMatch(o -> o == 1);
        boolean b = lists.stream().allMatch(o -> o == 1);
        boolean b2 = lists.stream().noneMatch(o -> o == 1);
        //过滤
        System.out.println("filter:" + lists.stream().filter(o1 -> o1 > 3 && o1 < 8).collect(Collectors.toList()));
        Predicate<Integer> gt = integer -> integer > 3;//函数式接口Predicate
        Predicate<Integer> lt = integer -> integer < 8;
        System.out.println("重用filter:" + lists.stream().filter(gt.and(lt)).collect(Collectors.toList()));
        //删除
        lists.removeIf(item -> item > 3);//根据条件删除，不用收集
        //聚合
        System.out.println("reduce:" + lists.stream().reduce((o1, o2) -> o1 + o2).get());//聚合
        System.out.println("reduce:" + lists.stream().reduce(0, (o1, o2) -> o1 + o2));//聚合(给定默认值)
        System.out.println("ids:" + list.stream().reduce((sum, item) -> sum + "," + item).get());//常用//list(a,b,c)-->,a,b,c-->a,b,c
        System.out.println("ids:" + list.stream().reduce("", (sum, item) -> sum + "," + item).substring(1));
        String s = list.stream().reduce("", (sum, item) -> sum + "'" + item + "',");//list(a,b,c)-->'a','b','c',-->'a','b','c'
        System.out.println("id in:" + s.substring(0, s.lastIndexOf(",")));
        //join
        System.out.println("join:" + list.stream().collect(Collectors.joining(",")));//list(a,b,c)-->a,b,c
        System.out.println("join:" + String.join(",", list));
        //统计
        IntSummaryStatistics statistics = lists.stream().mapToInt(x -> x).summaryStatistics();
        System.out.println("List中最大的数字 : " + statistics.getMax());
        System.out.println("List中最小的数字 : " + statistics.getMin());
        System.out.println("所有数字的总和   : " + statistics.getSum());
        System.out.println("所有数字的平均值 : " + statistics.getAverage());
        System.out.println("List成员个数     : " + statistics.getCount());
        //all example
        System.out.println("all:" + lists.stream().filter(Objects::nonNull).distinct().mapToInt(num -> num * 2).skip(2).limit(4).sum());
        //遍历list存入map里
        Map<String, SysUser> userMap = new SysUserService().listAll().stream().collect(Collectors.toMap(SysUser::getId, Function.identity()));
        Map<String, String> nameMap = new SysUserService().listAll().stream().collect(Collectors.toMap(SysUser::getId, SysUser::getName));
        Map<String, String> jsonMap = new ArrayList<JSONObject>().stream().collect(Collectors.toMap(o1 -> o1.getString(""), o2 -> o2.getString("")));
        //分组
        Map<String, List<SysUser>> groupMap = new SysUserService().listAll().stream().collect(Collectors.groupingBy(SysUser::getSex));//(分组条件为key，分组成员为value)
        //非空判断
        Optional<String> optional = list.stream().reduce((sum, item) -> sum + "," + item);
        String reduce;
        if (optional.isPresent()) {
            reduce = optional.get();
        }
        //异步回调
        CompletableFuture<List<SysUser>> future = CompletableFuture.supplyAsync(LambdaDemo::getList, taskExecutor);
        try {
            List<SysUser> sysUsers = future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * filter
     *
     * @return
     */
    public List<SysUser> getRoleUsers(int flag) {
        if (flag == 0) {//例一
            Predicate<SysUser> roles = sysUser -> {
                List<SysUserRole> sysUserRoles = sysUserRoleService.listByUserId(sysUser.getId());
                return sysUserRoles.size() > 0;
            };
            return new ArrayList<SysUser>().stream().filter(roles).collect(Collectors.toList());
        } else {//例二
            return new ArrayList<SysUser>().stream().filter(sysUser -> {
                List<SysUserRole> sysUserRoles = sysUserRoleService.listByUserId(sysUser.getId());
                return sysUserRoles.size() > 0;
            }).collect(Collectors.toList());
        }
    }

    public static List<SysUser> getList() {
        return null;
    }
}
