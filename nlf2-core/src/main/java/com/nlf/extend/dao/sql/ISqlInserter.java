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
   * @return SQL查询器
   */
  ISqlInserter table(String tables);

  /**
   * 当满足条件时指定表
   * 
   * @param tables 表名
   * @param condition 条件是否满足
   * @return SQL查询器
   */
  ISqlInserter tableIf(String tables,boolean condition);

  ISqlInserter set(Bean bean);
  ISqlInserter set(String sql);
  ISqlInserter set(String sql,Bean param);
  ISqlInserter set(String column,Object value);

  ISqlInserter setIf(Bean bean,boolean condition);
  ISqlInserter setIf(String sql,boolean condition);
  ISqlInserter setIf(String sql,Bean param,boolean condition);
  ISqlInserter setIf(String column,Object value,boolean condition);

  /**
   * 执行删除操作
   * 
   * @return 受影响的记录数
   */
  int insert();

}