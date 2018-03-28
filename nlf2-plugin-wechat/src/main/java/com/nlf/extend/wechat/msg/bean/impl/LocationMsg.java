package com.nlf.extend.wechat.msg.bean.impl;

import com.nlf.extend.wechat.msg.bean.AbstractMsg;
import com.nlf.extend.wechat.msg.bean.IRequestMsg;
import com.nlf.extend.wechat.msg.type.MsgType;

/**
 * 地理位置消息
 * 
 * @author 6tail
 *
 */
public class LocationMsg extends AbstractMsg implements IRequestMsg{
  private String locationX;
  private String locationY;
  private String scale;
  private String label;
  private String msgId;

  public LocationMsg(){
    setMsgType(MsgType.location);
  }

  public String getLocationX(){
    return locationX;
  }

  public void setLocationX(String locationX){
    this.locationX = locationX;
  }

  public String getLocationY(){
    return locationY;
  }

  public void setLocationY(String locationY){
    this.locationY = locationY;
  }

  public String getScale(){
    return scale;
  }

  public void setScale(String scale){
    this.scale = scale;
  }

  public String getLabel(){
    return label;
  }

  public void setLabel(String label){
    this.label = label;
  }

  public String getMsgId(){
    return msgId;
  }

  public void setMsgId(String msgId){
    this.msgId = msgId;
  }
}
