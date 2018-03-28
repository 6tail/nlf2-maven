package com.nlf.extend.dao.noSql;

import com.nlf.App;
import com.nlf.core.IRequest;
import com.nlf.dao.executer.AbstractDaoExecuter;
import com.nlf.dao.paging.PageData;

public abstract class AbstractNoSqlExecuter extends AbstractDaoExecuter implements INoSqlExecuter{

  public boolean support(String dbType){
    return true;
  }

  public PageData paging(String key){
    IRequest r = App.getRequest();
    return page(key,r.getPageNumber(),r.getPageSize());
  }
}