package com.nlf.extend.web;

import javax.servlet.http.HttpServletResponse;
import com.nlf.core.IResponse;

public interface IWebResponse extends IResponse{
  void setServletResponse(HttpServletResponse servletResponse);
  HttpServletResponse getServletResponse();
}