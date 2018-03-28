package com.nlf.extend.wechat.msg.bean.impl;

import com.nlf.extend.wechat.msg.bean.AbstractMsg;
import com.nlf.extend.wechat.msg.bean.IResponseMsg;
import com.nlf.extend.wechat.msg.type.MsgType;

/**
 * 音乐
 * 
 * @author 6tail
 *
 */
public class MusicMsg extends AbstractMsg implements IResponseMsg{
  /** 标题 */
  private String title;
  /** 描述 */
  private String description;
  /** 音乐URL */
  private String musicUrl;
  private String hqMusicUrl;
  private String thumbMediaId;

  public MusicMsg(){
    setMsgType(MsgType.music);
  }

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

  public String getMusicUrl(){
    return musicUrl;
  }

  public void setMusicUrl(String musicUrl){
    this.musicUrl = musicUrl;
  }

  public String getHqMusicUrl(){
    return hqMusicUrl;
  }

  public void setHqMusicUrl(String hqMusicUrl){
    this.hqMusicUrl = hqMusicUrl;
  }

  public String getThumbMediaId(){
    return thumbMediaId;
  }

  public void setThumbMediaId(String thumbMediaId){
    this.thumbMediaId = thumbMediaId;
  }
}
