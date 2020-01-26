package com.xwbing.config.util.dingTalk;

import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiangwb
 */
public class TextMessage implements Message {
    private String text;
    private List<String> atMobiles;
    private boolean isAtAll = false;

    public TextMessage(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getAtMobiles() {
        return this.atMobiles;
    }

    public void setAtMobiles(List<String> atMobiles) {
        this.atMobiles = atMobiles;
    }

    public boolean isAtAll() {
        return this.isAtAll;
    }

    public void setIsAtAll(boolean isAtAll) {
        this.isAtAll = isAtAll;
    }

    public String toJsonString() {
        Map<String, Object> items = new HashMap<>();
        //msgtype
        items.put("msgtype", "text");
        //text
        Map<String, String> textContent = new HashMap<>();
        textContent.put("content", this.text);
        items.put("text", textContent);
        //at
        Map<String, Object> atItems = new HashMap<>();
        if (this.isAtAll) {
            atItems.put("isAtAll", true);
        } else if (CollectionUtils.isNotEmpty(atMobiles)) {
            atItems.put("atMobiles", this.atMobiles);
        }
        items.put("at", atItems);
        return JSON.toJSONString(items);
    }
}
