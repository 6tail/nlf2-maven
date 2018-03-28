package com.nlf.extend.dao.noSql.type.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisDriver{
  private static JedisPool jedisPool = null;
  static{
    init();
  }

  private static void init(){}

  private synchronized static void initPool(RedisSetting setting){
    if(null!=jedisPool) return;
    JedisPoolConfig config = new JedisPoolConfig();
    if(-1!=setting.getMaxTotal()){
      config.setMaxActive(setting.getMaxTotal());
    }
    if(-1!=setting.getMaxIdle()){
      config.setMaxIdle(setting.getMaxIdle());
    }
    if(-1!=setting.getMaxWaitMillis()){
      config.setMaxWait(setting.getMaxWaitMillis());
    }
    if(-1!=setting.getMinEvictableIdleTimeMillis()){
      config.setMinEvictableIdleTimeMillis(setting.getMinEvictableIdleTimeMillis());
    }
    if(-1!=setting.getMinIdle()){
      config.setMinIdle(setting.getMinIdle());
    }
    if(-1!=setting.getNumTestsPerEvictionRun()){
      config.setNumTestsPerEvictionRun(setting.getNumTestsPerEvictionRun());
    }
    if(-1!=setting.getSoftMinEvictableIdleTimeMillis()){
      config.setSoftMinEvictableIdleTimeMillis(setting.getSoftMinEvictableIdleTimeMillis());
    }
    if(-1!=setting.getTimeBetweenEvictionRunsMillis()){
      config.setTimeBetweenEvictionRunsMillis(setting.getTimeBetweenEvictionRunsMillis());
    }
    config.setWhenExhaustedAction((byte)(setting.isBlockWhenExhausted()?1:0));
    config.setTestOnBorrow(setting.isTestOnBorrow());
    config.setTestOnReturn(setting.isTestOnReturn());
    config.setTestWhileIdle(setting.isTestWhileIdle());
    String password = setting.getPassword();
    if(null==password||password.length()<1) {
      jedisPool = new JedisPool(config, setting.getHost(), setting.getPort(), setting.getTimeOut());
    }else{
      jedisPool = new JedisPool(config, setting.getHost(), setting.getPort(), setting.getTimeOut(),password);
    }
  }

  /**
   * 获取Jedis实例
   * 
   * @return
   */
  public static Jedis getJedis(RedisSetting setting){
    initPool(setting);
    try{
      return jedisPool.getResource();
    }catch(Exception e){
      e.printStackTrace();
      return null;
    }
  }

  /**
   * 释放jedis资源
   * 
   * @param jedis
   */
  public static void closeQuietly(final Jedis jedis){
    if(null==jedisPool) return;
    if(null==jedis) return;
    try{
      jedisPool.returnResource(jedis);
    }catch(Exception e){}
  }
}