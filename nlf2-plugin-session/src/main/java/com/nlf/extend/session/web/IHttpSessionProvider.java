package com.nlf.extend.session.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * http session 提供器
 */
public interface IHttpSessionProvider {
  /**
   * 创建http session
   * @param sessionId session id
   * @param autoCreate 是否自动创建
   * @param request HttpServletRequest
   * @return HttpSession
   * @see HttpServletRequest
   * @see HttpSession
   */
  HttpSession create(String sessionId, boolean autoCreate, HttpServletRequest request);
}