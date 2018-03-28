package com.nlf.extend.wechat.msg.bean.impl;

import com.nlf.extend.wechat.msg.bean.AbstractEventMsg;
import com.nlf.extend.wechat.msg.type.EventType;

/**
 * 地理位置事件
 * 
 * @author 6tail
 *
 */
public class LocationEventMsg extends AbstractEventMsg{
  /** 纬度 */
  private String latitude;
  /** 经度 */
  private String longitude;
  /** 精度 */
  private String precision;

  public LocationEventMsg(){
    setEventType(EventType.LOCATION);
  }

  /**
   * 获取纬度
   * 
   * @return 纬度
   */
  public String getLatitude(){
    return latitude;
  }

  /**
   * 设置纬度
   * 
   * @param latitude 纬度
   */
  public void setLatitude(String latitude){
    this.latitude = latitude;
  }

  /**
   * 获取经度
   * 
   * @return 经度
   */
  public String getLongitude(){
    return longitude;
  }

  /**
   * 设置经度
   * 
   * @param longitude 经度
   */
  public void setLongitude(String longitude){
    this.longitude = longitude;
  }

  /**
   * 获取精度
   * 
   * @return 精度
   */
  public String getPrecision(){
    return precision;
  }

  /**
   * 设置精度
   * 
   * @param precision 精度
   */
  public void setPrecision(String precision){
    this.precision = precision;
  }
}
