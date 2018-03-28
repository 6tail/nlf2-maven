package com.nlf.extend.dao.sql.type.hikari;

import java.util.HashMap;
import java.util.Map;
import com.nlf.dao.setting.AbstractDbSetting;

/**
 * HikariCP连接池配置
 * 
 * @author 6tail
 * 
 */
public class HikariSetting extends AbstractDbSetting{
  private static final long serialVersionUID = 1;
  /** 默认连接类型 */
  public static final String DEFAULT_TYPE = "HikariCP";
  private int port;
  private int minimumIdle = -1;
  private int maximumPoolSize = -1;
  private long maxLifetime = -1;
  private long idleTimeout = -1;
  private long connectionTimeout = -1;
  private Map<String,Object> properties = new HashMap<String,Object>();
  private String connectionTestQuery;
  private String server;
  private String dataSourceClassName;
  
  public HikariSetting(){
    type = DEFAULT_TYPE;
  }

  public int getPort(){
    return port;
  }

  public void setPort(int port){
    this.port = port;
  }

  public void setServer(String server){
    this.server = server;
  }

  public String getServer(){
    return server;
  }

  public int getMinimumIdle(){
    return minimumIdle;
  }

  public void setMinimumIdle(int minimumIdle){
    this.minimumIdle = minimumIdle;
  }

  public int getMaximumPoolSize(){
    return maximumPoolSize;
  }

  public void setMaximumPoolSize(int maximumPoolSize){
    this.maximumPoolSize = maximumPoolSize;
  }

  public void setProperty(String key,Object value){
    properties.put(key,value);
  }

  public Map<String,Object> getProperties(){
    return properties;
  }

  public void setProperties(Map<String,Object> properties){
    this.properties = properties;
  }

  public long getMaxLifetime(){
    return maxLifetime;
  }

  public void setMaxLifetime(long maxLifetime){
    this.maxLifetime = maxLifetime;
  }

  public long getIdleTimeout(){
    return idleTimeout;
  }

  public void setIdleTimeout(long idleTimeout){
    this.idleTimeout = idleTimeout;
  }

  public long getConnectionTimeout(){
    return connectionTimeout;
  }

  public void setConnectionTimeout(long connectionTimeout){
    this.connectionTimeout = connectionTimeout;
  }

  public String getConnectionTestQuery(){
    return connectionTestQuery;
  }

  public void setConnectionTestQuery(String connectionTestQuery){
    this.connectionTestQuery = connectionTestQuery;
  }

  public String getDataSourceClassName(){
    return dataSourceClassName;
  }

  public void setDataSourceClassName(String dataSourceClassName){
    this.dataSourceClassName = dataSourceClassName;
  }
}