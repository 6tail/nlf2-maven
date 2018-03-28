package com.nlf.dao.setting.impl;

/**
 * DB配置文件过滤
 *
 * @author 6tail
 *
 */
public class DefaultDbSettingFileFilter implements com.nlf.dao.setting.IDbSettingFileFilter{
  public boolean accept(java.io.File f){
    if(f.isDirectory()) return false;
    if(f.getName().startsWith(".")) return false;
    return true;
  }
}