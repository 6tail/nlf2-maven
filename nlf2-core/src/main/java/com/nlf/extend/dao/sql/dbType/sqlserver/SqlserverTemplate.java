package com.nlf.extend.dao.sql.dbType.sqlserver;

import java.util.List;
import com.nlf.App;
import com.nlf.Bean;
import com.nlf.dao.exception.DaoException;
import com.nlf.dao.paging.PageData;
import com.nlf.extend.dao.sql.dbType.common.ASqlTemplate;
import com.nlf.log.Logger;

/**
 * SQL模板的sqlserver实现
 *
 * @author 6tail
 */
public class SqlserverTemplate extends ASqlTemplate{
  @Override
  public boolean support(String dbType){
    return "sqlserver".equalsIgnoreCase(dbType);
  }

  @Override
  public List<Bean> top(int count){
    params.clear();
    String sql = buildSql();
    sql = "SELECT TOP "+count+sql.replaceFirst("SELECT","");
    sql = buildParams(sql,param);
    this.sql = sql;
    Logger.getLog().debug(buildLog());
    return queryList();
  }

  @Override
  public int count(){
    params.clear();
    String sql = buildSql();
    sql = sql.replace("\r"," ").replace("\n"," ");
    String upperSql = sql.toUpperCase();
    int orderIndex = upperSql.indexOf(" ORDER ");
    if(orderIndex>-1){
      sql = sql.substring(0,orderIndex);
    }
    sql = "SELECT COUNT(*) NLFCOUNT_ FROM ("+sql+") NLFTABLE_";
    sql = buildParams(sql,param);
    this.sql = sql;
    Logger.getLog().debug(buildLog());
    List<Bean> l = queryList();
    if(l.size()<1){
      throw new DaoException(App.getProperty("nlf.exception.dao.select.one.not_found"));
    }
    Bean o = l.get(0);
    return o.getInt("NLFCOUNT_",0);
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
    String sql = buildSql();
    sql = "SELECT TOP "+(d.getPageNumber()*d.getPageSize())+sql.replaceFirst("SELECT","");
    sql = buildParams(sql,param);
    this.sql = sql;
    Logger.getLog().debug(buildLog());
    List<Bean> l = queryList((d.getPageNumber()-1)*d.getPageSize());
    d.setData(l);
    return d;
  }
}