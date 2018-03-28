package com.nlf.extend.wechat.msg.bean.impl;

import java.util.ArrayList;
import java.util.List;
import com.nlf.extend.wechat.msg.bean.AbstractMsg;
import com.nlf.extend.wechat.msg.bean.IResponseMsg;
import com.nlf.extend.wechat.msg.type.MsgType;

/**
 * 图文消息
 * 
 * @author 6tail
 * 
 */
public class NewsMsg extends AbstractMsg implements IResponseMsg{
  /** 图文列表 */
  private List<NewsItem> items = new ArrayList<NewsItem>();

  public NewsMsg(){
    setMsgType(MsgType.news);
  }

  public void addItem(NewsItem item){
    items.add(item);
  }

  public List<NewsItem> getItems(){
    return items;
  }

  public void setItems(List<NewsItem> items){
    this.items = items;
  }
}