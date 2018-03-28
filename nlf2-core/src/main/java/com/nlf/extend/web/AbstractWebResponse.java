package com.nlf.extend.web;

import javax.servlet.http.HttpServletResponse;
import com.nlf.core.AbstractResponse;

/**
 * 抽象WEB响应
 * 
 * @author 6tail
 *
 */
public abstract class AbstractWebResponse extends AbstractResponse implements IWebResponse{
  /** 原生响应 */
  protected HttpServletResponse servletResponse;

  public HttpServletResponse getServletResponse(){
    return servletResponse;
  }

  public void setServletResponse(HttpServletResponse servletResponse){
    this.servletResponse = servletResponse;
  }
}