package com.nlf.dao.executer;


/**
 * Dao执行器接口
 * 
 * @author 6tail
 *
 */
public interface IDaoExecuter{
  /**
   * 是否支持指定DB类型
   * @param dbType DB类型
   * @return true支持；false不支持
   */
  boolean support(String dbType);
}