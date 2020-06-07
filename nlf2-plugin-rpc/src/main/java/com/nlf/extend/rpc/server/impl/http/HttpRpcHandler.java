package com.nlf.extend.rpc.server.impl.http;

import com.nlf.App;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

/**
 * 静态资源处理器
 * @author 6tail
 */
public class HttpRpcHandler implements HttpHandler {

  public void handle(HttpExchange exchange) throws IOException {
    IHttpRpcResponse response = App.getProxy().newInstance(IHttpRpcResponse.class.getName());
    response.setHttpExchange(exchange);
    response.sendResource(exchange.getRequestURI().getRawPath(),false);
  }

}
