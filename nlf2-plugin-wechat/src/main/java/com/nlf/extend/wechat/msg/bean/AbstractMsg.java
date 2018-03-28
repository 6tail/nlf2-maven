package com.nlf.extend.wechat.msg.bean;

import com.nlf.extend.wechat.msg.type.MsgType;

/**
 * 抽象消息
 *
 * @author 6tail
 *
 */
public abstract class AbstractMsg{
  /** 消息类型 */
  protected MsgType msgType;
  /** 发送者 */
  protected String fromUser;
  /** 接收者 */
  protected String toUser;
  /** 创建时间 */
  protected String createTime;

  public MsgType getMsgType(){
    return msgType;
  }

  public void setMsgType(MsgType msgType){
    this.msgType = msgType;
  }

  public String getFromUser(){
    return fromUser;
  }

  public void setFromUser(String fromUser){
    this.fromUser = fromUser;
  }

  public String getToUser(){
    return toUser;
  }

  public void setToUser(String toUser){
    this.toUser = toUser;
  }

  public String getCreateTime(){
    return createTime;
  }

  public void setCreateTime(String createTime){
    this.createTime = createTime;
  }
}