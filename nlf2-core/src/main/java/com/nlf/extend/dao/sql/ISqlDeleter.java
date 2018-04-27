package com.nlf.extend.dao.sql;

import com.nlf.Bean;

/**
 * SQL删除器
 * 
 * @author 6tail
 *
 */
public interface ISqlDeleter extends ISqlExecuter{

  /**
   * 指定表
   * 
   * @param tables 表名
   * @return SQL查询器
   */
  ISqlDeleter table(String tables);

  /**
   * 当满足条件时指定表
   * 
   * @param tables 表名
   * @param condition 条件是否满足
   * @return SQL查询器
   */
  ISqlDeleter tableIf(String tables,boolean condition);

  /**
   * 纯SQL语句的where
   * 
   * @param sql SQL语句
   * @return SQL查询器
   */
  ISqlDeleter where(String sql);

  /**
   * 带1个参数的where
   * 
   * @param column 列
   * @param value 参数值
   * @return SQL查询器
   */
  ISqlDeleter where(String column,Object value);

  /**
   * 带多个参数的where
   * 
   * @param sql SQL语句，使用冒号加参数名绑定参数，如(age>:age or name=:name)中:age将绑定到values中key为age的值，:name将绑定到values中key为name的值
   * @param param 参数
   * @return SQL查询器
   */
  ISqlDeleter where(String sql,Bean param);

  /**
   * 当满足条件时执行where
   * 
   * @param sql SQL语句
   * @param condition 条件是否满足
   * @return SQL查询器
   */
  ISqlDeleter whereIf(String sql,boolean condition);

  /**
   * 当满足条件时执行where
   * 
   * @param column 列
   * @param value 值
   * @param condition 条件是否满足
   * @return SQL查询器
   */
  ISqlDeleter whereIf(String column,Object value,boolean condition);

  /**
   * 当满足条件时执行where
   * 
   * @param sql SQL语句，使用冒号加参数名绑定参数，如(age>:age or name=:name)中:age将绑定到values中key为age的值，:name将绑定到values中key为name的值
   * @param values Bean
   * @param condition 条件是否满足
   * @return SQL查询器
   */
  ISqlDeleter whereIf(String sql,Bean values,boolean condition);

  ISqlDeleter whereIn(String column,Object... values);
  ISqlDeleter whereNotIn(String column,Object... values);
  ISqlDeleter whereNotEqual(String column,Object value);

  /**
   * 执行删除操作
   * 
   * @return 受影响的记录数
   */
  int delete();

}