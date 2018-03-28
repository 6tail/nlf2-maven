package com.nlf.extend.dao.sql.type.hikari;

import java.util.Map.Entry;
import com.nlf.App;
import com.nlf.Bean;
import com.nlf.dao.setting.IDbSetting;
import com.nlf.dao.setting.IDbSettingProvider;

/**
 * HikariCP连接池配置提供器
 * @author 6tail
 *
 */
public class HikariSettingProvider implements IDbSettingProvider{

  public IDbSetting buildDbSetting(Bean o){
    String type = o.getString("type","");
    String alias = o.getString("alias","");
    String dbType = o.getString("dbtype","");
    String user = o.getString("user","");
    String password = o.getString("password","");
    String server = o.getString("server","");
    int port = o.getInt("port",0);
    String dbname = o.getString("dbname","");
    type = type.toUpperCase();
    dbType = dbType.toLowerCase();
    HikariSetting ps = new HikariSetting();
    ps.setAlias(alias);
    ps.setServer(server);
    ps.setPort(port);
    ps.setDriver(HikariDriver.class.getName());
    ps.setPassword(password);
    ps.setUrl(App.getProperty("nlf.dao.setting."+dbType+".url",server,port+"",dbname));
    ps.setUser(user);
    ps.setDbType(dbType);
    ps.setDbName(dbname);
    ps.setMinimumIdle(o.getInt("minimumIdle",-1));
    ps.setMaximumPoolSize(o.getInt("maximumPoolSize",-1));
    ps.setConnectionTestQuery(o.getString("testSql"));
    ps.setConnectionTimeout(o.getLong("connectionTimeout",-1));
    ps.setIdleTimeout(o.getLong("idleTimeout",-1));
    ps.setMaxLifetime(o.getLong("maxLifeTime",-1));
    Bean properties = o.getBean("properties");
    if(null!=properties){
      for(Entry<String,Object> en:properties.entrySet()){
        ps.setProperty(en.getKey(),en.getValue());
      }
    }
    ps.setDataSourceClassName(App.getProperty("nlf.dao.setting."+dbType+".dataSource"));
    return ps;
  }

  public boolean support(String type){
    return "HikariCP".equalsIgnoreCase(type);
  }
}