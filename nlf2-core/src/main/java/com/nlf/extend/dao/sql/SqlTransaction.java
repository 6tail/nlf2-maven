package com.nlf.extend.dao.sql;

import java.sql.SQLException;
import com.nlf.App;
import com.nlf.dao.exception.DaoException;
import com.nlf.dao.transaction.AbstractTransaction;
import com.nlf.util.IOUtil;

/**
 * SQL事务
 * 
 * @author 6tail
 *
 */
public class SqlTransaction extends AbstractTransaction{
  public void commit(){
    try{
      SqlConnection conn = (SqlConnection)connection;
      conn.getConnection().commit();
      conn.setInBatch(false);
    }catch(SQLException e){
      throw new DaoException(e);
    }
  }

  public void rollback(){
    try{
      SqlConnection conn = (SqlConnection)connection;
      conn.getConnection().rollback();
      conn.setInBatch(false);
    }catch(SQLException e){
      throw new DaoException(e);
    }
  }

  public void startBatch(){
    SqlConnection conn = (SqlConnection)connection;
    if(!conn.supportsBatchUpdates()){
      throw new DaoException(App.getProperty("nlf.exception.dao.batch_not_support",conn.getDbSetting().getAlias()));
    }
    conn.setStatement(null);
    conn.setInBatch(true);
  }
  
  public int[] executeBatch(){
    SqlConnection conn = (SqlConnection)connection;
    if(!conn.isInBatch()){
      throw new DaoException(App.getProperty("nlf.exception.dao.batch_not_start"));
    }
    try{
      int[] n = conn.getStatement().executeBatch();
      conn.setInBatch(false);
      return n;
    }catch(SQLException e){
      throw new DaoException(e);
    }finally{
      IOUtil.closeQuietly(conn.getStatement());
    }
  }
}