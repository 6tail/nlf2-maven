package com.nlf.extend.web;

import com.nlf.util.InputStreamCache;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;

/**
 * 抽象WEB请求
 *
 * @author 6tail
 *
 */
public abstract class AbstractWebRequest extends com.nlf.core.AbstractRequest implements IWebRequest{
  /** 原生请求 */
  protected HttpServletRequest servletRequest;
  /** 输入流缓存 */
  protected InputStreamCache inputStreamCache;

  public HttpServletRequest getServletRequest(){
    return servletRequest;
  }

  public void setServletRequest(HttpServletRequest servletRequest){
    this.servletRequest = servletRequest;
  }

  public String getPath(){
    String includePath = (String)servletRequest.getAttribute(RequestDispatcher.INCLUDE_REQUEST_URI);
    if(null==includePath){
      return servletRequest.getServletPath();
    }
    for(String suffix:WebApp.SERVLET_SUFFIXS){
      if(includePath.endsWith(suffix)){
        includePath = includePath.substring(0,includePath.lastIndexOf(suffix));
        if(includePath.startsWith(WebApp.contextPath)){
          includePath = includePath.substring(WebApp.contextPath.length());
        }
        break;
      }
    }
    return includePath;
  }

  public InputStream getInputStream() throws IOException {
    if(null == inputStreamCache){
      inputStreamCache = new InputStreamCache(servletRequest.getInputStream());
    }
    return inputStreamCache.getInputStream();
  }
}
