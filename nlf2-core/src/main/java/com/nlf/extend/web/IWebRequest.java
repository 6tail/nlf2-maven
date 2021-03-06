package com.nlf.extend.web;

import javax.servlet.http.HttpServletRequest;

import com.nlf.core.IHttpRequest;

/**
 * WEB请求接口
 *
 * @author 6tail
 */
public interface IWebRequest extends IHttpRequest {
  /**
   * 设置HttpServletRequest
   * @param servletRequest HttpServletRequest
   */
  void setServletRequest(HttpServletRequest servletRequest);

  /**
   * 获取HttpServletRequest
   * @return HttpServletRequest
   */
  HttpServletRequest getServletRequest();

  /**
   * 初始化
   */
  void init();
}
