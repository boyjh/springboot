package com.xwbing.util;

import com.xwbing.constant.Base;
import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;
import java.util.Date;

/**
 * @author xiangwb
 * @date 20/1/18 20:38
 * 订单号生产工具类
 */
public class OrderNoUtil {
    public static String createOrderId() {
        int businessType;
        String env = EnvUtil.getEnv();
        if (StringUtils.equals(env, Base.ENV_DEV)) {
            businessType = Base.BUSINESS_LEASE_DEV;
        } else if (StringUtils.equals(env, Base.ENV_TEST)) {
            businessType = Base.BUSINESS_LEASE_TEST;
        } else if (StringUtils.equals(env, Base.ENV_SANDBOX)) {
            businessType = Base.BUSINESS_LEASE_TEST;
        } else {
            businessType = Base.BUSINESS_LEASE_PROD;
        }
        Long seq = redisTemplate.opsForValue().increment("or-sequence", 1);
        boolean flag = false;
        if (seq > Base.ORDER_MAX_SEQ) {
            seq = 1L;
            flag = true;
        }
        if (flag) {
            redisTemplate.opsForValue().set("or-sequence", seq);
        }
        String sequence = new DecimalFormat("000000").format(seq);
        String date = DateUtil2.dateToStr(new Date(), DateUtil2.YYYYMMDD);
        return businessType + date + sequence;
    }
}
