package com.nlf.extend.wechat.message.bean;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 模板消息
 * 
 * @author 6tail
 *
 */
public class Message{
  private String toUser;
  private String templateId;
  private String url;
  private String topColor;
  private MessageNode first;
  private MessageNode remark;
  private Map<String,MessageNode> datas = new LinkedHashMap<String,MessageNode>();

  public String getToUser(){
    return toUser;
  }

  public void setToUser(String toUser){
    this.toUser = toUser;
  }

  public String getTemplateId(){
    return templateId;
  }

  public void setTemplateId(String templateId){
    this.templateId = templateId;
  }

  public String getUrl(){
    return url;
  }

  public void setUrl(String url){
    this.url = url;
  }

  public String getTopColor(){
    return topColor;
  }

  public void setTopColor(String topColor){
    this.topColor = topColor;
  }

  public MessageNode getFirst(){
    return first;
  }

  public void setFirst(MessageNode first){
    this.first = first;
  }

  public MessageNode getRemark(){
    return remark;
  }

  public void setRemark(MessageNode remark){
    this.remark = remark;
  }

  public Map<String,MessageNode> getDatas(){
    return datas;
  }

  public void setDatas(Map<String,MessageNode> datas){
    this.datas = datas;
  }

  public void addData(String key,MessageNode data){
    datas.put(key,data);
  }
}