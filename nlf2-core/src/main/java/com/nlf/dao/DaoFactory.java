package com.nlf.dao;

import com.nlf.App;
import com.nlf.dao.setting.DbSettingFactory;
import java.util.HashMap;
import java.util.Map;

/**
 * 通用的Dao工厂
 * 
 * @author 6tail
 *
 */
public class DaoFactory{
  /** alias与Dao实现类的映射 */
  protected static final Map<String,String> DAOS = new HashMap<String,String>();
  /**
   * 根据别名获取Dao
   * 
   * @param alias 别名
   * @return Dao
   */
  public static IDao getDao(String alias){
    if(!DAOS.containsKey(alias)){
      com.nlf.dao.setting.IDbSetting setting = DbSettingFactory.getSetting(alias);
      java.util.List<String> impls = App.getImplements(IDao.class);
      for(String klass:impls){
        AbstractDao dao = App.getProxy().newInstance(klass);
        if(dao.support(setting.getDbType())){
          DAOS.put(alias,klass);
          dao.setAlias(alias);
          return dao;
        }
      }
      DAOS.put(alias,null);
    }else{
      String impl = DAOS.get(alias);
      if(null!=impl){
        AbstractDao dao = App.getProxy().newInstance(impl);
        dao.setAlias(alias);
        return dao;
      }
    }
    throw new com.nlf.dao.exception.DaoException(App.getProperty("nlf.exception.dao.not_found",alias));
  }

  /**
   * 获取默认DB配置的Dao
   * 
   * @return Dao
   */
  public static IDao getDao(){
    return getDao(DbSettingFactory.getDefaultSetting().getAlias());
  }

  /**
   * 获取指定Dao类型的默认DB配置的Dao
   *
   * @return Dao
   */
  public static IDao getDao(DaoType daoType){
    return getDao(DbSettingFactory.getDefaultSetting(daoType).getAlias());
  }
}