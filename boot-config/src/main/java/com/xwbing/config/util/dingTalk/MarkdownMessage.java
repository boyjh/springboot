package com.xwbing.config.util.dingTalk;

import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 * @author xiangwb
 */
public class MarkdownMessage implements Message {
    /**
     * 首屏会话透出的展示内容
     */
    private String title;
    /**
     * -@所有人时：true，否则为：false
     */
    private boolean isAtAll = false;
    /**
     * 被@人的手机号(在text内容里要有@手机号)
     */
    private List<String> atMobiles;
    /**
     * markdown格式的消息
     */
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

    public List<String> getAtMobiles() {
        return atMobiles;
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
        Map<String, Object> items = new HashMap<>();
        //msgtype
        items.put("msgtype", "markdown");
        //at
        Map<String, Object> atItems = new HashMap<>();
        boolean atMobile = false;
        if (this.isAtAll) {
            atItems.put("isAtAll", true);
        } else if (CollectionUtils.isNotEmpty(this.atMobiles)) {
            atItems.put("atMobiles", this.atMobiles);
            atMobile = true;
        }
        items.put("at", atItems);
        //markdown
        Map<String, Object> markdown = new HashMap<>();
        markdown.put("title", this.title);
        StringBuilder markdownText = new StringBuilder();
        for (Object item : this.items) {
            markdownText.append(item).append("\n\n");
        }
        //text添加@信息，否则@不起作用
        if (this.isAtAll) {
            markdownText.append("@所有人");
        } else if (atMobile && !this.isAtAll) {
            atMobiles.forEach(mobile -> markdownText.append("@").append(mobile).append("\n"));
        }
        markdown.put("text", markdownText.toString());
        items.put("markdown", markdown);
        return JSON.toJSONString(items);
    }
}
