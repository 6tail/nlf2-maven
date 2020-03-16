package com.nlf.extend.dao.sql.dbType.mysql;

import com.nlf.Bean;
import com.nlf.dao.paging.PageData;
import com.nlf.extend.dao.sql.dbType.common.ASqlSelecter;
import com.nlf.log.Logger;

import java.util.List;

/**
 * SQL查询器的mysql实现
 * @author 6tail
 *
 */
public class MysqlSelecter extends ASqlSelecter{
  @Override
  public boolean support(String dbType){
    return "mysql".equalsIgnoreCase(dbType);
  }

  @Override
  public List<Bean> top(int count){
    params.clear();
    sql = buildSql();
    sql += " LIMIT 0,"+count;
    Logger.getLog().debug(buildLog());
    return queryList();
  }

  @Override
  public PageData page(int pageNumber, int pageSize){
    PageData d = new PageData();
    d.setPageSize(pageSize);
    d.setPageNumber(pageNumber);
    d.setRecordCount(count());
    if(d.getPageNumber()>d.getPageCount()){
      return d;
    }
    params.clear();
    sql = buildSql();
    sql += " LIMIT "+((d.getPageNumber()-1)*d.getPageSize())+","+d.getPageSize();
    Logger.getLog().debug(buildLog());
    List<Bean> l = queryList();
    d.setData(l);
    return d;
  }
}
