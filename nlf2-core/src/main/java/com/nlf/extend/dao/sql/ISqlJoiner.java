package com.nlf.extend.dao.sql;

/**
 * SQL JOIN封装器
 * @author 6tail
 */
public interface ISqlJoiner {

  /**
   * inner join
   * @param table 表名
   * @return JOIN封装器
   */
  ISqlJoiner innerJoin(String table);

  /**
   * left outer join
   * @param table 表名
   * @return JOIN封装器
   */
  ISqlJoiner leftJoin(String table);

  /**
   * right outer join
   * @param table 表名
   * @return JOIN封装器
   */
  ISqlJoiner rightJoin(String table);

  /**
   * cross join
   * @param table 表名
   * @return JOIN封装器
   */
  ISqlJoiner crossJoin(String table);

  /**
   * join条件
   * @param sql SQL语句
   * @return JOIN封装器
   */
  ISqlJoiner on(String sql);

  /**
   * 获取SQL查询器
   * @return SQL查询器
   */
  ISqlSelecter getSelecter();

  /**
   * 设置SQL查询器
   * @param selecter SQL查询器
   */
  void setSelecter(ISqlSelecter selecter);
}
