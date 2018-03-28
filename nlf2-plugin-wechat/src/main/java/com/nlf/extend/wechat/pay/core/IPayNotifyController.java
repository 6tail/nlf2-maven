package com.nlf.extend.wechat.pay.core;

/**
 * 微信支付通知控制接口
 * 
 * @author 6tail
 *
 */
public interface IPayNotifyController{
  /**
   * 处理通知请求
   * 
   * @param handler 微信支付通知处理接口
   * @return 处理结果字符串
   */
  String handle(IPayNotifyHandler handler);
}