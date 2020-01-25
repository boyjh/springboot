package com.xwbing.config.util.dingTalk;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @author xiangwb
 */
public class DingTalkClient {
    private HttpClient httpclient = HttpClients.createDefault();

    public DingTalkClient() {
    }

    public SendResult send(String webHook, String secret, Message message) throws IOException {
        String dingTalkUrl = dingTalkUrl(webHook, secret);
        SendResult sendResult = new SendResult();
        if (dingTalkUrl != null) {
            HttpPost httppost = new HttpPost(dingTalkUrl);
            httppost.addHeader("Content-Type", "application/json; charset=utf-8");
            StringEntity se = new StringEntity(message.toJsonString(), "utf-8");
            httppost.setEntity(se);
            HttpResponse response = this.httpclient.execute(httppost);
            if (response.getStatusLine().getStatusCode() == 200) {
                String result = EntityUtils.toString(response.getEntity());
                JSONObject obj = JSONObject.parseObject(result);
                Integer errCode = obj.getInteger("errcode");
                sendResult.setErrorCode(errCode);
                sendResult.setErrorMsg(obj.getString("errmsg"));
                sendResult.setSuccess(errCode.equals(0));
            }
        } else {
            sendResult.setSuccess(false);
            sendResult.setErrorMsg("加签失败");
        }
        return sendResult;
    }

    /**
     * 钉钉安全设置:加签
     *
     * @return url
     */
    private String dingTalkUrl(String webHook, String secret) {
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
