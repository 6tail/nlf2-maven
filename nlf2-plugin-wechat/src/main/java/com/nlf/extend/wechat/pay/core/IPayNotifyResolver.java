package com.nlf.extend.wechat.pay.core;

import com.nlf.extend.wechat.pay.bean.PayNotifyRequest;
import com.nlf.extend.wechat.pay.bean.PayNotifyResponse;

/**
 * 微信支付通知解析接口
 * 
 * @author 6tail
 *
 */
public interface IPayNotifyResolver{
  /**
   * 将通知内容字符串转换为请求
   * 
   * @param str 内容字符串
   * @return 请求
   */
  PayNotifyRequest decode(String str);

  /**
   * 将响应转换为内容字符串
   * 
   * @param msg 响应
   * @return 内容字符串
   */
  String encode(PayNotifyResponse msg);
}