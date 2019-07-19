package com.nlf.extend.dao.sql.dbType.common;

import java.util.ArrayList;
import java.util.List;
import com.nlf.Bean;
import com.nlf.extend.dao.sql.*;
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
    if(condition){
      table(tables);
    }
    return this;
  }

  @Override
  public String buildSql(){
    StringBuilder s = new StringBuilder();
    s.append("UPDATE ");
    s.append(StringUtil.join(tables,","));
    s.append(" SET ");
    for(int i=0,j=columns.size();i<j;i++){
      s.append(i<1?"":",");
      s.append(buildSqlParams(columns.get(i)));
    }
    s.append(buildSqlWhere());
    return s.toString();
  }

  public int update(){
    return executeUpdate();
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
    if (null!=valueOrBean && columnOrSql.contains(NAMED_PLACEHOLDER_PREFIX) && valueOrBean instanceof Bean) {
      cond.setType(ConditionType.multi_params);
      cond.setStart("");
      cond.setPlaceholder("");
    } else {
      cond.setType(ConditionType.one_param);
      cond.setStart("=");
      cond.setPlaceholder(PLACEHOLDER);
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
    if(condition){
      set(sql);
    }
    return this;
  }

  public ISqlUpdater setIf(String columnOrSql,Object valueOrBean,boolean condition){
    if(condition){
      set(columnOrSql,valueOrBean);
    }
    return this;
  }

  public ISqlUpdater setIf(Bean bean,boolean condition){
    if(condition){
      set(bean);
    }
    return this;
  }

  @Override
  public ISqlUpdater where(String sql){
    super.where(sql);
    return this;
  }

  @Override
  public ISqlUpdater where(String columnOrSql, Object valueOrBean){
    super.where(columnOrSql,valueOrBean);
    return this;
  }

  @Override
  public ISqlUpdater whereIf(String sql, boolean condition){
    if(condition){
      where(sql);
    }
    return this;
  }

  @Override
  public ISqlUpdater whereIf(String columnOrSql, Object valueOrBean, boolean condition){
    if(condition){
      where(columnOrSql,valueOrBean);
    }
    return this;
  }

  @Override
  public ISqlUpdater whereIn(String column, Object... values){
    super.whereIn(column,values);
    return this;
  }

  @Override
  public ISqlUpdater whereNotIn(String column, Object... values){
    super.whereNotIn(column,values);
    return this;
  }

  @Override
  public ISqlUpdater whereNotEqual(String column, Object value){
    super.whereNotEqual(column,value);
    return this;
  }
}