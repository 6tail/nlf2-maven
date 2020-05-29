package com.nlf.extend.model.paging;

import com.nlf.dao.paging.AbstractPageable;
import com.nlf.extend.model.Model;

import java.util.List;

/**
 * 分页当前页数据封装
 *
 * @author 6tail
 *
 */
public class ModelPage<M extends Model> extends AbstractPageable<M>{
  private static final long serialVersionUID = 1;

  public ModelPage() {
    super();
  }

  public ModelPage(List<M> data, int pageSize, int pageNumber, int recordCount) {
    super(data, pageSize, pageNumber, recordCount);
  }
}
