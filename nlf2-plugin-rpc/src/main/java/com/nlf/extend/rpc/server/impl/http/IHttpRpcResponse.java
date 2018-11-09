package com.nlf.extend.rpc.server.impl.http;

import com.nlf.core.IResponse;
import com.sun.net.httpserver.HttpExchange;

public interface IHttpRpcResponse extends IResponse{
  void setHttpExchange(HttpExchange exchange);
  HttpExchange getHttpExchange();
}