package com.nlf.extend.dao.sql.type.c3p0;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.nlf.dao.connection.AbstractConnectionProvider;
import com.nlf.dao.connection.IConnection;
import com.nlf.dao.exception.DaoException;
import com.nlf.extend.dao.sql.SqlConnection;

/**
 * C3P0连接提供器
 *
 * @author 6tail
 *
 */
public class C3p0ConnectionProvider extends AbstractConnectionProvider{
  private static final Map<String,ComboPooledDataSource> dataSources = new HashMap<String,ComboPooledDataSource>();

  public IConnection getConnection(){
    C3p0Setting setting = (C3p0Setting)this.setting;
    Connection conn = null;
    try{
      String key = setting.getAlias();
      ComboPooledDataSource dataSource = dataSources.get(key);
      if(null==dataSource){
        dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(setting.getDriver());
        dataSource.setJdbcUrl(setting.getUrl());
        dataSource.setUser(setting.getUser());
        dataSource.setPassword(setting.getPassword());
        dataSource.setTestConnectionOnCheckin(setting.isTestConnectionOnCheckin());
        dataSource.setTestConnectionOnCheckout(setting.isTestConnectionOnCheckout());
        if(-1!=setting.getMinPoolSize()){
          dataSource.setMinPoolSize(setting.getMinPoolSize());
        }
        if(-1!=setting.getMinPoolSize()){
          dataSource.setMinPoolSize(setting.getMinPoolSize());
        }
        if(-1!=setting.getMaxPoolSize()){
          dataSource.setMaxPoolSize(setting.getMaxPoolSize());
        }
        if(-1!=setting.getInitialPoolSize()){
          dataSource.setInitialPoolSize(setting.getInitialPoolSize());
        }
        if(-1!=setting.getMaxIdleTime()){
          dataSource.setMaxIdleTime(setting.getMaxIdleTime());
        }
        if(-1!=setting.getMaxConnectionAge()){
          dataSource.setMaxConnectionAge(setting.getMaxConnectionAge());
        }
        if(-1!=setting.getAcquireIncrement()){
          dataSource.setAcquireIncrement(setting.getAcquireIncrement());
        }
        if(-1!=setting.getAcquireRetryAttempts()){
          dataSource.setAcquireRetryAttempts(setting.getAcquireRetryAttempts());
        }
        if(-1!=setting.getAcquireRetryDelay()){
          dataSource.setAcquireRetryDelay(setting.getAcquireRetryDelay());
        }
        if(-1!=setting.getIdleConnectionTestPeriod()){
          dataSource.setIdleConnectionTestPeriod(setting.getIdleConnectionTestPeriod());
        }
        if(-1!=setting.getCheckoutTimeout()){
          dataSource.setCheckoutTimeout(setting.getCheckoutTimeout());
        }
        if(-1!=setting.getMaxStatements()){
          dataSource.setMaxStatements(setting.getMaxStatements());
        }
        if(-1!=setting.getMaxStatementsPerConnection()){
          dataSource.setMaxStatementsPerConnection(setting.getMaxStatementsPerConnection());
        }
        dataSource.setAutomaticTestTable(setting.getAutomaticTestTable());
        dataSources.put(key,dataSource);
      }
      conn = dataSource.getConnection();
    }catch(SQLException e){
      throw new DaoException(e);
    }catch(PropertyVetoException e){
      throw new DaoException(e);
    }
    SqlConnection sc = new SqlConnection(conn);
    sc.setDbSetting(setting);
    return sc;
  }

  public boolean support(String type){
    return "c3p0".equalsIgnoreCase(type);
  }
}