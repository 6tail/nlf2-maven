package com.nlf.extend.dao.sql.type.jdbc;

import com.nlf.dao.setting.AbstractDbSetting;

/**
 * JDBC连接配置
 * 
 * @author 6tail
 * 
 */
public class JdbcSetting extends AbstractDbSetting{

  private static final long serialVersionUID = 1;
  /** 默认连接类型 */
  public static final String DEFAULT_TYPE = "jdbc";

  public JdbcSetting(){
    type = DEFAULT_TYPE;
  }
}