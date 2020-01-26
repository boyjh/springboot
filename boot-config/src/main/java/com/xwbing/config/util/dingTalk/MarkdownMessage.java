package com.xwbing.config.util.dingTalk;

import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 * @author xiangwb
 */
public class MarkdownMessage implements Message {
    private String title;
    private boolean isAtAll = false;
    private List<String> atMobiles;
    private List<String> items = new ArrayList<>();

    public MarkdownMessage() {
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isAtAll() {
        return isAtAll;
    }

    public void setAtAll(boolean atAll) {
        isAtAll = atAll;
    }

    public void setAtMobiles(List<String> atMobiles) {
        this.atMobiles = atMobiles;
    }

    public void add(String text) {
        this.items.add(text);
    }

    public void add(int index, String text) {
        this.items.add(index, text);
    }


    /**
     * 标题
     *
     * @param headerType
     * @param text
     * @return
     */
    public static String getHeaderText(int headerType, String text) {
        if (headerType >= 1 && headerType <= 6) {
            StringBuilder numbers = new StringBuilder();

            for (int i = 0; i < headerType; ++i) {
                numbers.append("#");
            }

            return numbers + " " + text;
        } else {
            throw new IllegalArgumentException("headerType should be in [1, 6]");
        }
    }

    /**
     * 引用
     *
     * @param text
     * @return
     */
    public static String getReferenceText(String text) {
        return "> " + text;
    }

    /**
     * 加粗
     *
     * @param text
     * @return
     */
    public static String getBoldText(String text) {
        return "**" + text + "**";
    }

    /**
     * 斜体
     *
     * @param text
     * @return
     */
    public static String getItalicText(String text) {
        return "*" + text + "*";
    }

    /**
     * 链接
     *
     * @param text
     * @param href
     * @return
     */
    public static String getLinkText(String text, String href) {
        return "[" + text + "](" + href + ")";
    }

    /**
     * 图片
     *
     * @param imageUrl
     * @return
     */
    public static String getImageText(String imageUrl) {
        return "![image](" + imageUrl + ")";
    }

    /**
     * 有序列表
     *
     * @param orderItem
     * @return
     */
    public static String getOrderListText(List<String> orderItem) {
        if (orderItem.isEmpty()) {
            return "";
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i <= orderItem.size(); i++) {
                sb.append(i).append(". ").append(orderItem.get(i - 1)).append("\n");
            }
            return sb.toString();
        }
    }

    /**
     * 无序列表
     *
     * @param unOrderItem
     * @return
     */
    public static String getUnOrderListText(List<String> unOrderItem) {
        if (unOrderItem.isEmpty()) {
            return "";
        } else {
            StringBuilder sb = new StringBuilder();
            for (String anUnOrderItem : unOrderItem) {
                sb.append("- ").append(anUnOrderItem).append("\n");
            }
            return sb.toString();
        }
    }

    public String toJsonString() {
        Map<String, Object> result = new HashMap<>();
        result.put("msgtype", "markdown");
        Map<String, Object> markdown = new HashMap<>();
        markdown.put("title", this.title);
        StringBuilder markdownText = new StringBuilder();
        Iterator i$ = this.items.iterator();

        while (i$.hasNext()) {
            String item = (String) i$.next();
            markdownText.append(item).append("\n\n");
        }

        markdown.put("text", markdownText.toString());
        result.put("markdown", markdown);
        Map<String, Object> atItems = new HashMap<>();
        if (this.isAtAll) {
            atItems.put("isAtAll", true);
        } else if (CollectionUtils.isNotEmpty(atMobiles)) {
            atItems.put("atMobiles", this.atMobiles);
        }
        result.put("at", atItems);
        return JSON.toJSONString(result);
    }
}
