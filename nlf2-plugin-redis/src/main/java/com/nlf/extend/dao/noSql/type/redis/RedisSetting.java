package com.nlf.extend.dao.noSql.type.redis;

import com.nlf.dao.setting.AbstractDbSetting;

/**
 * Redis连接配置
 * 
 * @author 6tail
 * 
 */
public class RedisSetting extends AbstractDbSetting{
  private static final long serialVersionUID = 1;
  /** 默认连接类型 */
  public static final String DEFAULT_TYPE = "redis";
  private int maxTotal;
  private int maxIdle;
  private int minIdle;
  private long maxWaitMillis;
  private int numTestsPerEvictionRun;
  private int timeOut;
  private long minEvictableIdleTimeMillis;
  private long softMinEvictableIdleTimeMillis;
  private long timeBetweenEvictionRunsMillis;
  private boolean testOnBorrow;
  private boolean testOnReturn;
  private boolean testWhileIdle;
  private boolean blockWhenExhausted;
  private String host;
  private int port;

  public RedisSetting(){
    type = DEFAULT_TYPE;
  }

  public String getHost(){
    return host;
  }

  public void setHost(String host){
    this.host = host;
  }

  public int getPort(){
    return port;
  }

  public void setPort(int port){
    this.port = port;
  }

  public boolean isBlockWhenExhausted() {
    return blockWhenExhausted;
  }

  public void setBlockWhenExhausted(boolean blockWhenExhausted) {
    this.blockWhenExhausted = blockWhenExhausted;
  }

  public int getMaxTotal() {
    return maxTotal;
  }

  public void setMaxTotal(int maxTotal) {
    this.maxTotal = maxTotal;
  }

  public int getMaxIdle(){
    return maxIdle;
  }

  public void setMaxIdle(int maxIdle){
    this.maxIdle = maxIdle;
  }

  public int getMinIdle(){
    return minIdle;
  }

  public void setMinIdle(int minIdle){
    this.minIdle = minIdle;
  }

  public long getMaxWaitMillis(){
    return maxWaitMillis;
  }

  public void setMaxWaitMillis(long maxWaitMillis){
    this.maxWaitMillis = maxWaitMillis;
  }

  public int getNumTestsPerEvictionRun(){
    return numTestsPerEvictionRun;
  }

  public void setNumTestsPerEvictionRun(int numTestsPerEvictionRun){
    this.numTestsPerEvictionRun = numTestsPerEvictionRun;
  }

  public int getTimeOut(){
    return timeOut;
  }

  public void setTimeOut(int timeOut){
    this.timeOut = timeOut;
  }

  public long getMinEvictableIdleTimeMillis(){
    return minEvictableIdleTimeMillis;
  }

  public void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis){
    this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
  }

  public long getSoftMinEvictableIdleTimeMillis(){
    return softMinEvictableIdleTimeMillis;
  }

  public void setSoftMinEvictableIdleTimeMillis(long softMinEvictableIdleTimeMillis){
    this.softMinEvictableIdleTimeMillis = softMinEvictableIdleTimeMillis;
  }

  public long getTimeBetweenEvictionRunsMillis(){
    return timeBetweenEvictionRunsMillis;
  }

  public void setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis){
    this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
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

  public boolean isTestWhileIdle(){
    return testWhileIdle;
  }

  public void setTestWhileIdle(boolean testWhileIdle){
    this.testWhileIdle = testWhileIdle;
  }
}