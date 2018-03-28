package com.nlf.extend.dao.sql.type.dbcp;

import com.nlf.dao.setting.AbstractDbSetting;

/**
 * dbcp连接池配置
 * 
 * @author 6tail
 * 
 */
public class DbcpSetting extends AbstractDbSetting{
  private static final long serialVersionUID = 1;
  /** 默认连接类型 */
  public static final String DEFAULT_TYPE = "dbcp";
  private int initialSize = -1;
  private int minIdle = -1;
  private int maxActive = -1;
  private long maxWait = -1;
  private long timeBetweenEvictionRunsMillis = -1;
  private long minEvictableIdleTimeMillis = -1;
  private boolean testWhileIdle = false;
  private boolean testOnBorrow = false;
  private boolean testOnReturn = false;
  private boolean poolPreparedStatements = false;

  public DbcpSetting(){
    type = DEFAULT_TYPE;
  }

  public int getInitialSize(){
    return initialSize;
  }

  public void setInitialSize(int initialSize){
    this.initialSize = initialSize;
  }

  public int getMinIdle(){
    return minIdle;
  }

  public void setMinIdle(int minIdle){
    this.minIdle = minIdle;
  }

  public int getMaxActive(){
    return maxActive;
  }

  public void setMaxActive(int maxActive){
    this.maxActive = maxActive;
  }

  public long getMaxWait(){
    return maxWait;
  }

  public void setMaxWait(long maxWait){
    this.maxWait = maxWait;
  }

  public long getTimeBetweenEvictionRunsMillis(){
    return timeBetweenEvictionRunsMillis;
  }

  public void setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis){
    this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
  }

  public long getMinEvictableIdleTimeMillis(){
    return minEvictableIdleTimeMillis;
  }

  public void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis){
    this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
  }

  public boolean isTestWhileIdle(){
    return testWhileIdle;
  }

  public void setTestWhileIdle(boolean testWhileIdle){
    this.testWhileIdle = testWhileIdle;
  }

  public boolean isTestOnBorrow(){
    return testOnBorrow;
  }

  public void setTestOnBorrow(boolean testOnBorrow){
    this.testOnBorrow = testOnBorrow;
  }

  public boolean isTestOnReturn(){
    return testOnReturn;
  }

  public void setTestOnReturn(boolean testOnReturn){
    this.testOnReturn = testOnReturn;
  }

  public boolean isPoolPreparedStatements(){
    return poolPreparedStatements;
  }

  public void setPoolPreparedStatements(boolean poolPreparedStatements){
    this.poolPreparedStatements = poolPreparedStatements;
  }

}