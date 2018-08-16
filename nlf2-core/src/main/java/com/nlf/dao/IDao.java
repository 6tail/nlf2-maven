package com.nlf.dao;

/**
 * Dao接口
 * 
 * @author 6tail
 *
 */
public interface IDao{
  /**
   * 获取DB别名
   * 
   * @return DB别名
   */
  String getAlias();

  /**
   * 获取Dao类型
   * @return Dao类型
   */
  DaoType getType();

  /**
   * 是否支持指定DB类型
   * 
   * @param dbType DB类型，如mysql、oracle、sqlserver等
   * @return true支持；false不支持
   */
  boolean support(String dbType);

  /**
   * 开启事务
   * 
   * @return 事务接口
   */
  com.nlf.dao.transaction.ITransaction beginTransaction();
}