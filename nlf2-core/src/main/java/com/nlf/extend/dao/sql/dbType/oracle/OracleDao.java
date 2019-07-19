package com.nlf.extend.dao.sql.dbType.oracle;

import com.nlf.extend.dao.sql.dbType.common.ASqlDao;

/**
 * Oracle Dao
 *
 * @author 6tail
 */
public class OracleDao extends ASqlDao{
  @Override
  public boolean support(String dbType){
    return "oracle".equalsIgnoreCase(dbType);
  }
}