package com.nlf.extend.dao.setting.impl;

import com.nlf.App;
import com.nlf.Bean;
import com.nlf.dao.exception.DaoException;
import com.nlf.dao.setting.IDbSetting;
import com.nlf.dao.setting.IDbSettingManager;
import com.nlf.dao.setting.IDbSettingProvider;
import com.nlf.log.Logger;
import com.nlf.util.StringUtil;
import com.nlf.util.Strings;

import java.util.*;

/**
 * 从.properties文件自动读取db配置的插件
 *
 * @author 6tail
 */
public class PropertiesDbSettingManager implements IDbSettingManager {

  public static final String KEY_TYPE = "type";
  public static final String KEY_ALIAS = "alias";
  /**
   * 默认配置前缀
   */
  public static final String DEFAULT_SETTING_PREFIX = "db.alias";
  /**
   * 配置前缀
   */
  public static final String SETTING_PREFIX = App.getPropertyString("nlf.dao.setting.prefix", DEFAULT_SETTING_PREFIX);
  protected static final List<IDbSettingProvider> DB_SETTING_PROVIDERS = new ArrayList<IDbSettingProvider>();

  static {
    init();
  }

  protected static void init() {
    List<String> impls = App.getImplements(IDbSettingProvider.class);
    for (String klass : impls) {
      IDbSettingProvider dsp = App.getProxy().newInstance(klass);
      DB_SETTING_PROVIDERS.add(dsp);
    }
  }

  /**
   * 解析额外的参数，例如：db.alias.xxx.properties.useUnicode=true将解析为{properties:{useUnicode:true}}
   * @param config Bean
   * @param key 参数名，例如：properties.useUnicode
   * @param value 参数值
   */
  protected void buildExtra(Bean config, String key, String value) {
    Bean node = config;
    String nodeKey = key;
    List<String> keys = StringUtil.list(key, Strings.SLASH_RIGHT + Strings.DOT);
    int last = keys.size() - 1;
    for (int i = 0; i < last; i++) {
      nodeKey = keys.get(i);
      Bean child = node.getBean(nodeKey);
      if (null == child) {
        child = new Bean();
        node.set(nodeKey, child);
      }
      node = child;
    }
    node.set(keys.get(last), value);
  }

  public List<IDbSetting> listDbSettings() {
    Map<String, String> properties = new HashMap<String, String>(16);
    Map<String, Bean> settings = new HashMap<String, Bean>(16);

    for (String i18n : App.I18N) {
      ResourceBundle rb;
      try {
        rb = ResourceBundle.getBundle(i18n, Locale.getDefault());
      } catch (MissingResourceException e) {
        continue;
      }
      Enumeration<String> keys = rb.getKeys();
      while (keys.hasMoreElements()) {
        String key = keys.nextElement();
        if (key.startsWith(SETTING_PREFIX)) {
          String value = rb.getString(key);
          properties.put(key, value);
          if (key.equals(SETTING_PREFIX)) {
            for (String alias : StringUtil.list(value, Strings.COMMA)) {
              alias = alias.trim();
              if (alias.length() > 0) {
                settings.put(alias, new Bean(KEY_ALIAS, alias));
              }
            }
          }
        }
      }
    }

    for (Map.Entry<String, Bean> setting : settings.entrySet()) {
      String alias = setting.getKey();
      Bean config = setting.getValue();
      String prefix = String.format("%s.%s.", SETTING_PREFIX, alias);
      for (Map.Entry<String, String> prop : properties.entrySet()) {
        String key = prop.getKey();
        if (key.startsWith(prefix)) {
          buildExtra(config, StringUtil.right(key, prefix), prop.getValue());
        }
      }
    }

    List<IDbSetting> l = new ArrayList<IDbSetting>();
    for (Bean o : settings.values()) {
      String type = o.getString(KEY_TYPE, Strings.EMPTY).toUpperCase();
      String alias = o.getString(KEY_ALIAS, Strings.EMPTY);
      boolean support = false;
      try {
        for (IDbSettingProvider dsp : DB_SETTING_PROVIDERS) {
          if (dsp.support(type)) {
            l.add(dsp.buildDbSetting(o));
            support = true;
            break;
          }
        }
      } catch (Exception e) {
        throw new DaoException(App.getProperty("nlf.exception.dao.setting.format", alias), e);
      }
      if (support) {
        Logger.getLog().info(App.getProperty("nlf.dao.setting.provider.found", type, alias));
      } else {
        Logger.getLog().warn(App.getProperty("nlf.dao.setting.provider.not_found", alias, type));
      }
    }

    return l;
  }
}
