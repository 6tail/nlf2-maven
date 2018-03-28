package com.nlf.extend.wechat.msg.bean;

import com.nlf.extend.wechat.msg.type.EventType;
import com.nlf.extend.wechat.msg.type.MsgType;

/**
 * 抽象事件消息
 * 
 * @author 6tail
 * 
 */
public abstract class AbstractEventMsg extends AbstractMsg implements IEventMsg{
  /** 事件类型 */
  protected EventType eventType;

  public AbstractEventMsg(){
    setMsgType(MsgType.event);
  }

  public EventType getEventType(){
    return eventType;
  }

  public void setEventType(EventType eventType){
    this.eventType = eventType;
  }
}