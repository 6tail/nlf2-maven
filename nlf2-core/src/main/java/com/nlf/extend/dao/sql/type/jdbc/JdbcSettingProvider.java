package com.nlf.extend.dao.sql.type.jdbc;

import com.nlf.App;
import com.nlf.Bean;
import com.nlf.dao.setting.IDbSetting;
import com.nlf.dao.setting.IDbSettingProvider;

/**
 * JDBC连接设置提供器
 *
 * @author 6tail
 */
public class JdbcSettingProvider implements IDbSettingProvider{

  public static final String URL_SEARCH_PREFIX = "?";

  public IDbSetting buildDbSetting(Bean o){
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
    if(extra.length()>0&&!extra.startsWith(URL_SEARCH_PREFIX)){
      extra = URL_SEARCH_PREFIX+extra;
    }
    dbType = dbType.toLowerCase();
    JdbcSetting js = new JdbcSetting();
    js.setAlias(alias);
    js.setPassword(password);
    js.setUser(user);
    js.setDbType(dbType);
    js.setDbName(dbname);
    if(driver.length()>0){
      js.setDriver(driver);
    }else {
      js.setDriver(App.getProperty("nlf.dao.setting." + dbType + ".driver"));
    }
    if(url.length()>0){
      js.setUrl(url);
    }else {
      js.setUrl(App.getProperty("nlf.dao.setting." + dbType + ".url", server, port, dbname) + extra);
    }
    return js;
  }

  public boolean support(String type){
    return "jdbc".equalsIgnoreCase(type);
  }
}