package com.nlf.extend.dao.sql.dbType.hsqldb;

import com.nlf.extend.dao.sql.dbType.mysql.MysqlSelecter;

/**
 * SQL查询器的hsqldb实现
 * @author 6tail
 *
 */
public class HsqldbSelecter extends MysqlSelecter {
  public boolean support(String dbType){
    return "hsqldb".equalsIgnoreCase(dbType);
  }
}