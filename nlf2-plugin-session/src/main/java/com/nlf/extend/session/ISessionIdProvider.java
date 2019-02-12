package com.nlf.extend.session;

/**
 * session id 提供器接口
 */
public interface ISessionIdProvider {
  /**
   * 读session id
   * @return session id
   */
  String read();

  /**
   * 写session id
   * @param sessionId session id
   */
  void write(String sessionId);
}