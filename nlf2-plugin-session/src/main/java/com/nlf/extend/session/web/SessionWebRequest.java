package com.nlf.extend.session.web;

import com.nlf.extend.web.impl.DefaultWebRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * 自定义WebRequest，重新封装HttpServletRequest以替换HttpSession
 *
 * @author 6tail
 */
public class SessionWebRequest extends DefaultWebRequest {
  @Override
  public void setServletRequest(HttpServletRequest servletRequest) {
    super.setServletRequest(new SessionServletRequestWrapper(servletRequest));
  }
}
