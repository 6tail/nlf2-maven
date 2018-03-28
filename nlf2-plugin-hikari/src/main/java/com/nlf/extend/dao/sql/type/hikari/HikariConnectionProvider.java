package com.nlf.extend.dao.sql.type.hikari;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import com.nlf.dao.connection.AbstractConnectionProvider;
import com.nlf.dao.connection.IConnection;
import com.nlf.dao.exception.DaoException;
import com.nlf.extend.dao.sql.SqlConnection;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * HikariCP连接提供器
 *
 * @author 6tail
 *
 */
public class HikariConnectionProvider extends AbstractConnectionProvider{
  private static final Map<String,HikariDataSource> dataSources = new HashMap<String,HikariDataSource>();

  public IConnection getConnection(){
    HikariSetting setting = (HikariSetting)this.setting;
    Connection conn = null;
    try{
      String key = setting.getAlias();
      HikariDataSource dataSource = dataSources.get(key);
      if(null==dataSource){
        HikariConfig config = new HikariConfig();
        config.setDataSourceClassName(setting.getDataSourceClassName());
        config.setUsername(setting.getUser());
        config.setPassword(setting.getPassword());
        config.setConnectionTestQuery(setting.getConnectionTestQuery());
        if(setting.getIdleTimeout()>-1){
          config.setIdleTimeout(setting.getIdleTimeout());
        }
        if(setting.getConnectionTimeout()>-1){
          config.setConnectionTimeout(setting.getConnectionTimeout());
        }
        if(setting.getMaxLifetime()>-1){
          config.setMaxLifetime(setting.getMaxLifetime());
        }
        if(setting.getMaximumPoolSize()>-1){
          config.setMaximumPoolSize(setting.getMaximumPoolSize());
        }
        if(setting.getMinimumIdle()>-1){
          config.setMinimumIdle(setting.getMinimumIdle());
        }
        config.addDataSourceProperty("url",setting.getUrl());
        for(Entry<String,Object> en:setting.getProperties().entrySet()){
          config.addDataSourceProperty(en.getKey(),en.getValue());
        }
        dataSource = new HikariDataSource(config);
        dataSources.put(key,dataSource);
      }
      conn = dataSource.getConnection();
    }catch(SQLException e){
      throw new DaoException(e);
    }
    SqlConnection sc = new SqlConnection(conn);
    sc.setDbSetting(setting);
    return sc;
  }

  public boolean support(String type){
    return "HikariCP".equalsIgnoreCase(type);
  }
}