package com.nlf.extend.model;

import com.nlf.Bean;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Iterator;

/**
 * 结果集迭代器
 *
 * @author 6tail
 *
 */
public class ModelIterator<M extends Model> implements Iterator<M>{
  private Model model;
  private Class<M> klass;
  /** 结果集 */
  private ResultSet rs;
  /** 是否调用hasNext */
  private boolean calledHasNext;
  /** 是否还有记录 */
  private boolean hasNext;

  public ModelIterator(Class<M> klass, Model model, ResultSet rs){
    this.klass = klass;
    this.model = model;
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

  @SuppressWarnings("unchecked")
  public M next(){
    if(!calledHasNext){
      hasNext();
    }
    try{
      ResultSetMetaData rsmd = rs.getMetaData();
      int columnCount = rsmd.getColumnCount();
      Bean o = new Bean();
      for(int i = 0;i<columnCount;i++){
        o.set(model.decode(rsmd.getColumnName(i+1)),rs.getObject(i+1));
      }
      Model m = o.toObject(klass);
      return (M)m;
    }catch(Exception e){
      return null;
    }finally{
      calledHasNext = false;
    }
  }

  public void remove(){
    //删除最近(最后)使用next()方法的元素，在这里木有必要
  }
}
