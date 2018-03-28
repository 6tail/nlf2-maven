package com.nlf.extend.dao.sql.dbType.sqlserver;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import com.nlf.App;
import com.nlf.Bean;
import com.nlf.dao.exception.DaoException;
import com.nlf.dao.paging.PageData;
import com.nlf.extend.dao.sql.SqlConnection;
import com.nlf.extend.dao.sql.dbType.common.ASqlSelecter;
import com.nlf.log.Logger;

/**
 * SQL查询器的sqlserver实现
 * @author 6tail
 *
 */
public class SqlserverSelecter extends ASqlSelecter{
  public boolean support(String dbType){
    return "sqlserver".equalsIgnoreCase(dbType);
  }

  public List<Bean> top(int count){
    params.clear();
    sql = buildSql();
    sql = "SELECT TOP "+count+sql.replaceFirst("SELECT","");
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

  public int count(){
    params.clear();
    sql = buildSql();
    sql = sql.replace("\r"," ").replace("\n"," ");
    String upperSql = sql.toUpperCase();
    int orderIndex = upperSql.indexOf(" ORDER ");
    if(orderIndex>-1){
      sql = sql.substring(0,orderIndex);
    }
    sql = "SELECT COUNT(*) NLFCOUNT_ FROM ("+sql+") NLFTABLE_";
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
    if(l.size()<1){
      throw new DaoException(App.getProperty("nlf.exception.dao.select.one.not_found"));
    }
    Bean o = l.get(0);
    return o.getInt("NLFCOUNT_",0);
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
    sql = "SELECT TOP "+(d.getPageNumber()*d.getPageSize())+sql.replaceFirst("SELECT","");
    Logger.getLog().debug(buildLog());
    PreparedStatement stmt = null;
    ResultSet rs = null;
    List<Bean> l = null;
    try{
      stmt = ((SqlConnection)connection).getConnection().prepareStatement(sql);
      bindParams(stmt);
      rs = stmt.executeQuery();
      rs.absolute((d.getPageNumber()-1)*d.getPageSize());
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