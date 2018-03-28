package com.nlf.extend.dao.sql.dbType.mysql;

import com.nlf.extend.dao.sql.dbType.common.ASqlDao;

public class MysqlDao extends ASqlDao{
  public boolean support(String dbType){
    return "mysql".equalsIgnoreCase(dbType);
  }
}