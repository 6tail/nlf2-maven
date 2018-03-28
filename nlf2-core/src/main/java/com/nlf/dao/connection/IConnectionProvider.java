package com.nlf.dao.connection;

/**
 * DB连接提供器接口
 *
 * @author 6tail
 *
 */
public interface IConnectionProvider{

  /**
   * 是否支持指定连接类型
   *
   * @param type 连接类型，如jdbc
   * @return true支持；false不支持
   */
  boolean support(String type);

  /**
   * 获取连接
   *
   * @return 连接
   */
  IConnection getConnection();

  /**
   * 设置DB配置
   *
   * @param dbSetting DB配置
   */
  void setDbSetting(com.nlf.dao.setting.IDbSetting dbSetting);
}