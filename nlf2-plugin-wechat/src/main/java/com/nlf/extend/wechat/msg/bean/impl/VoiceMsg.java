package com.nlf.extend.wechat.msg.bean.impl;

import com.nlf.extend.wechat.msg.bean.AbstractMsg;
import com.nlf.extend.wechat.msg.bean.IRequestMsg;
import com.nlf.extend.wechat.msg.bean.IResponseMsg;
import com.nlf.extend.wechat.msg.type.MsgType;

/**
 * 语音
 * 
 * @author 6tail
 *
 */
public class VoiceMsg extends AbstractMsg implements IRequestMsg,IResponseMsg{
  /** 语音格式 */
  private String format;
  /** 语音消息媒体id */
  private String mediaId;
  /** 消息id */
  private String msgId;
  /** 语音识别结果 */
  private String recognition;

  public VoiceMsg(){
    setMsgType(MsgType.voice);
  }

  public String getRecognition(){
    return recognition;
  }

  public void setRecognition(String recognition){
    this.recognition = recognition;
  }

  public String getFormat(){
    return format;
  }

  public void setFormat(String format){
    this.format = format;
  }

  public String getMediaId(){
    return mediaId;
  }

  public void setMediaId(String mediaId){
    this.mediaId = mediaId;
  }

  public String getMsgId(){
    return msgId;
  }

  public void setMsgId(String msgId){
    this.msgId = msgId;
  }
}