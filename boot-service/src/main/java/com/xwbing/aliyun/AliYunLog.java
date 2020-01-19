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

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.Vector;

@Slf4j
public class AliYunLog {
    private DingtalkChatbotClient dingTalkClient;
    private Client client;
    private String logStore;
    private String topic;
    private String project;
    private String webHook;
    private String secret;

    public AliYunLog(Client client, String logStore, String topic, String project, String webHook, String secret) {
        this.dingTalkClient = new DingtalkChatbotClient();
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

    /**
     * 钉钉群发送消息
     *
     * @param source
     * @param params
     */
    public void postDingTalk(String source, Object... params) {
        StringBuilder content = new StringBuilder("host: ").append(EnvUtil.getHost()).append("\n").append("source: ").append(source).append("\n");
        int i = 1;
        for (Object obj : params) {
            content.append("params").append(i).append(": ").append(obj).append("\n");
            i++;
        }
        String dingTalkUrl = dingTalkUrl();
        if (dingTalkUrl != null) {
            try {
                dingTalkClient.send(dingTalkUrl, new TextMessage(content.toString()));
            } catch (Exception e) {
                log.error(ExceptionUtils.getStackTrace(e));
            }
        }
    }

    /**
     * 钉钉安全设置：加签
     *
     * @return url
     */
    private String dingTalkUrl() {
        try {
            Long timestamp = System.currentTimeMillis();
            String stringToSign = timestamp + "\n" + secret;
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            byte[] signData = mac.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8));
            String sign = URLEncoder.encode(Base64.getEncoder().encodeToString(signData), "UTF-8");
            return String.format("%s&timestamp=%s&sign=%s", webHook, timestamp, sign);
        } catch (NoSuchAlgorithmException | InvalidKeyException | UnsupportedEncodingException ex) {
            return null;
        }
    }
}