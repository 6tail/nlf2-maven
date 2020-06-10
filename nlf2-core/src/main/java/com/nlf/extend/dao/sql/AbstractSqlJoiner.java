package com.nlf.extend.dao.sql;

/**
 * 抽象SQL JOIN封装器
 * @author 6tail
 */
public abstract class AbstractSqlJoiner implements ISqlJoiner{

  protected ISqlSelecter selecter;

  public ISqlSelecter getSelecter(){
    return selecter;
  }

  public void setSelecter(ISqlSelecter selecter){
    this.selecter = selecter;
  }

  public ISqlJoiner innerJoin(String table){
    selecter.table("*INNER JOIN "+table);
    return this;
  }

  public ISqlJoiner leftJoin(String table){
    selecter.table("*LEFT JOIN "+table);
    return this;
  }

  public ISqlJoiner rightJoin(String table){
    selecter.table("*RIGHT JOIN "+table);
    return this;
  }

  public ISqlJoiner crossJoin(String table){
    selecter.table(table);
    return this;
  }

  public ISqlJoiner on(String sql){
    selecter.table("*ON "+sql);
    return this;
  }
}
