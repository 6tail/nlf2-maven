package com.nlf.extend.wechat.msg.core;

import com.nlf.extend.wechat.msg.bean.IRequestMsg;
import com.nlf.extend.wechat.msg.bean.IResponseMsg;

/**
 * 微信公众号消息解析接口
 * 
 * @author 6tail
 *
 */
public interface IMsgResolver{
  /**
   * 将消息内容字符串转换为请求消息
   * 
   * @param str 内容字符串
   * @return 请求消息
   */
  IRequestMsg decode(String str);

  /**
   * 将响应消息转换为内容字符串
   * 
   * @param msg 响应消息
   * @return 内容字符串
   */
  String encode(IResponseMsg msg);
}