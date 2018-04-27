package com.nlf.dao.setting.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import com.nlf.App;
import com.nlf.dao.setting.IDbSetting;
import com.nlf.dao.setting.IDbSettingFileFilter;
import com.nlf.dao.setting.IDbSettingProvider;
import com.nlf.log.Logger;

/**
 * DB配置管理器
 *
 * @author 6tail
 *
 */
public class DefaultDbSettingManager implements com.nlf.dao.setting.IDbSettingManager{

  protected static IDbSettingFileFilter filter;
  protected static List<IDbSettingProvider> dbSettingProviders = new ArrayList<IDbSettingProvider>();

  static{
    init();
  }
  protected static void init(){
    filter = App.getProxy().newInstance(IDbSettingFileFilter.class.getName());
    List<String> impls = App.getImplements(IDbSettingProvider.class);
    for (String klass : impls) {
      IDbSettingProvider dsp = App.getProxy().newInstance(klass);
      dbSettingProviders.add(dsp);
    }
  }

  protected List<IDbSetting> listDbSettings(String directory){
    List<IDbSetting> l = new ArrayList<IDbSetting>();
    File dir = new File(directory,App.getProperty("nlf.dao.setting.dir"));
    if(dir.exists()) {
      File[] fs = dir.listFiles(filter);
      for (File f : fs) {
        String fileName = f.getName();
        try {
          com.nlf.Bean o = com.nlf.serialize.json.JSON.toBean(com.nlf.util.FileUtil.readAsText(f));
          String type = o.getString("type", "").toUpperCase();
          boolean support = false;
          for (IDbSettingProvider dsp : dbSettingProviders) {
            if (dsp.support(type)) {
              l.add(dsp.buildDbSetting(o));
              support = true;
              break;
            }
          }
          if(support){
            Logger.getLog().info(App.getProperty("nlf.dao.setting.provider.found",type,fileName));
          }else{
            Logger.getLog().warn(App.getProperty("nlf.dao.setting.provider.not_found",fileName,type));
          }
        } catch (Exception e) {
          throw new com.nlf.dao.exception.DaoException(App.getProperty("nlf.exception.dao.setting.format", fileName), e);
        }
      }
    }
    return l;
  }

  public List<IDbSetting> listDbSettings(){
    List<IDbSetting> l = new ArrayList<IDbSetting>();
    for(String directory:App.DIRECTORIES){
      l.addAll(listDbSettings(directory));
    }
    return l;
  }
}