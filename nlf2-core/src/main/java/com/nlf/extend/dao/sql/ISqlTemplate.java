package com.nlf.extend.dao.sql;

import java.util.Iterator;
import java.util.List;
import com.nlf.Bean;
import com.nlf.dao.paging.PageData;

/**
 * SQL模板
 * 
 * @author 6tail
 *
 */
public interface ISqlTemplate extends ISqlExecuter{
  /**
   * 拼接SQL语句
   * 
   * @param sql SQL语句，使用:key对应到参数key，如select * from user where user_name=:name
   * @return SQL模板
   */
  ISqlTemplate sql(String sql);

  /**
   * 当满足条件时拼接该SQL语句
   * @param sql SQL语句，使用:key对应到参数key，如select * from user where user_name=:name
   * @param condition 条件是否满足
   * @return SQL模板
   */
  ISqlTemplate sqlIf(String sql,boolean condition);

  /**
   * 绑定参数
   * 
   * @param param 参数，如new Bean("name","六特尔")
   * @return SQL模板
   */
  ISqlTemplate param(Bean param);

  /**
   * 绑定参数
   * 
   * @param key 参数名，如SQL语句中为:name，则参数名为name
   * @param value 参数值
   * @return SQL模板
   */
  ISqlTemplate param(String key,Object value);

  /**
   * 查询
   * 
   * @return Bean们
   */
  List<Bean> query();

  /**
   * 查询并返回指定条数的记录
   * @param count 记录数
   * @return Bean们
   */
  List<Bean> top(int count);

  /**
   * 查询并返回第一条记录
   * @return Bean
   */
  Bean topOne();

  /**
   * 在有且只有一条记录时直接获取该记录
   * @return Bean
   */
  Bean one();

  /**
   * 查询记录数
   * @return 记录数
   */
  int count();

  /**
   * 分页
   * @param pageNumber 页码，从1开始
   * @param pageSize 每页记录数
   * @return 分页数据
   */
  PageData page(int pageNumber,int pageSize);

  /**
   * 全自动分页
   * @return 分页数据
   */
  PageData paging();

  /**
   * 更新
   * @return 更新记录数
   */
  int update();

  /**
   * 迭代结果集
   * @return 结果集迭代器
   */
  Iterator<Bean> iterator();
}