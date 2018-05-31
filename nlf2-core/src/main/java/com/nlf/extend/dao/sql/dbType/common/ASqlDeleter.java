package com.nlf.extend.dao.sql.dbType.common;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.nlf.Bean;
import com.nlf.dao.exception.DaoException;
import com.nlf.extend.dao.sql.*;
import com.nlf.log.Logger;
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
    StringBuffer s = new StringBuffer();
    s.append("DELETE FROM ");
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
    return s.toString();
  }

  public int delete(){
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

}