package com.nlf.dao.connection;

import com.nlf.dao.setting.IDbSetting;

/**
 * 抽象连接
 *
 * @author 6tail
 */
public abstract class AbstractConnection implements IConnection{
  protected IDbSetting dbSetting;
  /** 是否支持批量更新 */
  protected boolean supportBatchUpdate;

  public IDbSetting getDbSetting(){
    return dbSetting;
  }

  public void setDbSetting(IDbSetting dbSetting){
    this.dbSetting = dbSetting;
  }

  public boolean supportsBatchUpdates(){
    return supportBatchUpdate;
  }
}