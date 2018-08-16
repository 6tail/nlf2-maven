package com.nlf.extend.dao.noSql;

import com.nlf.dao.AbstractDao;
import com.nlf.dao.DaoType;

/**
 * 抽象NoSqlDao
 * 
 * @author 6tail
 *
 */
public abstract class AbstractNoSqlDao extends AbstractDao implements INoSqlDao{

  public DaoType getType() {
    return DaoType.nosql;
  }

  public INoSqlExecuter getExecuter(){
    return (INoSqlExecuter)getExecuter(INoSqlExecuter.class.getName());
  }
}