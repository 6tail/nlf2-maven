package com.nlf.extend.rpc.server.impl.http;

import com.sun.net.httpserver.HttpExchange;

/**
 * HttpExchange接口
 * @author 6tail
 */
public interface IHttpRpcExchange {
  String KEY_CORS_ENABLE = "nlf.rpc.server.http.cors.enable";
  String KEY_CORS_ALLOW_CREDENTIALS = "nlf.rpc.server.http.cors.allow_credentials";
  String KEY_CORS_ALLOW_ORIGIN = "nlf.rpc.server.http.cors.allow_origin";
  String KEY_CORS_ALLOW_METHODS = "nlf.rpc.server.http.cors.allow_methods";
  String KEY_CORS_ALLOW_HEADERS = "nlf.rpc.server.http.cors.allow_headers";
  String KEY_CORS_MAX_AGE = "nlf.rpc.server.http.cors.max_age";

  String CONTENT_TYPE = "Content-Type";
  String CONTENT_LENGTH = "Content-Length";
  String CONTENT_DISPOSITION = "Content-Disposition";
  String CONTENT_ENCODING = "Content-Encoding";

  String ACCEPT_ENCODING = "Accept-Encoding";
  String ACCEPT_LANGUAGE = "Accept-Language";

  String ACCESS_CONTROL_ALLOW_CREDENTIALS = "Access-Control-Allow-Credentials";
  String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
  String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";
  String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";
  String ACCESS_CONTROL_MAX_AGE = "Access-Control-Max-Age";

  String ENCODING_GZIP = "gzip";
  String ETAG = "Etag";
  String IF_NONE_MATCH = "If-None-Match";
  String LAST_MODIFIED = "Last-Modified";
  String IF_MODIFIED_SINCE = "If-Modified-Since";

  String DEFAULT_MSG_304 = "304 Not Modified";
  String DEFAULT_MSG_403 = "403 Forbidden";
  String DEFAULT_MSG_404 = "404 Not Found";
  String DEFAULT_MSG_HTML = "<!DOCTYPE html><html><head><meta charset=\"%s\" /><title>%s</title></head><body><h3>%s</h3></body></html>";

  String DEFAULT_ROOT = "";
  String DEFAULT_CHARSET = "UTF-8";
  String DEFAULT_HOME_PAGE = "index.html";
  String DEFAULT_GZIP_FILE_EXT = ".htm,.html,.css,.js,.bmp,.gif,.jpg,.jpeg,.png,.xml,.svg,.ttf";
  boolean DEFAULT_DIR_ALLOWED = true;
  boolean DEFAULT_GZIP_ENABLE = true;
  int DEFAULT_GZIP_MIN_SIZE = 10240;

  /**
   * 设置HttpExchange
   * @param exchange HttpExchange
   */
  void setHttpExchange(HttpExchange exchange);

  /**
   * 获取HttpExchange
   * @return HttpExchange
   */
  HttpExchange getHttpExchange();
}
