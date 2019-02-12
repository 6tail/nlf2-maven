package com.nlf.extend.session.web;

import com.nlf.extend.web.impl.DefaultWebRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * 自定义WebRequest，重新封装HttpServletRequest以替换HttpSession
 */
public class SessionWebRequest extends DefaultWebRequest {
  public void setServletRequest(HttpServletRequest servletRequest) {
    super.setServletRequest(new SessionServletRequestWrapper(servletRequest));
  }
}