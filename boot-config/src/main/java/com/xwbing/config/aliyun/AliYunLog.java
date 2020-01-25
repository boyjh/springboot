package com.xwbing.config.aliyun;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.log.Client;
import com.aliyun.openservices.log.common.LogItem;
import com.aliyun.openservices.log.request.PutLogsRequest;
import com.xwbing.config.util.dingTalk.DingTalkClient;
import com.xwbing.config.util.dingTalk.SendResult;
import com.xwbing.config.util.dingTalk.TextMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.util.Date;
import java.util.List;
import java.util.Vector;

@Slf4j
public class AliYunLog {
    private DingTalkClient dingTalkClient;
    private Client client;
    private String logStore;
    private String topic;
    private String project;
    private String webHook;
    private String secret;

    public AliYunLog(Client client, String logStore, String topic, String project, String webHook, String secret) {
        this.dingTalkClient = new DingTalkClient();
        this.client = client;
        this.project = project;
        this.logStore = logStore;
        this.topic = topic;
        this.webHook = webHook;
        this.secret = secret;
    }

    /**
     * 打印log
     *
     * @param source
     * @param key
     * @param value
     */
    public void write(String source, String key, String value) {
        if (StringUtils.isEmpty(key)) {
            key = "default";
        }
        Vector<LogItem> logGroup = new Vector<>();
        LogItem logItem = new LogItem((int) ((new Date()).getTime() / 1000L));
        String host = System.getenv("hostName");
        logItem.PushBack(key, host + "_: " + value);
        logGroup.add(logItem);
        PutLogsRequest putLogsRequest = new PutLogsRequest(project, logStore, topic, source, logGroup);
        try {
            client.PutLogs(putLogsRequest);
            log.info("{} - {} - {}", source, key, value);
        } catch (Exception e) {
            log.error("{} - {}", key, ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * 钉钉群发送消息
     *
     * @param title
     * @param atAll
     * @param atMobiles
     * @param params
     */
    public void dingTalkText(String title, boolean atAll, List<String> atMobiles, Object... params) {
        String host = System.getenv("hostName");
        StringBuilder content = new StringBuilder("host: ").append(host).append("\n").append("title: ").append(title).append("\n");
        int i = 1;
        for (Object obj : params) {
            content.append("params").append(i).append(": ").append(obj).append("\n");
            i++;
        }
        try {
            TextMessage textMessage = new TextMessage(content.toString());
            textMessage.setAtMobiles(atMobiles);
            textMessage.setIsAtAll(atAll);
            SendResult send = dingTalkClient.send(webHook, secret, textMessage);
            if (!send.isSuccess()) {
                log.error("{} - {}", title, JSONObject.toJSON(send));
            }
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
        }
    }
}