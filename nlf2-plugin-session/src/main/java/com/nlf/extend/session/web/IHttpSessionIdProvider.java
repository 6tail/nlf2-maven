package com.nlf.extend.session.web;

import com.nlf.extend.session.ISessionIdProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * http session id 提供器接口
 */
public interface IHttpSessionIdProvider extends ISessionIdProvider {
  /**
   * 设置httpServletRequest
   * @param httpServletRequest HttpServletRequest
   */
  void setHttpServletRequest(HttpServletRequest httpServletRequest);

  /**
   * 设置HttpServletResponse
   * @param httpServletResponse HttpServletResponse
   */
  void setHttpServletResponse(HttpServletResponse httpServletResponse);
}