package com.nlf.extend.wechat.msg.bean.impl;

/**
 * 图文消息项
 * 
 * @author 6tail
 * 
 */
public class NewsItem{
  /** 标题 */
  private String title;
  /** 描述 */
  private String description;
  /** 图片URL */
  private String picUrl;
  /** 点击跳转URL */
  private String url;

  public String getTitle(){
    return title;
  }

  public void setTitle(String title){
    this.title = title;
  }

  public String getDescription(){
    return description;
  }

  public void setDescription(String description){
    this.description = description;
  }

  public String getPicUrl(){
    return picUrl;
  }

  public void setPicUrl(String picUrl){
    this.picUrl = picUrl;
  }

  public String getUrl(){
    return url;
  }

  public void setUrl(String url){
    this.url = url;
  }
}
