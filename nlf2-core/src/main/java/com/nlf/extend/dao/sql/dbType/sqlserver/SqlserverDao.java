package com.nlf.extend.dao.sql.dbType.sqlserver;

import com.nlf.extend.dao.sql.dbType.common.ASqlDao;

/**
 * SqlServer Dao
 *
 * @author 6tail
 */
public class SqlserverDao extends ASqlDao{
  @Override
  public boolean support(String dbType){
    return "sqlserver".equalsIgnoreCase(dbType);
  }
}