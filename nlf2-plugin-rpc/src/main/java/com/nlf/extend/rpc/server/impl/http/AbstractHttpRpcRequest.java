package com.nlf.extend.rpc.server.impl.http;

import com.sun.net.httpserver.HttpExchange;

/**
 * 抽象HTTP RPC请求
 * 
 * @author 6tail
 *
 */
public abstract class AbstractHttpRpcRequest extends com.nlf.core.AbstractRequest implements IHttpRpcRequest{
  protected HttpExchange exchange;

  public HttpExchange getHttpExchange() {
    return exchange;
  }

  public void setHttpExchange(HttpExchange exchange) {
    this.exchange = exchange;
  }

  public String getPath(){
    return exchange.getRequestURI().getPath();
  }
}