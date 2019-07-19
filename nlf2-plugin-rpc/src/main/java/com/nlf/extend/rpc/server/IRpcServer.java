package com.nlf.extend.rpc.server;

import java.io.IOException;

/**
 * RPC服务端接口
 *
 * @author 6tail
 */
public interface IRpcServer {

  /**
   * 绑定端口
   * @param port 端口
   * @throws IOException IOException
   */
  void bind(int port) throws IOException;

  /**
   * 是否支持
   * @param type 类型
   * @return true/false
   */
  boolean support(String type);
}
