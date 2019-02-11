package com.nlf.extend.dao.sql.dbType.hsqldb;

import com.nlf.extend.dao.sql.dbType.mysql.MysqlTemplate;

/**
 * SQL模板的hsqldb实现
 * @author 6tail
 *
 */
public class HsqldbTemplate extends MysqlTemplate {

  public boolean support(String dbType){
    return "hsqldb".equalsIgnoreCase(dbType);
  }
}