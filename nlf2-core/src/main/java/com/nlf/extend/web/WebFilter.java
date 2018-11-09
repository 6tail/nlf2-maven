package com.nlf.extend.web;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.nlf.App;
import com.nlf.core.IDispatcher;
import com.nlf.core.Statics;

/**
 * 框架默认的web应用过滤器，你可以自定义子类并配置到web.xml以从起步阶段更改框架行为，比如替换扫描器、调度器等。
 * 
 * @author 6tail
 *
 */
public class WebFilter implements Filter{
  public void destroy(){}

  public void doFilter(ServletRequest servletRequest,ServletResponse servletResponse,FilterChain filterChain) throws IOException{
    servletRequest.setCharacterEncoding(Statics.ENCODE);
    IWebRequest request = App.getProxy().newInstance(IWebRequest.class.getName());
    IWebResponse response = App.getProxy().newInstance(IWebResponse.class.getName());
    IWebFilterChain chain = App.getProxy().newInstance(IWebFilterChain.class.getName());
    request.setServletRequest((HttpServletRequest)servletRequest);
    response.setServletResponse((HttpServletResponse)servletResponse);
    App.set(Statics.REQUEST,request);
    App.set(Statics.RESPONSE,response);
    request.init();
    chain.setFilterChain(filterChain);
    IDispatcher dispatcher = App.getProxy().newInstance(IWebDispatcher.class.getName());
    dispatcher.service(request,response,chain);
  }

  public void init(FilterConfig config){
    //web应用初始化
    WebApp.init(config.getServletContext());
    WebApp.context.setAttribute(WebStatics.CONTEXT_PATH_TAG,WebApp.contextPath);
    IDispatcher dispatcher = App.getProxy().newInstance(IWebDispatcher.class.getName());
    dispatcher.init();
    start();
  }

  /**
   * 启动完成后调用
   */
  protected void start(){}

}