package com.nlf.extend.web;

import javax.servlet.http.HttpServletRequest;
import com.nlf.core.IRequest;

public interface IWebRequest extends IRequest{
  void setServletRequest(HttpServletRequest servletRequest);
  HttpServletRequest getServletRequest();
  void init();
}