package com.nlf.extend.wechat.media.bean;

import com.nlf.extend.wechat.media.MediaType;

/**
 * 媒体
 * @author 6tail
 *
 */
public class Media{
  private String mediaId;
  private MediaType type;
  private String createdAt;

  public String getMediaId(){
    return mediaId;
  }

  public void setMediaId(String mediaId){
    this.mediaId = mediaId;
  }

  public MediaType getType(){
    return type;
  }

  public void setType(MediaType type){
    this.type = type;
  }

  public String getCreatedAt(){
    return createdAt;
  }

  public void setCreatedAt(String createdAt){
    this.createdAt = createdAt;
  }
}