package com.nlf.extend.dao.sql.dbType.sqlserver;

import com.nlf.extend.dao.sql.dbType.common.ASqlDao;

public class SqlserverDao extends ASqlDao{
  public boolean support(String dbType){
    return "sqlserver".equalsIgnoreCase(dbType);
  }
}