package com.nlf.extend.dao.noSql;

import com.nlf.dao.AbstractDao;

/**
 * 抽象NoSqlDao
 * 
 * @author 6tail
 *
 */
public abstract class AbstractNoSqlDao extends AbstractDao implements INoSqlDao{
  public INoSqlExecuter getExecuter(){
    return (INoSqlExecuter)getExecuter(INoSqlExecuter.class.getName());
  }
}