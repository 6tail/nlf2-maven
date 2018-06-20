package com.nlf.extend.dao.sql.dbType.common;

import com.nlf.extend.dao.sql.*;
import com.nlf.util.StringUtil;

/**
 * SQL删除器的默认实现
 * 
 * @author 6tail
 *
 */
public class ASqlDeleter extends AbstractSqlExecuter implements ISqlDeleter{
  public ISqlDeleter table(String tables){
    this.tables.add(tables);
    return this;
  }

  public ISqlDeleter tableIf(String tables,boolean condition){
    if(condition) table(tables);
    return this;
  }

  public ISqlDeleter where(String sql){
    super.where(sql);
    return this;
  }

  public ISqlDeleter where(String columnOrSql,Object valueOrBean){
    super.where(columnOrSql,valueOrBean);
    return this;
  }

  public ISqlDeleter whereIf(String sql,boolean condition){
    if(condition) where(sql);
    return this;
  }

  public ISqlDeleter whereIf(String columnOrSql,Object valueOrBean,boolean condition){
    if(condition) where(columnOrSql,valueOrBean);
    return this;
  }

  public ISqlDeleter whereIn(String column,Object... values){
    super.whereIn(column,values);
    return this;
  }

  public ISqlDeleter whereNotIn(String column,Object... values){
    super.whereNotIn(column,values);
    return this;
  }

  public ISqlDeleter whereNotEqual(String column, Object value){
    super.whereNotEqual(column,value);
    return this;
  }

  public String buildSql(){
    return "DELETE FROM "+StringUtil.join(tables,",")+buildSqlWhere();
  }

  public int delete(){
    return executeUpdate();
  }

}