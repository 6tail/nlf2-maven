package com.nlf.extend.wechat.msg.bean.impl;

import com.nlf.extend.wechat.msg.bean.AbstractMsg;
import com.nlf.extend.wechat.msg.bean.IRequestMsg;
import com.nlf.extend.wechat.msg.bean.IResponseMsg;
import com.nlf.extend.wechat.msg.type.MsgType;

/**
 * 文本消息
 * 
 * @author 6tail
 * 
 */
public class TextMsg extends AbstractMsg implements IRequestMsg,IResponseMsg{
  /** 消息内容 */
  private String content;
  /** 消息id */
  private String msgId;

  public TextMsg(){
    setMsgType(MsgType.text);
  }

  /**
   * 获取内容
   * 
   * @return 内容
   */
  public String getContent(){
    return content;
  }

  /**
   * 设置内容
   * 
   * @param content 内容
   */
  public void setContent(String content){
    this.content = content;
  }

  /**
   * 获取消息id
   * 
   * @return 消息id
   */
  public String getMsgId(){
    return msgId;
  }

  /**
   * 设置消息id
   * 
   * @param msgId 消息id
   */
  public void setMsgId(String msgId){
    this.msgId = msgId;
  }
}
