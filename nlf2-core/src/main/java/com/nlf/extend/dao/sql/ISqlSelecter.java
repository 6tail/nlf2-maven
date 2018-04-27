package com.nlf.extend.dao.sql;

import java.util.Iterator;
import java.util.List;
import com.nlf.Bean;
import com.nlf.dao.exception.DaoException;
import com.nlf.dao.paging.PageData;

/**
 * SQL查询器
 * 
 * @author 6tail
 *
 */
public interface ISqlSelecter extends ISqlExecuter{

  /**
   * 指定表
   * 
   * @param tables 表名
   * @return SQL查询器
   */
  ISqlSelecter table(String tables);

  /**
   * 当满足条件时指定表
   * 
   * @param tables 表名
   * @param condition 条件是否满足
   * @return SQL查询器
   */
  ISqlSelecter tableIf(String tables,boolean condition);

  /**
   * 指定列
   * 
   * @param columns 列名
   * @return SQL查询器
   */
  ISqlSelecter column(String columns);

  /**
   * 当满足条件时指定列
   * 
   * @param columns 列名
   * @param condition 条件是否满足
   * @return SQL查询器
   */
  ISqlSelecter columnIf(String columns,boolean condition);

  /**
   * 纯SQL语句的where
   * 
   * @param sql SQL语句
   * @return SQL查询器
   */
  ISqlSelecter where(String sql);

  /**
   * 带1个参数的where
   * 
   * @param column 列
   * @param value 参数值
   * @return SQL查询器
   */
  ISqlSelecter where(String column,Object value);

  /**
   * 带多个参数的where
   * 
   * @param sql SQL语句，使用冒号加参数名绑定参数，如(age>:age or name=:name)中:age将绑定到values中key为age的值，:name将绑定到values中key为name的值
   * @param param 参数
   * @return SQL查询器
   */
  ISqlSelecter where(String sql,Bean param);

  /**
   * 当满足条件时执行where
   * 
   * @param sql SQL语句
   * @param condition 条件是否满足
   * @return SQL查询器
   */
  ISqlSelecter whereIf(String sql,boolean condition);

  /**
   * 当满足条件时执行where
   * 
   * @param column 列
   * @param value 值
   * @param condition 条件是否满足
   * @return SQL查询器
   */
  ISqlSelecter whereIf(String column,Object value,boolean condition);

  /**
   * 当满足条件时执行where
   * 
   * @param sql SQL语句，使用冒号加参数名绑定参数，如(age>:age or name=:name)中:age将绑定到values中key为age的值，:name将绑定到values中key为name的值
   * @param param 参数
   * @param condition 条件是否满足
   * @return SQL查询器
   */
  ISqlSelecter whereIf(String sql,Bean param,boolean condition);

  ISqlSelecter whereIn(String column,Object... values);
  ISqlSelecter whereNotIn(String column,Object... values);
  ISqlSelecter whereNotEqual(String column,Object value);

  ISqlSelecter groupBy(String columns);

  ISqlSelecter groupByIf(String columns,boolean condition);

  /**
   * 纯SQL语句的having
   * 
   * @param sql SQL语句
   * @return SQL查询器
   */
  ISqlSelecter having(String sql);

  /**
   * 带1个参数的having
   * 
   * @param column 列
   * @param value 参数值
   * @return SQL查询器
   */
  ISqlSelecter having(String column,Object value);

  /**
   * 带多个参数的having
   * 
   * @param sql SQL语句，使用冒号加参数名绑定参数，如(age>:age or name=:name)中:age将绑定到values中key为age的值，:name将绑定到values中key为name的值
   * @param param 参数
   * @return SQL查询器
   */
  ISqlSelecter having(String sql,Bean param);

  /**
   * 当满足条件时执行having
   * 
   * @param sql SQL语句
   * @param condition 条件是否满足
   * @return SQL查询器
   */
  ISqlSelecter havingIf(String sql,boolean condition);

  /**
   * 当满足条件时执行having
   * 
   * @param column 列
   * @param value 值
   * @param condition 条件是否满足
   * @return SQL查询器
   */
  ISqlSelecter havingIf(String column,Object value,boolean condition);

  /**
   * 当满足条件时执行having
   * 
   * @param sql SQL语句，使用冒号加参数名绑定参数，如(age>:age or name=:name)中:age将绑定到values中key为age的值，:name将绑定到values中key为name的值
   * @param param 参数
   * @param condition 条件是否满足
   * @return SQL查询器
   */
  ISqlSelecter havingIf(String sql,Bean param,boolean condition);

  ISqlSelecter asc(String columns);

  ISqlSelecter ascIf(String columns,boolean condition);

  ISqlSelecter desc(String columns);

  ISqlSelecter descIf(String columns,boolean condition);

  /**
   * 查询
   * 
   * @return Bean列表
   */
  List<Bean> query();

  List<Bean> top(int count);

  Bean topOne();

  /**
   * 获取一条记录，如果未获取到，抛出异常，如果获取到多条记录，返回第一条
   * 
   * @return Bean
   * @throws DaoException 数据异常
   */
  Bean one();

  int count();

  PageData page(int pageNumber,int pageSize);
  
  /**
   * 全自动分页
   * @return 分页
   */
  PageData paging();

  /**
   * 迭代结果集
   * @return 结果集迭代器
   */
  Iterator<Bean> iterator();

}