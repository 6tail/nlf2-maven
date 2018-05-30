package com.nlf.extend.dao.sql;

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
   * @return SQL删除器
   */
  ISqlDeleter table(String tables);

  /**
   * 当满足条件时指定表
   * 
   * @param tables 表名
   * @param condition 条件是否满足
   * @return SQL删除器
   */
  ISqlDeleter tableIf(String tables,boolean condition);

  /**
   * 纯SQL语句的where
   * 
   * @param sql SQL语句
   * @return SQL删除器
   */
  ISqlDeleter where(String sql);

  /**
   * 带参数的where
   *
   * @param columnOrSql 列名或SQL语句，SQL语句使用冒号加参数名绑定参数，如(age>:age or name=:name)中:age将绑定到values中key为age的值，:name将绑定到values中key为name的值
   * @param valueOrBean 参数值或Bean，Bean用于给多个参数赋值
   * @return SQL删除器
   */
  ISqlDeleter where(String columnOrSql,Object valueOrBean);

  /**
   * 当满足条件时执行where
   * 
   * @param sql SQL语句
   * @param condition 条件是否满足
   * @return SQL删除器
   */
  ISqlDeleter whereIf(String sql,boolean condition);

  /**
   * 当满足条件时执行where
   *
   * @param columnOrSql 列名或SQL语句，SQL语句使用冒号加参数名绑定参数，如(age>:age or name=:name)中:age将绑定到values中key为age的值，:name将绑定到values中key为name的值
   * @param valueOrBean 参数值或Bean，Bean用于给多个参数赋值
   * @param condition 条件是否满足
   * @return SQL删除器
   */
  ISqlDeleter whereIf(String columnOrSql,Object valueOrBean,boolean condition);

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