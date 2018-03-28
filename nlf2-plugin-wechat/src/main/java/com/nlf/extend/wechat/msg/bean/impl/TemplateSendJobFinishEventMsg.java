package com.nlf.extend.wechat.msg.bean.impl;

import com.nlf.extend.wechat.msg.bean.AbstractEventMsg;
import com.nlf.extend.wechat.msg.type.EventType;

/**
 * 模板消息发送完成事件
 * 
 * @author 6tail
 * 
 */
public class TemplateSendJobFinishEventMsg extends AbstractEventMsg{
  /** 消息ID */
  private String msgId;
  /** 状态，形如：success,failed:user block,failed:system failed */
  private String status;

  public TemplateSendJobFinishEventMsg(){
    setEventType(EventType.TEMPLATESENDJOBFINISH);
  }

  /**
   * 获取消息ID
   * 
   * @return 消息ID
   */
  public String getMsgId(){
    return msgId;
  }

  /**
   * 设置消息ID
   * 
   * @param msgId 消息ID
   */
  public void setMsgId(String msgId){
    this.msgId = msgId;
  }

  /**
   * 获取状态
   * @return 状态
   */
  public String getStatus(){
    return status;
  }

  /**
   * 设置状态
   * @param status 状态
   */
  public void setStatus(String status){
    this.status = status;
  }
}