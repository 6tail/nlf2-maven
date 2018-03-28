package com.nlf.extend.dao.sql.type.proxool;

import com.nlf.App;
import com.nlf.Bean;
import com.nlf.dao.setting.IDbSetting;
import com.nlf.dao.setting.IDbSettingProvider;

public class ProxoolSettingProvider implements IDbSettingProvider{

  public IDbSetting buildDbSetting(Bean o){
    String type = o.getString("type","");
    String alias = o.getString("alias","");
    String dbType = o.getString("dbtype","");
    String user = o.getString("user","");
    String password = o.getString("password","");
    String server = o.getString("server","");
    String port = o.getString("port","");
    String dbname = o.getString("dbname","");
    type = type.toUpperCase();
    dbType = dbType.toLowerCase();
    ProxoolSetting ps = new ProxoolSetting();
    ps.setAlias(alias);
    ps.setDriver(App.getProperty("nlf.dao.setting."+dbType+".driver"));
    ps.setPassword(password);
    ps.setUrl(App.getProperty("nlf.dao.setting."+dbType+".url",server,port,dbname));
    ps.setUser(user);
    ps.setDbType(dbType);
    ps.setDbName(dbname);
    ps.setPrototypeCount(o.getInt("prototypeCount",-1));
    ps.setHouseKeepingSleepTime(o.getInt("houseKeepingSleepTime",-1));
    ps.setMaximumActiveTime(o.getLong("maximumActiveTime",-1));
    ps.setMaximumConnectionLifeTime(o.getInt("maximumConnectionLifeTime",-1));
    ps.setMaximumConnectionCount(o.getInt("maximumConnectionCount",-1));
    ps.setMinimumConnectionCount(o.getInt("minimumConnectionCount",-1));
    ps.setSimultaneousBuildThrottle(o.getInt("simultaneousBuildThrottle",-1));
    ps.setTestBeforeUse(o.getBoolean("testBeforeUse",true));
    ps.setTestAfterUse(o.getBoolean("testAfterUse",true));
    return ps;
  }

  public boolean support(String type){
    return "proxool".equalsIgnoreCase(type);
  }
}