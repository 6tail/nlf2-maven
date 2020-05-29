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
   * @param pageable 分页数据
   * @return 字符串
   */
  String render(IPageable pageable);

  /**
   * 是否支持渲染
   * @return true/false
   */
  boolean support();
}
