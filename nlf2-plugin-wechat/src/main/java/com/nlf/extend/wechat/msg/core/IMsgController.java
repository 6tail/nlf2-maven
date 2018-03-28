package com.nlf.extend.wechat.msg.core;

/**
 * 消息控制接口
 * 
 * @author 6tail
 *
 */
public interface IMsgController{
  /**
   * 处理消息请求
   * 
   * @param handler 微信公众号消息处理接口
   * @return 处理结果字符串
   */
  String handle(IMsgHandler handler);
}