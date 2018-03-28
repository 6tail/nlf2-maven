package com.nlf.extend.wechat.qrcode.bean;

/**
 * 二维码响应
 * 
 * @author 6tail
 *
 */
public class QrCodeResponse{
  /** 二维码的有效时间，以秒为单位。最大不超过1800。 */
  private int expireIn;
  /** 获取的二维码ticket，凭借此ticket可以在有效时间内换取二维码。 */
  private String ticket;
  /** 二维码图片解析后的地址，开发者可根据该地址自行生成需要的二维码图片 */
  private String url;

  public int getExpireIn(){
    return expireIn;
  }

  public void setExpireIn(int expireIn){
    this.expireIn = expireIn;
  }

  public String getTicket(){
    return ticket;
  }

  public void setTicket(String ticket){
    this.ticket = ticket;
  }

  public String getUrl(){
    return url;
  }

  public void setUrl(String url){
    this.url = url;
  }
}