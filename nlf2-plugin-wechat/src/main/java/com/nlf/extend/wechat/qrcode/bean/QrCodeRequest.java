package com.nlf.extend.wechat.qrcode.bean;

import com.nlf.extend.wechat.qrcode.type.QrCodeType;

/**
 * 二维码请求
 * 
 * @author 6tail
 *
 */
public class QrCodeRequest{
  /** 该二维码有效时间，以秒为单位。 最大不超过1800。 */
  private int expireIn;
  /** 二维码类型，QR_SCENE为临时,QR_LIMIT_SCENE为永久 */
  private QrCodeType type;
  /** 场景值ID，临时二维码时为32位非0整型，永久二维码时最大值为100000（目前参数只支持1--100000） */
  private int sceneId;

  public int getExpireIn(){
    return expireIn;
  }

  public void setExpireIn(int expireIn){
    this.expireIn = expireIn;
  }

  public QrCodeType getType(){
    return type;
  }

  public void setType(QrCodeType type){
    this.type = type;
  }

  public int getSceneId(){
    return sceneId;
  }

  public void setSceneId(int sceneId){
    this.sceneId = sceneId;
  }
}