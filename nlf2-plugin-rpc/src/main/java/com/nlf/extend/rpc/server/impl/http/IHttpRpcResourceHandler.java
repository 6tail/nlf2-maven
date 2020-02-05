package com.nlf.extend.rpc.server.impl.http;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

/**
 * 资源处理接口
 *
 * @author 6tail
 */
public interface IHttpRpcResourceHandler {

  /**
   * 资源处理
   *
   * @param exchange HttpExchange
   * @throws IOException IOException
   */
  void handle(HttpExchange exchange) throws IOException;
}
