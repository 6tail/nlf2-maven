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
   * 带1个参数的where
   * 
   * @param column 列
   * @param value 参数值
   * @return SQL更新器
   */
  ISqlUpdater where(String column,Object value);

  /**
   * 带多个参数的where
   * 
   * @param sql SQL语句，使用冒号加参数名绑定参数，如(age>:age or name=:name)中:age将绑定到values中key为age的值，:name将绑定到values中key为name的值
   * @param param 参数
   * @return SQL更新器
   */
  ISqlUpdater where(String sql,Bean param);

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
   * @param column 列
   * @param value 值
   * @param condition 条件是否满足
   * @return SQL更新器
   */
  ISqlUpdater whereIf(String column,Object value,boolean condition);

  /**
   * 当满足条件时执行where
   * 
   * @param sql SQL语句，使用冒号加参数名绑定参数，如(age>:age or name=:name)中:age将绑定到values中key为age的值，:name将绑定到values中key为name的值
   * @param value Bean
   * @param condition 条件是否满足
   * @return SQL更新器
   */
  ISqlUpdater whereIf(String sql,Bean values,boolean condition);

  ISqlUpdater whereIn(String column,Object... values);
  ISqlUpdater whereNotIn(String column,Object... values);
  ISqlUpdater whereNotEqual(String column,Object value);

  ISqlUpdater set(Bean bean);
  ISqlUpdater set(String sql);
  /**
   * 赋值为sql语句
   * @param sql SQL语句，使用冒号加参数名绑定参数，如(age>:age or name=:name)中:age将绑定到values中key为age的值，:name将绑定到values中key为name的值
   * @param param 参数
   * @return SQL更新器
   */
  ISqlUpdater set(String sql,Bean param);
  ISqlUpdater set(String column,Object value);
  ISqlUpdater setIf(Bean bean,boolean condition);
  ISqlUpdater setIf(String sql,boolean condition);
  ISqlUpdater setIf(String sql,Bean param,boolean condition);
  ISqlUpdater setIf(String column,Object value,boolean condition);

  /**
   * 执行更新操作
   * 
   * @return 受影响的记录数
   */
  int update();

}