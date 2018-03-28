package com.nlf.extend.wechat.base.bean;

/**
 * 微信令牌
 * 
 * @author 6tail
 *
 */
public class AccessToken{
  /** 令牌 */
  private String token;
  /** 过期时间，秒 */
  private int expiresIn;
  /**创建时间*/
  private long createTime;

  public long getCreateTime(){
    return createTime;
  }

  public void setCreateTime(long createTime){
    this.createTime = createTime;
  }

  public String getToken(){
    return token;
  }

  public void setToken(String token){
    this.token = token;
  }

  public int getExpiresIn(){
    return expiresIn;
  }

  public void setExpiresIn(int expiresIn){
    this.expiresIn = expiresIn;
  }
}
