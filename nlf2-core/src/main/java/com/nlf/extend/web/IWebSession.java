package com.nlf.extend.web;

import javax.servlet.http.HttpSession;
import com.nlf.core.ISession;

/**
 * WEB会话接口
 * @author 6tail
 *
 */
public interface IWebSession extends ISession{
  /**
   * 获取HttpSession
   * @return
   * @see javax.servlet.http.HttpSession
   */
  HttpSession getSession();
}