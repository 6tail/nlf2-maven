package com.nlf.dao.connection;

import com.nlf.App;
import com.nlf.dao.setting.IDbSetting;

/**
 * 连接提供器父类
 * 
 * @author 6tail
 * 
 */
public abstract class AbstractConnectionProvider implements IConnectionProvider{

  /** 已注册驱动 */
  protected static final java.util.Set<String> REGISTED_DRIVERS = new java.util.HashSet<String>();
  /** 连接设置 */
  protected IDbSetting setting;

  public void setDbSetting(IDbSetting setting){
    this.setting = setting;
    String driver = setting.getDriver();
    if(null==driver) return;
    synchronized(this){
      if(REGISTED_DRIVERS.contains(driver)){
        return;
      }
      try{
        Class.forName(driver);
        REGISTED_DRIVERS.add(driver);
      }catch(ClassNotFoundException e){
        throw new com.nlf.dao.exception.DaoException(App.getProperty("nlf.exception.dao.driver.not_found",driver),e);
      }
      com.nlf.log.Logger.getLog().debug(App.getProperty("nlf.dao.driver.registed",driver));
    }
  }

}