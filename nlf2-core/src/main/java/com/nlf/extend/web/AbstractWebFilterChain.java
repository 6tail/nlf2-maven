package com.nlf.extend.web;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import com.nlf.core.AbstractFilterChain;
import com.nlf.core.IRequest;
import com.nlf.core.IResponse;

/**
 * 抽象WEB过滤链
 * 
 * @author 6tail
 *
 */
public abstract class AbstractWebFilterChain extends AbstractFilterChain implements IWebFilterChain{
  /** 原生过滤链 */
  protected FilterChain filterChain;

  public void setFilterChain(FilterChain filterChain){
    this.filterChain = filterChain;
  }

  public void doFilter(IRequest request,IResponse response) throws IOException{
    IWebRequest webRequest = (IWebRequest)request;
    IWebResponse webResponse = (IWebResponse)response;
    try{
      filterChain.doFilter(webRequest.getServletRequest(),webResponse.getServletResponse());
    }catch(ServletException e){
      throw new RuntimeException(e);
    }
  }
}