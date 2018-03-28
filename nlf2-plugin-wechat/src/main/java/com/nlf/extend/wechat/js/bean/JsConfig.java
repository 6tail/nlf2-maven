package com.nlf.extend.wechat.js.bean;

/**
 * js配置
 * 
 * @author 6tail
 *
 */
public class JsConfig{
  private boolean debug;
  private String appId;
  private String noncestr;
  private String ticket;
  private String timestamp;
  private String url;
  private String[] jsApiList = {};

  public boolean isDebug(){
    return debug;
  }

  public void setDebug(boolean debug){
    this.debug = debug;
  }

  public String getAppId(){
    return appId;
  }

  public void setAppId(String appId){
    this.appId = appId;
  }

  public String getNoncestr(){
    return noncestr;
  }

  public void setNoncestr(String noncestr){
    this.noncestr = noncestr;
  }

  public String getTicket(){
    return ticket;
  }

  public void setTicket(String ticket){
    this.ticket = ticket;
  }

  public String getTimestamp(){
    return timestamp;
  }

  public void setTimestamp(String timestamp){
    this.timestamp = timestamp;
  }

  public String getUrl(){
    return url;
  }

  public void setUrl(String url){
    this.url = url;
  }

  public String[] getJsApiList(){
    return jsApiList;
  }

  public void setJsApiList(String[] jsApiList){
    this.jsApiList = jsApiList;
  }
}