package com.nlf.extend.wechat.msg.bean.impl;

import com.nlf.extend.wechat.msg.bean.AbstractEventMsg;
import com.nlf.extend.wechat.msg.type.EventType;

/**
 * 二维码扫描事件
 * 
 * @author 6tail
 *
 */
public class ScanEventMsg extends AbstractEventMsg{
  private String eventKey;
  private String ticket;

  public ScanEventMsg(){
    setEventType(EventType.SCAN);
  }

  public String getEventKey(){
    return eventKey;
  }

  public void setEventKey(String eventKey){
    this.eventKey = eventKey;
  }

  public String getTicket(){
    return ticket;
  }

  public void setTicket(String ticket){
    this.ticket = ticket;
  }
}
