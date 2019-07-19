package com.nlf.extend.dao.sql.dbType.common;

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
    if(condition){
      table(tables);
    }
    return this;
  }

  public ISqlSelecter column(String columns){
    this.columns.add(columns);
    return this;
  }

  public ISqlSelecter columnIf(String columns,boolean condition){
    if(condition){
      column(columns);
    }
    return this;
  }

  @Override
  public ISqlSelecter where(String sql){
    super.where(sql);
    return this;
  }

  @Override
  public ISqlSelecter where(String columnOrSql, Object valueOrBean){
    super.where(columnOrSql,valueOrBean);
    return this;
  }

  @Override
  public ISqlSelecter whereIf(String sql, boolean condition){
    if(condition){
      where(sql);
    }
    return this;
  }

  @Override
  public ISqlSelecter whereIf(String columnOrSql, Object valueOrBean, boolean condition){
    if(condition){
      where(columnOrSql,valueOrBean);
    }
    return this;
  }

  @Override
  public ISqlSelecter whereIn(String column, Object... values){
    super.whereIn(column,values);
    return this;
  }

  @Override
  public ISqlSelecter whereNotIn(String column, Object... values){
    super.whereNotIn(column,values);
    return this;
  }

  @Override
  public ISqlSelecter whereNotEqual(String column, Object value){
    super.whereNotEqual(column,value);
    return this;
  }

  public ISqlSelecter having(String sql){
    havings.add(buildPureSqlCondition(sql));
    return this;
  }

  public ISqlSelecter having(String columnOrSql,Object valueOrBean){
    Condition cond = new Condition();
    cond.setColumn(columnOrSql);
    if(null==valueOrBean){
      cond.setStart(" IS");
      cond.setPlaceholder(" NULL");
      cond.setType(ConditionType.pure_sql);
    }else{
      if(columnOrSql.contains(NAMED_PLACEHOLDER_PREFIX)&&valueOrBean instanceof Bean){
        cond.setStart("");
        cond.setPlaceholder("");
        cond.setEnd("");
        cond.setValue(valueOrBean);
        cond.setType(ConditionType.multi_params);
      }else{
        cond.setValue(valueOrBean);
      }
    }
    havings.add(cond);
    return this;
  }

  public ISqlSelecter havingIf(String sql,boolean condition){
    if(condition){
      having(sql);
    }
    return this;
  }

  public ISqlSelecter havingIf(String columnOrSql,Object valueOrBean,boolean condition){
    if(condition){
      having(columnOrSql,valueOrBean);
    }
    return this;
  }

  public ISqlSelecter groupBy(String columns){
    groupBys.add(columns);
    return this;
  }

  public ISqlSelecter groupByIf(String columns,boolean condition){
    if(condition){
      groupBy(columns);
    }
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
    if(condition){
      asc(columns);
    }
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
    if(condition){
      desc(columns);
    }
    return this;
  }

  @Override
  public String buildSql(){
    StringBuilder s = new StringBuilder();
    s.append("SELECT ");
    s.append(columns.size()<1?"*":StringUtil.join(columns,","));
    s.append(" FROM ");
    s.append(StringUtil.join(tables,","));
    s.append(buildSqlWhere());
    for(int i = 0,j = groupBys.size();i<j;i++){
      s.append(" ");
      s.append(i<1?"GROUP BY ":",");
      s.append(groupBys.get(i));
    }
    for(int i = 0,l = havings.size();i<l;i++){
      s.append(" ");
      s.append(i<1?"HAVING":"AND");
      s.append(" ");
      s.append(buildSqlParams(havings.get(i)));
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
    return queryList();
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
    List<Bean> l = queryList();
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
    return queryIterator();
  }
}