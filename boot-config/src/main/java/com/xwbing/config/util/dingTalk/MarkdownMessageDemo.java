package com.xwbing.config.util.dingTalk;


import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * @author xiangwb
 */
public class MarkdownMessageDemo {
    private DingTalkClient client = new DingTalkClient();

    public MarkdownMessageDemo() {
    }

    public void testSendMarkdownMessage() throws Exception {
        MarkdownMessage message = new MarkdownMessage();
        message.setTitle("This is a markdown message");
        message.add(MarkdownMessage.getHeaderText(1, "header 1"));
        message.add(MarkdownMessage.getHeaderText(2, "header 2"));
        message.add(MarkdownMessage.getHeaderText(3, "header 3"));
        message.add(MarkdownMessage.getHeaderText(4, "header 4"));
        message.add(MarkdownMessage.getHeaderText(5, "header 5"));
        message.add(MarkdownMessage.getHeaderText(6, "header 6"));
        message.add(MarkdownMessage.getReferenceText("reference text"));
        message.add("\n\n");
        message.add("normal text");
        message.add("\n\n");
        message.add(MarkdownMessage.getBoldText("Bold Text"));
        message.add("\n\n");
        message.add(MarkdownMessage.getItalicText("Italic Text"));
        message.add("\n\n");
        ArrayList<String> orderList = new ArrayList<>();
        orderList.add("order item1");
        orderList.add("order item2");
        message.add(MarkdownMessage.getOrderListText(orderList));
        message.add("\n\n");
        ArrayList<String> unorderList = new ArrayList<>();
        unorderList.add("unorder item1");
        unorderList.add("unorder item2");
        message.add(MarkdownMessage.getUnorderListText(unorderList));
        message.add("\n\n");
        message.add(MarkdownMessage.getImageText("http://img01.taobaocdn.com/top/i1/LB1GCdYQXXXXXXtaFXXXXXXXXXX"));
        message.add(MarkdownMessage.getLinkText("This is a link", "dtmd://dingtalkclient/sendMessage?content=linkmessage"));
        message.add(MarkdownMessage.getLinkText("中文跳转", "dtmd://dingtalkclient/sendMessage?content=" + URLEncoder.encode("链接消息", "UTF-8")));
        SendResult result = this.client.send("https://oapi.dingtalk.com/robot/send?access_token=f41b013832ca349f45cabce7dd7e64b19f3e1aabc4414d71a1e9ee050d65e141","SEC9f4390e8c897e118ab487b0ed5874100a7693920eb87e9a968ee8129b0874e3f", message);
        System.out.println(result);
    }
}
