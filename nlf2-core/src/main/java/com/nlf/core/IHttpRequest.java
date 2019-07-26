package com.nlf.core;

/**
 * HTTP请求接口
 *
 * @author 6tail
 */
public interface IHttpRequest extends IRequest{
  /** multipart标识 */
  String MULTIPART_TAG = "multipart/form-data";

  /** 代理标识 */
  String[] PROXY_HEADER = {
      "X-REAL-IP",
      "X-FORWARDED-FOR",
      "PROXY-CLIENT-IP",
      "WL-PROXY-CLIENT-IP",
      "HTTP_CLIENT_IP",
      "HTTP_X_FORWARDED_FOR",
      "HTTP_X_FORWARDED",
      "HTTP_FORWARDED_FOR",
      "HTTP_FORWARDED"
  };
}
