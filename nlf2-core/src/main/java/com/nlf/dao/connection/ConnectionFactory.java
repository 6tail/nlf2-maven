package com.nlf.dao.connection;

import com.nlf.App;
import com.nlf.core.Statics;
import com.nlf.dao.setting.DbSettingFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 连接工厂
 *
 * @author 6tail
 */
public class ConnectionFactory{

  /** 连接提供器缓存 */
  protected static final Map<String,IConnectionProvider> POOL = new HashMap<String,IConnectionProvider>();

  protected ConnectionFactory(){}

  /**
   * 清理连接
   */
  public static void closeAll(){
    Map<String,IConnection> connections = App.get(Statics.CONNECTIONS);
    if(null!=connections){
      for(IConnection connection:connections.values()){
        connection.close();
      }
    }
  }

  /**
   * 根据别名获取连接
   *
   * @param alias 别名
   * @return 连接
   */
  public static IConnection getConnection(String alias){
    Map<String,IConnection> connections = App.get(Statics.CONNECTIONS);
    if(null==connections){
      connections = new HashMap<String,IConnection>(2);
      App.set(Statics.CONNECTIONS,connections);
    }
    IConnection connection = connections.get(alias);
    if(null!=connection&&!connection.isClosed()){
      return connection;
    }
    com.nlf.dao.setting.IDbSetting setting = DbSettingFactory.getSetting(alias);
    String type = setting.getType();
    if(!POOL.containsKey(alias)){
      java.util.List<String> impls = App.getImplements(IConnectionProvider.class);
      for(String impl:impls){
        IConnectionProvider p = App.getProxy().newInstance(impl);
        if(p.support(type)){
          POOL.put(alias,p);
          p.setDbSetting(setting);
          IConnection conn = p.getConnection();
          connections.put(alias,conn);
          return conn;
        }
      }
      POOL.put(alias,null);
    }else{
      IConnectionProvider p = POOL.get(alias);
      if(null!=p){
        IConnection conn = p.getConnection();
        connections.put(alias,conn);
        return conn;
      }
    }
    throw new com.nlf.dao.exception.DaoException(App.getProperty("nlf.exception.dao.connection.provider.not_found",type));
  }

  /**
   * 获取默认连接
   *
   * @return 默认连接
   */
  public static IConnection getConnection(){
    return getConnection(DbSettingFactory.getDefaultSetting().getAlias());
  }
}
