package com.nlf.extend.wechat.pay.core.impl;

import com.nlf.extend.wechat.pay.bean.PayNotifyRequest;
import com.nlf.extend.wechat.pay.bean.PayNotifyResponse;
import com.nlf.extend.wechat.pay.core.IPayNotifyHandler;

/**
 * 抽象微信支付通知处理者
 * 
 * @author 6tail
 *
 */
public abstract class DefaulPayNotifyHandler implements IPayNotifyHandler{
  public PayNotifyResponse onHandle(PayNotifyRequest request){
    return null;
  }
  
}