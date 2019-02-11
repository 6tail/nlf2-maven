package com.nlf.extend.dao.sql.dbType.mysql;

import java.util.List;
import com.nlf.Bean;
import com.nlf.dao.paging.PageData;
import com.nlf.extend.dao.sql.dbType.common.ASqlTemplate;
import com.nlf.log.Logger;

/**
 * SQL模板的mysql实现
 * @author 6tail
 *
 */
public class MysqlTemplate extends ASqlTemplate{

  public boolean support(String dbType){
    return "mysql".equalsIgnoreCase(dbType);
  }

  public List<Bean> top(int count){
    params.clear();
    String sql = buildSql();
    sql = sql+" LIMIT 0,"+count;
    sql = buildParams(sql,param);
    this.sql = sql;
    Logger.getLog().debug(buildLog());
    return queryList();
  }

  public PageData page(int pageNumber,int pageSize){
    PageData d = new PageData();
    d.setPageSize(pageSize);
    d.setPageNumber(pageNumber);
    d.setRecordCount(count());
    if(d.getPageNumber()>d.getPageCount()){
      return d;
    }
    params.clear();
    String sql = buildSql();
    sql = "SELECT * FROM ("+sql+") NLFTABLE_ LIMIT "+((d.getPageNumber()-1)*d.getPageSize())+","+d.getPageSize();
    sql = buildParams(sql,param);
    this.sql = sql;
    Logger.getLog().debug(buildLog());
    List<Bean> l = queryList();
    d.setData(l);
    return d;
  }
}