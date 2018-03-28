package com.nlf.extend.wechat.user.bean;


/**
 * 关注的微信用户信息
 * 
 * @author 6tail
 *
 */
public class UserInfo{
  /** 用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。 */
  private int subscribe;
  /** 普通用户的标识，对当前开发者帐号唯一 */
  private String openid;
  /** 普通用户昵称 */
  private String nickname;
  /** 普通用户性别，1为男性，2为女性 */
  private int sex;
  /** 普通用户个人资料填写的省份 */
  private String province;
  /** 普通用户个人资料填写的城市 */
  private String city;
  /** 国家，如中国为CN */
  private String country;
  /** 用户的语言，简体中文为zh_CN */
  private String language;
  /** 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空 */
  private String headimgurl;
  /** 用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间 */
  private long subscribeTime;
  /** 用户统一标识。针对一个微信开放平台帐号下的应用，同一用户的unionid是唯一的。 */
  private String unionid;

  public String getOpenid(){
    return openid;
  }

  public void setOpenid(String openid){
    this.openid = openid;
  }

  public String getNickname(){
    return nickname;
  }

  public void setNickname(String nickname){
    this.nickname = nickname;
  }

  public int getSex(){
    return sex;
  }

  public void setSex(int sex){
    this.sex = sex;
  }

  public String getProvince(){
    return province;
  }

  public void setProvince(String province){
    this.province = province;
  }

  public String getCity(){
    return city;
  }

  public void setCity(String city){
    this.city = city;
  }

  public String getCountry(){
    return country;
  }

  public void setCountry(String country){
    this.country = country;
  }

  public String getHeadimgurl(){
    return headimgurl;
  }

  public void setHeadimgurl(String headimgurl){
    this.headimgurl = headimgurl;
  }

  public String getUnionid(){
    return unionid;
  }

  public void setUnionid(String unionid){
    this.unionid = unionid;
  }

  public int getSubscribe(){
    return subscribe;
  }

  public void setSubscribe(int subscribe){
    this.subscribe = subscribe;
  }

  public String getLanguage(){
    return language;
  }

  public void setLanguage(String language){
    this.language = language;
  }

  public long getSubscribeTime(){
    return subscribeTime;
  }

  public void setSubscribeTime(long subscribeTime){
    this.subscribeTime = subscribeTime;
  }
}