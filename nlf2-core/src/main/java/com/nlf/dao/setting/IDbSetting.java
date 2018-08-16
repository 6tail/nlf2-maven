package com.nlf.dao.setting;

import com.nlf.dao.DaoType;

/**
 * DB配置接口
 *
 * @author 6tail
 *
 */
public interface IDbSetting extends java.io.Serializable{

  /**
   * 获取配置类型，如jdbc、c3p0
   *
   * @return 配置类型
   */
  String getType();

  /**
   * 获取别名
   *
   * @return 别名
   */
  String getAlias();

  /**
   * 获取DB实例名
   *
   * @return DB实例名
   */
  String getDbName();

  /**
   * 获取驱动
   *
   * @return 驱动
   */
  String getDriver();

  /**
   * 获取DB类型，如oracle、mysql、sqlserver
   *
   * @return DB类型
   */
  String getDbType();

  /**
   * 获取DAO类型
   * @return DAO类型
   */
  DaoType getDaoType();
}