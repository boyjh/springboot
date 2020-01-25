package com.xwbing.config.util.dingTalk;


import java.util.ArrayList;

/**
 * @author xiangwb
 */
public class TextMessageDemo {
    private DingTalkClient client = new DingTalkClient();

    public TextMessageDemo() {
    }

    public void testSendTextMessage() throws Exception {
        TextMessage message = new TextMessage("我就是我, 是不一样的烟火");
        SendResult result = this.client.send("https://oapi.dingtalk.com/robot/send?access_token=5dbe45a28dfcf369a8ac651461ebf5205f3ed1c19b61e3a08e180070e18aca4c", message);
        System.out.println(result);
    }

    public void testSendTextMessageWithAt() throws Exception {
        TextMessage message = new TextMessage("我就是我, 是不一样的烟火");
        ArrayList<String> atMobiles = new ArrayList<>();
        atMobiles.add("137xxxx3310");
        message.setAtMobiles(atMobiles);
        SendResult result = this.client.send("https://oapi.dingtalk.com/robot/send?access_token=5dbe45a28dfcf369a8ac651461ebf5205f3ed1c19b61e3a08e180070e18aca4c", message);
        System.out.println(result);
    }

    public void testSendTextMessageWithAtAll() throws Exception {
        TextMessage message = new TextMessage("我就是我, 是不一样的烟火");
        message.setIsAtAll(true);
        SendResult result = this.client.send("https://oapi.dingtalk.com/robot/send?access_token=5dbe45a28dfcf369a8ac651461ebf5205f3ed1c19b61e3a08e180070e18aca4c", message);
        System.out.println(result);
    }

    public void testSendTextMessageWithAtAndAtAll() throws Exception {
        TextMessage message = new TextMessage("我就是我, 是不一样的烟火");
        ArrayList<String> atMobiles = new ArrayList<>();
        atMobiles.add("186xxxx0822");
        message.setAtMobiles(atMobiles);
        message.setIsAtAll(true);
        SendResult result = this.client.send("https://oapi.dingtalk.com/robot/send?access_token=5dbe45a28dfcf369a8ac651461ebf5205f3ed1c19b61e3a08e180070e18aca4c", message);
        System.out.println(result);
    }
}
