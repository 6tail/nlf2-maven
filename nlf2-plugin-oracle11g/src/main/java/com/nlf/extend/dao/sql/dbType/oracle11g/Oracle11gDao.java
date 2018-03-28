package com.nlf.extend.dao.sql.dbType.oracle11g;

import com.nlf.extend.dao.sql.dbType.common.ASqlDao;

public class Oracle11gDao extends ASqlDao{
  public boolean support(String dbType){
    return "oracle11g".equalsIgnoreCase(dbType);
  }
}