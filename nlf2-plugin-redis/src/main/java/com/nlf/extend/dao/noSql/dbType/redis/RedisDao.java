package com.nlf.extend.dao.noSql.dbType.redis;

import com.nlf.extend.dao.noSql.dbType.common.ANoSqlDao;

public class RedisDao extends ANoSqlDao{
  public boolean support(String dbType){
    return "redis".equals(dbType);
  }
}