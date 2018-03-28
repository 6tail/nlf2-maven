package com.nlf.extend.wechat.pay.core;

import com.nlf.extend.wechat.pay.bean.PayNotifyRequest;
import com.nlf.extend.wechat.pay.bean.PayNotifyResponse;

/**
 * 微信支付通知处理接口
 * 
 * @author 6tail
 *
 */
public interface IPayNotifyHandler{
  PayNotifyResponse onHandle(PayNotifyRequest request);
}