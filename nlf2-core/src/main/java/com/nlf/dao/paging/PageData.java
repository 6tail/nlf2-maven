package com.nlf.dao.paging;

import com.nlf.Bean;

import java.util.List;

/**
 * 分页当前页数据封装
 *
 * @author 6tail
 *
 */
public class PageData extends AbstractPageable<Bean>{
  private static final long serialVersionUID = 1;

  public PageData() {
    super();
  }

  public PageData(List<Bean> data, int pageSize, int pageNumber, int recordCount) {
    super(data, pageSize, pageNumber, recordCount);
  }
}
