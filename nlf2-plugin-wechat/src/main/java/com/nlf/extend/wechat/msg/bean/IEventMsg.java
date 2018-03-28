package com.nlf.extend.wechat.msg.bean;

import com.nlf.extend.wechat.msg.type.EventType;

/**
 * 微信公众号事件消息接口，事件消息只可能是请求消息
 *
 * @author 6tail
 *
 */
public interface IEventMsg extends IRequestMsg{
  /**
   * 设置事件类型
   *
   * @param msgType 事件类型
   */
  void setEventType(EventType msgType);

  /**
   * 获取事件类型
   *
   * @return 事件类型
   */
  EventType getEventType();
}