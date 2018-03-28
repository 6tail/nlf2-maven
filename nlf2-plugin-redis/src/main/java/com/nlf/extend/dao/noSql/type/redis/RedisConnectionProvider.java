package com.nlf.extend.dao.noSql.type.redis;

import redis.clients.jedis.Jedis;
import com.nlf.App;
import com.nlf.dao.connection.AbstractConnectionProvider;
import com.nlf.dao.connection.IConnection;
import com.nlf.dao.exception.DaoException;

/**
 * Redis连接提供器
 *
 * @author 6tail
 *
 */
public class RedisConnectionProvider extends AbstractConnectionProvider{

  public IConnection getConnection(){
    RedisSetting setting = (RedisSetting)this.setting;
    Jedis conn = RedisDriver.getJedis(setting);
    if(null==conn){
      throw new DaoException(App.getProperty("nlf.exception.dao.connection.redis.no_avaliable"));
    }
    RedisConnection rc = new RedisConnection(conn);
    rc.setDbSetting(setting);
    return rc;
  }

  public boolean support(String type){
    return "redis".equalsIgnoreCase(type);
  }

}