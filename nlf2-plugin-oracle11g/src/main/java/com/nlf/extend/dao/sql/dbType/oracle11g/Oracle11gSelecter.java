package com.nlf.extend.dao.sql.dbType.oracle11g;

import com.nlf.extend.dao.sql.dbType.oracle.OracleSelecter;

/**
 * SQL查询器的oracle11g实现
 * @author 6tail
 *
 */
public class Oracle11gSelecter extends OracleSelecter{
  public boolean support(String dbType){
    return "oracle11g".equalsIgnoreCase(dbType);
  }
}