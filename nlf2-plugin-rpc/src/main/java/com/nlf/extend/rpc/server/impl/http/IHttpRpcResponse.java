package com.nlf.extend.rpc.server.impl.http;

import com.nlf.core.IResponse;
import com.sun.net.httpserver.HttpExchange;

/**
 * @author 6tail
 */
public interface IHttpRpcResponse extends IResponse{

  String KEY_CORS_ENABLE = "nlf.rpc.server.http.cors.enable";
  String KEY_CORS_ALLOW_CREDENTIALS = "nlf.rpc.server.http.cors.allow-credentials";
  String KEY_CORS_ALLOW_ORIGIN = "nlf.rpc.server.http.cors.allow-origin";
  String KEY_CORS_ALLOW_METHODS = "nlf.rpc.server.http.cors.allow-methods";
  String KEY_CORS_ALLOW_HEADERS = "nlf.rpc.server.http.cors.allow-headers";
  String KEY_CORS_MAX_AGE = "nlf.rpc.server.http.cors.max-age";

  void setHttpExchange(HttpExchange exchange);

  HttpExchange getHttpExchange();
}
