package com.nlf.extend.dao.sql;

import com.nlf.App;
import com.nlf.dao.exception.DaoException;
import com.nlf.dao.transaction.AbstractTransaction;
import com.nlf.util.IOUtil;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * SQL事务
 *
 * @author 6tail
 *
 */
public class SqlTransaction extends AbstractTransaction{
  public void commit(){
    SqlConnection conn = (SqlConnection)connection;
    try{
      conn.getConnection().commit();
      conn.setInBatch(false);
    }catch(SQLException e){
      throw new DaoException(e);
    }
  }

  public void rollback(){
    SqlConnection conn = (SqlConnection)connection;
    try{
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

  public void cancelBatch(){
    SqlConnection conn = (SqlConnection)connection;
    if(!conn.isInBatch()){
      return;
    }
    PreparedStatement stmt = conn.getStatement();
    try{
      if(null!=stmt){
        stmt.clearBatch();
      }
    }catch(SQLException ignore){
    }finally{
      IOUtil.closeQuietly(stmt);
      conn.setInBatch(false);
    }
  }

  public int[] executeBatch(){
    SqlConnection conn = (SqlConnection)connection;
    if(!conn.isInBatch()){
      throw new DaoException(App.getProperty("nlf.exception.dao.batch_not_start"));
    }
    PreparedStatement stmt = conn.getStatement();
    int[] n = new int[0];
    try{
      if(null!=stmt) {
        n = stmt.executeBatch();
      }
    }catch(SQLException e){
      throw new DaoException(e);
    }finally{
      IOUtil.closeQuietly(stmt);
      conn.setInBatch(false);
    }
    return n;
  }
}
