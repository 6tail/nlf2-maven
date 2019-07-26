package com.nlf.extend.rpc.server.impl.http;

import com.nlf.core.IHttpRequest;
import com.sun.net.httpserver.HttpExchange;

public interface IHttpRpcRequest extends IHttpRequest {
  void init();
  void setHttpExchange(HttpExchange exchange);
  HttpExchange getHttpExchange();
}
