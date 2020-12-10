package com.nlf.extend.web.impl;

import com.nlf.util.InputStreamCache;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;

/**
 * @author 6tail
 */
public class DefaultHttpServletRequest extends HttpServletRequestWrapper {
  /** 原生请求 */
  protected HttpServletRequest request;
  /** 输入流缓存 */
  protected InputStreamCache inputStreamCache;
  public DefaultHttpServletRequest(HttpServletRequest request) {
    super(request);
    this.request = request;
  }

  @Override
  public ServletInputStream getInputStream() throws IOException {
    if(null == inputStreamCache){
      inputStreamCache = new InputStreamCache(request.getInputStream());
    }
    return new DefaultServletInputStream(inputStreamCache.getInputStream());
  }

  public HttpServletRequest getHttpServletRequest(){
    return request;
  }
}
