package com.nlf.extend.dao.sql;

import com.nlf.App;
import com.nlf.Bean;
import com.nlf.dao.exception.DaoException;
import com.nlf.dao.executer.AbstractDaoExecuter;
import com.nlf.log.Logger;
import com.nlf.util.IOUtil;
import com.nlf.util.StringUtil;

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
  protected List<Condition> columns = new ArrayList<Condition>();
  protected List<String> groupBys = new ArrayList<String>();
  protected List<String> sorts = new ArrayList<String>();
  protected List<Condition> wheres = new ArrayList<Condition>();
  protected List<Condition> havings = new ArrayList<Condition>();
  /** 带名称的变量占位符 */
  protected static Pattern namedPlaceHolderPattern = Pattern.compile(":\\w+");
  /** 提取on的表名 */
  protected static Pattern onPattern = Pattern.compile("\\w+(?=\\.)");
  /** 变量排序器 */
  protected static Comparator<String> keyComparator = new Comparator<String>(){
    public int compare(String a,String b){
      return b.length()-a.length();
    }
  };
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
    ResultSetMetaData md = rs.getMetaData();
    while(rs.next()){
      Bean o = new Bean();
      for(int i = 1,j = md.getColumnCount();i<=j;i++){
        o.set(md.getColumnLabel(i),rs.getObject(i));
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
      stmt = prepareStatement((SqlConnection)connection);
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
      stmt = prepareStatement((SqlConnection)connection);
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
    Collections.sort(keys,keyComparator);
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
    if(null==sql){
      sql = buildSql();
    }
    return sql;
  }

  public List<Object> getParams(){
    return params;
  }

  protected Condition buildPureSqlCondition(String sql){
    return new Condition(ConditionType.pure_sql,sql,"","",null,"");
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
    Bean param = new Bean();
    String placeholder = buildSqlIn(column,param,values);
    wheres.add(new Condition(ConditionType.multi_params,column," IN(",placeholder,param,")"));
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
    Bean param = new Bean();
    String placeholder = buildSqlIn(column,param,values);
    wheres.add(new Condition(ConditionType.multi_params,column," NOT IN(",placeholder,param,")"));
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

  protected String buildTables(){
    StringBuilder s = new StringBuilder();
    Set<String> onAliases = new HashSet<String>();
    Set<String> existAliases = new HashSet<String>();
    Map<String,String> joinTables = new HashMap<String, String>(8);
    Map<String,String> notJoinTables = new HashMap<String, String>(8);
    Map<String,Set<String>> ons = new HashMap<String, Set<String>>(8);
    for(String table:tables){
      if(table.startsWith("*ON ")){
        Set<String> aliases = new HashSet<String>();
        Matcher m = onPattern.matcher(table);
        while(m.find()){
          String alias = m.group().toUpperCase();
          aliases.add(alias);
          onAliases.add(alias);
        }
        ons.put(table,aliases);
      }else if(table.startsWith("*")&&table.contains(" JOIN ")){
        String name = StringUtil.right(table," JOIN ").toUpperCase();
        String alias = name;
        if(name.contains(" AS ")){
          alias = StringUtil.right(name," AS ");
        }else if(name.contains(" ")){
          alias = StringUtil.right(name," ");
        }
        alias = alias.trim();
        joinTables.put(alias,table);
      }else{
        String alias = table;
        if(table.contains(" AS ")){
          alias = StringUtil.right(table," AS ");
        }else if(table.contains(" ")){
          alias = StringUtil.right(table," ");
        }
        alias = alias.toUpperCase().trim();
        existAliases.add(alias);
        notJoinTables.put(table,alias);
      }
    }
    for(Map.Entry<String,String> entry:notJoinTables.entrySet()){
      if(!onAliases.contains(entry.getValue())){
        if(s.length() > 0){
          s.append(", ");
        }
        s.append(entry.getKey());
      }
    }
    for(Map.Entry<String,String> entry:notJoinTables.entrySet()){
      if(onAliases.contains(entry.getValue())){
        if(s.length() > 0){
          s.append(", ");
        }
        s.append(entry.getKey());
      }
    }
    while(!ons.isEmpty()) {
      Set<String> already = new HashSet<String>();
      for(Map.Entry<String, Set<String>> on : ons.entrySet()) {
        int notMatched = 0;
        Set<String> value = on.getValue();
        for (String alias : value) {
          if (!existAliases.contains(alias)) {
            notMatched++;
          }
        }
        if(notMatched>1){
          continue;
        }
        for (String alias : value) {
          String table = joinTables.remove(alias);
          if(null!=table) {
            s.append(" ");
            s.append(table.substring(1));
            existAliases.add(alias);
          }
        }
        String key = on.getKey();
        s.append(" ");
        s.append(key.substring(1));
        already.add(key);
      }
      for(String key:already) {
        ons.remove(key);
      }
    }
    return s.toString();
  }

  protected PreparedStatement prepareStatement(SqlConnection conn) throws SQLException {
    return prepareStatement(conn,false);
  }

  protected PreparedStatement prepareStatement(SqlConnection conn, boolean generatedKeys) throws SQLException {
    PreparedStatement stmt = null;
    if(!conn.isInBatch()){
      if(!generatedKeys) {
        stmt = conn.getConnection().prepareStatement(sql);
      }else{
        stmt = conn.getConnection().prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
      }
    }else{
      stmt = conn.getStatement();
      if(null!=stmt){
        stmt.clearParameters();
        if(!sql.equals(conn.getSql())){
          throw new DaoException(App.getProperty("nlf.exception.dao.batch_sql_not_match"));
        }
      }else{
        stmt = conn.getConnection().prepareStatement(sql);
        conn.setStatement(stmt);
        conn.setSql(sql);
      }
    }
    bindParams(stmt);
    return stmt;
  }

  protected int executeUpdate(){
    params.clear();
    sql = buildSql();
    Logger.getLog().debug(buildLog());
    PreparedStatement stmt = null;
    SqlConnection conn = null;
    try{
      conn = (SqlConnection)connection;
      stmt = prepareStatement(conn);
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
      conn = (SqlConnection)connection;
      stmt = prepareStatement(conn,true);
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
