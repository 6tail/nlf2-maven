package com.nlf.extend.dao.sql.dbType.common;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import com.nlf.App;
import com.nlf.Bean;
import com.nlf.core.IRequest;
import com.nlf.dao.exception.DaoException;
import com.nlf.dao.paging.PageData;
import com.nlf.extend.dao.sql.AbstractSqlExecuter;
import com.nlf.extend.dao.sql.Condition;
import com.nlf.extend.dao.sql.ConditionType;
import com.nlf.extend.dao.sql.ISqlSelecter;
import com.nlf.extend.dao.sql.ResultSetIterator;
import com.nlf.extend.dao.sql.SqlConnection;
import com.nlf.log.Logger;
import com.nlf.util.StringUtil;

/**
 * SQL查询器的默认实现
 * 
 * @author 6tail
 *
 */
public class ASqlSelecter extends AbstractSqlExecuter implements ISqlSelecter{
  public ISqlSelecter table(String tables){
    this.tables.add(tables);
    return this;
  }

  public ISqlSelecter tableIf(String tables,boolean condition){
    if(condition) table(tables);
    return this;
  }

  public ISqlSelecter column(String columns){
    this.columns.add(columns);
    return this;
  }

  public ISqlSelecter columnIf(String columns,boolean condition){
    if(condition) column(columns);
    return this;
  }

  public ISqlSelecter where(String sql){
    super.where(sql);
    return this;
  }

  public ISqlSelecter where(String column,Object value){
    super.where(column,value);
    return this;
  }

  public ISqlSelecter where(String sql,Bean param){
    super.where(sql,param);
    return this;
  }

  public ISqlSelecter whereIf(String sql,boolean condition){
    if(condition) where(sql);
    return this;
  }

  public ISqlSelecter whereIf(String column,Object value,boolean condition){
    if(condition) where(column,value);
    return this;
  }

  public ISqlSelecter whereIf(String sql,Bean param,boolean condition){
    if(condition) where(sql,param);
    return this;
  }

  public ISqlSelecter whereIn(String column,Object... values){
    super.whereIn(column,values);
    return this;
  }

  public ISqlSelecter whereNotIn(String column,Object... values){
    super.whereNotIn(column,values);
    return this;
  }

  public ISqlSelecter whereNotEqual(String column,Object value){
    super.whereNotEqual(column,value);
    return this;
  }

  public ISqlSelecter having(String sql){
    Condition cond = new Condition();
    cond.setColumn(sql);
    cond.setStart("");
    cond.setPlaceholder("");
    cond.setEnd("");
    cond.setType(ConditionType.pure_sql);
    havings.add(cond);
    return this;
  }

  public ISqlSelecter having(String column,Object value){
    Condition cond = new Condition();
    cond.setColumn(column);
    cond.setValue(value);
    havings.add(cond);
    return this;
  }

  public ISqlSelecter having(String sql,Bean param){
    Condition cond = new Condition();
    cond.setColumn(sql);
    cond.setStart("");
    cond.setPlaceholder("");
    cond.setEnd("");
    cond.setValue(param);
    cond.setType(ConditionType.multi_params);
    havings.add(cond);
    return this;
  }

  public ISqlSelecter havingIf(String sql,boolean condition){
    if(condition) having(sql);
    return this;
  }

  public ISqlSelecter havingIf(String column,Object value,boolean condition){
    if(condition) having(column,value);
    return this;
  }

  public ISqlSelecter havingIf(String sql,Bean param,boolean condition){
    if(condition) having(sql,param);
    return this;
  }

  public ISqlSelecter groupBy(String columns){
    groupBys.add(columns);
    return this;
  }

  public ISqlSelecter groupByIf(String columns,boolean condition){
    if(condition) groupBy(columns);
    return this;
  }

  public ISqlSelecter asc(String columns){
    String[] cols = columns.split(",",-1);
    for(String col:cols){
      if(col.length()>0){
        sorts.add(col+" ASC");
      }
    }
    return this;
  }

  public ISqlSelecter ascIf(String columns,boolean condition){
    if(condition) asc(columns);
    return this;
  }

  public ISqlSelecter desc(String columns){
    String[] cols = columns.split(",",-1);
    for(String col:cols){
      if(col.length()>0){
        sorts.add(col+" DESC");
      }
    }
    return this;
  }

  public ISqlSelecter descIf(String columns,boolean condition){
    if(condition) desc(columns);
    return this;
  }

  public String buildSql(){
    StringBuffer s = new StringBuffer();
    s.append("SELECT ");
    s.append(columns.size()<1?"*":StringUtil.join(columns,","));
    s.append(" FROM ");
    s.append(StringUtil.join(tables,","));
    for(int i = 0,l = wheres.size();i<l;i++){
      s.append(" ");
      s.append(i<1?"WHERE":"AND");
      s.append(" ");
      Condition r = wheres.get(i);
      switch(r.getType()){
        case one_param:
          params.add(r.getValue());
        case pure_sql:
          s.append(r.getColumn());
          s.append(r.getStart());
          s.append(r.getPlaceholder());
          s.append(r.getEnd());
          break;
        case multi_params:
          Bean o = (Bean)r.getValue();
          s.append(buildParams(r.getColumn(),o));
          s.append(buildParams(r.getStart(),o));
          s.append(buildParams(r.getPlaceholder(),o));
          s.append(buildParams(r.getEnd(),o));
          break;
      }
    }
    for(int i = 0,j = groupBys.size();i<j;i++){
      s.append(" ");
      s.append(i<1?"GROUP BY ":",");
      s.append(groupBys.get(i));
    }
    for(int i = 0,l = havings.size();i<l;i++){
      s.append(" ");
      s.append(i<1?"HAVING":"AND");
      s.append(" ");
      Condition r = havings.get(i);
      switch(r.getType()){
        case one_param:
          params.add(r.getValue());
        case pure_sql:
          s.append(r.getColumn());
          s.append(r.getStart());
          s.append(r.getPlaceholder());
          s.append(r.getEnd());
          break;
        case multi_params:
          Bean o = (Bean)r.getValue();
          s.append(buildParams(r.getColumn(),o));
          s.append(buildParams(r.getStart(),o));
          s.append(buildParams(r.getPlaceholder(),o));
          s.append(buildParams(r.getEnd(),o));
          break;
      }
    }
    for(int i = 0,j = sorts.size();i<j;i++){
      s.append(" ");
      s.append(i<1?"ORDER BY ":",");
      s.append(sorts.get(i));
    }
    return s.toString();
  }

  public List<Bean> query(){
    params.clear();
    sql = buildSql();
    Logger.getLog().debug(buildLog());
    PreparedStatement stmt = null;
    ResultSet rs = null;
    try{
      stmt = ((SqlConnection)connection).getConnection().prepareStatement(sql);
      bindParams(stmt);
      rs = stmt.executeQuery();
      return toBeans(rs);
    }catch(SQLException e){
      throw new DaoException(e);
    }finally{
      finalize(stmt,rs);
    }
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
    params.clear();
    sql = buildSql();
    sql = "SELECT COUNT(*) NLFCOUNT_ FROM ("+sql+") NLFTABLE_";
    Logger.getLog().debug(buildLog());
    PreparedStatement stmt = null;
    ResultSet rs = null;
    List<Bean> l = null;
    try{
      stmt = ((SqlConnection)connection).getConnection().prepareStatement(sql);
      bindParams(stmt);
      rs = stmt.executeQuery();
      l = toBeans(rs);
    }catch(SQLException e){
      throw new DaoException(e);
    }finally{
      finalize(stmt,rs);
    }
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

  public Iterator<Bean> iterator(){
    params.clear();
    sql = buildSql();
    Logger.getLog().debug(buildLog());
    Iterator<Bean> iterator = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    try{
      stmt = ((SqlConnection)connection).getConnection().prepareStatement(sql);
      bindParams(stmt);
      rs = stmt.executeQuery();
      iterator = new ResultSetIterator(rs);
    }catch(SQLException e){
      finalize(stmt,rs);
      throw new DaoException(e);
    }
    return iterator;
  }
}