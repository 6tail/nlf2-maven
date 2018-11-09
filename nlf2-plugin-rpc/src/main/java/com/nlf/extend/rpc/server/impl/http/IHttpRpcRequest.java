package com.nlf.extend.rpc.server.impl.http;

import com.nlf.core.IRequest;
import com.sun.net.httpserver.HttpExchange;

public interface IHttpRpcRequest extends IRequest{
  void init();
  void setHttpExchange(HttpExchange exchange);
  HttpExchange getHttpExchange();
}