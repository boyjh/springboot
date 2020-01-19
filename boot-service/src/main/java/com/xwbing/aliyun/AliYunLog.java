package com.xwbing.aliyun;

import com.aliyun.openservices.log.Client;
import com.aliyun.openservices.log.common.LogItem;
import com.aliyun.openservices.log.request.PutLogsRequest;
import com.dingtalk.chatbot.DingtalkChatbotClient;
import com.dingtalk.chatbot.message.TextMessage;
import com.xwbing.util.EnvUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.util.Date;
import java.util.Vector;

@Slf4j
public class AliYunLog {
    private DingtalkChatbotClient dingTalkClient;
    private Client client;
    private String logStore;
    private String topic;
    private String project;
    private String dingTalkPrefix;
    private String dingTalkToken;

    public AliYunLog(Client client, String logStore, String topic, String project, String dingTalkPrefix, String dingTalkToken) {
        this.dingTalkClient = new DingtalkChatbotClient();
        this.client = client;
        this.project = project;
        this.logStore = logStore;
        this.topic = topic;
        this.dingTalkPrefix = dingTalkPrefix;
        this.dingTalkToken = dingTalkToken;
    }

    public void write(String source, String key, String value) {
        if (StringUtils.isEmpty(key)) {
            key = "default";
        }
        Vector<LogItem> logGroup = new Vector<>();
        LogItem logItem = new LogItem((int) ((new Date()).getTime() / 1000L));
        logItem.PushBack(key, EnvUtil.getHost() + "_: " + value);
        logGroup.add(logItem);
        PutLogsRequest putLogsRequest = new PutLogsRequest(project, logStore, topic, source, logGroup);
        try {
            client.PutLogs(putLogsRequest);
            log.info("{} - {} - {}", source, key, value);
        } catch (Exception e) {
            log.error("{} - {}", key, ExceptionUtils.getStackTrace(e));
        }
    }

    public void postDingTalk(String source, Object... params) {
        StringBuilder content = new StringBuilder("host: ").append(EnvUtil.getHost()).append("\n").append("source: ").append(source).append("\n");
        int i = 1;
        for (Object obj : params) {
            content.append("params").append(i).append(": ").append(obj).append("\n");
            i++;
        }
        try {
            dingTalkClient.send(dingTalkPrefix + dingTalkToken, new TextMessage(content.toString()));
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
        }
    }
}
