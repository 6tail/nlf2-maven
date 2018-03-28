package com.nlf.extend.web.view;

import com.nlf.extend.web.WebView;

/**
 * 重定向
 * 
 * @author 6tail
 *
 */
public class RedirectView extends WebView{
  /** 页面地址 */
  protected String uri;

  public RedirectView(){}

  public String getUri(){
    return uri;
  }

  public RedirectView setUri(String uri){
    this.uri = uri;
    return this;
  }

  public RedirectView(String uri){
    this.uri = uri;
  }
}