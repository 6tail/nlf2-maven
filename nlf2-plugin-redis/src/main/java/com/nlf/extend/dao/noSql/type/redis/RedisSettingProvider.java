package com.nlf.extend.dao.noSql.type.redis;

import com.nlf.App;
import com.nlf.Bean;
import com.nlf.dao.setting.IDbSetting;
import com.nlf.dao.setting.IDbSettingProvider;

public class RedisSettingProvider implements IDbSettingProvider{

  public IDbSetting buildDbSetting(Bean o){
    String type = o.getString("type","");
    String alias = o.getString("alias","");
    String dbType = o.getString("dbtype","");
    String password = o.getString("password","");
    String server = o.getString("server","");
    int port = o.getInt("port",6379);
    type = type.toUpperCase();
    dbType = dbType.toLowerCase();
    RedisSetting rs = new RedisSetting();
    rs.setAlias(alias);
    rs.setDbType(dbType);
    rs.setDriver(App.getProperty("nlf.dao.setting."+dbType+".driver"));
    rs.setPassword(password);
    int maxTotal = o.getInt("maxTotal",-1);
    if(-1==maxTotal){
      maxTotal = o.getInt("maxActive",-1);
    }
    rs.setMaxTotal(maxTotal);
    rs.setMaxIdle(o.getInt("maxIdle",-1));
    long maxWaitMillis = o.getLong("maxWaitMillis",-1);
    if(-1==maxWaitMillis){
      maxWaitMillis = o.getLong("maxWait",-1);
    }
    rs.setMaxWaitMillis(maxWaitMillis);
    rs.setMinEvictableIdleTimeMillis(o.getLong("minEvictableIdleTimeMillis",-1));
    rs.setMinIdle(o.getInt("minIdle",-1));
    rs.setNumTestsPerEvictionRun(o.getInt("numTestsPerEvictionRun",-1));
    rs.setSoftMinEvictableIdleTimeMillis(o.getLong("softMinEvictableIdleTimeMillis",-1));
    rs.setTestOnBorrow(o.getBoolean("testOnBorrow",false));
    rs.setTestOnReturn(o.getBoolean("testOnReturn",false));
    rs.setTestWhileIdle(o.getBoolean("testWhileIdle",false));
    rs.setTimeBetweenEvictionRunsMillis(o.getLong("timeBetweenEvictionRunsMillis",-1));
    rs.setTimeOut(o.getInt("timeOut",-1));
    rs.setType(type);
    boolean blockWhenExhausted = o.getBoolean("blockWhenExhausted",false);
    if(!blockWhenExhausted) {
      blockWhenExhausted = o.getBoolean("whenExhaustedAction",false);
    }
    rs.setBlockWhenExhausted(blockWhenExhausted);
    rs.setHost(server);
    rs.setPort(port);
    return rs;
  }

  public boolean support(String type){
    return "redis".equalsIgnoreCase(type);
  }
}