package com.nlf.extend.session.web;

import com.nlf.App;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

/**
 * 自定义HttpServletRequest
 */
public class SessionServletRequestWrapper extends HttpServletRequestWrapper {

  private HttpSession session;
  private HttpServletRequest request;

  public SessionServletRequestWrapper(HttpServletRequest request) {
    super(request);
    this.request = request;
  }

  public HttpSession getSession(boolean autoCreate) {
    if (null == session) {
      //替换为自定义的HttpSession
      IHttpSessionIdProvider sessionIdProvider = App.getProxy().newInstance(IHttpSessionIdProvider.class.getName());
      sessionIdProvider.setHttpServletRequest(request);
      IHttpSessionProvider sessionProvider = App.getProxy().newInstance(IHttpSessionProvider.class.getName());
      session = sessionProvider.create(sessionIdProvider.read(),autoCreate,request);
    }
    return session;
  }
}