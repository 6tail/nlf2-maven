package com.nlf.extend.dao.sql.dbType.common;

import com.nlf.Bean;
import com.nlf.extend.dao.sql.AbstractSqlExecuter;
import com.nlf.extend.dao.sql.Condition;
import com.nlf.extend.dao.sql.ISqlInserter;
import com.nlf.util.StringUtil;

/**
 * SQL插入器的默认实现
 *
 * @author 6tail
 *
 */
public class ASqlInserter extends AbstractSqlExecuter implements ISqlInserter{

  public ISqlInserter table(String tables){
    this.tables.add(tables);
    return this;
  }

  public ISqlInserter tableIf(String tables,boolean condition){
    if(condition){
      table(tables);
    }
    return this;
  }

  @Override
  public String buildSql(){
    StringBuilder s = new StringBuilder();
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
      params.add(r.getValue());
      s.append(r.getStart());
      s.append(r.getPlaceholder());
      s.append(r.getEnd());
    }
    s.append(")");
    return s.toString();
  }

  public int insert(){
    return executeUpdate();
  }

  public Bean insertAndGetGenerated() {
    return executeUpdateAndGetGenerated();
  }

  public ISqlInserter set(String column, Object value){
    Condition cond = new Condition();
    cond.setColumn(column);
    cond.setValue(value);
    cond.setStart("");
    columns.add(cond);
    return this;
  }

  public ISqlInserter set(Bean bean){
    for(String key:bean.keySet()){
      set(key,bean.get(key));
    }
    return this;
  }

  public ISqlInserter setIf(String column,Object value,boolean condition){
    if(condition){
      set(column,value);
    }
    return this;
  }

  public ISqlInserter setIf(Bean bean,boolean condition){
    if(condition){
      set(bean);
    }
    return this;
  }
}
