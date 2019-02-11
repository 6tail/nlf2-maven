package com.nlf.extend.dao.sql.dbType.common;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import com.nlf.App;
import com.nlf.Bean;
import com.nlf.core.IRequest;
import com.nlf.dao.exception.DaoException;
import com.nlf.dao.paging.PageData;
import com.nlf.extend.dao.sql.AbstractSqlExecuter;
import com.nlf.extend.dao.sql.ISqlTemplate;
import com.nlf.extend.dao.sql.SqlConnection;
import com.nlf.log.Logger;

/**
 * SQL模板的默认实现
 * 
 * @author 6tail
 *
 */
public class ASqlTemplate extends AbstractSqlExecuter implements ISqlTemplate{
  protected List<String> sqls = new ArrayList<String>();
  protected Bean param = new Bean();

  public ISqlTemplate sql(String sql){
    sqls.add(sql);
    return this;
  }

  public ISqlTemplate sqlIf(String sql,boolean condition){
    if(condition) sql(sql);
    return this;
  }

  public ISqlTemplate param(Bean param){
    for(Entry<String,Object> en:param.entrySet()){
      param(en.getKey(),en.getValue());
    }
    return this;
  }

  public ISqlTemplate param(String key,Object value){
    param.set(key,value);
    return this;
  }

  protected String buildSql(){
    StringBuilder s = new StringBuilder();
    for(String sql:sqls){
      s.append(sql);
    }
    return s.toString();
  }

  protected List<Bean> query(String sql){
    params.clear();
    sql = buildParams(sql,param);
    this.sql = sql;
    Logger.getLog().debug(buildLog());
    return queryList();
  }

  public List<Bean> query(){
    return query(buildSql());
  }

  public List<Bean> top(int count){
    throw new DaoException(App.getProperty("nlf.exception.dao.operation_not_support"));
  }

  public Bean topOne(){
    List<Bean> l = top(1);
    if(l.size()<1){
      throw new DaoException(App.getProperty("nlf.exception.dao.select.one.not_found"));
    }
    return l.get(0);
  }

  public Bean one(){
    List<Bean> l = query();
    if(l.size()<1){
      throw new DaoException(App.getProperty("nlf.exception.dao.select.one.not_found"));
    }
    return l.get(0);
  }

  public int count(){
    List<Bean> l = query("SELECT COUNT(*) NLFCOUNT_ FROM ("+buildSql()+") NLFTABLE_");
    if(l.size()<1){
      throw new DaoException(App.getProperty("nlf.exception.dao.select.one.not_found"));
    }
    Bean o = l.get(0);
    return o.getInt("NLFCOUNT_",0);
  }

  public PageData page(int pageNumber,int pageSize){
    throw new DaoException(App.getProperty("nlf.exception.dao.operation_not_support"));
  }

  public PageData paging(){
    IRequest r = App.getRequest();
    return page(r.getPageNumber(),r.getPageSize());
  }

  public int update(){
    params.clear();
    sql = buildParams(buildSql(),param);
    Logger.getLog().debug(buildLog());
    PreparedStatement stmt = null;
    try{
      SqlConnection conn = ((SqlConnection)connection);
      stmt = conn.getConnection().prepareStatement(sql);
      conn.setStatement(stmt);
      bindParams(stmt);
      if(conn.isInBatch()){
        stmt.addBatch();
        return -1;
      }
      return stmt.executeUpdate();
    }catch(SQLException e){
      throw new DaoException(e);
    }finally{
      finalize(stmt);
    }
  }

  public Iterator<Bean> iterator(){
    params.clear();
    sql = buildParams(buildSql(),param);
    Logger.getLog().debug(buildLog());
    return queryIterator();
  }
}