package com.nlf.dao.setting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.nlf.App;
import com.nlf.dao.exception.DaoException;

/**
 * DB配置工厂
 * 
 * @author 6tail
 * 
 */
public class DbSettingFactory{

  /** 默认的DB配置排序比较器 */
  public static final Comparator<IDbSetting> DEFAULT_COMPARATOR = new com.nlf.dao.setting.impl.DefaultDbSettingComparator();
  /** DB配置映射 */
  protected static final Map<String,IDbSetting> SETTING_POOL = new HashMap<String,IDbSetting>();
  /** DB配置列表，与映射对应 */
  protected static final List<IDbSetting> SETTING_LIST = new ArrayList<IDbSetting>();
  /** DB配置排序比较器 */
  protected static Comparator<IDbSetting> comparator = DEFAULT_COMPARATOR;

  protected DbSettingFactory(){}
  static{
    init();
  }

  /**
   * 初始化
   */
  private synchronized static void init(){
    IDbSettingManager dsm = App.getProxy().newInstance(IDbSettingManager.class.getName());
    List<IDbSetting> l = dsm.listDbSettings();
    for(IDbSetting o:l){
      SETTING_POOL.put(o.getAlias(),o);
      SETTING_LIST.add(o);
    }
    if(l.size()>0){
      com.nlf.log.Logger.getLog().debug(App.getProperty("nlf.dao.setting.found",l.size(),l));
      sort();
    }
  }

  /**
   * 连接配置优先级排序
   */
  protected synchronized static void sort(){
    Collections.sort(SETTING_LIST,comparator);
  }

  /**
   * 设置DB配置排序比较器
   * @param c DB配置排序比较器
   */
  public synchronized static void setComparator(Comparator<IDbSetting> c){
    comparator = c;
  }

  /**
   * 获取已经按优先级排序的DB配置别名列表
   * @return DB配置的别名列表
   */
  public static List<String> getSettingAliasList(){
    List<String> l = new ArrayList<String>();
    for(IDbSetting setting:SETTING_LIST){
      l.add(setting.getAlias());
    }
    return l;
  }

  /**
   * 动态添加DB配置到内存中，如果alias重复，则会先移除老的配置再添加
   * @param setting DB配置
   */
  public synchronized static void addSetting(IDbSetting setting){
    String alias = setting.getAlias();
    removeSetting(alias);
    SETTING_LIST.add(setting);
    SETTING_POOL.put(alias,setting);
    sort();
  }

  /**
   * 动态移除内存中的DB配置
   * @param alias 别名
   */
  public synchronized static void removeSetting(String alias){
    if(!SETTING_POOL.containsKey(alias)) return;
    int index = -1;
    for(int i=0,j=SETTING_LIST.size();i<j;i++){
      if(SETTING_LIST.get(i).getAlias().equals(alias)){
        index = i;
        break;
      }
    }
    if(index>-1){
      SETTING_LIST.remove(index);
    }
    SETTING_POOL.remove(alias);
  }

  /**
   * 根据别名获取DB配置
   * 
   * @param alias 别名
   * @return DB配置
   */
  public static IDbSetting getSetting(String alias){
    if(SETTING_POOL.containsKey(alias)){
      return SETTING_POOL.get(alias);
    }
    throw new DaoException(App.getProperty("nlf.exception.dao.setting.not_found",alias));
  }

  /**
   * 获取总配置数量
   * 
   * @return 总配置数量
   */
  public static int size(){
    return SETTING_POOL.size();
  }

  /**
   * 获取默认DB配置，如果有多个配置文件，返回别名alias最大的配置
   * 
   * @return 默认DB配置，如果有多个配置文件，返回别名alias最大的配置
   * @throws DaoException 数据访问异常
   */
  public static IDbSetting getDefaultSetting(){
    if(SETTING_LIST.size()<1){
      throw new DaoException(App.getProperty("nlf.exception.dao.setting.default_not_found"));
    }
    return SETTING_LIST.get(0);
  }
}