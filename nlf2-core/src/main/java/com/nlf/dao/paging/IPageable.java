package com.nlf.dao.paging;

import java.io.Serializable;
import java.util.List;

/**
 * 可分页数据接口
 * @author 6tail
 */
public interface IPageable<M> extends List<M>,Serializable {

  /**
   * 获取唯一标识
   * @return 唯一标识
   */
  String getId();

  /**
   * 获取该页数据条数
   *
   * @return 该页数据条数
   */
  int getSize();

  /**
   * 获取该页页码
   *
   * @return 页码
   */
  int getPageNumber();

  /**
   * 获取每页记录数
   *
   * @return 每页记录数
   */
  int getPageSize();

  /**
   * 获取总记录数
   *
   * @return 总记录数
   */
  int getRecordCount();

  /**
   * 获取总页数
   *
   * @return 总页数
   */
  int getPageCount();

  /**
   * 获取前一页页码
   *
   * @return 前一页页码
   */
  int getPreviousPageNumber();

  /**
   * 获取后一页页码
   *
   * @return 页码
   */
  int getNextPageNumber();

  /**
   * 获取第一页页码
   *
   * @return 页码
   */
  int getFirstPageNumber();

  /**
   * 获取最后页页码
   *
   * @return 页码
   */
  int getLastPageNumber();

  /**
   * 是否有下一页
   * @return true/false
   */
  boolean isHasNextPage();

  /**
   * 获取前后相邻的页码
   *
   * @return 相邻的页码数组
   */
  int[] getNearPageNumbers();

  /**
   * 获取该页数据
   *
   * @return 该页数据
   */
  List<M> getData();
}
