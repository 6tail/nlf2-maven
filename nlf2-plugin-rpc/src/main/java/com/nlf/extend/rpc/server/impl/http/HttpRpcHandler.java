package com.nlf.extend.rpc.server.impl.http;

import com.nlf.App;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

/**
 * @author 6tail
 */
public class HttpRpcHandler implements HttpHandler {

  public void handle(HttpExchange exchange) throws IOException {
    IHttpRpcResourceHandler handler = App.getProxy().newInstance(IHttpRpcResourceHandler.class.getName());
    handler.handle(exchange);
  }

}
