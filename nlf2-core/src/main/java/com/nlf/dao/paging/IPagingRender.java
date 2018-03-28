package com.nlf.dao.paging;

/**
 * 自动分页渲染接口
 * 
 * @author 6tail
 *
 */
public interface IPagingRender{
  /**
   * 渲染
   * 
   * @param pd 分页数据
   * @return 字符串
   */
  String render(PageData pd);
}