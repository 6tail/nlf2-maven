package com.nlf.extend.web;

import javax.servlet.http.HttpServletResponse;
import com.nlf.core.IResponse;

/**
 * WEB响应接口
 *
 * @author 6tail
 */
public interface IWebResponse extends IResponse{

  /**
   * 设置HttpServletResponse
   * @param servletResponse HttpServletResponse
   */
  void setServletResponse(HttpServletResponse servletResponse);

  /**
   * 获取HttpServletResponse
   * @return HttpServletResponse
   */
  HttpServletResponse getServletResponse();
}