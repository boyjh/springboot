package com.xwbing.aliyun;

import com.aliyun.openservices.log.Client;
import com.aliyun.openservices.log.common.LogItem;
import com.aliyun.openservices.log.request.PutLogsRequest;
import com.dingtalk.chatbot.DingtalkChatbotClient;
import com.dingtalk.chatbot.message.TextMessage;
import com.xwbing.util.EnvUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Vector;


public class AliYunLog {
    private static final Logger logger = LoggerFactory.getLogger(AliYunLog.class);

    private DingtalkChatbotClient dingTalkClient;
    private Client client;
    private String logStore;
    private String topic;
    private String project;

    public AliYunLog(Client client, String logStore, String topic, String project) {
        this.dingTalkClient = new DingtalkChatbotClient();
        this.client = client;
        this.project = project;
        this.logStore = logStore;
        this.topic = topic;
    }

    public void write(String source, String key, String value) {
        if (StringUtils.isEmpty(key)) {
            key = "default";
        }
        Vector<LogItem> logGroup = new Vector<>();
        LogItem logItem = new LogItem((int) ((new Date()).getTime() / 1000L));
        logItem.PushBack(key, EnvUtil.getHost() + "_: " + value);
        logGroup.add(logItem);
        PutLogsRequest req2 = new PutLogsRequest(project, logStore, topic, source, logGroup);
        try {
            client.PutLogs(req2);
            logger.info("{} - {} - {}", source, key, value);
        } catch (Exception e) {
            logger.error("{} - {}", key, ExceptionUtils.getStackTrace(e));
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
            String defaultDingTalkUrl = "https://oapi.dingtalk.com/robot/send?access_token=xxx";
            String env = EnvUtil.getEnv();
            if ("prod".equals(env)) {
                defaultDingTalkUrl = "https://oapi.dingtalk.com/robot/send?access_token=xxx";
            }
            dingTalkClient.send(defaultDingTalkUrl, new TextMessage(content.toString()));
        } catch (Exception e) {
            logger.error(ExceptionUtils.getStackTrace(e));
        }
    }
}
