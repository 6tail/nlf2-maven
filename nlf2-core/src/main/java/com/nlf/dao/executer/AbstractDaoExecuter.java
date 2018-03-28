package com.nlf.dao.executer;

import com.nlf.dao.connection.IConnection;

/**
 * 抽象Dao执行器，所有的Dao执行器实现都应该继承俺
 * 
 * @author 6tail
 *
 */
public abstract class AbstractDaoExecuter implements IDaoExecuter{
  /** DB连接 */
  protected IConnection connection;

  /**
   * 供子类绑定DB连接接口
   * 
   * @param connection DB连接接口
   */
  public void setConnection(IConnection connection){
    this.connection = connection;
  }
}