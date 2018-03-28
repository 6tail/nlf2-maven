package com.nlf.extend.dao.noSql.type.redis;

import redis.clients.jedis.Jedis;
import com.nlf.dao.connection.AbstractConnection;

/**
 * Redis连接
 * 
 * @author 6tail
 *
 */
public class RedisConnection extends AbstractConnection{
  /** 实际的连接 */
  protected Jedis connection;
  /** 连接是否已关闭 */
  protected boolean closed;

  public RedisConnection(Jedis connection){
    this.connection = connection;
  }

  /**
   * 获取实际的连接
   * 
   * @return 实际的连接
   */
  public Jedis getConnection(){
    return connection;
  }

  public void close(){
    RedisDriver.closeQuietly(connection);
    closed = true;
  }

  public boolean isClosed(){
    return closed;
  }
}