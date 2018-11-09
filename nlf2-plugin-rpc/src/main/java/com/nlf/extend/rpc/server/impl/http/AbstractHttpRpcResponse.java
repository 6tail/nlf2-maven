package com.nlf.extend.rpc.server.impl.http;

import com.nlf.core.AbstractResponse;
import com.sun.net.httpserver.HttpExchange;


/**
 * 抽象HTTP RPC响应
 * 
 * @author 6tail
 *
 */
public abstract class AbstractHttpRpcResponse extends AbstractResponse implements IHttpRpcResponse{
  protected HttpExchange exchange;

  public HttpExchange getHttpExchange() {
    return exchange;
  }

  public void setHttpExchange(HttpExchange exchange) {
    this.exchange = exchange;
  }
}