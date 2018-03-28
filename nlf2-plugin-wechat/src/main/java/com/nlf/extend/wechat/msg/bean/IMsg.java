package com.nlf.extend.wechat.msg.bean;

import com.nlf.extend.wechat.msg.type.MsgType;

/**
 * 微信公众号消息接口
 * 
 * @author 6tail
 *
 */
public interface IMsg{
  /**
   * 设置消息类型
   * 
   * @param msgType 消息类型
   */
  void setMsgType(MsgType msgType);

  /**
   * 获取消息类型
   * 
   * @return
   */
  MsgType getMsgType();

  /**
   * 获取发送者
   * 
   * @return 发送者
   */
  String getFromUser();

  /**
   * 设置发送者
   * 
   * @param fromUser 发送者
   */
  void setFromUser(String fromUser);

  /**
   * 获取接收者
   * 
   * @return 接收者
   */
  String getToUser();

  /**
   * 设置接收者
   * 
   * @param toUser 接收者
   */
  void setToUser(String toUser);

  /**
   * 获取创建时间
   * 
   * @return 创建时间
   */
  String getCreateTime();

  /**
   * 设置创建时间
   * 
   * @param createTime 创建时间
   */
  void setCreateTime(String createTime);
}