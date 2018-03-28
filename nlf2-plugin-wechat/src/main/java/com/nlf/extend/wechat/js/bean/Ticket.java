package com.nlf.extend.wechat.js.bean;


/**
 * 模板消息
 * 
 * @author 6tail
 *
 */
public class Ticket{
  private String ticket;
  /** 过期时间，秒 */
  private int expiresIn;
  /** 创建时间 */
  private long createTime;

  public String getTicket(){
    return ticket;
  }

  public void setTicket(String ticket){
    this.ticket = ticket;
  }

  public int getExpiresIn(){
    return expiresIn;
  }

  public void setExpiresIn(int expiresIn){
    this.expiresIn = expiresIn;
  }

  public long getCreateTime(){
    return createTime;
  }

  public void setCreateTime(long createTime){
    this.createTime = createTime;
  }
}