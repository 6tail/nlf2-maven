package com.nlf.extend.dao.sql.type.proxool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.logicalcobwebs.proxool.ProxoolDataSource;
import com.nlf.App;
import com.nlf.dao.connection.AbstractConnectionProvider;
import com.nlf.dao.connection.IConnection;
import com.nlf.dao.exception.DaoException;
import com.nlf.extend.dao.sql.SqlConnection;

/**
 * proxool连接提供器
 *
 * @author 6tail
 *
 */
public class ProxoolConnectionProvider extends AbstractConnectionProvider{
  private static final Map<String,ProxoolDataSource> dataSources = new HashMap<String,ProxoolDataSource>();

  public IConnection getConnection(){
    ProxoolSetting setting = (ProxoolSetting)this.setting;
    Connection conn = null;
    try{
      String key = setting.getAlias();
      ProxoolDataSource dataSource = dataSources.get(key);
      if(null==dataSource){
        dataSource = new ProxoolDataSource();
        dataSource.setDriver(setting.getDriver());
        dataSource.setDriverUrl(setting.getUrl());
        dataSource.setUser(setting.getUser());
        dataSource.setPassword(setting.getPassword());
        dataSource.setAlias(setting.getAlias());
        dataSource.setHouseKeepingTestSql(App.getProperty("nlf.dao.setting."+setting.getDbType()+".sql"));
        dataSource.setTestBeforeUse(setting.isTestBeforeUse());
        dataSource.setTestAfterUse(setting.isTestAfterUse());
        if(-1!=setting.getHouseKeepingSleepTime()){
          dataSource.setHouseKeepingSleepTime(setting.getHouseKeepingSleepTime());
        }
        if(-1!=setting.getMaximumActiveTime()){
          dataSource.setMaximumActiveTime(setting.getMaximumActiveTime());
        }
        if(-1!=setting.getMaximumConnectionCount()){
          dataSource.setMaximumConnectionCount(setting.getMaximumConnectionCount());
        }
        if(-1!=setting.getMaximumConnectionLifeTime()){
          dataSource.setMaximumConnectionLifetime(setting.getMaximumConnectionLifeTime());
        }
        if(-1!=setting.getMinimumConnectionCount()){
          dataSource.setMinimumConnectionCount(setting.getMinimumConnectionCount());
        }
        if(-1!=setting.getPrototypeCount()){
          dataSource.setPrototypeCount(setting.getPrototypeCount());
        }
        if(-1!=setting.getSimultaneousBuildThrottle()){
          dataSource.setSimultaneousBuildThrottle(setting.getSimultaneousBuildThrottle());
        }
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
    return "proxool".equalsIgnoreCase(type);
  }
}