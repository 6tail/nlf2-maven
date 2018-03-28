package com.nlf.extend.wechat.msg.bean.impl;

import com.nlf.extend.wechat.msg.bean.AbstractEventMsg;
import com.nlf.extend.wechat.msg.type.EventType;

/**
 * 菜单点击事件
 * 
 * @author 6tail
 * 
 */
public class ClickEventMsg extends AbstractEventMsg {
  /** 菜单对应的关键字 */
  private String eventKey;

  public ClickEventMsg(){
    setEventType(EventType.CLICK);
  }

  /**
   * 获取菜单对应的关键字
   * 
   * @return 菜单对应的关键字
   */
  public String getEventKey(){
    return eventKey;
  }

  /**
   * 设置菜单对应的关键字
   * 
   * @param eventKey 菜单对应的关键字
   */
  public void setEventKey(String eventKey){
    this.eventKey = eventKey;
  }
}
