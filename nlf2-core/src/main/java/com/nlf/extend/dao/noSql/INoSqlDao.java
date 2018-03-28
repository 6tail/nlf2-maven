package com.nlf.extend.dao.noSql;

import java.util.List;
import java.util.Set;
import com.nlf.dao.IDao;
import com.nlf.dao.paging.PageData;

/**
 * NoSqlDao接口
 * 
 * @author 6tail
 *
 */
public interface INoSqlDao extends IDao{
  /**
   * 设置值
   * 
   * @param key 键
   * @param value 值
   * @return 如果设置成功返回true，否则返回false
   */
  boolean set(String key,String value);

  /**
   * 获取值
   * 
   * @param key 键
   * @return 值，如果不存在返回null
   */
  String get(String key);

  /**
   * 加1
   * @param key 键
   * @return 加1后的值，如果键不存在，先赋值为0，再加1
   */
  long increase(String key);

  /**
   * 增加
   * @param key 键
   * @param increment 数量
   * @return 增加后的值，如果键不存在，先赋值为0，再增加
   */
  long increase(String key,long increment);

  /**
   * 减少
   * @param key 键
   * @param increment 数量
   * @return 减少后的值，如果键不存在，先赋值为0，再减少
   */
  long decrease(String key,long decrement);

  /**
   * 减1
   * @param key 键
   * @return 减1后的值，如果键不存在，先赋值为0，再减1
   */
  long decrease(String key);

  /**
   * 删除
   * @param keys 键
   * @return 删除数量
   */
  long delete(String... keys);

  /**
   * 是否存在
   * @param key 键
   * @return true存在，false不存在
   */
  boolean exists(String key);

  /**
   * 设置过期时间
   * @param key 键
   * @param seconds 秒数
   * @return
   */
  long expire(String key,int seconds);

  /**
   * 获取剩余生存时间
   * @param key 键
   * @return 剩余生存秒数
   */
  long ttl(String key);

  /**
   * 移除过期时间限制
   * @param key 键
   * @return
   */
  long persist(String key);

  /**
   * 获取符合条件的键们
   * @param pattern 键
   * @return 键们
   */
  Set<String> keys(String pattern);

  /**
   * 在集合末尾添加元素
   * @param key 键
   * @param value 值
   * @return
   */
  long push(String key,String value);

  /**
   * 从集合中把最后一个元素删除，并返回这个元素的值。
   * @param key 键
   * @return 元素值
   */
  String pop(String key);

  /**
   * 从集合中把第一个元素删除，并返回这个元素的值。
   * @param key 键
   * @return 元素值
   */
  String shift(String key);

  /**
   * 在集合头部添加元素
   * @param key 键
   * @param value 值
   * @return
   */
  long unshift(String key,String value);

  /**
   * 获取集合长度
   * @param key 键
   * @return 长度
   */
  long count(String key);

  /**
   * 获取集合全部数据
   * @param key 键
   * @return 列表
   */
  List<String> list(String key);

  /**
   * 获取集合头部的数据
   * @param key 键
   * @param count 记录条数
   * @return 列表
   */
  List<String> head(String key,int count);

  /**
   * 获取集合尾部的数据
   * @param key 键
   * @param count 记录条数
   * @return 列表
   */
  List<String> tail(String key,int count);

  /**
   * 集合分页
   * @param key 键
   * @param pageNumber 页码，从1开始
   * @param pageSize 每页大小
   * @return
   */
  PageData page(String key,int pageNumber,int pageSize);

  /**
   * 集合全自动分页
   * @param key 键
   * @return
   */
  PageData paging(String key);
}