package com.nlf.dao;

import java.util.HashMap;
import java.util.Map;
import com.nlf.App;
import com.nlf.dao.connection.IConnection;
import com.nlf.dao.executer.AbstractDaoExecuter;
import com.nlf.dao.executer.IDaoExecuter;

/**
 * 抽象Dao，所有Dao实现都应该继承俺
 * 
 * @author 6tail
 *
 */
public abstract class AbstractDao implements IDao{
  /** 执行器缓存，{dbType:{executerInterface:executerImpl} */
  protected static final Map<String,Map<String,String>> executers = new HashMap<String,Map<String,String>>();
  /** DB别名 */
  protected String alias;

  public String getAlias(){
    return alias;
  }

  public void setAlias(String alias){
    this.alias = alias;
  }

  /**
   * 供子类获取DB连接接口
   * @return DB连接接口
   */
  protected IConnection getConnection(){
    return com.nlf.dao.connection.ConnectionFactory.getConnection(alias);
  }

  protected IDaoExecuter getImpl(String dbType,String executerInterface){
    Map<String,String> impls = executers.get(dbType);
    if(null==impls){
      impls = new HashMap<String,String>();
      executers.put(dbType,impls);
    }
    if(!impls.containsKey(executerInterface)){
      java.util.List<String> l = App.getImplements(executerInterface);
      for(String klass:l){
        IDaoExecuter executer = App.getProxy().newInstance(klass);
        if(executer.support(dbType)){
          impls.put(executerInterface,klass);
          return executer;
        }
      }
      impls.put(executerInterface,null);
    }else{
      String impl = impls.get(executerInterface);
      if(null!=impl){
        return App.getProxy().newInstance(impl);
      }
    }
    throw new com.nlf.dao.exception.DaoException(App.getProperty("nlf.exception.dao.executer.not_found",dbType,executerInterface));
  }

  protected IDaoExecuter getExecuter(String executerInterface){
    IConnection connection = getConnection();
    String dbType = connection.getDbSetting().getDbType();
    AbstractDaoExecuter executer = (AbstractDaoExecuter)getImpl(dbType,executerInterface);
    executer.setConnection(connection);
    return executer;
  }
}