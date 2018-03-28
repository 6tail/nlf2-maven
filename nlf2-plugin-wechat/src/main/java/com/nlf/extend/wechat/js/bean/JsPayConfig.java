package com.nlf.extend.wechat.js.bean;

/**
 * JS支付配置信息
 * @author 6tail
 *
 */
public class JsPayConfig{
  private String appId;
  private String timeStamp;
  private String nonceStr;
  private String pkg;
  private String signType = "MD5";
  private String paySign;

  public String getPaySign(){
    return paySign;
  }

  public void setPaySign(String paySign){
    this.paySign = paySign;
  }

  public String getAppId(){
    return appId;
  }

  public void setAppId(String appId){
    this.appId = appId;
  }

  public String getTimeStamp(){
    return timeStamp;
  }

  public void setTimeStamp(String timeStamp){
    this.timeStamp = timeStamp;
  }

  public String getNonceStr(){
    return nonceStr;
  }

  public void setNonceStr(String nonceStr){
    this.nonceStr = nonceStr;
  }

  public String getPkg(){
    return pkg;
  }

  public void setPkg(String pkg){
    this.pkg = pkg;
  }

  public String getSignType(){
    return signType;
  }

  public void setSignType(String signType){
    this.signType = signType;
  }
}