package com.nlf.extend.dao.sql;

import com.nlf.dao.DaoFactory;

/**
 * 通用的SqlDao工厂
 * 
 * @author 6tail
 *
 */
public class SqlDaoFactory extends DaoFactory{
  /**
   * 根据别名获取SqlDao
   * 
   * @param alias 别名
   * @return SqlDao
   */
  public static ISqlDao getDao(String alias){
    return (ISqlDao)DaoFactory.getDao(alias);
  }

  /**
   * 获取默认DB配置的SqlDao
   * 
   * @return SqlDao
   */
  public static ISqlDao getDao(){
    return (ISqlDao)DaoFactory.getDao();
  }
}