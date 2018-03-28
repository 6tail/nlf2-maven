package com.nlf.core;

import java.util.Locale;

/**
 * 客户端
 * 
 * @author 6tail
 *
 */
public class Client{
  /** IP地址 */
  protected String ip = "";
  protected Locale locale;

  public String getIp(){
    return ip;
  }

  public void setIp(String ip){
    this.ip = ip;
  }

  public Locale getLocale(){
    return null==locale?Locale.getDefault():locale;
  }

  public void setLocale(Locale locale){
    this.locale = locale;
  }
}