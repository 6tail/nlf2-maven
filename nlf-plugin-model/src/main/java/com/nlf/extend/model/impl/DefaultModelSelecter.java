package com.nlf.extend.model.impl;

import com.nlf.App;
import com.nlf.Bean;
import com.nlf.core.IRequest;
import com.nlf.dao.exception.DaoException;
import com.nlf.dao.paging.PageData;
import com.nlf.dao.transaction.ITransaction;
import com.nlf.extend.dao.sql.ISqlSelecter;
import com.nlf.extend.dao.sql.SqlConnection;
import com.nlf.extend.dao.sql.SqlDaoFactory;
import com.nlf.extend.model.IModelSelecter;
import com.nlf.extend.model.Model;
import com.nlf.extend.model.ModelIterator;
import com.nlf.extend.model.paging.ModelPage;
import com.nlf.util.IOUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 默认Model查询器
 * @param <M> Model
 * @author 6tail
 */
public class DefaultModelSelecter<M extends Model> implements IModelSelecter<M>{

  protected Model model;
  private ISqlSelecter selecter;

  public DefaultModelSelecter(Model model){
    this.model = model;
    selecter = SqlDaoFactory.getDao().getSelecter().table(model.tableName());
  }

  public IModelSelecter<M> column(String columns) {
    selecter.column(columns);
    return this;
  }

  public IModelSelecter<M> columnIf(String columns, boolean condition) {
    selecter.columnIf(columns,condition);
    return this;
  }

  public IModelSelecter<M> where(String sql) {
    selecter.where(sql);
    return this;
  }

  public IModelSelecter<M> where(String columnOrSql, Object valueOrBean) {
    selecter.where(columnOrSql,valueOrBean);
    return this;
  }

  public IModelSelecter<M> whereIf(String sql, boolean condition) {
    selecter.whereIf(sql,condition);
    return this;
  }

  public IModelSelecter<M> whereIf(String columnOrSql, Object valueOrBean, boolean condition) {
    selecter.whereIf(columnOrSql,valueOrBean,condition);
    return this;
  }

  public IModelSelecter<M> whereIn(String column, Object... values) {
    selecter.whereIn(column,values);
    return this;
  }

  public IModelSelecter<M> whereNotIn(String column, Object... values) {
    selecter.whereNotIn(column,values);
    return this;
  }

  public IModelSelecter<M> whereNotEqual(String column, Object value) {
    selecter.whereNotEqual(column,value);
    return this;
  }

  public IModelSelecter<M> groupBy(String columns) {
    selecter.groupBy(columns);
    return this;
  }

  public IModelSelecter<M> groupByIf(String columns, boolean condition) {
    selecter.groupByIf(columns,condition);
    return this;
  }

  public IModelSelecter<M> having(String sql) {
    selecter.having(sql);
    return this;
  }

  public IModelSelecter<M> having(String columnOrSql, Object valueOrBean) {
    selecter.having(columnOrSql,valueOrBean);
    return this;
  }

  public IModelSelecter<M> havingIf(String sql, boolean condition) {
    selecter.havingIf(sql,condition);
    return this;
  }

  public IModelSelecter<M> havingIf(String columnOrSql, Object valueOrBean, boolean condition) {
    selecter.havingIf(columnOrSql,valueOrBean,condition);
    return this;
  }

  public IModelSelecter<M> asc(String columns) {
    selecter.asc(columns);
    return this;
  }

  public IModelSelecter<M> ascIf(String columns, boolean condition) {
    selecter.ascIf(columns,condition);
    return this;
  }

  public IModelSelecter<M> desc(String columns) {
    selecter.desc(columns);
    return this;
  }

  public IModelSelecter<M> descIf(String columns, boolean condition) {
    selecter.descIf(columns,condition);
    return this;
  }

  public List<M> top(int count) {
    return null;
  }

  public M topOne() {
    List<M> l = top(1);
    if(l.size()<1){
      throw new DaoException(App.getProperty("nlf.exception.dao.select.one.not_found"));
    }
    return l.get(0);
  }

  public M one() {
    List<M> l = query();
    if(l.size()<1){
      throw new DaoException(App.getProperty("nlf.exception.dao.select.one.not_found"));
    }
    return l.get(0);
  }

  public int count() {
    return selecter.count();
  }

  @SuppressWarnings("unchecked")
  public ModelPage<M> page(int pageNumber, int pageSize) {
    PageData pd = selecter.page(pageNumber,pageSize);
    List<M> l = new ArrayList<M>(pd.getSize());
    for(Bean o:pd){
      try {
        l.add((M)o.toObject(model.getClass()));
      } catch (Exception e) {
        throw new DaoException(e);
      }
    }
    return new ModelPage<M>(l,pd.getPageSize(),pd.getPageNumber(),pd.getRecordCount());
  }

  public ModelPage<M> paging() {
    IRequest r = App.getRequest();
    return page(r.getPageNumber(),r.getPageSize());
  }

  protected void bindParams(PreparedStatement stmt) throws SQLException{
    for(int i = 1,j = selecter.getParams().size();i<=j;i++){
      Object p = selecter.getParams().get(i-1);
      if(p instanceof Timestamp){
        stmt.setTimestamp(i,(Timestamp)p);
      }else if(p instanceof Date){
        stmt.setDate(i,(Date)p);
      }else if(p instanceof java.util.Date){
        java.util.Date dd = (java.util.Date)p;
        stmt.setDate(i,new Date(dd.getTime()));
      }else{
        stmt.setObject(i,p);
      }
    }
  }

  @SuppressWarnings("unchecked")
  public Iterator<M> iterator() {
    ITransaction t = SqlDaoFactory.getDao().beginTransaction();
    SqlConnection connection = (SqlConnection)t.getConnection();
    Iterator<M> iterator = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    try{
      stmt = connection.getConnection().prepareStatement(selecter.getSql());
      bindParams(stmt);
      rs = stmt.executeQuery();
      iterator = new ModelIterator(model.getClass(),rs);
    }catch(SQLException e){
      IOUtil.closeQuietly(stmt);
      throw new DaoException(e);
    }
    return iterator;
  }

  @SuppressWarnings("unchecked")
  public List<M> query() {
    List<Bean> l = selecter.query();
    List<M> ret = new ArrayList<M>(l.size());
    for(Bean o:l){
      try {
        Model m = o.toObject(model.getClass());
        ret.add((M)m);
      } catch (Exception ignore) {
      }
    }
    return ret;
  }

}
