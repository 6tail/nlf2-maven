package com.nlf.extend.dao.sql.dbType.common;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.nlf.Bean;
import com.nlf.dao.exception.DaoException;
import com.nlf.extend.dao.sql.AbstractSqlExecuter;
import com.nlf.extend.dao.sql.Condition;
import com.nlf.extend.dao.sql.ConditionType;
import com.nlf.extend.dao.sql.ISqlInserter;
import com.nlf.extend.dao.sql.SqlConnection;
import com.nlf.log.Logger;
import com.nlf.util.StringUtil;

/**
 * SQL插入器的默认实现
 * 
 * @author 6tail
 *
 */
public class ASqlInserter extends AbstractSqlExecuter implements ISqlInserter{
  protected List<Condition> columns = new ArrayList<Condition>();

  public ISqlInserter table(String tables){
    this.tables.add(tables);
    return this;
  }

  public ISqlInserter tableIf(String tables,boolean condition){
    if(condition) table(tables);
    return this;
  }

  public String buildSql(){
    StringBuffer s = new StringBuffer();
    s.append("INSERT INTO ");
    s.append(StringUtil.join(tables,","));
    s.append("(");
    for(int i = 0,l = columns.size();i<l;i++){
      s.append(i<1?"":",");
      Condition r = columns.get(i);
      s.append(r.getColumn());
    }
    s.append(") VALUES(");
    for(int i = 0,j = columns.size();i<j;i++){
      s.append(i<1?"":",");
      Condition r = columns.get(i);
      switch(r.getType()){
        case one_param:
          params.add(r.getValue());
        case pure_sql:
          s.append(r.getStart());
          s.append(r.getPlaceholder());
          s.append(r.getEnd());
          break;
        case multi_params:
          Bean o = (Bean)r.getValue();
          s.append(buildParams(r.getStart(),o));
          s.append(buildParams(r.getPlaceholder(),o));
          s.append(buildParams(r.getEnd(),o));
          break;
      }
    }
    s.append(")");
    return s.toString();
  }

  public int insert(){
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

  public ISqlInserter set(String sql){
    Condition cond = new Condition();
    cond.setColumn(sql);
    cond.setStart("");
    cond.setPlaceholder("");
    cond.setType(ConditionType.pure_sql);
    columns.add(cond);
    return this;
  }

  public ISqlInserter set(String column,Object value){
    Condition cond = new Condition();
    cond.setColumn(column);
    cond.setStart("");
    cond.setValue(value);
    columns.add(cond);
    return this;
  }

  public ISqlInserter set(String sql,Bean param){
    Condition cond = new Condition();
    cond.setColumn(sql);
    cond.setStart("");
    cond.setValue(param);
    if(null!=param){
      cond.setType(ConditionType.multi_params);
      cond.setPlaceholder("");
    }
    columns.add(cond);
    return this;
  }

  public ISqlInserter set(Bean bean){
    for(String key:bean.keySet()){
      set(key,bean.get(key));
    }
    return this;
  }

  public ISqlInserter setIf(String sql,boolean condition){
    if(condition) set(sql);
    return this;
  }

  public ISqlInserter setIf(String column,Object value,boolean condition){
    if(condition) set(column,value);
    return this;
  }

  public ISqlInserter setIf(String sql,Bean param,boolean condition){
    if(condition) set(sql,param);
    return this;
  }

  public ISqlInserter setIf(Bean bean,boolean condition){
    if(condition) set(bean);
    return this;
  }
}