package com.nlf.extend.dao.noSql;

import com.nlf.dao.DaoFactory;
import com.nlf.dao.DaoType;

/**
 * 通用的NoSqlDao工厂
 * 
 * @author 6tail
 *
 */
public class NoSqlDaoFactory extends DaoFactory{
  /**
   * 根据别名获取NoSqlDao
   * 
   * @param alias 别名
   * @return NoSqlDao
   */
  public static INoSqlDao getDao(String alias){
    return (INoSqlDao)DaoFactory.getDao(alias);
  }

  /**
   * 获取默认DB配置的NoSqlDao
   * 
   * @return NoSqlDao
   */
  public static INoSqlDao getDao(){
    return (INoSqlDao)DaoFactory.getDao(DaoType.nosql);
  }
}