package com.nlf.extend.dao.sql.type.dbcp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.dbcp.BasicDataSource;
import com.nlf.App;
import com.nlf.dao.connection.AbstractConnectionProvider;
import com.nlf.dao.connection.IConnection;
import com.nlf.dao.exception.DaoException;
import com.nlf.extend.dao.sql.SqlConnection;

/**
 * dbcp连接提供器
 *
 * @author 6tail
 *
 */
public class DbcpConnectionProvider extends AbstractConnectionProvider{
  private static final Map<String,BasicDataSource> dataSources = new HashMap<String,BasicDataSource>();

  public IConnection getConnection(){
    DbcpSetting setting = (DbcpSetting)this.setting;
    Connection conn = null;
    try{
      String key = setting.getAlias();
      BasicDataSource dataSource = dataSources.get(key);
      if(null==dataSource){
        dataSource = new BasicDataSource();
        dataSource.setDriverClassName(setting.getDriver());
        dataSource.setUrl(setting.getUrl());
        dataSource.setUsername(setting.getUser());
        dataSource.setPassword(setting.getPassword());
        dataSource.setTestOnBorrow(setting.isTestOnBorrow());
        dataSource.setTestOnReturn(setting.isTestOnReturn());
        dataSource.setTestWhileIdle(setting.isTestWhileIdle());
        dataSource.setPoolPreparedStatements(setting.isPoolPreparedStatements());
        if(-1!=setting.getInitialSize()){
          dataSource.setInitialSize(setting.getInitialSize());
        }
        if(-1!=setting.getMinIdle()){
          dataSource.setMinIdle(setting.getMinIdle());
        }
        if(-1!=setting.getMaxActive()){
          dataSource.setMaxActive(setting.getMaxActive());
        }
        if(-1!=setting.getMaxWait()){
          dataSource.setMaxWait(setting.getMaxWait());
        }
        if(-1!=setting.getTimeBetweenEvictionRunsMillis()){
          dataSource.setTimeBetweenEvictionRunsMillis(setting.getTimeBetweenEvictionRunsMillis());
        }
        if(-1!=setting.getMinEvictableIdleTimeMillis()){
          dataSource.setMinEvictableIdleTimeMillis(setting.getMinEvictableIdleTimeMillis());
        }
        dataSource.setValidationQuery(App.getProperty("nlf.dao.setting."+setting.getDbType()+".sql"));
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
    return "dbcp".equalsIgnoreCase(type);
  }
}