package com.nlf.dao.paging.impl;

public class DefaultPagingRender implements com.nlf.dao.paging.IPagingRender{
  public String render(com.nlf.dao.paging.PageData pd){
    return com.nlf.serialize.json.JSON.fromObject(pd);
  }
}