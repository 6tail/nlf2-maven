package com.nlf.extend.dao.sql.dbType.hsqldb;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import com.nlf.Bean;
import com.nlf.dao.exception.DaoException;
import com.nlf.dao.paging.PageData;
import com.nlf.extend.dao.sql.SqlConnection;
import com.nlf.extend.dao.sql.dbType.common.ASqlTemplate;
import com.nlf.log.Logger;

/**
 * SQL模板的hsqldb实现
 * @author 6tail
 *
 */
public class HsqldbTemplate extends ASqlTemplate{

  public boolean support(String dbType){
    return "hsqldb".equalsIgnoreCase(dbType);
  }

  public List<Bean> top(int count){
    params.clear();
    sql = buildSql();
    sql = sql+" LIMIT 0,"+count;
    Logger.getLog().debug(buildLog());
    PreparedStatement stmt = null;
    ResultSet rs = null;
    try{
      stmt = ((SqlConnection)connection).getConnection().prepareStatement(sql);
      bindParams(stmt);
      rs = stmt.executeQuery();
      return toBeans(rs);
    }catch(SQLException e){
      throw new DaoException(e);
    }finally{
      finalize(stmt,rs);
    }
  }

  public PageData page(int pageNumber,int pageSize){
    PageData d = new PageData();
    d.setPageSize(pageSize);
    d.setPageNumber(pageNumber);
    d.setRecordCount(count());
    if(d.getPageNumber()>d.getPageCount()){
      return d;
    }
    params.clear();
    sql = buildSql();
    sql = "SELECT * FROM ("+sql+") NLFTABLE_ LIMIT "+((d.getPageNumber()-1)*d.getPageSize())+","+d.getPageSize();
    Logger.getLog().debug(buildLog());
    PreparedStatement stmt = null;
    ResultSet rs = null;
    List<Bean> l = null;
    try{
      stmt = ((SqlConnection)connection).getConnection().prepareStatement(sql);
      bindParams(stmt);
      rs = stmt.executeQuery();
      l = toBeans(rs);
    }catch(SQLException e){
      throw new DaoException(e);
    }finally{
      finalize(stmt,rs);
    }
    d.setData(l);
    return d;
  }
}