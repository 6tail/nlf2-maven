package com.nlf.extend.dao.noSql.dbType.redis;

import com.nlf.Bean;
import com.nlf.dao.paging.PageData;
import com.nlf.extend.dao.noSql.AbstractNoSqlExecuter;
import com.nlf.extend.dao.noSql.type.redis.RedisConnection;
import com.nlf.serialize.json.JSON;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class RedisExecuter extends AbstractNoSqlExecuter{

  public boolean set(String key,String value){
    Jedis jedis = ((RedisConnection)connection).getConnection();
    String s = jedis.set(key,value);
    return "OK".equalsIgnoreCase(s);
  }

  public String get(String key){
    Jedis jedis = ((RedisConnection)connection).getConnection();
    return jedis.get(key);
  }
  
  public long increase(String key){
    Jedis jedis = ((RedisConnection)connection).getConnection();
    return jedis.incr(key);
  }
  
  public long decrease(String key){
    Jedis jedis = ((RedisConnection)connection).getConnection();
    return jedis.decr(key);
  }

  public long increase(String key,long increment){
    Jedis jedis = ((RedisConnection)connection).getConnection();
    return jedis.incrBy(key,increment);
  }

  public long decrease(String key,long decrement){
    Jedis jedis = ((RedisConnection)connection).getConnection();
    return jedis.decrBy(key,decrement);
  }

  public long delete(String... keys){
    Jedis jedis = ((RedisConnection)connection).getConnection();
    return jedis.del(keys);
  }

  public boolean exists(String key){
    Jedis jedis = ((RedisConnection)connection).getConnection();
    return jedis.exists(key);
  }

  public long expire(String key,int seconds){
    Jedis jedis = ((RedisConnection)connection).getConnection();
    return jedis.expire(key,seconds);
  }

  public long ttl(String key){
    Jedis jedis = ((RedisConnection)connection).getConnection();
    return jedis.ttl(key);
  }

  public long persist(String key){
    Jedis jedis = ((RedisConnection)connection).getConnection();
    return jedis.persist(key);
  }

  public Set<String> keys(String pattern){
    Jedis jedis = ((RedisConnection)connection).getConnection();
    return jedis.keys(pattern);
  }

  public long push(String key,String value){
    Jedis jedis = ((RedisConnection)connection).getConnection();
    return jedis.rpush(key,value);
  }

  public String pop(String key){
    Jedis jedis = ((RedisConnection)connection).getConnection();
    return jedis.rpop(key);
  }

  public String shift(String key){
    Jedis jedis = ((RedisConnection)connection).getConnection();
    return jedis.lpop(key);
  }

  public long unshift(String key,String value){
    Jedis jedis = ((RedisConnection)connection).getConnection();
    return jedis.lpush(key,value);
  }

  public long count(String key){
    Jedis jedis = ((RedisConnection)connection).getConnection();
    return jedis.llen(key);
  }

  public List<String> list(String key){
    Jedis jedis = ((RedisConnection)connection).getConnection();
    long size = jedis.llen(key);
    if(size<1){
      return new ArrayList<String>(0);
    }
    return jedis.lrange(key,0,size-1);
  }

  public PageData page(String key,int pageNumber,int pageSize){
    PageData d = new PageData();
    d.setPageSize(pageSize);
    d.setPageNumber(pageNumber);
    d.setRecordCount((int)count(key));
    if(d.getPageNumber()>d.getPageCount()){
      return d;
    }
    Jedis jedis = ((RedisConnection)connection).getConnection();
    List<String> l = jedis.lrange(key,(d.getPageNumber()-1)*d.getPageSize(),d.getPageNumber()*d.getPageSize()-1);
    List<Bean> datas = new ArrayList<Bean>(l.size());
    for(String s:l){
      Bean o = null;
      try{
        o = JSON.toBean(s);
      }catch(Exception e){
        o = new Bean("value",s);
      }
      datas.add(o);
    }
    d.setData(datas);
    return d;
  }

  public List<String> head(String key,int count){
    Jedis jedis = ((RedisConnection)connection).getConnection();
    long size = jedis.llen(key);
    long to = count;
    if(to>size) {
      to = size;
    }
    if(to<1){
      return new ArrayList<String>(0);
    }
    return jedis.lrange(key,0,to-1);
  }

  public List<String> tail(String key,int count){
    Jedis jedis = ((RedisConnection)connection).getConnection();
    long size = jedis.llen(key);
    long from = size-count;
    if(from>=size) {
      return new ArrayList<String>(0);
    }
    if(from<0) {
      from = 0;
    }
    List<String> l = jedis.lrange(key,from,size-1);
    Collections.reverse(l);
    return l;
  }

}