package com.nlf.extend.dao.sql.dbType.hsqldb;

import com.nlf.extend.dao.sql.dbType.common.ASqlDao;

public class HsqldbDao extends ASqlDao{
  public boolean support(String dbType){
    return "hsqldb".equalsIgnoreCase(dbType);
  }
}