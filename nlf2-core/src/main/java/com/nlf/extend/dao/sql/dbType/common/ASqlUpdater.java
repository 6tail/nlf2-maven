package com.nlf.extend.dao.sql.dbType.common;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.nlf.Bean;
import com.nlf.dao.exception.DaoException;
import com.nlf.extend.dao.sql.*;
import com.nlf.log.Logger;
import com.nlf.util.StringUtil;

/**
 * SQL更新器的默认实现
 * 
 * @author 6tail
 *
 */
public class ASqlUpdater extends AbstractSqlExecuter implements ISqlUpdater{
  protected List<Condition> columns = new ArrayList<Condition>();
  
  public ISqlUpdater table(String tables){
    this.tables.add(tables);
    return this;
  }

  public ISqlUpdater tableIf(String tables,boolean condition){
    if(condition) table(tables);
    return this;
  }

  public String buildSql(){
    StringBuilder s = new StringBuilder();
    s.append("UPDATE ");
    s.append(StringUtil.join(tables,","));
    s.append(" SET ");
    for(int i=0,j=columns.size();i<j;i++){
      s.append(i<1?"":",");
      Condition r = columns.get(i);
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
    return s.toString();
  }

  public int update(){
    params.clear();
    sql = buildSql();
    Logger.getLog().debug(buildLog());
    PreparedStatement stmt = null;
    SqlConnection conn = null;
    try{
      conn = ((SqlConnection)connection);
      if(conn.isInBatch()){
        stmt = conn.getStatement();
        if(null==stmt){
          stmt = conn.getConnection().prepareStatement(sql);
          conn.setStatement(stmt);
        }
      }else{
        stmt = conn.getConnection().prepareStatement(sql);
      }
      bindParams(stmt);
      if(conn.isInBatch()){
        stmt.addBatch();
        return -1;
      }
      return stmt.executeUpdate();
    }catch(SQLException e){
      throw new DaoException(e);
    }finally{
      if(!conn.isInBatch()){
        finalize(stmt);
      }
    }
  }

  public ISqlUpdater set(String sql){
    Condition cond = new Condition();
    cond.setType(ConditionType.pure_sql);
    cond.setColumn(sql);
    cond.setStart("");
    cond.setPlaceholder("");
    cond.setEnd("");
    columns.add(cond);
    return this;
  }

  public ISqlUpdater set(String columnOrSql,Object valueOrBean){
    Condition cond = new Condition();
    cond.setColumn(columnOrSql);
    cond.setValue(valueOrBean);
    cond.setEnd("");
    if (null!=valueOrBean && columnOrSql.contains(":") && valueOrBean instanceof Bean) {
      cond.setType(ConditionType.multi_params);
      cond.setStart("");
      cond.setPlaceholder("");
    } else {
      cond.setType(ConditionType.one_param);
      cond.setStart("=");
      cond.setPlaceholder("?");
    }
    columns.add(cond);
    return this;
  }

  public ISqlUpdater set(Bean bean){
    for(String key:bean.keySet()){
      set(key,bean.get(key));
    }
    return this;
  }

  public ISqlUpdater setIf(String sql,boolean condition){
    if(condition) set(sql);
    return this;
  }

  public ISqlUpdater setIf(String columnOrSql,Object valueOrBean,boolean condition){
    if(condition) set(columnOrSql,valueOrBean);
    return this;
  }

  public ISqlUpdater setIf(Bean bean,boolean condition){
    if(condition) set(bean);
    return this;
  }

  public ISqlUpdater where(String sql){
    super.where(sql);
    return this;
  }

  public ISqlUpdater where(String columnOrSql,Object valueOrBean){
    super.where(columnOrSql,valueOrBean);
    return this;
  }

  public ISqlUpdater whereIf(String sql,boolean condition){
    if(condition) where(sql);
    return this;
  }

  public ISqlUpdater whereIf(String columnOrSql,Object valueOrBean,boolean condition){
    if(condition) where(columnOrSql,valueOrBean);
    return this;
  }

  public ISqlUpdater whereIn(String column,Object... values){
    super.whereIn(column,values);
    return this;
  }

  public ISqlUpdater whereNotIn(String column,Object... values){
    super.whereNotIn(column,values);
    return this;
  }

  public ISqlUpdater whereNotEqual(String column, Object value){
    super.whereNotEqual(column,value);
    return this;
  }
}