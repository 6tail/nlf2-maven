package com.nlf.extend.wechat.msg.bean.impl;

import com.nlf.extend.wechat.msg.bean.AbstractEventMsg;
import com.nlf.extend.wechat.msg.type.EventType;

/**
 * 用户取消关注事件
 * 
 * @author 6tail
 *
 */
public class UnSubscribeEventMsg extends AbstractEventMsg{
  public UnSubscribeEventMsg(){
    setEventType(EventType.unsubscribe);
  }
}
