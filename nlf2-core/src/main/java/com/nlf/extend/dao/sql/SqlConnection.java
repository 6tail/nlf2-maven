package com.nlf.extend.dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.nlf.dao.connection.AbstractConnection;
import com.nlf.dao.exception.DaoException;
import com.nlf.util.IOUtil;

/**
 * SQL连接
 * 
 * @author 6tail
 *
 */
public class SqlConnection extends AbstractConnection{
  /** 是否处于批处理中 */
  protected boolean inBatch;
  /** 最近一次操作的statement */
  protected PreparedStatement statement;
  /** 实际的连接 */
  protected Connection connection;

  public SqlConnection(Connection connection){
    this.connection = connection;
    try{
      supportBatchUpdate = connection.getMetaData().supportsBatchUpdates();
    }catch(SQLException e){}
  }

  /**
   * 获取实际的连接
   * 
   * @return 实际的连接
   */
  public Connection getConnection(){
    return connection;
  }

  public void close(){
    IOUtil.closeQuietly(connection);
  }

  public boolean isClosed(){
    try{
      return connection.isClosed();
    }catch(SQLException e){
      throw new DaoException(e);
    }
  }

  public boolean isInBatch(){
    return inBatch;
  }

  public void setInBatch(boolean inBatch){
    this.inBatch = inBatch;
  }

  public PreparedStatement getStatement(){
    return statement;
  }

  public void setStatement(PreparedStatement statement){
    this.statement = statement;
  }
}