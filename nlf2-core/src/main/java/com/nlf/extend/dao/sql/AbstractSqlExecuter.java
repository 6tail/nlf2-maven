package com.nlf.extend.dao.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.nlf.App;
import com.nlf.Bean;
import com.nlf.dao.executer.AbstractDaoExecuter;
import com.nlf.util.IOUtil;

public abstract class AbstractSqlExecuter extends AbstractDaoExecuter implements ISqlExecuter{
  /** 最近一次操作生成的SQL语句 */
  protected String sql;
  /** 最近一次操作的参数列表 */
  protected List<Object> params = new ArrayList<Object>();
  protected List<String> tables = new ArrayList<String>();
  protected List<String> columns = new ArrayList<String>();
  protected List<String> groupBys = new ArrayList<String>();
  protected List<String> sorts = new ArrayList<String>();
  protected List<Condition> wheres = new ArrayList<Condition>();
  protected List<Condition> havings = new ArrayList<Condition>();

  public boolean support(String dbType){
    return true;
  }

  /**
   * 构建日志内容
   * @return 日志内容
   */
  protected String buildLog(){
    StringBuilder s = new StringBuilder();
    s.append(App.getProperty("nlf.dao.executer.sql.statement",sql));
    for(int i = 0,j = params.size();i<j;i++){
      s.append(App.getProperty("nlf.dao.executer.sql.parameter",i,params.get(i)));
    }
    return s.toString();
  }

  protected List<Bean> toBeans(ResultSet rs) throws SQLException{
    List<Bean> l = new ArrayList<Bean>();
    while(rs.next()){
      ResultSetMetaData rsmd = rs.getMetaData();
      Bean o = new Bean();
      for(int i = 1,j = rsmd.getColumnCount();i<=j;i++){
        o.set(rsmd.getColumnLabel(i),rs.getObject(i));
      }
      l.add(o);
    }
    return l;
  }

  protected void finalize(Statement stmt,ResultSet rs){
    IOUtil.closeQuietly(rs);
    IOUtil.closeQuietly(stmt);
    rs = null;
    stmt = null;
  }

  protected void finalize(Statement stmt){
    finalize(stmt,null);
  }

  protected void bindParams(PreparedStatement stmt) throws SQLException{
    for(int i = 1,j = params.size();i<=j;i++){
      Object p = params.get(i-1);
      if(p instanceof java.sql.Timestamp){
        stmt.setTimestamp(i,(java.sql.Timestamp)p);
      }else if(p instanceof java.sql.Date){
        stmt.setDate(i,(java.sql.Date)p);
      }else if(p instanceof java.util.Date){
        java.util.Date dd = (java.util.Date)p;
        stmt.setDate(i,new java.sql.Date(dd.getTime()));
      }else{
        stmt.setObject(i,p);
      }
    }
  }

  protected String buildParams(String sql,Bean o){
    if(null==o) return sql;
    if(sql.length()<1) return sql;
    if(!sql.contains(":")) return sql;
    List<String> keys = new ArrayList<String>();
    //匹配以冒号开头的字母或下划线组合，识别为变量名
    Matcher m = Pattern.compile(":{1}\\w+").matcher(sql);
    while(m.find()){
      String key = m.group();
      if(!keys.contains(key)){
        keys.add(key);
      }
      params.add(o.get(key.substring(1)));
    }
    //排序，让更长的变量名先替换，如果存在包含关系的变量名，先替换短的会出问题，如:name、:names
    Collections.sort(keys,new Comparator<String>(){
      public int compare(String a,String b){
        return b.length()-a.length();
      }
    });
    //将变量替换为占位符
    String newSql = sql;
    for(String key:keys){
      newSql = newSql.replace(key,"?");
    }
    return newSql;
  }

  protected void buildParams(List<Condition> l){
    for(Condition c:l){
      switch(c.getType()){
        case one_param:
          params.add(c.getValue());
          break;
        case pure_sql:
          break;
        case multi_params:
          Bean o = (Bean)c.getValue();
          buildParams(c.getColumn(),o);
          buildParams(c.getStart(),o);
          buildParams(c.getPlaceholder(),o);
          buildParams(c.getEnd(),o);
          break;
      }
    }
  }

  public String getSql(){
    return sql;
  }

  public List<Object> getParams(){
    return params;
  }

  protected ISqlExecuter where(String sql){
    Condition cond = new Condition();
    cond.setColumn(sql);
    cond.setStart("");
    cond.setPlaceholder("");
    cond.setEnd("");
    cond.setType(ConditionType.pure_sql);
    wheres.add(cond);
    return this;
  }

  protected ISqlExecuter where(String columnOrSql,Object valueOrBean){
    Condition cond = new Condition();
    cond.setColumn(columnOrSql);
    if(null==valueOrBean){
      cond.setStart(" IS");
      cond.setPlaceholder(" NULL");
      cond.setType(ConditionType.pure_sql);
    }else{
      if(columnOrSql.contains(":")&&valueOrBean instanceof Bean){
        cond.setStart("");
        cond.setPlaceholder("");
        cond.setEnd("");
        cond.setValue(valueOrBean);
        cond.setType(ConditionType.multi_params);
      }else{
        cond.setValue(valueOrBean);
      }
    }
    wheres.add(cond);
    return this;
  }

  protected ISqlExecuter whereNotEqual(String column,Object value){
    Condition cond = new Condition();
    cond.setColumn(column);
    if(null==value){
      cond.setStart(" IS NOT");
      cond.setPlaceholder(" NULL");
      cond.setType(ConditionType.pure_sql);
    }else{
      cond.setStart("!=");
      cond.setValue(value);
    }
    wheres.add(cond);
    return this;
  }

  protected ISqlExecuter whereIf(String sql,boolean condition){
    if(condition) where(sql);
    return this;
  }

  protected ISqlExecuter whereIf(String column,Object value,boolean condition){
    if(condition) where(column,value);
    return this;
  }

  protected ISqlExecuter whereIf(String sql,Bean values,boolean condition){
    if(condition) where(sql,values);
    return this;
  }

  protected ISqlExecuter whereIn(String column,Object... values){
    if(1==values.length){
      return where(column,values[0]);
    }
    Condition cond = new Condition();
    cond.setColumn(column);
    cond.setStart(" IN(");
    Bean param = new Bean();
    StringBuilder placeholder = new StringBuilder();
    int i=0;
    for(Object v:values){
      if(i>0){
        placeholder.append(",");
      }
      String key = column.replaceAll("\\W","")+"_"+i;
      placeholder.append(":"+key);
      param.set(key,v);
      i++;
    }
    cond.setPlaceholder(placeholder.toString());
    cond.setEnd(")");
    cond.setValue(param);
    cond.setType(ConditionType.multi_params);
    wheres.add(cond);
    return this;
  }

  protected ISqlExecuter whereNotIn(String column,Object... values){
    if(1==values.length){
      Object v = values[0];
      if(null==v){
        return where(column+" IS NOT NULL");
      }else{
        return where(column+" != :"+column,new Bean(column,v));
      }
    }
    Condition cond = new Condition();
    cond.setColumn(column);
    cond.setStart(" NOT IN(");
    Bean param = new Bean();
    StringBuilder placeholder = new StringBuilder();
    int i=0;
    for(Object v:values){
      if(i>0){
        placeholder.append(",");
      }
      String key = column.replaceAll("\\W","")+"_"+i;
      placeholder.append(":"+key);
      param.set(key,v);
      i++;
    }
    cond.setPlaceholder(placeholder.toString());
    cond.setEnd(")");
    cond.setValue(param);
    cond.setType(ConditionType.multi_params);
    wheres.add(cond);
    return this;
  }
}