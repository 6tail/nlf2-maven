package com.nlf.extend.dao.sql.type.c3p0;

import com.nlf.dao.setting.AbstractDbSetting;

/**
 * C3P0连接池配置
 * 
 * @author 6tail
 * 
 */
public class C3p0Setting extends AbstractDbSetting{
  private static final long serialVersionUID = 1;
  /** 默认连接类型 */
  public static final String DEFAULT_TYPE = "c3p0";
  private int minPoolSize = -1;
  private int maxPoolSize = -1;
  private int initialPoolSize = -1;
  private int maxIdleTime = -1;
  private int maxConnectionAge = -1;
  private int acquireIncrement = -1;
  private int acquireRetryAttempts = -1;
  private int acquireRetryDelay = -1;
  private int idleConnectionTestPeriod = -1;
  private int checkoutTimeout = -1;
  private int maxStatements = -1;
  private int maxStatementsPerConnection = -1;
  private String automaticTestTable = null;
  private boolean testConnectionOnCheckin = false;
  private boolean testConnectionOnCheckout = false;

  public C3p0Setting(){
    type = DEFAULT_TYPE;
  }

  public int getMinPoolSize(){
    return minPoolSize;
  }

  public void setMinPoolSize(int minPoolSize){
    this.minPoolSize = minPoolSize;
  }

  public int getMaxPoolSize(){
    return maxPoolSize;
  }

  public void setMaxPoolSize(int maxPoolSize){
    this.maxPoolSize = maxPoolSize;
  }

  public int getInitialPoolSize(){
    return initialPoolSize;
  }

  public void setInitialPoolSize(int initialPoolSize){
    this.initialPoolSize = initialPoolSize;
  }

  public int getMaxIdleTime(){
    return maxIdleTime;
  }

  public void setMaxIdleTime(int maxIdleTime){
    this.maxIdleTime = maxIdleTime;
  }

  public int getMaxConnectionAge(){
    return maxConnectionAge;
  }

  public void setMaxConnectionAge(int maxConnectionAge){
    this.maxConnectionAge = maxConnectionAge;
  }

  public int getAcquireIncrement(){
    return acquireIncrement;
  }

  public void setAcquireIncrement(int acquireIncrement){
    this.acquireIncrement = acquireIncrement;
  }

  public int getAcquireRetryAttempts(){
    return acquireRetryAttempts;
  }

  public void setAcquireRetryAttempts(int acquireRetryAttempts){
    this.acquireRetryAttempts = acquireRetryAttempts;
  }

  public int getAcquireRetryDelay(){
    return acquireRetryDelay;
  }

  public void setAcquireRetryDelay(int acquireRetryDelay){
    this.acquireRetryDelay = acquireRetryDelay;
  }

  public int getIdleConnectionTestPeriod(){
    return idleConnectionTestPeriod;
  }

  public void setIdleConnectionTestPeriod(int idleConnectionTestPeriod){
    this.idleConnectionTestPeriod = idleConnectionTestPeriod;
  }

  public int getCheckoutTimeout(){
    return checkoutTimeout;
  }

  public void setCheckoutTimeout(int checkoutTimeout){
    this.checkoutTimeout = checkoutTimeout;
  }

  public int getMaxStatements(){
    return maxStatements;
  }

  public void setMaxStatements(int maxStatements){
    this.maxStatements = maxStatements;
  }

  public int getMaxStatementsPerConnection(){
    return maxStatementsPerConnection;
  }

  public void setMaxStatementsPerConnection(int maxStatementsPerConnection){
    this.maxStatementsPerConnection = maxStatementsPerConnection;
  }

  public String getAutomaticTestTable(){
    return automaticTestTable;
  }

  public void setAutomaticTestTable(String automaticTestTable){
    this.automaticTestTable = automaticTestTable;
  }

  public boolean isTestConnectionOnCheckin(){
    return testConnectionOnCheckin;
  }

  public void setTestConnectionOnCheckin(boolean testConnectionOnCheckin){
    this.testConnectionOnCheckin = testConnectionOnCheckin;
  }

  public boolean isTestConnectionOnCheckout(){
    return testConnectionOnCheckout;
  }

  public void setTestConnectionOnCheckout(boolean testConnectionOnCheckout){
    this.testConnectionOnCheckout = testConnectionOnCheckout;
  }
}