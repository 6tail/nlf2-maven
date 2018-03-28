package com.nlf.extend.wechat.sns.bean;

/**
 * 微信登录令牌，请注意不要与base中的令牌混淆
 * 
 * @author 6tail
 *
 */
public class AccessToken{
  /** 令牌 */
  private String token;
  /** 过期时间，秒 */
  private int expiresIn;
  /** 刷新令牌 */
  private String refreshToken;
  /** 用户唯一标识 */
  private String openid;
  /** 用户授权的作用域 */
  private String scope;
  /** 创建时间 */
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

  public String getRefreshToken(){
    return refreshToken;
  }

  public void setRefreshToken(String refreshToken){
    this.refreshToken = refreshToken;
  }

  public String getOpenid(){
    return openid;
  }

  public void setOpenid(String openid){
    this.openid = openid;
  }

  public String getScope(){
    return scope;
  }

  public void setScope(String scope){
    this.scope = scope;
  }
}