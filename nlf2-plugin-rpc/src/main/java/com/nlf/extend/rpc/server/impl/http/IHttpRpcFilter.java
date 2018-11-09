package com.nlf.extend.rpc.server.impl.http;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public interface IHttpRpcFilter {
  void destroy();
  void doFilter(HttpExchange exchange,Filter.Chain filterChain) throws IOException;
  void init();
}
