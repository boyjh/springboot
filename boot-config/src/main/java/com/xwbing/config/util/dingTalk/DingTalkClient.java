package com.xwbing.config.util.dingTalk;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @author xiangwb
 */
public class DingTalkClient {
    private HttpClient httpclient = HttpClients.createDefault();

    public DingTalkClient() {
    }

    public SendResult send(String webHook, Message message) throws IOException {
        HttpPost httppost = new HttpPost(webHook);
        httppost.addHeader("Content-Type", "application/json; charset=utf-8");
        StringEntity se = new StringEntity(message.toJsonString(), "utf-8");
        httppost.setEntity(se);
        SendResult sendResult = new SendResult();
        HttpResponse response = this.httpclient.execute(httppost);
        if (response.getStatusLine().getStatusCode() == 200) {
            String result = EntityUtils.toString(response.getEntity());
            JSONObject obj = JSONObject.parseObject(result);
            Integer errCode = obj.getInteger("errcode");
            sendResult.setErrorCode(errCode);
            sendResult.setErrorMsg(obj.getString("errmsg"));
            sendResult.setSuccess(errCode.equals(0));
        }
        return sendResult;
    }
}
