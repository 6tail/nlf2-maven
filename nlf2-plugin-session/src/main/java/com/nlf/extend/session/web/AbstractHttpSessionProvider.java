package com.nlf.extend.session.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 抽象的http session id提供器
 */
public abstract class AbstractHttpSessionProvider implements IHttpSessionIdProvider{
  protected HttpServletRequest request;
  protected HttpServletResponse response;

  public void setHttpServletRequest(HttpServletRequest request) {
    this.request = request;
  }

  public void setHttpServletResponse(HttpServletResponse response) {
    this.response = response;
  }
}
