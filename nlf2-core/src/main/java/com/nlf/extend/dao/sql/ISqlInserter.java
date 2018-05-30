package com.nlf.extend.dao.sql;

import com.nlf.Bean;


/**
 * SQL插入器
 * 
 * @author 6tail
 *
 */
public interface ISqlInserter extends ISqlExecuter{

  /**
   * 指定表
   * 
   * @param tables 表名
   * @return SQL插入器
   */
  ISqlInserter table(String tables);

  /**
   * 当满足条件时指定表
   * 
   * @param tables 表名
   * @param condition 条件是否满足
   * @return SQL插入器
   */
  ISqlInserter tableIf(String tables,boolean condition);

  ISqlInserter set(Bean bean);

  /**
   * 赋值
   * @param column 列名
   * @param value 参数值
   * @return SQL插入器
   */
  ISqlInserter set(String column,Object value);

  ISqlInserter setIf(Bean bean,boolean condition);

  /**
   * 当满足条件时赋值
   * @param column 列名
   * @param value 参数值
   * @param condition 条件是否满足
   * @return SQL插入器
   */
  ISqlInserter setIf(String column,Object value,boolean condition);

  /**
   * 执行删除操作
   * 
   * @return 受影响的记录数
   */
  int insert();

}