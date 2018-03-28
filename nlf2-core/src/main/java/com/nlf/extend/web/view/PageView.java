package com.nlf.extend.web.view;

import com.nlf.Bean;
import com.nlf.dao.paging.PageData;
import com.nlf.extend.web.WebView;

/**
 * 页面
 * 
 * @author 6tail
 *
 */
public class PageView extends WebView{
  /** 页面地址 */
  protected String uri;
  /** 传递参数 */
  protected Bean attributes = new Bean();

  public PageView(String uri){
    this.uri = uri;
  }

  public String getUri(){
    return uri;
  }

  public PageView setUri(String uri){
    this.uri = uri;
    return this;
  }

  public Bean getAttributes(){
    return attributes;
  }

  public PageView setAttribute(String key,Object value){
    attributes.set(key,value);
    if(value instanceof PageData){
      ((PageData)value).setId(key);
    }
    return this;
  }
}