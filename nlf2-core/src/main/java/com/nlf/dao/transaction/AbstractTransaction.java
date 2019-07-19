package com.nlf.dao.transaction;

import com.nlf.dao.connection.IConnection;

/**
 * 抽象事务
 *
 * @author 6tail
 */
public abstract class AbstractTransaction implements ITransaction{
  protected IConnection connection;

  public IConnection getConnection(){
    return connection;
  }

  public void setConnection(IConnection connection){
    this.connection = connection;
  }
}