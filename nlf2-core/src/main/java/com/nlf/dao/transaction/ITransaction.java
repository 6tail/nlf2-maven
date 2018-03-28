package com.nlf.dao.transaction;

/**
 * 事务接口
 * 
 * @author 6tail
 *
 */
public interface ITransaction{
  /**
   * 提交事务
   */
  void commit();

  /**
   * 回滚事务
   */
  void rollback();

  /**
   * 获取DB连接
   * 
   * @return DB连接
   */
  com.nlf.dao.connection.IConnection getConnection();

  /**
   * 开始批处理
   */
  void startBatch();

  /**
   * 执行批处理
   * @return
   */
  int[] executeBatch();
}