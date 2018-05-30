package com.nlf.extend.dao.sql;

import com.nlf.Bean;

/**
 * SQL更新器
 * 
 * @author 6tail
 *
 */
public interface ISqlUpdater extends ISqlExecuter{

  /**
   * 指定表
   * 
   * @param tables 表名
   * @return SQL更新器
   */
  ISqlUpdater table(String tables);

  /**
   * 当满足条件时指定表
   * 
   * @param tables 表名
   * @param condition 条件是否满足
   * @return SQL更新器
   */
  ISqlUpdater tableIf(String tables,boolean condition);

  /**
   * 纯SQL语句的where
   * 
   * @param sql SQL语句
   * @return SQL更新器
   */
  ISqlUpdater where(String sql);

  /**
   * 带参数的where
   *
   * @param columnOrSql 列名或SQL语句，SQL语句使用冒号加参数名绑定参数，如(age>:age or name=:name)中:age将绑定到values中key为age的值，:name将绑定到values中key为name的值
   * @param valueOrBean 参数值或Bean，Bean用于给多个参数赋值
   * @return SQL查询器
   */
  ISqlUpdater where(String columnOrSql,Object valueOrBean);

  /**
   * 当满足条件时执行where
   * 
   * @param sql SQL语句
   * @param condition 条件是否满足
   * @return SQL更新器
   */
  ISqlUpdater whereIf(String sql,boolean condition);

  /**
   * 当满足条件时执行where
   *
   * @param columnOrSql 列名或SQL语句，SQL语句使用冒号加参数名绑定参数，如(age>:age or name=:name)中:age将绑定到values中key为age的值，:name将绑定到values中key为name的值
   * @param valueOrBean 参数值或Bean，Bean用于给多个参数赋值
   * @param condition 条件是否满足
   * @return SQL更新器
   */
  ISqlUpdater whereIf(String columnOrSql,Object valueOrBean,boolean condition);

  ISqlUpdater whereIn(String column,Object... values);
  ISqlUpdater whereNotIn(String column,Object... values);
  ISqlUpdater whereNotEqual(String column,Object value);

  ISqlUpdater set(Bean bean);
  ISqlUpdater set(String sql);
  /**
   * 赋值
   * @param columnOrSql 列名或SQL语句，SQL语句使用冒号加参数名绑定参数，如(age>:age or name=:name)中:age将绑定到values中key为age的值，:name将绑定到values中key为name的值
   * @param valueOrBean 参数值或Bean，Bean用于给多个参数赋值
   * @return SQL更新器
   */
  ISqlUpdater set(String columnOrSql,Object valueOrBean);
  ISqlUpdater setIf(Bean bean,boolean condition);
  ISqlUpdater setIf(String sql,boolean condition);

  /**
   * 当满足条件时赋值
   * @param columnOrSql 列名或SQL语句，SQL语句使用冒号加参数名绑定参数，如(age>:age or name=:name)中:age将绑定到values中key为age的值，:name将绑定到values中key为name的值
   * @param valueOrBean 参数值或Bean，Bean用于给多个参数赋值
   * @param condition 条件是否满足
   * @return SQL更新器
   */
  ISqlUpdater setIf(String columnOrSql,Object valueOrBean,boolean condition);

  /**
   * 执行更新操作
   * 
   * @return 受影响的记录数
   */
  int update();

}