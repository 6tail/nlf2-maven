package com.nlf.extend.dao.sql.type.c3p0;

import com.nlf.App;
import com.nlf.Bean;
import com.nlf.dao.setting.IDbSetting;
import com.nlf.dao.setting.IDbSettingProvider;

/**
 * C3P0连接池配置提供器
 * @author 6tail
 *
 */
public class C3p0SettingProvider implements IDbSettingProvider{

  public IDbSetting buildDbSetting(Bean o){
    //String type = o.getString("type","");
    String alias = o.getString("alias","");
    String dbType = o.getString("dbtype","");
    String user = o.getString("user","");
    String password = o.getString("password","");
    String server = o.getString("server","");
    String port = o.getString("port","");
    String dbname = o.getString("dbname","");
    //驱动，如果这里配置了则优先使用
    String driver = o.getString("driver","");
    //URL，如果这里配置了则优先使用，server、port、dbname和extra都无效
    String url = o.getString("url","");
    //附加参数
    String extra = o.getString("extra","");
    if(extra.length()>0&&!extra.startsWith("?")){
      extra = "?"+extra;
    }
    //type = type.toUpperCase();
    dbType = dbType.toLowerCase();
    C3p0Setting ps = new C3p0Setting();
    ps.setAlias(alias);
    ps.setPassword(password);
    ps.setUser(user);
    ps.setDbType(dbType);
    ps.setDbName(dbname);
    ps.setMinPoolSize(o.getInt("minPoolSize",-1));
    ps.setMaxPoolSize(o.getInt("maxPoolSize",-1));
    ps.setInitialPoolSize(o.getInt("initialPoolSize",-1));
    ps.setMaxIdleTime(o.getInt("maxIdleTime",-1));
    ps.setAcquireIncrement(o.getInt("acquireIncrement",-1));
    ps.setAcquireRetryAttempts(o.getInt("acquireRetryAttempts",-1));
    ps.setAcquireRetryDelay(o.getInt("acquireRetryDelay",-1));
    ps.setTestConnectionOnCheckin(o.getBoolean("testConnectionOnCheckin",true));
    ps.setTestConnectionOnCheckout(o.getBoolean("testConnectionOnCheckout",true));
    ps.setAutomaticTestTable(o.getString("automaticTestTable"));
    ps.setIdleConnectionTestPeriod(o.getInt("idleConnectionTestPeriod",-1));
    ps.setCheckoutTimeout(o.getInt("checkoutTimeout",-1));
    ps.setMaxStatements(o.getInt("maxStatements",-1));
    ps.setMaxStatementsPerConnection(o.getInt("maxStatementsPerConnection",-1));
    if(driver.length()>0){
      ps.setDriver(driver);
    }else {
      ps.setDriver(App.getProperty("nlf.dao.setting." + dbType + ".driver"));
    }
    if(url.length()>0){
      ps.setUrl(url);
    }else {
      ps.setUrl(App.getProperty("nlf.dao.setting." + dbType + ".url", server, port, dbname) + extra);
    }
    return ps;
  }

  public boolean support(String type){
    return "c3p0".equalsIgnoreCase(type);
  }
}