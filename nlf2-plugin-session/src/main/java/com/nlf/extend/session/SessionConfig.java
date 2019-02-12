package com.nlf.extend.session;

import com.nlf.App;

/**
 * session配置
 */
public class SessionConfig {
  /** 默认保持秒数 */
  public static final int DEFAULT_MAX_INACTIVE_INTERVAL = 1800;
  /** 保持秒数 */
  public static final int MAX_INACTIVE_INTERVAL = App.getPropertyInt("session.max_inactive_interval",DEFAULT_MAX_INACTIVE_INTERVAL);
}