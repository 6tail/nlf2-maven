package com.nlf.extend.dao.sql.type.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.nlf.dao.connection.AbstractConnectionProvider;
import com.nlf.dao.connection.IConnection;
import com.nlf.dao.exception.DaoException;
import com.nlf.extend.dao.sql.SqlConnection;

/**
 * JDBC连接提供器
 *
 * @author 6tail
 *
 */
public class JdbcConnectionProvider extends AbstractConnectionProvider{

  public IConnection getConnection(){
    JdbcSetting setting = (JdbcSetting)this.setting;
    Connection conn = null;
    try{
      conn = DriverManager.getConnection(setting.getUrl(),setting.getUser(),setting.getPassword());
    }catch(SQLException e){
      throw new DaoException(e);
    }
    SqlConnection sc = new SqlConnection(conn);
    sc.setDbSetting(setting);
    return sc;
  }

  public boolean support(String type){
    return "jdbc".equalsIgnoreCase(type);
  }

}