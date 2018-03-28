package com.nlf.extend.dao.sql.dbType.oracle;

import com.nlf.extend.dao.sql.dbType.common.ASqlDao;

public class OracleDao extends ASqlDao{
  public boolean support(String dbType){
    return "oracle".equalsIgnoreCase(dbType);
  }
}