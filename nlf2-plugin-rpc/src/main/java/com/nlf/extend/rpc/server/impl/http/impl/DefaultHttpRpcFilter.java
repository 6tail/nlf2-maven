package com.nlf.extend.rpc.server.impl.http.impl;

import com.nlf.App;
import com.nlf.core.IDispatcher;
import com.nlf.core.Statics;
import com.nlf.extend.rpc.server.impl.http.*;
import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class DefaultHttpRpcFilter extends Filter implements IHttpRpcFilter {

  public String description() {
    return null;
  }

  public void destroy() {

  }

  public void doFilter(HttpExchange exchange, Chain filterChain) throws IOException {
    IHttpRpcRequest request = App.getProxy().newInstance(IHttpRpcRequest.class.getName());
    IHttpRpcResponse response = App.getProxy().newInstance(IHttpRpcResponse.class.getName());
    IHttpRpcFilterChain chain = App.getProxy().newInstance(IHttpRpcFilterChain.class.getName());
    request.setHttpExchange(exchange);
    response.setHttpExchange(exchange);
    App.set(Statics.REQUEST,request);
    App.set(Statics.RESPONSE,response);
    request.init();
    chain.setFilterChain(filterChain);
    IDispatcher dispatcher = App.getProxy().newInstance(IHttpRpcDispatcher.class.getName());
    dispatcher.service(request,response,chain);
  }

  public void init() {
    IDispatcher dispatcher = App.getProxy().newInstance(IHttpRpcDispatcher.class.getName());
    dispatcher.init();
    start();
  }

  /**
   * 启动完成后调用
   */
  protected void start(){}
}