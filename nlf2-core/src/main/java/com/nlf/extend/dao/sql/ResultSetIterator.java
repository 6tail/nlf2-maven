package com.nlf.extend.dao.sql;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Iterator;
import com.nlf.Bean;

/**
 * 结果集迭代器
 * 
 * @author 6tail
 *
 */
public class ResultSetIterator implements Iterator<Bean>{
  /** 结果集 */
  private ResultSet rs;
  /** 是否调用hasNext */
  private boolean calledHasNext;
  /** 是否还有记录 */
  private boolean hasNext;

  public ResultSetIterator(ResultSet rs){
    this.rs = rs;
  }

  public boolean hasNext(){
    if(calledHasNext){
      return hasNext;
    }
    try{
      hasNext = rs.next();
    }catch(SQLException e){
      hasNext = false;
    }finally{
      calledHasNext = true;
    }
    return hasNext;
  }

  public Bean next(){
    if(!calledHasNext){
      hasNext();
    }
    try{
      ResultSetMetaData rsmd = rs.getMetaData();
      int columnCount = rsmd.getColumnCount();
      Bean o = new Bean();
      for(int i = 0;i<columnCount;i++){
        o.set(rsmd.getColumnName(i+1),rs.getObject(i+1));
      }
      return o;
    }catch(SQLException e){
      return null;
    }finally{
      calledHasNext = false;
    }
  }

  public void remove(){
    //删除最近(最后)使用next()方法的元素，在这里木有必要
  }
}