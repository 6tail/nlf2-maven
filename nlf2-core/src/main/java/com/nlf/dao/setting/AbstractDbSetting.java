package com.nlf.dao.setting;


import com.nlf.dao.DaoType;

/**
 * 连接配置抽象
 * 
 * @author 6tail
 * 
 */
public abstract class AbstractDbSetting implements IDbSetting{

  private static final long serialVersionUID = 1;
  
  /** 连接类型，如jdbc、c3p0 */
  protected String type;
  
  /** 连接别名 */
  protected String alias;
  
  /** 连接URL */
  protected String url;
  
  /** 用户名 */
  protected String user;
  
  /** 密码 */
  protected String password;
  
  /** 驱动类 */
  protected String driver;
  
  /** 数据库类型，如oracle、mysql、sqlserver */
  protected String dbType;
  
  /** 数据库实例名 */
  protected String dbName;

  public DaoType getDaoType(){
    return DaoType.sql;
  }

  /**
   * 获取连接类型
   * @return 连接类型，如jdbc、c3p0
   */
  public String getType(){
    return type;
  }

  /**
   * 设置连接类型
   * @param type 连接类型，如jdbc、c3p0
   */
  public void setType(String type){
    this.type = type;
  }

  /**
   * 获取连接别名
   * @return 连接别名
   */
  public String getAlias(){
    return alias;
  }

  /**
   * 设置连接别名
   * @param alias 连接别名
   */
  public void setAlias(String alias){
    this.alias = alias;
  }

  /**
   * 获取连接URL
   * @return url
   */
  public String getUrl(){
    return url;
  }

  /**
   * 设置连接URL
   * @param url 连接url
   */
  public void setUrl(String url){
    this.url = url;
  }

  /**
   * 获取用户名
   * @return 用户名
   */
  public String getUser(){
    return user;
  }

  /**
   * 设置用户名
   * @param user 用户名
   */
  public void setUser(String user){
    this.user = user;
  }

  /**
   * 获取密码
   * @return 密码
   */
  public String getPassword(){
    return password;
  }

  /**
   * 设置密码
   * @param password 密码
   */
  public void setPassword(String password){
    this.password = password;
  }

  /**
   * 获取驱动程序
   * @return 驱动程序
   */
  public String getDriver(){
    return driver;
  }

  /**
   * 设置驱动程序
   * @param driver 驱动程序
   */
  public void setDriver(String driver){
    this.driver = driver;
  }

  /**
   * 获取数据库类型
   * @return 数据库类型，如oracle、mysql、sqlserver
   */
  public String getDbType(){
    return dbType;
  }

  /**
   * 设置数据库类型
   * @param dbType 数据库类型，如oracle、mysql、sqlserver
   */
  public void setDbType(String dbType){
    this.dbType = dbType;
  }

  /**
   * 获取数据库实例名
   * @return 数据库实例名
   */
  public String getDbName(){
    return dbName;
  }

  /**
   * 设置数据库实例名
   * @param dbName 数据库实例名
   */
  public void setDbName(String dbName){
    this.dbName = dbName;
  }
  
  public String toString(){
    return alias;
  }
}