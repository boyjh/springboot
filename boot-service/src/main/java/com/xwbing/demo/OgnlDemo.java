package com.xwbing.demo;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.ognl.Ognl;
import org.apache.ibatis.ognl.OgnlException;

import java.util.*;

/**
 * @author xiangwb
 * @date 2019/6/3 19:08
 * 强大的表达式语言,可以存取对象的任意属性,遍历整个对象的结构图
 * 当有根据对象的某个不确定字段的值进行过滤的需求，可以考虑使用
 * {}代表数组。非root取值前加#
 */
@Slf4j
public class OgnlDemo {
    private static Map<String, Object> context = new HashMap<>();

    public static void main(String[] args) throws OgnlException {
        Use root = new Use();
        root.setAge(18);
        root.setName("test");
        root.setColor("red");
        JSONObject detail = new JSONObject();
        detail.put("detail", "in root");
        detail.put("no", 18);
        root.setDetail(detail);
        context.put("detail", "out root");
        boolean b1 = (boolean) Ognl.getValue("name == 'test'", context, root);
        boolean b2 = (boolean) Ognl.getValue("name != 'test'", context, root);
        boolean b3 = (boolean) Ognl.getValue("name in {'test','demo'}", context, root);
        boolean b4 = (boolean) Ognl.getValue("age in {17,19}", context, root);
        boolean b5 = (boolean) Ognl.getValue("detail.no >= 20", context, root);
        boolean b6 = (boolean) Ognl.getValue("detail.detail == 'in root'", context, root);
        boolean b7 = (boolean) Ognl.getValue("#detail == 'out root'", context, root);
        boolean b8 = (boolean) Ognl.getValue("name == 'test',age == 18", context, root);
        boolean b9 = (boolean) Ognl.getValue("name == 'test',age in {17,18}", context, root);
        detail = (JSONObject) Ognl.getValue("detail", context, root);

        //模糊过滤
        Map<String, Object> param = new HashMap<>();
        param.put("name", "te");
        param.put("color", "red,blue");
        boolean allMatch = param.entrySet().stream().allMatch(paramEntry -> {
            try {
                String original = String.valueOf(Ognl.getValue(paramEntry.getKey(), context, root));
                String value = String.valueOf(paramEntry.getValue());
                return Arrays.stream(value.split(",")).anyMatch(original::contains);
            } catch (OgnlException e) {
                return true;
            }
        });

        //推荐
        List<String> expressions = new ArrayList<>();
        expressions.add("name == 'test'");
        expressions.add("age in {17,18}");
        expressions.add("color like {'blue','red'}");
        expressions.add("detail.detail like 'in'");
        boolean match = match(expressions, root);
        System.out.println(match);
    }

    /**
     * 语法:element.element.element....+比较符。元素和比较符之间要有空格
     * element == 'xxx'
     * element != 'xxx'
     * element in {'xxx','xxx'}
     * element not in {'xxx','xxx'}
     * element > 18
     * element >= 18
     * element < 18
     * element <= 18
     * element == 'xxx',element > 18 //以上表达式可以任意组合
     * //like不是ognl原生的，不能组合
     * element like 'xxx'
     * element like {'xxx','xxx'}
     *
     * @param expressions
     * @param root
     * @return
     */
    private static boolean match(List<String> expressions, Object root) {
        return expressions.stream().allMatch(expression -> {
            try {
                if (expression.contains("like")) {
                    String field = expression.substring(0, expression.indexOf(" "));
                    String value = expression.substring(expression.lastIndexOf(" ") + 1);
                    return (boolean) Ognl.getValue("@com.xwbing.demo.OgnlDemo@like(" + field + "," + value + ")", context, root);
                } else {
                    return (boolean) Ognl.getValue(expression, context, root);
                }
            } catch (OgnlException e) {
                return true;
            }
        });

    }

    public static boolean like(String original, String value) {
        if (value.contains("[")) {
            value = value.substring(value.indexOf("[") + 1, value.indexOf("]"));
            return Arrays.stream(value.split(", ")).anyMatch(original::contains);
        } else {
            return original.contains(value);
        }
    }
}

@Data
class Use {
    private String name;
    private String color;
    private int age;
    private JSONObject detail;
}