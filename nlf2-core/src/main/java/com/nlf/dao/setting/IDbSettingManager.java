package com.nlf.dao.setting;

/**
 * DB配置管理接口
 *
 * @author 6tail
 *
 */
public interface IDbSettingManager{

  /**
   * 获取DB配置列表
   * @return DB配置列表
   */
  java.util.List<IDbSetting> listDbSettings();
}