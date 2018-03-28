package com.nlf.dao.connection;

/**
 * DB连接接口
 * 
 * @author 6tail
 *
 */
public interface IConnection extends java.io.Closeable{
  /**
   * 获取DB配置
   * 
   * @return DB配置
   */
  com.nlf.dao.setting.IDbSetting getDbSetting();

  /**
   * 是否已关闭
   * 
   * @return true已关闭；false未关闭
   */
  boolean isClosed();

  /**
   * 是否支持批量更新
   * 
   * @return true支持；false不支持
   */
  boolean supportsBatchUpdates();

  /**
   * 关闭
   */
  void close();
}