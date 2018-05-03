package com.nlf.extend.dao.sql.type.jdbc;

import com.nlf.App;
import com.nlf.Bean;
import com.nlf.dao.setting.IDbSetting;
import com.nlf.dao.setting.IDbSettingProvider;

public class JdbcSettingProvider implements IDbSettingProvider{

  public IDbSetting buildDbSetting(Bean o){
    String alias = o.getString("alias","");
    String dbType = o.getString("dbtype","");
    String user = o.getString("user","");
    String password = o.getString("password","");
    String server = o.getString("server","");
    String port = o.getString("port","");
    String dbname = o.getString("dbname","");
    dbType = dbType.toLowerCase();
    JdbcSetting js = new JdbcSetting();
    js.setAlias(alias);
    js.setDriver(App.getProperty("nlf.dao.setting."+dbType+".driver"));
    js.setPassword(password);
    js.setUrl(App.getProperty("nlf.dao.setting."+dbType+".url",server,port,dbname));
    js.setUser(user);
    js.setDbType(dbType);
    js.setDbName(dbname);
    return js;
  }

  public boolean support(String type){
    return "jdbc".equalsIgnoreCase(type);
  }
}