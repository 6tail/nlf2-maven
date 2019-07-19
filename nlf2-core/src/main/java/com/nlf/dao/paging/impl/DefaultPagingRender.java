package com.nlf.dao.paging.impl;

/**
 * 默认分页渲染器
 * @author 6tail
 */
public class DefaultPagingRender implements com.nlf.dao.paging.IPagingRender{
  public String render(com.nlf.dao.paging.PageData pd){
    return com.nlf.serialize.json.JSON.fromObject(pd);
  }
}