package com.nlf.extend.dao.noSql.dbType.common;

import java.util.List;
import java.util.Set;
import com.nlf.dao.paging.PageData;
import com.nlf.dao.transaction.ITransaction;
import com.nlf.extend.dao.noSql.AbstractNoSqlDao;

/**
 * 通用NoSqlDao
 *
 * @author 6tail
 */
public class ANoSqlDao extends AbstractNoSqlDao{
  public boolean set(String key,String value){
    return getExecuter().set(key,value);
  }

  public String get(String key){
    return getExecuter().get(key);
  }

  public boolean support(String dbType){
    return true;
  }

  public ITransaction beginTransaction(){
    return null;
  }

  public long increase(String key){
    return getExecuter().increase(key);
  }

  public long increase(String key,long increment){
    return getExecuter().increase(key,increment);
  }

  public long decrease(String key,long decrement){
    return getExecuter().decrease(key,decrement);
  }

  public long decrease(String key){
    return getExecuter().decrease(key);
  }
  
  public long delete(String... keys){
    return getExecuter().delete(keys);
  }
  
  public boolean exists(String key){
    return getExecuter().exists(key);
  }

  public long expire(String key,int seconds){
    return getExecuter().expire(key,seconds);
  }

  public long ttl(String key){
    return getExecuter().ttl(key);
  }

  public long persist(String key){
    return getExecuter().persist(key);
  }

  public Set<String> keys(String pattern){
    return getExecuter().keys(pattern);
  }

  public long push(String key,String value){
    return getExecuter().push(key,value);
  }

  public String pop(String key){
    return getExecuter().pop(key);
  }

  public String shift(String key){
    return getExecuter().shift(key);
  }

  public long unshift(String key,String value){
    return getExecuter().unshift(key,value);
  }

  public long count(String key){
    return getExecuter().count(key);
  }

  public List<String> list(String key){
    return getExecuter().list(key);
  }

  public List<String> head(String key,int count){
    return getExecuter().head(key,count);
  }

  public List<String> tail(String key,int count){
    return getExecuter().tail(key,count);
  }

  public PageData page(String key,int pageNumber,int pageSize){
    return getExecuter().page(key,pageNumber,pageSize);
  }

  public PageData paging(String key){
    return getExecuter().paging(key);
  }
}