package com.nlf.extend.dao.sql.type.druid;

import com.nlf.App;
import com.nlf.Bean;
import com.nlf.dao.setting.IDbSetting;
import com.nlf.dao.setting.IDbSettingProvider;

/**
 * druid连接池配置提供器
 * @author 6tail
 *
 */
public class DruidSettingProvider implements IDbSettingProvider{

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
    DruidSetting ds = new DruidSetting();
    ds.setAlias(alias);
    ds.setPassword(password);
    ds.setUser(user);
    ds.setDbType(dbType);
    ds.setDbName(dbname);
    ds.setInitialSize(o.getInt("initialSize",-1));
    ds.setMinIdle(o.getInt("minIdle",-1));
    ds.setMaxActive(o.getInt("maxActive",-1));
    ds.setMaxWait(o.getLong("maxWait",-1));
    ds.setTimeBetweenEvictionRunsMillis(o.getLong("timeBetweenEvictionRunsMillis",-1));
    ds.setMinEvictableIdleTimeMillis(o.getLong("minEvictableIdleTimeMillis",-1));
    ds.setMaxPoolPreparedStatementPerConnectionSize(o.getInt("maxPoolPreparedStatementPerConnectionSize",-1));
    ds.setTestWhileIdle(o.getBoolean("testWhileIdle",false));
    ds.setTestOnBorrow(o.getBoolean("testOnBorrow",false));
    ds.setTestOnReturn(o.getBoolean("testOnReturn",false));
    ds.setPoolPreparedStatements(o.getBoolean("poolPreparedStatements",false));
    ds.setFilters(o.getString("filters"));
    if(driver.length()>0){
      ds.setDriver(driver);
    }else {
      ds.setDriver(App.getProperty("nlf.dao.setting." + dbType + ".driver"));
    }
    if(url.length()>0){
      ds.setUrl(url);
    }else {
      ds.setUrl(App.getProperty("nlf.dao.setting." + dbType + ".url", server, port, dbname) + extra);
    }
    return ds;
  }

  public boolean support(String type){
    return "druid".equalsIgnoreCase(type);
  }
}