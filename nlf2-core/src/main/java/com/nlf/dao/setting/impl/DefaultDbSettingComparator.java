package com.nlf.dao.setting.impl;

import com.nlf.dao.setting.IDbSetting;

/**
 * 默认的DB配置比较器，按别名alias比较，越大的越前
 * 
 * @author 6tail
 * 
 */
public class DefaultDbSettingComparator implements java.util.Comparator<IDbSetting>{
  public int compare(IDbSetting o1,IDbSetting o2){
    return o2.getAlias().compareTo(o1.getAlias());
  }
}