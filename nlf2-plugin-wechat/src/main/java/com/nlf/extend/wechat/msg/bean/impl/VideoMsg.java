package com.nlf.extend.wechat.msg.bean.impl;

import com.nlf.extend.wechat.msg.bean.AbstractMsg;
import com.nlf.extend.wechat.msg.bean.IRequestMsg;
import com.nlf.extend.wechat.msg.bean.IResponseMsg;
import com.nlf.extend.wechat.msg.type.MsgType;

/**
 * 视频
 * 
 * @author 6tail
 *
 */
public class VideoMsg extends AbstractMsg implements IRequestMsg,IResponseMsg{
  /** 缩略图媒体id，用于请求 */
  private String thumbMediaId;
  /** 媒体id，请求和响应均可 */
  private String mediaId;
  /** 消息id，用于请求 */
  private String msgId;
  /** 标题，用于响应 */
  private String title;
  /** 描述，用于响应 */
  private String description;

  public VideoMsg(){
    setMsgType(MsgType.video);
  }

  public String getTitle(){
    return title;
  }

  public void setTitle(String title){
    this.title = title;
  }

  public String getDescription(){
    return description;
  }

  public void setDescription(String description){
    this.description = description;
  }

  public String getThumbMediaId(){
    return thumbMediaId;
  }

  public void setThumbMediaId(String thumbMediaId){
    this.thumbMediaId = thumbMediaId;
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