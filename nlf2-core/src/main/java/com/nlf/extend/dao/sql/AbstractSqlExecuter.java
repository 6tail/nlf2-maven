package com.nlf.extend.dao.sql;

import com.nlf.App;
import com.nlf.Bean;
import com.nlf.dao.exception.DaoException;
import com.nlf.dao.executer.AbstractDaoExecuter;
import com.nlf.log.Logger;
import com.nlf.util.IOUtil;

import java.sql.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 抽象Sql Dao执行器
 *
 * @author 6tail
 */
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
  /** 带名称的变量占位符 */
  protected Pattern namedPlaceHolderPattern = Pattern.compile(":\\w+");

  /** 变量占位符 */
  public static final String PLACEHOLDER = "?";
  /** 变量占位符正则 */
  public static final String PLACEHOLDER_REG = "\\?";
  /** 带名称的变量占位符 */
  public static final String NAMED_PLACEHOLDER_PREFIX = ":";

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

  protected List<Bean> queryList(){
    return queryList(0);
  }

  protected List<Bean> queryList(int row){
    PreparedStatement stmt = null;
    ResultSet rs = null;
    try{
      stmt = ((SqlConnection)connection).getConnection().prepareStatement(sql);
      bindParams(stmt);
      rs = stmt.executeQuery();
      if(row>0){
        rs.absolute(row);
      }
      return toBeans(rs);
    }catch(SQLException e){
      throw new DaoException(e);
    }finally{
      finalize(stmt,rs);
    }
  }

  public Iterator<Bean> queryIterator(){
    Iterator<Bean> iterator = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    try{
      stmt = ((SqlConnection)connection).getConnection().prepareStatement(sql);
      bindParams(stmt);
      rs = stmt.executeQuery();
      iterator = new ResultSetIterator(rs);
    }catch(SQLException e){
      finalize(stmt);
      throw new DaoException(e);
    }
    return iterator;
  }

  protected void finalize(Statement stmt,ResultSet rs){
    IOUtil.closeQuietly(rs);
    IOUtil.closeQuietly(stmt);
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
    if(null==o){
      return sql;
    }
    if(sql.length()<1){
      return sql;
    }
    if(!sql.contains(NAMED_PLACEHOLDER_PREFIX)){
      return sql;
    }
    List<String> keys = new ArrayList<String>();
    //匹配以冒号开头的字母或下划线组合，识别为变量名
    Matcher m = namedPlaceHolderPattern.matcher(sql);
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
      newSql = newSql.replace(key,PLACEHOLDER);
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
        default:
      }
    }
  }

  public String getSql(){
    return sql;
  }

  public List<Object> getParams(){
    return params;
  }

  protected Condition buildPureSqlCondition(String sql){
    Condition cond = new Condition();
    cond.setColumn(sql);
    cond.setStart("");
    cond.setPlaceholder("");
    cond.setEnd("");
    cond.setType(ConditionType.pure_sql);
    return cond;
  }

  protected ISqlExecuter where(String sql){
    wheres.add(buildPureSqlCondition(sql));
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
      //有:占位符的SQL语句
      if(columnOrSql.contains(NAMED_PLACEHOLDER_PREFIX)){
        Bean value;
        if(valueOrBean instanceof Bean) {
          value = (Bean)valueOrBean;
        }else if(valueOrBean instanceof Map) {
          Map<?,?> map = (Map<?,?>)valueOrBean;
          value = new Bean();
          for(Map.Entry entry:map.entrySet()){
            value.set(entry.getKey()+"",entry.getValue());
          }
        }else{
          //值不是Bean的，自动生成Bean，并按占位符赋值
          value = new Bean();
          Matcher m = namedPlaceHolderPattern.matcher(columnOrSql);
          while(m.find()){
            String key = m.group().substring(1);
            value.set(key,valueOrBean);
          }
        }
        cond.setStart("");
        cond.setPlaceholder("");
        cond.setEnd("");
        cond.setValue(value);
        cond.setType(ConditionType.multi_params);
      }else if(columnOrSql.contains(PLACEHOLDER)){
        //有?占位符的SQL语句，参数值为数组，集合或单个值
        boolean singleValue = false;
        Bean value = new Bean();
        if(valueOrBean.getClass().isArray()) {
          Object[] l = (Object[])valueOrBean;
          for(int i=0,j=l.length;i<j;i++){
            value.set("_"+i,l[i]);
          }
        }else if(valueOrBean instanceof Collection) {
          Collection<?> l = (Collection<?>)valueOrBean;
          int count=0;
          for(Object o:l){
            value.set("_"+count++,o);
          }
        }else{
          singleValue = true;
        }
        //?占位符修改为:占位符
        String newSql = columnOrSql;
        int count = 0;
        while(newSql.contains(PLACEHOLDER)) {
          String key = "_"+count++;
          newSql = newSql.replaceFirst(PLACEHOLDER_REG, ":" + key);
          if(singleValue){
            value.set(key,valueOrBean);
          }
        }
        cond.setColumn(newSql);
        cond.setStart("");
        cond.setPlaceholder("");
        cond.setEnd("");
        cond.setValue(value);
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
    if(condition){
      where(sql);
    }
    return this;
  }

  protected ISqlExecuter whereIf(String column,Object value,boolean condition){
    if(condition){
      where(column,value);
    }
    return this;
  }

  protected ISqlExecuter whereIf(String sql,Bean values,boolean condition){
    if(condition){
      where(sql,values);
    }
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
    cond.setPlaceholder(buildSqlIn(column,param,values));
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
    cond.setPlaceholder(buildSqlIn(column,param,values));
    cond.setEnd(")");
    cond.setValue(param);
    cond.setType(ConditionType.multi_params);
    wheres.add(cond);
    return this;
  }

  /**
   * 构建SQL语句
   * @return SQL语句
   */
  protected abstract String buildSql();

  protected String buildSqlIn(String column,Bean param,Object... values){
    StringBuilder s = new StringBuilder();
    int i=0;
    for(Object v:values){
      if(i>0){
        s.append(",");
      }
      String key = column.replaceAll("\\W","")+"_"+i;
      s.append(NAMED_PLACEHOLDER_PREFIX);
      s.append(key);
      param.set(key,v);
      i++;
    }
    return s.toString();
  }

  protected String buildSqlParams(Condition r){
    StringBuilder s = new StringBuilder();
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
      default:
    }
    return s.toString();
  }

  protected String buildSqlWhere(){
    StringBuilder s = new StringBuilder();
    for(int i = 0,l = wheres.size();i<l;i++){
      s.append(" ");
      s.append(i<1?"WHERE":"AND");
      s.append(" ");
      s.append(buildSqlParams(wheres.get(i)));
    }
    return s.toString();
  }

  protected int executeUpdate(){
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

  protected Bean executeUpdateAndGetGenerated(){
    Bean ret = new Bean();
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
        stmt = conn.getConnection().prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
      }
      bindParams(stmt);
      if(conn.isInBatch()){
        stmt.addBatch();
      }else {
        stmt.executeUpdate();
        ResultSet rs = stmt.getGeneratedKeys();
        List<Bean> l = toBeans(rs);
        if(!l.isEmpty()){
          ret = l.get(0);
        }
      }
      return ret;
    }catch(SQLException e){
      throw new DaoException(e);
    }finally{
      if(!conn.isInBatch()){
        finalize(stmt);
      }
    }
  }
}
