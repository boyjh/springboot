package com.xwbing.service.rest;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.xwbing.domain.entity.pay.alipay.AlipayTradePayNotifyRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xiangwb
 * @date 20/2/9 16:11
 * 支付宝异步通知处理
 */
@Slf4j
@Service
public class AliPayNotifyService {
    @Value("${aliPay.rsaPublicKey}")
    private String publicKey;

    /**
     * 验签
     *
     * @param aliPayTradePayNotifyRequest
     */
    public void verifyTradePayParams(AlipayTradePayNotifyRequest aliPayTradePayNotifyRequest) {
        String outTradeNo = aliPayTradePayNotifyRequest.getOut_trade_no();
        try {
            Map<String, String> params = new HashMap<>();
            params.put("notify_time", aliPayTradePayNotifyRequest.getNotify_time());
            params.put("notify_type", aliPayTradePayNotifyRequest.getNotify_type());
            params.put("notify_id", aliPayTradePayNotifyRequest.getNotify_id());
            params.put("sign_type", aliPayTradePayNotifyRequest.getSign_type());
            params.put("sign", aliPayTradePayNotifyRequest.getSign());
            params.put("trade_no", aliPayTradePayNotifyRequest.getTrade_no());
            params.put("app_id", aliPayTradePayNotifyRequest.getApp_id());
            params.put("out_trade_no", aliPayTradePayNotifyRequest.getOut_trade_no());
            params.put("out_biz_no", aliPayTradePayNotifyRequest.getOut_biz_no());
            params.put("buyer_id", aliPayTradePayNotifyRequest.getBuyer_id());
            params.put("buyer_logon_id", aliPayTradePayNotifyRequest.getBuyer_logon_id());
            params.put("seller_id", aliPayTradePayNotifyRequest.getSeller_id());
            params.put("seller_email", aliPayTradePayNotifyRequest.getSeller_email());
            params.put("trade_status", aliPayTradePayNotifyRequest.getTrade_status());
            params.put("total_amount", aliPayTradePayNotifyRequest.getTotal_amount());
            params.put("receipt_amount", aliPayTradePayNotifyRequest.getReceipt_amount());
            params.put("invoice_amount", aliPayTradePayNotifyRequest.getInvoice_amount());
            params.put("buyer_pay_amount", aliPayTradePayNotifyRequest.getBuyer_pay_amount());
            params.put("point_amount", aliPayTradePayNotifyRequest.getPoint_amount());
            params.put("refund_fee", aliPayTradePayNotifyRequest.getRefund_fee());
            params.put("send_back_fee", aliPayTradePayNotifyRequest.getSend_back_fee());
            params.put("subject", aliPayTradePayNotifyRequest.getSubject());
            params.put("body", aliPayTradePayNotifyRequest.getBody());
            params.put("gmt_create", aliPayTradePayNotifyRequest.getGmt_create());
            params.put("gmt_payment", aliPayTradePayNotifyRequest.getGmt_payment());
            params.put("gmt_refund", aliPayTradePayNotifyRequest.getGmt_refund());
            params.put("gmt_close", aliPayTradePayNotifyRequest.getGmt_close());
            params.put("fund_bill_list", aliPayTradePayNotifyRequest.getFund_bill_list());
            //去除无效参数
            params = paramFilterWithOutNull(params);
            //验签
            boolean flag = AlipaySignature.rsaCheckV1(params, publicKey, "utf-8", aliPayTradePayNotifyRequest.getSign_type());
            if (!flag) {
                log.error("verifyTradeCreateParams {} 验签失败", outTradeNo);
            }
        } catch (AlipayApiException e) {
            log.error("verifyTradeCreateParams {} exception:{}", outTradeNo, ExceptionUtils.getStackTrace(e));
        }
    }

    private Map<String, String> paramFilterWithOutNull(Map<String, String> param) {
        Map<String, String> result = new HashMap<>();
        if (MapUtils.isEmpty(param)) {
            return result;
        }
        param.forEach((key, value) -> {
            if (StringUtils.isNotEmpty(value)) {
                result.put(key, value);
            }
        });
        return result;
    }
}